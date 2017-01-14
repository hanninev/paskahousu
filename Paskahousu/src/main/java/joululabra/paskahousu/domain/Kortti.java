package joululabra.paskahousu.domain;

/**
 * Luokka tarjoaa yksittäiseen korttii liittyviä metodeja.
 */
public class Kortti implements Comparable<Kortti> {

    public static final int PATA = 0;
    public static final int HERTTA = 1;
    public static final int RUUTU = 2;
    public static final int RISTI = 3;

    public static final String[] MAAT = {"Pata", "Hertta", "Ruutu", "Risti"};
    public static final String[] ARVOT = {"-", "-", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
    private int arvo;
    private int maa;

    /**
     * Konstruktori tekee uuden olioilmentymän luokasta Kortti.
     * @param arvo Kortin arvo
     * @param maa Kortin maa
     */
    public Kortti(int maa, int arvo) {
        this.maa = maa;
        this.arvo = arvo;
    }

    public int getArvo() {
        return arvo;
    }

    public int getMaa() {
        return maa;
    }

    @Override
    public String toString() {
        return MAAT[maa] + " " + ARVOT[arvo];
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + this.arvo;
        hash = 37 * hash + this.maa;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Kortti other = (Kortti) obj;
        if (this.arvo != other.arvo) {
            return false;
        }
        if (this.maa != other.maa) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Kortti o) {
        if (this.arvo == o.arvo) {
            return this.maa - o.maa;
        }
        return this.arvo - o.arvo;
    }
}
