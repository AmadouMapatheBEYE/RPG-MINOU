import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class SpriteGenerator {

    static final int W = 16;  // largeur sprite
    static final int H = 16;  // hauteur sprite

    public static void main(String[] args) throws Exception {
        creerDossiers();
        genererKai();
        genererMinou();
        genererSara();
        genererLena();
        genererYuki();
        genererMaya();
        genererEllie();
        genererNoa();
        System.out.println("✅ Tous les sprites ont été générés !");
    }

    static void creerDossiers() {
        String[] dossiers = {
            "assets/sprites/kai",
            "assets/sprites/minou",
            "assets/sprites/sara",
            "assets/sprites/lena",
            "assets/sprites/yuki",
            "assets/sprites/maya",
            "assets/sprites/ellie",
            "assets/sprites/noa"
        };
        for (String d : dossiers) new File(d).mkdirs();
    }

    // Sauvegarde une image PNG
    static void sauvegarder(BufferedImage img,
                             String dossier, String nom) throws Exception {
        ImageIO.write(img, "png",
            new File("assets/sprites/" + dossier + "/" + nom + ".png"));
    }

    // ============================================================
    // UTILITAIRES DE DESSIN
    // ============================================================

    static BufferedImage nouvelleImage() {
        return new BufferedImage(W, H, BufferedImage.TYPE_INT_ARGB);
    }

    // Dessine un personnage générique avec couleurs personnalisables
    static void dessinerPersonnage(Graphics2D g,
            Color peau, Color cheveux, Color habit, Color habit2,
            String direction, int frame) {

        // --- CORPS ---
        g.setColor(habit);
        switch (direction) {
            case "bas":
            case "haut":
                g.fillRect(5, 8, 6, 6);   // torse
                // Bras
                g.setColor(habit2);
                if (frame == 1) {
                    g.fillRect(3, 8, 2, 4);   // bras gauche avant
                    g.fillRect(11, 9, 2, 4);  // bras droit derrière
                } else {
                    g.fillRect(3, 9, 2, 4);
                    g.fillRect(11, 8, 2, 4);
                }
                break;
            case "gauche":
            case "droite":
                g.fillRect(5, 8, 5, 6);
                g.setColor(habit2);
                g.fillRect(4, 8, 2, 5);
                break;
        }

        // --- JAMBES ---
        g.setColor(habit.darker());
        switch (direction) {
            case "bas":
            case "haut":
                if (frame == 1) {
                    g.fillRect(5, 13, 3, 3);   // jambe gauche avant
                    g.fillRect(8, 13, 3, 3);   // jambe droite
                } else {
                    g.fillRect(5, 13, 3, 2);
                    g.fillRect(8, 14, 3, 2);
                }
                break;
            case "gauche":
            case "droite":
                if (frame == 1) {
                    g.fillRect(5, 13, 3, 3);
                    g.fillRect(8, 13, 2, 2);
                } else {
                    g.fillRect(5, 14, 3, 2);
                    g.fillRect(8, 13, 2, 3);
                }
                break;
        }

        // --- TÊTE ---
        g.setColor(peau);
        g.fillRect(4, 2, 8, 7);

        // --- CHEVEUX ---
        g.setColor(cheveux);
        g.fillRect(4, 2, 8, 3);   // dessus
        g.fillRect(3, 2, 2, 5);   // côté gauche
        if (direction.equals("bas") || direction.equals("gauche")
            || direction.equals("droite")) {
            g.fillRect(11, 2, 2, 4); // côté droit
        }

        // --- YEUX ---
        g.setColor(Color.BLACK);
        switch (direction) {
            case "bas":
                g.fillRect(6, 6, 2, 2);
                g.fillRect(9, 6, 2, 2);
                break;
            case "haut":
                // De dos, on ne voit pas les yeux
                break;
            case "gauche":
                g.fillRect(5, 6, 2, 2);
                break;
            case "droite":
                g.fillRect(10, 6, 2, 2);
                break;
        }

        // --- BOUCHE ---
        if (direction.equals("bas")) {
            g.setColor(new Color(180, 80, 80));
            g.fillRect(7, 8, 3, 1);
        }
    }

    // ============================================================
    // KAÏ — Bleu nuit, cheveux noirs, peau claire
    // ============================================================
    static void genererKai() throws Exception {
        Color peau    = new Color(255, 220, 185);
        Color cheveux = new Color(30, 20, 20);
        Color haut    = new Color(60, 80, 160);   // veste bleue
        Color bas     = new Color(40, 40, 80);    // pantalon sombre

        String[] dirs = {"bas", "haut", "gauche", "droite"};
        for (String dir : dirs) {
            for (int f = 1; f <= 2; f++) {
                BufferedImage img = nouvelleImage();
                Graphics2D g = img.createGraphics();
                dessinerPersonnage(g, peau, cheveux, haut, bas, dir, f);

                // Expression légèrement méfiante (sourcils)
                if (dir.equals("bas")) {
                    g.setColor(cheveux);
                    g.fillRect(5, 5, 3, 1);  // sourcil gauche froncé
                    g.fillRect(9, 5, 3, 1);  // sourcil droit froncé
                }
                g.dispose();
                sauvegarder(img, "kai", dir + "_" + f);
            }
        }
        System.out.println("  → Kaï généré");
    }

    // ============================================================
    // MINOU — Chat roux
    // ============================================================
    static void genererMinou() throws Exception {
        String[] dirs = {"bas", "haut", "gauche", "droite"};
        for (String dir : dirs) {
            for (int f = 1; f <= 2; f++) {
                BufferedImage img = nouvelleImage();
                Graphics2D g = img.createGraphics();

                Color roux   = new Color(210, 105, 30);
                Color claire = new Color(240, 180, 100);
                Color noir   = Color.BLACK;

                // Corps du chat
                g.setColor(roux);
                g.fillRect(3, 7, 10, 7);   // corps

                // Tête
                g.fillRect(4, 2, 8, 7);

                // Oreilles
                g.fillRect(3, 1, 3, 3);
                g.fillRect(10, 1, 3, 3);
                g.setColor(new Color(255, 180, 180));
                g.fillRect(4, 2, 2, 2);
                g.fillRect(11, 2, 2, 2);

                // Ventre
                g.setColor(claire);
                g.fillRect(5, 9, 6, 4);

                // Pattes (animation)
                g.setColor(roux);
                if (f == 1) {
                    g.fillRect(3, 13, 3, 3);
                    g.fillRect(10, 13, 3, 3);
                } else {
                    g.fillRect(3, 14, 3, 2);
                    g.fillRect(10, 12, 3, 2);
                }

                // Queue
                g.setColor(roux);
                if (dir.equals("droite") || dir.equals("bas")) {
                    g.fillRect(1, 8, 2, 6);
                    g.fillRect(1, 7, 2, 2);
                } else {
                    g.fillRect(13, 8, 2, 6);
                    g.fillRect(13, 7, 2, 2);
                }

                // Visage
                if (dir.equals("bas") || dir.equals("droite")
                    || dir.equals("gauche")) {
                    // Yeux
                    g.setColor(new Color(50, 200, 50));
                    g.fillRect(5, 5, 2, 2);
                    g.fillRect(9, 5, 2, 2);
                    g.setColor(noir);
                    g.fillRect(6, 5, 1, 2);
                    g.fillRect(10, 5, 1, 2);
                    // Nez
                    g.setColor(new Color(255, 150, 150));
                    g.fillRect(7, 7, 2, 1);
                    // Moustaches
                    g.setColor(new Color(200, 200, 200));
                    g.drawLine(2, 7, 5, 7);
                    g.drawLine(11, 7, 14, 7);
                }

                g.dispose();
                sauvegarder(img, "minou", dir + "_" + f);
            }
        }
        System.out.println("  → Minou généré");
    }

    // ============================================================
    // SARA — Cheveux noirs lisses, uniforme scolaire
    // ============================================================
    static void genererSara() throws Exception {
        Color peau    = new Color(255, 220, 185);
        Color cheveux = new Color(20, 15, 15);
        Color haut    = new Color(255, 255, 255);  // chemise blanche
        Color bas     = new Color(80, 40, 100);    // jupe violette

        String[] dirs = {"bas", "haut", "gauche", "droite"};
        for (String dir : dirs) {
            for (int f = 1; f <= 2; f++) {
                BufferedImage img = nouvelleImage();
                Graphics2D g = img.createGraphics();
                dessinerPersonnage(g, peau, cheveux, haut, bas, dir, f);

                // Cheveux longs de Sara
                g.setColor(cheveux);
                g.fillRect(3, 5, 2, 8);    // mèche gauche
                g.fillRect(11, 5, 2, 8);   // mèche droite

                // Lunettes
                if (dir.equals("bas")) {
                    g.setColor(new Color(100, 80, 200));
                    g.drawRect(5, 5, 3, 2);
                    g.drawRect(9, 5, 3, 2);
                    g.drawLine(8, 6, 9, 6);  // pont
                }

                g.dispose();
                sauvegarder(img, "sara", dir + "_" + f);
            }
        }
        System.out.println("  → Sara générée");
    }

    // ============================================================
    // LÉNA — Cheveux roux courts, veste de rock
    // ============================================================
    static void genererLena() throws Exception {
        Color peau    = new Color(255, 210, 170);
        Color cheveux = new Color(200, 60, 20);   // roux vif
        Color haut    = new Color(30, 30, 30);    // veste noire
        Color bas     = new Color(50, 50, 80);    // jean sombre

        String[] dirs = {"bas", "haut", "gauche", "droite"};
        for (String dir : dirs) {
            for (int f = 1; f <= 2; f++) {
                BufferedImage img = nouvelleImage();
                Graphics2D g = img.createGraphics();
                dessinerPersonnage(g, peau, cheveux, haut, bas, dir, f);

                // Cheveux courts ébouriffés
                g.setColor(cheveux);
                g.fillRect(4, 1, 8, 2);
                g.fillRect(3, 2, 2, 3);
                g.fillRect(12, 2, 2, 2);
                // Mèche rebelle
                g.fillRect(5, 0, 3, 2);
                g.fillRect(9, 1, 2, 2);

                // Détail veste (clous)
                if (dir.equals("bas") || dir.equals("droite")) {
                    g.setColor(new Color(200, 200, 200));
                    g.fillRect(6, 9, 1, 1);
                    g.fillRect(8, 10, 1, 1);
                }

                g.dispose();
                sauvegarder(img, "lena", dir + "_" + f);
            }
        }
        System.out.println("  → Léna générée");
    }

    // ============================================================
    // YUKI — Cheveux noirs avec frange, tenue colorée
    // ============================================================
    static void genererYuki() throws Exception {
        Color peau    = new Color(255, 230, 200);
        Color cheveux = new Color(15, 10, 10);
        Color haut    = new Color(220, 100, 130);  // rose vif
        Color bas     = new Color(240, 240, 255);  // blanc cassé

        String[] dirs = {"bas", "haut", "gauche", "droite"};
        for (String dir : dirs) {
            for (int f = 1; f <= 2; f++) {
                BufferedImage img = nouvelleImage();
                Graphics2D g = img.createGraphics();
                dessinerPersonnage(g, peau, cheveux, haut, bas, dir, f);

                // Frange droite caractéristique
                g.setColor(cheveux);
                g.fillRect(4, 4, 8, 2);   // frange épaisse
                g.fillRect(3, 2, 2, 6);   // côté gauche long
                g.fillRect(11, 2, 2, 6);  // côté droit long

                // Accessoire : petit nœud rose dans les cheveux
                g.setColor(new Color(255, 150, 180));
                g.fillRect(11, 2, 3, 2);

                // Yeux en amande (style anime)
                if (dir.equals("bas")) {
                    g.setColor(new Color(80, 40, 120));
                    g.fillRect(6, 6, 2, 2);
                    g.fillRect(9, 6, 2, 2);
                    // Reflets
                    g.setColor(Color.WHITE);
                    g.fillRect(6, 6, 1, 1);
                    g.fillRect(9, 6, 1, 1);
                }

                g.dispose();
                sauvegarder(img, "yuki", dir + "_" + f);
            }
        }
        System.out.println("  → Yuki générée");
    }

    // ============================================================
    // MAYA — Cheveux tressés, tenue sportive
    // ============================================================
    static void genererMaya() throws Exception {
        Color peau    = new Color(160, 100, 60);   // peau foncée
        Color cheveux = new Color(20, 10, 5);
        Color haut    = new Color(200, 50, 50);    // rouge sport
        Color bas     = new Color(30, 30, 30);     // short noir

        String[] dirs = {"bas", "haut", "gauche", "droite"};
        for (String dir : dirs) {
            for (int f = 1; f <= 2; f++) {
                BufferedImage img = nouvelleImage();
                Graphics2D g = img.createGraphics();
                dessinerPersonnage(g, peau, cheveux, haut, bas, dir, f);

                // Tresse épaisse
                g.setColor(cheveux);
                g.fillRect(3, 2, 2, 10);   // tresse gauche
                // Bandes sportives sur la veste
                g.setColor(Color.WHITE);
                g.fillRect(5, 9, 1, 4);
                g.fillRect(10, 9, 1, 4);

                // Expression déterminée
                if (dir.equals("bas")) {
                    g.setColor(cheveux);
                    g.fillRect(5, 5, 3, 1);  // sourcil gauche épais
                    g.fillRect(9, 5, 3, 1);
                }

                g.dispose();
                sauvegarder(img, "maya", dir + "_" + f);
            }
        }
        System.out.println("  → Maya générée");
    }

    // ============================================================
    // ELLIE — Cheveux blonds, tenue douce et effacée
    // ============================================================
    static void genererEllie() throws Exception {
        Color peau    = new Color(255, 230, 210);
        Color cheveux = new Color(220, 190, 80);   // blond doux
        Color haut    = new Color(180, 210, 200);  // vert sauge
        Color bas     = new Color(200, 180, 160);  // beige

        String[] dirs = {"bas", "haut", "gauche", "droite"};
        for (String dir : dirs) {
            for (int f = 1; f <= 2; f++) {
                BufferedImage img = nouvelleImage();
                Graphics2D g = img.createGraphics();
                dessinerPersonnage(g, peau, cheveux, haut, bas, dir, f);

                // Cheveux longs et doux
                g.setColor(cheveux);
                g.fillRect(3, 3, 2, 10);
                g.fillRect(11, 3, 2, 10);
                g.fillRect(4, 14, 2, 2);   // petites mèches

                // Carnet de croquis (accessoire visible de côté)
                if (dir.equals("droite") || dir.equals("bas")) {
                    g.setColor(new Color(240, 240, 200));
                    g.fillRect(11, 9, 3, 4);
                    g.setColor(new Color(150, 150, 120));
                    g.drawRect(11, 9, 3, 4);
                }

                // Expression timide (yeux baissés)
                if (dir.equals("bas")) {
                    g.setColor(new Color(100, 150, 200));
                    g.fillRect(6, 7, 2, 1);  // yeux mi-clos
                    g.fillRect(9, 7, 2, 1);
                }

                g.dispose();
                sauvegarder(img, "ellie", dir + "_" + f);
            }
        }
        System.out.println("  → Ellie générée");
    }

    // ============================================================
    // NOA — Cheveux blancs/argentés, tenue sombre mystérieuse
    // ============================================================
    static void genererNoa() throws Exception {
        Color peau    = new Color(230, 215, 215);  // peau très claire
        Color cheveux = new Color(200, 200, 220);  // argenté
        Color haut    = new Color(40, 30, 60);     // violet très sombre
        Color bas     = new Color(20, 20, 30);     // noir

        String[] dirs = {"bas", "haut", "gauche", "droite"};
        for (String dir : dirs) {
            for (int f = 1; f <= 2; f++) {
                BufferedImage img = nouvelleImage();
                Graphics2D g = img.createGraphics();
                dessinerPersonnage(g, peau, cheveux, haut, bas, dir, f);

                // Cheveux longs argentés avec mèche devant
                g.setColor(cheveux);
                g.fillRect(3, 2, 2, 12);
                g.fillRect(11, 2, 2, 12);
                // Mèche qui cache un œil
                g.fillRect(4, 4, 5, 3);

                // Yeux violets intenses
                if (dir.equals("bas")) {
                    g.setColor(new Color(150, 80, 200));
                    g.fillRect(9, 6, 2, 2);  // un seul œil visible
                    g.setColor(Color.WHITE);
                    g.fillRect(9, 6, 1, 1);
                }

                // Détail mystérieux : étoile sur la veste
                g.setColor(new Color(150, 120, 200));
                g.fillRect(8, 10, 1, 3);
                g.fillRect(7, 11, 3, 1);

                g.dispose();
                sauvegarder(img, "noa", dir + "_" + f);
            }
        }
        System.out.println("  → Noa générée");
    }
}