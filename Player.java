import entity.Entity;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class Player extends Entity {

    private GamePanel  gp;
    private KeyHandler keyH;

    public final int ecranX;
    public final int ecranY;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp   = gp;
        this.keyH = keyH;

        ecranX = gp.largeurEcran / 2 - gp.tailleTuile / 2;
        ecranY = gp.hauteurEcran / 2 - gp.tailleTuile / 2;

        collision = new Rectangle(8, 16, 32, 32);
        reinitialiser();
        chargerImages();
    }

    public void reinitialiser() {
        mondeX    = gp.tailleTuile * 10;
        mondeY    = gp.tailleTuile * 10;
        vitesse   = 4;
        direction = "bas";
    }

    private void chargerImages() {
        try {
            bas1    = chargerSprite("kai", "bas_1");
            bas2    = chargerSprite("kai", "bas_2");
            haut1   = chargerSprite("kai", "haut_1");
            haut2   = chargerSprite("kai", "haut_2");
            gauche1 = chargerSprite("kai", "gauche_1");
            gauche2 = chargerSprite("kai", "gauche_2");
            droite1 = chargerSprite("kai", "droite_1");
            droite2 = chargerSprite("kai", "droite_2");
            System.out.println("Sprites Kai charges");
        } catch (Exception e) {
            System.out.println("Sprites non trouves, placeholders.");
            chargerPlaceholders();
        }
    }

    private BufferedImage chargerSprite(String perso, String nom) throws Exception {
        return ImageIO.read(new File("assets/sprites/" + perso + "/" + nom + ".png"));
    }

    private void chargerPlaceholders() {
        BufferedImage ph = creerPlaceholder(new Color(100, 149, 237));
        bas1 = bas2 = haut1 = haut2 = gauche1 = gauche2 = droite1 = droite2 = ph;
    }

    private BufferedImage creerPlaceholder(Color couleur) {
        BufferedImage img = new BufferedImage(
            gp.tailleTuile, gp.tailleTuile, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setColor(couleur);         g2.fillRect(8, 0, 32, 48);
        g2.setColor(new Color(255,220,185)); g2.fillOval(12, 0, 24, 24);
        g2.setColor(Color.BLACK);
        g2.fillOval(17,8,4,4); g2.fillOval(27,8,4,4);
        g2.dispose(); return img;
    }

    public void update() {
        boolean enMouvement = false;

        if (keyH.haut)   { direction = "haut";   enMouvement = true; }
        if (keyH.bas)    { direction = "bas";     enMouvement = true; }
        if (keyH.gauche) { direction = "gauche";  enMouvement = true; }
        if (keyH.droite) { direction = "droite";  enMouvement = true; }

        if (enMouvement) {
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
            compteurAnimation++;
            if (compteurAnimation > 12) {
                numeroAnimation = (numeroAnimation == 1) ? 2 : 1;
                compteurAnimation = 0;
            }
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage img = null;
        switch (direction) {
            case "haut":   img = (numeroAnimation==1) ? haut1   : haut2;   break;
            case "bas":    img = (numeroAnimation==1) ? bas1    : bas2;    break;
            case "gauche": img = (numeroAnimation==1) ? gauche1 : gauche2; break;
            case "droite": img = (numeroAnimation==1) ? droite1 : droite2; break;
        }
        if (img != null)
            g2.drawImage(img, ecranX, ecranY, gp.tailleTuile, gp.tailleTuile, null);
    }
}