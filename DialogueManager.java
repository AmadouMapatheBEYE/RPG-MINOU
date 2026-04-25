import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.HashMap;
import javax.imageio.*;

public class DialogueManager {

    private GamePanel gp;

    // État du dialogue
    public boolean dialogueOuvert  = false;
    private String texteComplet    = "";
    private String texteAffiche    = "";
    private int    indexLettre     = 0;
    private int    compteurTexte   = 0;
    private int    vitesseTexte    = 2;  // plus petit = plus rapide
    private boolean texteFini      = false;

    // Personnage qui parle
    private String nomLocuteur     = "";
    private entity.NPC npcActuel   = null;

    // Portraits
    private HashMap<String, BufferedImage> portraits = new HashMap<>();

    // Dimensions de la boîte
    private final int BOX_X      = 40;
    private final int BOX_Y      = 400;  // en bas de l'écran
    private final int BOX_W      = 688;
    private final int BOX_H      = 150;
    private final int PORTRAIT_S = 120;  // taille du portrait

    public DialogueManager(GamePanel gp) {
        this.gp = gp;
        chargerPortraits();
    }

    private void chargerPortraits() {
        String[] persos = {
            "minou", "sara", "lena", "yuki", "maya", "ellie", "noa"
        };
        for (String p : persos) {
            try {
                BufferedImage img = ImageIO.read(
                    new File("assets/portraits/" + p + ".png"));
                portraits.put(p, img);
            } catch (Exception e) {
                // Portrait manquant → on génère un placeholder
                portraits.put(p, creerPortraitPlaceholder(p));
            }
        }
    }

