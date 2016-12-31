package joululabra.paskahousu.domain;

import java.util.Collections;

public class Pakka extends Korttijoukko {

    public Pakka() {
        luoKortit();
    }

    private void luoKortit() {
        for (int i = 0; i < Kortti.MAAT.length; i++) {
            for (int j = 2; j < Kortti.ARVOT.length; j++) {
                lisaaKortti(new Kortti(i, j));
            }
        }
    }

    public void sekoitaKortit() {
        Collections.shuffle(kortit);
    }

    public Kortti otaEnsimmainenKortti() throws Exception {
        return otaKortti(this.viimeisinKortti());
    }
}
