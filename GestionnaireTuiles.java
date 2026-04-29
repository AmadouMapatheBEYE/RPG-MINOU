import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.Scanner;

public class GestionnaireTuiles {

    private GamePanel gp;
    public Tuile[] tuiles;
    public int[][] carte;

    public static final int HERBE    = 0;
    public static final int MUR      = 1;
    public static final int EAU      = 2;
    public static final int ROUTE    = 3;
    public static final int TROTTOIR = 4;
    public static final int SABLE    = 5;
    public static final int PORTE    = 6;
    public static final int ARBRE    = 7;
    public static final int PONT     = 8;
    public static final int FLEUR    = 9;

    public GestionnaireTuiles(GamePanel gp) {
        this.gp = gp;
        tuiles  = new Tuile[20];
        carte   = new int[gp.lignesMonde][gp.colonnesMonde];
        creerTuiles();
        chargerCarte("assets/cartes/solevert.txt");
    }

    private void creerTuiles() {
        tuiles[HERBE]        = new Tuile(); tuiles[HERBE].image    = creerTuileHerbe();    tuiles[HERBE].solide    = false;
        tuiles[MUR]          = new Tuile(); tuiles[MUR].image      = creerTuileMur();      tuiles[MUR].solide      = true;
        tuiles[EAU]          = new Tuile(); tuiles[EAU].image      = creerTuileEau();      tuiles[EAU].solide      = true;
        tuiles[ROUTE]        = new Tuile(); tuiles[ROUTE].image    = creerTuileRoute();    tuiles[ROUTE].solide    = false;
        tuiles[TROTTOIR]     = new Tuile(); tuiles[TROTTOIR].image = creerTuileTrottoir(); tuiles[TROTTOIR].solide = false;
        tuiles[SABLE]        = new Tuile(); tuiles[SABLE].image    = creerTuileSable();    tuiles[SABLE].solide    = false;
        tuiles[PORTE]        = new Tuile(); tuiles[PORTE].image    = creerTuilePorte();    tuiles[PORTE].solide    = false;
        tuiles[ARBRE]        = new Tuile(); tuiles[ARBRE].image    = creerTuileArbre();    tuiles[ARBRE].solide    = true;
        tuiles[PONT]         = new Tuile(); tuiles[PONT].image     = creerTuilePont();     tuiles[PONT].solide     = false;
        tuiles[FLEUR]        = new Tuile(); tuiles[FLEUR].image    = creerTuileFleur();    tuiles[FLEUR].solide    = false;
    }

    private BufferedImage creerTuileHerbe() {
        BufferedImage img = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        g.setColor(new Color(34, 139, 34)); g.fillRect(0, 0, 16, 16);
        g.setColor(new Color(50, 160, 50));
        g.fillRect(2,3,1,2); g.fillRect(5,1,1,3); g.fillRect(9,4,1,2); g.fillRect(13,2,1,3);
        g.dispose(); return img;
    }

    private BufferedImage creerTuileMur() {
        BufferedImage img = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        g.setColor(new Color(120, 120, 130)); g.fillRect(0, 0, 16, 16);
        g.setColor(new Color(90, 90, 100));
        g.drawRect(0,0,7,7); g.drawRect(8,0,7,7); g.drawRect(0,8,15,7);
        g.setColor(new Color(150, 150, 160));
        g.fillRect(1,1,6,6); g.fillRect(9,1,6,6); g.fillRect(1,9,14,6);
        g.dispose(); return img;
    }

    private BufferedImage creerTuileEau() {
        BufferedImage img = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        g.setColor(new Color(64, 164, 223)); g.fillRect(0, 0, 16, 16);
        g.setColor(new Color(100, 190, 240));
        g.drawArc(0,5,8,4,0,180); g.drawArc(8,5,8,4,0,180);
        g.drawArc(0,11,8,4,0,180); g.drawArc(8,11,8,4,0,180);
        g.dispose(); return img;
    }

    private BufferedImage creerTuileRoute() {
        BufferedImage img = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        g.setColor(new Color(100, 100, 110)); g.fillRect(0, 0, 16, 16);
        g.setColor(new Color(120, 120, 130));
        g.fillRect(3,3,2,2); g.fillRect(10,7,2,2); g.fillRect(6,12,2,2);
        g.dispose(); return img;
    }

