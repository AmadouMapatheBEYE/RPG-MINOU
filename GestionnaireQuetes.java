import java.awt.*;
import java.util.ArrayList;

public class GestionnaireQuetes {

    public enum StatutQuete { INACTIVE, EN_COURS, COMPLETEE, ECHOUEE }

    public static class Quete {
        public String      id, titre, description, objectifActuel, recompense, personnageLie;
        public StatutQuete statut;
        public int         affectionRequise;

        public Quete(String id, String titre, String desc, String personnage, int affReq) {
            this.id = id; this.titre = titre; this.description = desc;
            this.personnageLie = personnage; this.affectionRequise = affReq;
            this.statut = StatutQuete.INACTIVE; this.objectifActuel = "";
        }
    }

    private ArrayList<Quete> quetes  = new ArrayList<>();
    private ArrayList<Quete> actives = new ArrayList<>();
    private GamePanel gp;

    public GestionnaireQuetes(GamePanel gp) {
        this.gp = gp;
        initialiserQuetes();
    }

    private void initialiserQuetes() {
        Quete q0 = new Quete("INTRO","Un nouveau depart","Explore Solevert avec Minou.","Minou",0);
        q0.objectifActuel = "Parler a Minou"; q0.statut = StatutQuete.EN_COURS;
        quetes.add(q0); actives.add(q0);

        String[][] data = {
            {"SARA_1","La perfectionniste","Sara cache quelque chose.","Sara","20"},
            {"LENA_1","La melodie oubliee","Lena joue en secret.","Lena","20"},
            {"YUKI_1","Perdue en traduction","Aide Yuki.","Yuki","15"},
            {"MAYA_1","Plus qu'un sport","Maya cache une blessure.","Maya","25"},
            {"ELLIE_1","L'art invisible","Ellie dessine en secret.","Ellie","15"},
            {"NOA_1","La nuit et les etoiles","Noa n'apparait que la nuit.","Noa","20"},
        };
        for (String[] d : data) {
            Quete q = new Quete(d[0], d[1], d[2], d[3], Integer.parseInt(d[4]));
            q.objectifActuel = "Parler a " + d[3]; quetes.add(q);
        }

        Quete crise = new Quete("CRISE","Encore la trahison ?","Quelque chose se passe...","",0);
        crise.objectifActuel = "Se debloque apres 3 personnages connus"; quetes.add(crise);
    }

    public void update() {
        SystemeAffection sa = gp.systemeAffection;
        for (Quete q : quetes) {
            if (q.statut == StatutQuete.INACTIVE && !q.personnageLie.isEmpty()) {
                if (sa.getAffection(q.personnageLie) >= q.affectionRequise) {
                    q.statut = StatutQuete.EN_COURS; actives.add(q);
                    System.out.println("[Quete debloquee] " + q.titre);
                }
            }
        }
        Quete crise = getQuete("CRISE");
        if (crise != null && crise.statut == StatutQuete.INACTIVE) {
            int nb = 0;
            for (String p : new String[]{"Sara","Lena","Yuki","Maya","Ellie","Noa"})
                if (sa.getAffection(p) >= 20) nb++;
            if (nb >= 3) { crise.statut = StatutQuete.EN_COURS; actives.add(crise); }
        }
    }

    public Quete getQuete(String id) {
        for (Quete q : quetes) if (q.id.equals(id)) return q;
        return null;
    }

    public void completerQuete(String id) {
        Quete q = getQuete(id);
        if (q != null) { q.statut = StatutQuete.COMPLETEE; actives.remove(q); }
    }

    public ArrayList<Quete> getActives() { return actives; }

    public void draw(Graphics2D g2, int largeur, int hauteur) {
        int bx = largeur/2-280, by = 40, bw = 560, bh = hauteur-80;
        g2.setColor(new Color(10,5,25,230)); g2.fillRoundRect(bx,by,bw,bh,15,15);
        g2.setColor(new Color(255,200,100)); g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(bx,by,bw,bh,15,15);
        g2.setFont(new Font("Serif",Font.BOLD,22)); g2.setColor(new Color(255,220,100));
        g2.drawString("Journal de Quetes", bx+20, by+35);
        g2.drawLine(bx+15, by+45, bx+bw-15, by+45);
        int y = by+70;
        g2.setFont(new Font("Serif",Font.BOLD,14)); g2.setColor(new Color(100,220,100));
        g2.drawString("EN COURS", bx+20, y); y += 20;
        for (Quete q : actives) {
            if (q.statut != StatutQuete.EN_COURS) continue;
            g2.setFont(new Font("Serif",Font.BOLD,13)); g2.setColor(Color.WHITE);
            g2.drawString("- " + q.titre, bx+25, y); y += 16;
            g2.setFont(new Font("Serif",Font.ITALIC,11)); g2.setColor(new Color(180,180,220));
            g2.drawString("  " + q.objectifActuel, bx+30, y); y += 20;
        }
        y += 10; g2.setColor(new Color(255,255,255,50)); g2.drawLine(bx+15,y,bx+bw-15,y); y += 20;
        g2.setFont(new Font("Serif",Font.BOLD,14)); g2.setColor(new Color(180,180,180));
        g2.drawString("COMPLETEES", bx+20, y); y += 20;
        for (Quete q : quetes) {
            if (q.statut != StatutQuete.COMPLETEE) continue;
            g2.setFont(new Font("Serif",Font.PLAIN,12)); g2.setColor(new Color(150,150,150));
            g2.drawString("v " + q.titre, bx+25, y); y += 18;
        }
        g2.setFont(new Font("Arial",Font.BOLD,11)); g2.setColor(new Color(200,200,200));
        g2.drawString("[J] Fermer", bx+bw-80, by+bh-15);
    }
}