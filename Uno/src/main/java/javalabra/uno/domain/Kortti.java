package javalabra.uno.domain;

public class Kortti implements Comparable<Kortti> {

    public static final int PUNAINEN = 0;
    public static final int KELTAINEN = 1;
    public static final int VIHREA = 2;
    public static final int SININEN = 3;
    public static final int PLUSKAKSI = 10;
    public static final int UUDESTAAN = 11;

    public static final String[] VARIT = {"Punainen", "Keltainen", "Vihreä", "Sininen"};
    public static final String[] ARVOT = {"-", "1", "2", "3", "4", "5", "6", "7", "8", "9", "+2", "(/)"};
    private int arvo;
    private int vari;

    public Kortti(int vari, int arvo) {
        this.vari = vari;
        this.arvo = arvo;
    }

    public int getArvo() {
        return arvo;
    }

    public int getVari() {
        return vari;
    }

    @Override
    public String toString() {
        return VARIT[vari] + " " + ARVOT[arvo];
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + this.arvo;
        hash = 37 * hash + this.vari;
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
        if (this.vari != other.vari) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Kortti o) {
        if (this.arvo == o.arvo) {
            return this.vari - o.vari;
        }
        return this.arvo - o.arvo;
    }
}