    private BufferedImage creerTuileTrottoir() {
        BufferedImage img = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        g.setColor(new Color(200, 195, 185)); g.fillRect(0, 0, 16, 16);
        g.setColor(new Color(180, 175, 165));
        g.drawLine(0,8,16,8); g.drawLine(8,0,8,8);
        g.dispose(); return img;
    }

    private BufferedImage creerTuileSable() {
        BufferedImage img = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        g.setColor(new Color(194, 178, 128)); g.fillRect(0, 0, 16, 16);
        g.setColor(new Color(210, 195, 145));
        g.fillRect(4,4,2,2); g.fillRect(11,9,2,2); g.fillRect(7,13,2,2);
        g.dispose(); return img;
    }

    private BufferedImage creerTuilePorte() {
        BufferedImage img = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        g.setColor(new Color(160, 120, 80)); g.fillRect(0, 0, 16, 16);
        g.setColor(new Color(100, 70, 40)); g.fillRect(3, 0, 10, 14);
        g.setColor(new Color(200, 160, 100)); g.fillRect(4, 1, 8, 12);
        g.setColor(new Color(255, 200, 50)); g.fillRect(10, 7, 2, 2);
        g.dispose(); return img;
    }

    private BufferedImage creerTuileArbre() {
        BufferedImage img = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        g.setColor(new Color(34, 139, 34)); g.fillRect(0, 0, 16, 16);
        g.setColor(new Color(101, 67, 33)); g.fillRect(6, 10, 4, 6);
        g.setColor(new Color(0, 120, 0)); g.fillOval(2, 0, 12, 12);
        g.setColor(new Color(0, 160, 0)); g.fillOval(4, 1, 8, 8);
        g.dispose(); return img;
    }

    private BufferedImage creerTuilePont() {
        BufferedImage img = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        g.setColor(new Color(64, 164, 223)); g.fillRect(0, 0, 16, 16);
        g.setColor(new Color(139, 90, 43)); g.fillRect(0, 4, 16, 8);
        g.setColor(new Color(160, 110, 60));
        g.fillRect(0,5,16,2); g.fillRect(0,9,16,2);
        g.setColor(new Color(100, 60, 20));
        g.fillRect(3,6,1,1); g.fillRect(8,6,1,1); g.fillRect(13,6,1,1);
        g.dispose(); return img;
    }

    private BufferedImage creerTuileFleur() {
        BufferedImage img = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        g.setColor(new Color(34, 139, 34)); g.fillRect(0, 0, 16, 16);
        g.setColor(new Color(0, 100, 0)); g.fillRect(7, 9, 2, 7);
        g.setColor(new Color(255, 150, 200));
        g.fillOval(5,3,4,4); g.fillOval(9,3,4,4);
        g.fillOval(5,7,4,4); g.fillOval(9,7,4,4);
        g.setColor(new Color(255, 230, 50)); g.fillOval(6, 5, 4, 4);
        g.dispose(); return img;
    }

    public void chargerCarte(String cheminFichier) {
        try {
            Scanner sc = new Scanner(new File(cheminFichier));
            for (int l = 0; l < gp.lignesMonde; l++)
                for (int c = 0; c < gp.colonnesMonde; c++)
                    if (sc.hasNextInt()) carte[l][c] = sc.nextInt();
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("Carte non trouvee : " + cheminFichier);
            for (int l = 0; l < gp.lignesMonde; l++)
                for (int c = 0; c < gp.colonnesMonde; c++)
                    carte[l][c] = (l == 0 || l == gp.lignesMonde-1 ||
                                   c == 0 || c == gp.colonnesMonde-1) ? MUR : HERBE;
        }
    }

    public void draw(Graphics2D g2) {
        int mondeCamX = gp.joueur.mondeX - gp.joueur.ecranX;
        int mondeCamY = gp.joueur.mondeY - gp.joueur.ecranY;

        for (int l = 0; l < gp.lignesMonde; l++) {
            for (int c = 0; c < gp.colonnesMonde; c++) {
                int ex = c * gp.tailleTuile - mondeCamX;
                int ey = l * gp.tailleTuile - mondeCamY;

                if (ex + gp.tailleTuile > 0 && ex < gp.largeurEcran &&
                    ey + gp.tailleTuile > 0 && ey < gp.hauteurEcran) {
                    int n = carte[l][c];
                    if (n >= 0 && n < tuiles.length && tuiles[n] != null)
                        g2.drawImage(tuiles[n].image, ex, ey,
                            gp.tailleTuile, gp.tailleTuile, null);
                }
            }
        }
    }
}