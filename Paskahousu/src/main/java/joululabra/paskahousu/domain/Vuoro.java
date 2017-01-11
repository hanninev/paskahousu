package joululabra.paskahousu.domain;

/**
 * Luokka tarjoaa yksittäiseen vuoroon liittyviä metodeita.
 */
public class Vuoro {

    private Pelaaja pelaaja;
    private int laitettuPinoon;
    private boolean jatkuu;
    private boolean kokeiliOnnea;
    private boolean nostiPinon;
    private boolean kaatoiPinon;

    /**
     * Konstruktori tekee uuden olioilmentymän luokasta Vuoro.
     *
     * @param pelaaja Vuoroon asetettava pelaaja
     */
    public Vuoro(Pelaaja pelaaja) {
        this.pelaaja = pelaaja;
        this.laitettuPinoon = 0;
        this.jatkuu = true;
        this.kokeiliOnnea = false;
        this.nostiPinon = false;
        this.kaatoiPinon = false;
    }

    public Pelaaja getPelaaja() {
        return pelaaja;
    }

    public void setKaatoiPinon(boolean kaatoiPinon) {
        this.kaatoiPinon = kaatoiPinon;
    }

    public boolean isKokeiliOnnea() {
        return kokeiliOnnea;
    }

    public void setNostiPinon(boolean nostiPinon) {
        this.nostiPinon = nostiPinon;
    }

    public void setKokeiliOnnea(boolean kokeiliOnnea) {
        this.kokeiliOnnea = kokeiliOnnea;
    }

    public boolean isKaatoiPinon() {
        return kaatoiPinon;
    }

    public boolean isNostiPinon() {
        return nostiPinon;
    }

    public void setLaitettuPinoon(int laitettuPinoon) {
        this.laitettuPinoon = laitettuPinoon;
    }

    /**
     * Metodi lisää pinoon laitetusta kortista tiedon oliomuuttujaan
     * laitettuPinoon.
     *
     */
    public void laittoiKortinPinoon() {
        this.laitettuPinoon++;
    }

    public int getLaitettuPinoon() {
        return laitettuPinoon;
    }

    public boolean isJatkuu() {
        return jatkuu;
    }

    public void setJatkuu(boolean jatkuu) {
        this.jatkuu = jatkuu;
    }
}
