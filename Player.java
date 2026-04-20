import entity.Entity;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

public class Player extends Entity {

    // Référence au gamePanel et au clavier
    private GamePanel gp;
    private KeyHandler keyH;

    // Position FIXE du joueur à l'écran (toujours au centre)
    public final int ecranX;
    public final int ecranY;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp   = gp;
        this.keyH = keyH;

        // Le joueur est toujours affiché au centre de l'écran
        ecranX = gp.largeurEcran / 2 - gp.tailleTuile / 2;
        ecranY = gp.hauteurEcran / 2 - gp.tailleTuile / 2;

        // Rectangle de collision (centré, un peu plus petit que la tuile)
        collision = new Rectangle(8, 16, 32, 32);

        reinitialiser();
        chargerImages();
    }

    public void reinitialiser() {
        // Position de départ dans le monde
        mondeX = gp.tailleTuile * 10;
        mondeY = gp.tailleTuile * 10;
        vitesse = 4;
        direction = "bas";
    }

    private void chargerImages() {
        // Pour l'instant : rectangles colorés
        // On remplacera par de vrais sprites à l'étape 4
        bas1    = creerSpritePlaceholder(new Color(100, 149, 237));
        bas2    = creerSpritePlaceholder(new Color(90,  139, 227));
        haut1   = creerSpritePlaceholder(new Color(80,  129, 217));
        haut2   = creerSpritePlaceholder(new Color(70,  119, 207));
        gauche1 = creerSpritePlaceholder(new Color(60,  109, 197));
        gauche2 = creerSpritePlaceholder(new Color(50,   99, 187));
        droite1 = creerSpritePlaceholder(new Color(40,   89, 177));
        droite2 = creerSpritePlaceholder(new Color(30,   79, 167));
    }

    // Crée une image colorée temporaire (placeholder)
    private BufferedImage creerSpritePlaceholder(Color couleur) {
        BufferedImage img = new BufferedImage(
            gp.tailleTuile, gp.tailleTuile,
            BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();

        // Corps
        g2.setColor(couleur);
        g2.fillRect(8, 0, 32, 48);

        // Tête
        g2.setColor(new Color(255, 220, 185));
        g2.fillOval(12, 0, 24, 24);

        // Yeux
        g2.setColor(Color.BLACK);
        g2.fillOval(17, 8, 4, 4);
        g2.fillOval(27, 8, 4, 4);

        g2.dispose();
        return img;
    }

    public void update() {
        boolean enMouvement = false;

        if (keyH.haut)   { direction = "haut";   enMouvement = true; }
        if (keyH.bas)    { direction = "bas";    enMouvement = true; }
        if (keyH.gauche) { direction = "gauche"; enMouvement = true; }
        if (keyH.droite) { direction = "droite"; enMouvement = true; }

        if (enMouvement) {

            // Vérifier les collisions AVANT de bouger
            collisionActive = false;
            gp.verificateurCollision.verifier(this);

            if (!collisionActive) {
                switch (direction) {
                    case "haut":   mondeY -= vitesse; break;
                    case "bas":    mondeY += vitesse; break;
                    case "gauche": mondeX -= vitesse; break;
                    case "droite": mondeX += vitesse; break;
                }
            }

            // Animation de marche
            compteurAnimation++;
            if (compteurAnimation > 12) {
                numeroAnimation = (numeroAnimation == 1) ? 2 : 1;
                compteurAnimation = 0;
            }
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage imgActuelle = null;

        switch (direction) {
            case "haut":   imgActuelle = (numeroAnimation==1) ? haut1   : haut2;   break;
            case "bas":    imgActuelle = (numeroAnimation==1) ? bas1    : bas2;    break;
            case "gauche": imgActuelle = (numeroAnimation==1) ? gauche1 : gauche2; break;
            case "droite": imgActuelle = (numeroAnimation==1) ? droite1 : droite2; break;
        }

        // Le joueur est TOUJOURS dessiné au centre de l'écran
        g2.drawImage(imgActuelle, ecranX, ecranY,
            gp.tailleTuile, gp.tailleTuile, null);

        // DEBUG : affiche la hitbox (on enlèvera plus tard)
        g2.setColor(Color.RED);
        g2.drawRect(
            ecranX + collision.x,
            ecranY + collision.y,
            collision.width,
            collision.height
        );
    }
}