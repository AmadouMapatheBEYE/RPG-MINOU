import java.awt.Color;

public class HorlogeJeu {

    private int heures   = 8;
    private int minutes  = 0;
    private int compteur = 0;
    private final int TICKS_PAR_MINUTE = 600;

    public enum Periode { MATIN, APRES_MIDI, SOIR, NUIT }

    public void update() {
        compteur++;
        if (compteur >= TICKS_PAR_MINUTE) {
            compteur = 0;
            minutes++;
            if (minutes >= 60) { minutes = 0; heures = (heures + 1) % 24; }
        }
    }

    public int getHeures()  { return heures; }
    public int getMinutes() { return minutes; }

    public String getHeureFormatee() {
        return String.format("%02d:%02d", heures, minutes);
    }

    public Periode getPeriode() {
        if (heures >= 6  && heures < 12) return Periode.MATIN;
        if (heures >= 12 && heures < 18) return Periode.APRES_MIDI;
        if (heures >= 18 && heures < 22) return Periode.SOIR;
        return Periode.NUIT;
    }

    public Color getCouleurAmbiance() {
        switch (getPeriode()) {
            case MATIN:      return new Color(255, 240, 200, 30);
            case APRES_MIDI: return new Color(255, 255, 255, 0);
            case SOIR:       return new Color(255, 150, 50,  60);
            case NUIT:       return new Color(0,   0,   80,  120);
            default:         return new Color(0, 0, 0, 0);
        }
    }

    public void avancerHeure() {
        heures = (heures + 1) % 24;
        minutes = 0;
    }
}