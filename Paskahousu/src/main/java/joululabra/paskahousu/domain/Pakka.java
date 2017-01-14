package joululabra.paskahousu.domain;

import java.util.Collections;

/**
 * Luokka tarjoaa pakkaan liittyviä metodeita.
 */
public class Pakka extends Korttijoukko {

    /**
     * Konstruktori tekee uuden olioilmentymän luokasta Pakka.
     *
     */
    public Pakka() {
        luoKortit();
    }

    /**
     * Metodi luo ja lisää korttipakkaan kaikki kortit.
     *
     */
    private void luoKortit() {
        for (int i = 0; i < Kortti.MAAT.length; i++) {
            for (int j = 2; j < Kortti.ARVOT.length; j++) {
                lisaa(new Kortti(i, j));
            }
        }
    }

    /**
     * Metodi sekoittaa pakan kortit.
     *
     */
    public void sekoitaKortit() {
        Collections.shuffle(kortit);
    }

    /**
     * Metodi ottaa korttipakasta ensimmäisen kortin.
     *
     * @throws Exception Pakasta ei voi nostaa korttia, jos pakka on tyhjä.
     * 
     * @return Pakan ensimmäinen kortti
     */
    public Kortti otaEnsimmainenKortti() throws Exception {
        if (this.kortit.isEmpty()) {
            throw new Exception("Et voi nostaa korttia pakasta, koska pakka on tyhjä.");
        }
        return ota(this.viimeisinKortti());
    }
}