    // Crée un portrait coloré si l'image n'existe pas
    private BufferedImage creerPortraitPlaceholder(String nom) {
        BufferedImage img = new BufferedImage(
            PORTRAIT_S, PORTRAIT_S, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();

        // Couleur unique par personnage
        Color[] couleurs = {
            new Color(210, 105, 30),   // minou  - roux
            new Color(100, 80, 200),   // sara   - violet
            new Color(200, 60, 20),    // lena   - rouge
            new Color(220, 100, 130),  // yuki   - rose
            new Color(200, 50, 50),    // maya   - rouge sport
            new Color(180, 210, 180),  // ellie  - vert sauge
            new Color(40, 30, 60),     // noa    - violet sombre
        };
        String[] noms = {
            "minou","sara","lena","yuki","maya","ellie","noa"
        };

        Color c = new Color(100, 100, 150);
        for (int i = 0; i < noms.length; i++) {
            if (noms[i].equals(nom)) { c = couleurs[i]; break; }
        }

        // Fond dégradé
        g.setColor(c.darker());
        g.fillRect(0, 0, PORTRAIT_S, PORTRAIT_S);
        g.setColor(c);
        g.fillOval(10, 10, PORTRAIT_S - 20, PORTRAIT_S - 20);

        // Initiale
        g.setColor(Color.WHITE);
        g.setFont(new Font("Serif", Font.BOLD, 40));
        FontMetrics fm = g.getFontMetrics();
        String lettre = nom.substring(0, 1).toUpperCase();
        g.drawString(lettre,
            PORTRAIT_S/2 - fm.stringWidth(lettre)/2,
            PORTRAIT_S/2 + fm.getAscent()/2 - 5);

        g.dispose();
        return img;
    }

    // Ouvre le dialogue avec un NPC
    public void ouvrirDialogue(entity.NPC npc) {
        if (dialogueOuvert) return;

        npcActuel      = npc;
        nomLocuteur    = npc.nom;
        texteComplet   = npc.getDialogueSuivant();
        texteAffiche   = "";
        indexLettre    = 0;
        compteurTexte  = 0;
        texteFini      = false;
        dialogueOuvert = true;
    }

    // Appelé à chaque frame
    public void update() {
        if (!dialogueOuvert) return;

        if (!texteFini) {
            compteurTexte++;
            if (compteurTexte >= vitesseTexte) {
                compteurTexte = 0;
                if (indexLettre < texteComplet.length()) {
                    texteAffiche += texteComplet.charAt(indexLettre);
                    indexLettre++;
                } else {
                    texteFini = true;
                }
            }
        }
    }

    // Appelé quand le joueur appuie sur Entrée pendant un dialogue
    public void avancer() {
        if (!dialogueOuvert) return;

        if (!texteFini) {
            // Affiche tout le texte immédiatement
            texteAffiche = texteComplet;
            indexLettre  = texteComplet.length();
            texteFini    = true;
        } else {
            // Ferme le dialogue
            fermerDialogue();
        }
    }

    public void fermerDialogue() {
        dialogueOuvert = false;
        npcActuel      = null;
        texteAffiche   = "";
        texteComplet   = "";
    }

    // Dessine la boîte de dialogue
    public void draw(Graphics2D g2) {
        if (!dialogueOuvert) return;

        // === FOND DE LA BOÎTE ===
        g2.setColor(new Color(0, 0, 0, 200));
        g2.fillRoundRect(BOX_X, BOX_Y, BOX_W, BOX_H, 15, 15);

        g2.setColor(new Color(255, 220, 180));
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(BOX_X, BOX_Y, BOX_W, BOX_H, 15, 15);

        // === PORTRAIT ===
        BufferedImage portrait = portraits.get(
            nomLocuteur.toLowerCase());
        if (portrait != null) {
            g2.drawImage(portrait,
                BOX_X + 10, BOX_Y + 15,
                PORTRAIT_S, PORTRAIT_S, null);
        }

        // Cadre portrait
        g2.setColor(new Color(255, 220, 180));
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(BOX_X + 10, BOX_Y + 15,
            PORTRAIT_S, PORTRAIT_S, 8, 8);

        // === NOM DU LOCUTEUR ===
        int textX = BOX_X + PORTRAIT_S + 25;

        g2.setColor(new Color(255, 220, 100));
        g2.setFont(new Font("Serif", Font.BOLD, 18));
        g2.drawString(nomLocuteur, textX, BOX_Y + 35);

        // Ligne sous le nom
        g2.setColor(new Color(255, 220, 100, 150));
        g2.setStroke(new BasicStroke(1));
        g2.drawLine(textX, BOX_Y + 42,
            BOX_X + BOX_W - 20, BOX_Y + 42);

        // === TEXTE ANIMÉ ===
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Serif", Font.PLAIN, 16));

        // Découpe le texte en lignes (retour à la ligne auto)
        String[] mots   = texteAffiche.split(" ");
        int largeurMax  = BOX_W - PORTRAIT_S - 50;
        StringBuilder ligne = new StringBuilder();
        int ligneY      = BOX_Y + 65;
        int ligneH      = 22;

        for (String mot : mots) {
            String test = ligne + (ligne.length() > 0 ? " " : "") + mot;
            FontMetrics fm = g2.getFontMetrics();
            if (fm.stringWidth(test) > largeurMax && ligne.length() > 0) {
                g2.drawString(ligne.toString(), textX, ligneY);
                ligne = new StringBuilder(mot);
                ligneY += ligneH;
            } else {
                if (ligne.length() > 0) ligne.append(" ");
                ligne.append(mot);
            }
        }
        if (ligne.length() > 0) {
            g2.drawString(ligne.toString(), textX, ligneY);
        }

        // === INDICATEUR (clignotant si texte fini) ===
        if (texteFini) {
            long temps = System.currentTimeMillis();
            if ((temps / 500) % 2 == 0) {  // clignote toutes les 0.5s
                g2.setColor(new Color(255, 220, 100));
                g2.setFont(new Font("Arial", Font.BOLD, 12));
                g2.drawString("▶ Entrée",
                    BOX_X + BOX_W - 80,
                    BOX_Y + BOX_H - 12);
            }
        }
    }

    // Vérifie si Kaï est assez proche d'un NPC pour parler
    public entity.NPC getNPCProche() {
        for (entity.NPC npc : gp.npcs) {
            if (npc == null) continue;

            int distX = Math.abs(npc.mondeX - gp.joueur.mondeX);
            int distY = Math.abs(npc.mondeY - gp.joueur.mondeY);

            // Dans un rayon d'1.5 tuile
            if (distX < gp.tailleTuile * 1.5 &&
                distY < gp.tailleTuile * 1.5) {
                return npc;
            }
        }
        return null;
    }

    public boolean isDialogueOuvert() { return dialogueOuvert; }
}