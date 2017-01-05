package joululabra.paskahousu.domain;

import java.util.Collections;

public class Pakka extends Korttijoukko {

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

    public void sekoitaKortit() {
        Collections.shuffle(kortit);
    }

    /**
     * Metodi ottaa korttipakasta ensimmäisen kortin.
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
