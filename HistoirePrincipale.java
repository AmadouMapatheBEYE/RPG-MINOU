import java.awt.*;

public class HistoirePrincipale {

    public enum Acte { ACTE_1, ACTE_2, ACTE_3 }

    private Acte      acteActuel  = Acte.ACTE_1;
    private GamePanel gp;

    public boolean introTerminee        = false;
    public boolean minouPerdu           = false;
    public boolean minouRetrouve        = false;
    public boolean criseDeClenchee      = false;
    public boolean choixFinal           = false;
    public String  finChoisie           = "";

    public HistoirePrincipale(GamePanel gp) { this.gp = gp; }

    public void update() {
        SystemeAffection sa = gp.systemeAffection;
        int nbConnus = compterPersonnagesConnus(sa, 15);
        int nbConnus2 = compterPersonnagesConnus(sa, 25);

        switch (acteActuel) {
            case ACTE_1:
                if (nbConnus >= 2 && !introTerminee) { introTerminee = true; }
                if (nbConnus >= 3) { acteActuel = Acte.ACTE_2; declencherActe2(); }
                break;
            case ACTE_2:
                if (nbConnus2 >= 4 && !minouPerdu)    declencherDisparitionMinou();
                if (nbConnus2 >= 5 && !criseDeClenchee) declencherCrise();
                if (minouRetrouve && criseDeClenchee)  { acteActuel = Acte.ACTE_3; declencherActe3(); }
                break;
            case ACTE_3:
                if (!choixFinal) {
                    int total = getTotalAffection(sa);
                    finChoisie = total >= 300 ? "FIN_HEUREUSE" : total >= 150 ? "FIN_AMITIE" : "FIN_SOLITUDE";
                }
                break;
        }
    }

    private void declencherActe2() {
        gp.dialogueManager.ouvrirDialogueTexte("Minou",
            "Minou te regarde intensement depuis ce matin. Quelque chose a change dans la ville...");
    }

    private void declencherDisparitionMinou() {
        minouPerdu = true;
        for (entity.NPC npc : gp.npcs)
            if (npc != null && npc.nom.equals("Minou")) { npc.mondeX = -2000; npc.mondeY = -2000; break; }
        gp.dialogueManager.ouvrirDialogueTexte("???",
            "Minou n'est plus la. Il n'est pas dans l'appartement non plus.\n"
            + "C'est la premiere fois que tu paniques vraiment depuis longtemps.");
    }

    private void declencherCrise() {
        criseDeClenchee = true;
        gp.dialogueManager.ouvrirDialogueTexte("Kai",
            "Tu entends une conversation par hasard.\n"
            + "Quelqu'un parle de toi. 'Trop froid', 'il fait jamais confiance'...\n"
            + "Est-ce que tout recommence comme avant ?");
    }

    private void declencherActe3() {
        for (entity.NPC npc : gp.npcs) {
            if (npc != null && npc.nom.equals("Minou")) {
                npc.mondeX = gp.joueur.mondeX - gp.tailleTuile;
                npc.mondeY = gp.joueur.mondeY;
                minouRetrouve = true; break;
            }
        }
        gp.dialogueManager.ouvrirDialogueTexte("Minou",
            "Minou est revenu. Il te regarde.\nComme s'il savait exactement ce que tu traverses.\nIl pose sa patte sur ta main.\n\n...OK Minou. T'as peut-etre raison.");
    }

    private int compterPersonnagesConnus(SystemeAffection sa, int seuil) {
        int count = 0;
        for (String p : new String[]{"Sara","Lena","Yuki","Maya","Ellie","Noa"})
            if (sa.getAffection(p) >= seuil) count++;
        return count;
    }

    private int getTotalAffection(SystemeAffection sa) {
        int total = 0;
        for (String p : new String[]{"Sara","Lena","Yuki","Maya","Ellie","Noa"})
            total += sa.getAffection(p);
        return total;
    }

    public Acte getActe() { return acteActuel; }

    public void drawActe(Graphics2D g2, int largeurEcran) {
        String label = acteActuel == Acte.ACTE_1 ? "Acte I - Les Murs"
                     : acteActuel == Acte.ACTE_2 ? "Acte II - Les Fissures"
                     : "Acte III - La Confiance";
        g2.setFont(new java.awt.Font("Serif", java.awt.Font.ITALIC, 11));
        g2.setColor(new java.awt.Color(255,255,255,120));
        java.awt.FontMetrics fm = g2.getFontMetrics();
        g2.drawString(label, largeurEcran/2 - fm.stringWidth(label)/2, 15);
    }
}