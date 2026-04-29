package entity;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class NPC extends Entity {

    public String[] dialogues;
    public int indexDialogue     = 0;
    public int compteurMouvement = 0;
    public int limiteAction      = 0;

    public NPC() {}

    public void chargerSprites(String nomPerso) {
        try {
            String base = "assets/sprites/" + nomPerso + "/";
            bas1    = ImageIO.read(new File(base + "bas_1.png"));
            bas2    = ImageIO.read(new File(base + "bas_2.png"));
            haut1   = ImageIO.read(new File(base + "haut_1.png"));
            haut2   = ImageIO.read(new File(base + "haut_2.png"));
            gauche1 = ImageIO.read(new File(base + "gauche_1.png"));
            gauche2 = ImageIO.read(new File(base + "gauche_2.png"));
            droite1 = ImageIO.read(new File(base + "droite_1.png"));
            droite2 = ImageIO.read(new File(base + "droite_2.png"));
            System.out.println("Sprites " + nomPerso + " charges");
        } catch (Exception e) {
            System.out.println("Sprites " + nomPerso + " manquants.");
        }
    }

    public BufferedImage getImageActuelle() {
        switch (direction) {
            case "haut":   return (numeroAnimation == 1) ? haut1   : haut2;
            case "bas":    return (numeroAnimation == 1) ? bas1    : bas2;
            case "gauche": return (numeroAnimation == 1) ? gauche1 : gauche2;
            case "droite": return (numeroAnimation == 1) ? droite1 : droite2;
            default:       return bas1;
        }
    }

    public void draw(Graphics2D g2,
                     int joueurMondeX, int joueurMondeY,
                     int joueurEcranX, int joueurEcranY,
                     int tailleTuile,
                     int largeurEcran, int hauteurEcran) {

        int ecranX = mondeX - joueurMondeX + joueurEcranX;
        int ecranY = mondeY - joueurMondeY + joueurEcranY;

        if (ecranX + tailleTuile > 0 &&
            ecranX < largeurEcran   &&
            ecranY + tailleTuile > 0 &&
            ecranY < hauteurEcran) {

            BufferedImage img = getImageActuelle();
            if (img != null) {
                g2.drawImage(img, ecranX, ecranY,
                    tailleTuile, tailleTuile, null);
            }

            if (nom != null && !nom.isEmpty()) {
                g2.setFont(new Font("Arial", Font.BOLD, 10));
                FontMetrics fm = g2.getFontMetrics();
                int largeurNom = fm.stringWidth(nom);
                int xNom = ecranX + tailleTuile / 2 - largeurNom / 2;
                int yNom = ecranY - 4;

                g2.setColor(new Color(0, 0, 0, 160));
                g2.fillRoundRect(xNom - 3, yNom - 11,
                    largeurNom + 6, 14, 5, 5);
                g2.setColor(Color.WHITE);
                g2.drawString(nom, xNom, yNom);
            }
        }
    }

    public String getDialogueSuivant() {
        if (dialogues == null || dialogues.length == 0) return "...";
        String d = dialogues[indexDialogue];
        indexDialogue = (indexDialogue + 1) % dialogues.length;
        return d;
    }
}