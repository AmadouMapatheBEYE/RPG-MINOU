import java.awt.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable {

    // Paramètres d'affichage
    public final int tailleTuileBase = 16;   // 16x16 pixels (pixel art)
    public final int echelle         = 3;    // zoom x3
    public final int tailleTuile     = tailleTuileBase * echelle; // = 48px

    // Taille de l'écran (en tuiles)
    public final int colonnesEcran = 16;
    public final int lignesEcran   = 12;
    public final int largeurEcran  = tailleTuile * colonnesEcran; // 768px
    public final int hauteurEcran  = tailleTuile * lignesEcran;   // 576px

    // Taille du monde (en tuiles)
    public final int colonnesMonde = 50;
    public final int lignesMonde   = 50;

    // FPS cible
    final int FPS = 60;

    // Composants du jeu
    public KeyHandler       keyH                = new KeyHandler();
    public CollisionChecker verificateurCollision;
    public GestionnaireTuiles gestionnaireTuiles;
    public Player           joueur;

    Thread threadJeu;

    public GamePanel() {
        setPreferredSize(new Dimension(largeurEcran, hauteurEcran));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);   // évite le scintillement
        addKeyListener(keyH);
        setFocusable(true);

        initialiser();
    }

    private void initialiser() {
        gestionnaireTuiles    = new GestionnaireTuiles(this);
        verificateurCollision = new CollisionChecker(this);
        joueur                = new Player(this, keyH);
    }

    public void demarrerBoucle() {
        threadJeu = new Thread(this);
        threadJeu.start();
    }

    // === LA BOUCLE DE JEU ===
    @Override
    public void run() {

        double intervalleNS    = 1_000_000_000.0 / FPS; // nanosecondes par frame
        double delta           = 0;
        long   dernierTemps    = System.nanoTime();
        long   tempsActuel;

        while (threadJeu != null) {

            tempsActuel = System.nanoTime();
            delta      += (tempsActuel - dernierTemps) / intervalleNS;
            dernierTemps = tempsActuel;

            if (delta >= 1) {
                update();    // mise à jour logique
                repaint();   // redessine l'écran
                delta--;
            }
        }
    }

    // Mise à jour de la logique
    public void update() {
        joueur.update();
    }

    private void dessinerUI(Graphics2D g2) {
    // Nom de la zone en bas à gauche
    g2.setFont(new Font("Arial", Font.BOLD, 14));
    g2.setColor(new Color(0, 0, 0, 150));
    g2.fillRoundRect(10, hauteurEcran - 40, 200, 28, 10, 10);
    g2.setColor(Color.WHITE);
    g2.drawString("🏙️ Solevert", 20, hauteurEcran - 20);

    // Coordonnées debug (à enlever plus tard)
    g2.setFont(new Font("Arial", Font.PLAIN, 11));
    g2.setColor(new Color(255, 255, 255, 180));
    g2.drawString("X:" + joueur.mondeX/tailleTuile
        + " Y:" + joueur.mondeY/tailleTuile, 10, 20);
}

    // Dessin de tout l'écran
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // 1. Dessiner la carte
        gestionnaireTuiles.draw(g2);

        // 2. Dessiner le joueur par-dessus
        joueur.draw(g2);
        // Afficher le nom de la zone actuelle
        dessinerUI(g2);

        g2.dispose();
    }
}