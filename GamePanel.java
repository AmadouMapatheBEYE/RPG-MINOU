import java.awt.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable {

    public final int tailleTuileBase = 16;
    public final int echelle         = 3;
    public final int tailleTuile     = tailleTuileBase * echelle;

    public final int colonnesEcran = 16;
    public final int lignesEcran   = 12;
    public final int largeurEcran  = tailleTuile * colonnesEcran;
    public final int hauteurEcran  = tailleTuile * lignesEcran;

    public final int colonnesMonde = 50;
    public final int lignesMonde   = 50;

    final int FPS = 60;

    public KeyHandler         keyH                 = new KeyHandler();
    public CollisionChecker   verificateurCollision;
    public GestionnaireTuiles gestionnaireTuiles;
    public Player             joueur;
    public entity.NPC[]       npcs;
    public DialogueManager    dialogueManager;      // NOUVEAU

    Thread threadJeu;

    public GamePanel() {
        setPreferredSize(new Dimension(largeurEcran, hauteurEcran));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        addKeyListener(keyH);
        setFocusable(true);
        initialiser();
    }

    private void initialiser() {
        gestionnaireTuiles    = new GestionnaireTuiles(this);
        verificateurCollision = new CollisionChecker(this);
        joueur                = new Player(this, keyH);
        dialogueManager       = new DialogueManager(this);  // NOUVEAU

        // Minou
        npcs    = new entity.NPC[2];
        npcs[0] = new entity.NPC();
        npcs[0].nom = "Minou";
        npcs[0].mondeX = tailleTuile * 12;
        npcs[0].mondeY = tailleTuile * 10;
        npcs[0].vitesse = 1;
        npcs[0].chargerSprites("minou");
        npcs[0].dialogues = new String[]{
            "*miaou*",
            "*te regarde fixement sans ciller*",
            "*se lèche la patte avec indifférence*",
            "*ronronne doucement*",
            "*renverse mentalement ton verre*"
        };

        // PNJ test — Sara
        npcs[1] = new entity.NPC();
        npcs[1].nom = "Sara";
        npcs[1].mondeX = tailleTuile * 8;
        npcs[1].mondeY = tailleTuile * 8;
        npcs[1].vitesse = 1;
        npcs[1].chargerSprites("sara");
        npcs[1].dialogues = new String[]{
            "Oh, bonjour ! Tu es dans mon cours de maths ?",
            "Je révisais justement... tu as besoin d'aide ?",
            "N'hésite pas si tu as des questions !"
        };
    }

    public void demarrerBoucle() {
        threadJeu = new Thread(this);
        threadJeu.start();
    }

    @Override
    public void run() {
        double intervalleNS  = 1_000_000_000.0 / FPS;
        double delta         = 0;
        long   dernierTemps  = System.nanoTime();
        long   tempsActuel;

        while (threadJeu != null) {
            tempsActuel   = System.nanoTime();
            delta        += (tempsActuel - dernierTemps) / intervalleNS;
            dernierTemps  = tempsActuel;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update() {

        // Si dialogue ouvert : bloquer le joueur
        if (dialogueManager.isDialogueOuvert()) {
            dialogueManager.update();

            // Entrée → avancer le dialogue
            if (keyH.interagirPresse) {
                dialogueManager.avancer();
                keyH.resetInteragir();
            }
            return;  // ← joueur ne bouge plus pendant dialogue
        }

        // Déplacement normal
        joueur.update();

        // Entrée → chercher un NPC proche
        if (keyH.interagirPresse) {
            entity.NPC proche = dialogueManager.getNPCProche();
            if (proche != null) {
                // Kaï se tourne vers le PNJ
                int dx = proche.mondeX - joueur.mondeX;
                int dy = proche.mondeY - joueur.mondeY;
                if (Math.abs(dx) > Math.abs(dy)) {
                    joueur.direction = dx > 0 ? "droite" : "gauche";
                } else {
                    joueur.direction = dy > 0 ? "bas" : "haut";
                }
                dialogueManager.ouvrirDialogue(proche);
            }
            keyH.resetInteragir();
        }

        // Animation NPCs
        for (entity.NPC npc : npcs) {
            if (npc != null) {
                npc.compteurAnimation++;
                if (npc.compteurAnimation > 20) {
                    npc.numeroAnimation =
                        (npc.numeroAnimation == 1) ? 2 : 1;
                    npc.compteurAnimation = 0;
                }
            }
        }
    }

    private void dessinerUI(Graphics2D g2) {
        g2.setFont(new Font("Arial", Font.BOLD, 14));
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRoundRect(10, hauteurEcran - 40, 160, 28, 10, 10);
        g2.setColor(Color.WHITE);
        g2.drawString("Solevert", 20, hauteurEcran - 20);

        g2.setFont(new Font("Arial", Font.PLAIN, 11));
        g2.setColor(new Color(255, 255, 255, 180));
        g2.drawString("X:" + joueur.mondeX / tailleTuile
            + " Y:" + joueur.mondeY / tailleTuile, 10, 20);

        // Icône interaction si PNJ proche
        if (!dialogueManager.isDialogueOuvert()) {
            entity.NPC proche = dialogueManager.getNPCProche();
            if (proche != null) {
                int ex = proche.mondeX - joueur.mondeX + joueur.ecranX;
                int ey = proche.mondeY - joueur.mondeY + joueur.ecranY;

                // Bulle "!" au-dessus du PNJ
                g2.setColor(new Color(255, 230, 50));
                g2.fillOval(ex + tailleTuile/2 - 8, ey - 28, 16, 16);
                g2.setColor(Color.BLACK);
                g2.setFont(new Font("Arial", Font.BOLD, 12));
                g2.drawString("!", ex + tailleTuile/2 - 3, ey - 16);

                // Hint "Entrée pour parler"
                g2.setColor(new Color(0, 0, 0, 180));
                g2.fillRoundRect(largeurEcran/2 - 90,
                    hauteurEcran - 75, 180, 24, 8, 8);
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Arial", Font.PLAIN, 12));
                g2.drawString("[Entrée] Parler à " + proche.nom,
                    largeurEcran/2 - 82, hauteurEcran - 57);
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Activer l'antialiasing pour le texte
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // 1. Carte
        gestionnaireTuiles.draw(g2);

        // 2. NPCs
        for (entity.NPC npc : npcs) {
            if (npc != null) npc.draw(g2,
                joueur.mondeX, joueur.mondeY,
                joueur.ecranX, joueur.ecranY,
                tailleTuile, largeurEcran, hauteurEcran);
        }

        // 3. Joueur
        joueur.draw(g2);

        // 4. UI + icônes
        dessinerUI(g2);

        // 5. Dialogue (toujours par-dessus tout)
        dialogueManager.draw(g2);

        g2.dispose();
    }
}