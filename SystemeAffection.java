import java.awt.Color;
import java.util.HashMap;

public class SystemeAffection {

    private HashMap<String, Integer> affection = new HashMap<>();

    public static final String SARA  = "Sara";
    public static final String LENA  = "Lena";
    public static final String YUKI  = "Yuki";
    public static final String MAYA  = "Maya";
    public static final String ELLIE = "Ellie";
    public static final String NOA   = "Noa";

    public SystemeAffection() {
        String[] persos = {SARA, LENA, YUKI, MAYA, ELLIE, NOA};
        for (String p : persos) affection.put(p, 0);
    }

    public void modifier(String nom, int valeur) {
        int actuel  = affection.getOrDefault(nom, 0);
        int nouveau = Math.max(0, Math.min(100, actuel + valeur));
        affection.put(nom, nouveau);
        System.out.println("[Affection] " + nom + " : " + actuel + " -> " + nouveau);
    }

    public int getAffection(String nom) {
        return affection.getOrDefault(nom, 0);
    }

    public String getEtat(String nom) {
        int val = getAffection(nom);
        if (val >= 80) return "Amour";
        if (val >= 60) return "Tres proche";
        if (val >= 40) return "Amis";
        if (val >= 20) return "Connaissance";
        return "Inconnus";
    }

    public Color getCouleurEtat(String nom) {
        int val = getAffection(nom);
        if (val >= 80) return new Color(255, 100, 150);
        if (val >= 60) return new Color(255, 180, 200);
        if (val >= 40) return new Color(150, 220, 150);
        if (val >= 20) return new Color(200, 200, 255);
        return new Color(180, 180, 180);
    }
}