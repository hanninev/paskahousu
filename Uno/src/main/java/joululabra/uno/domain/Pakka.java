package joululabra.uno.domain;

import java.util.Collections;

public class Pakka extends Korttijoukko {

    public Pakka() {
        luoKortit();
    }

    private void luoKortit() {
        for (int i = 0; i < Kortti.VARIT.length; i++) {
            for (int j = 1; j < Kortti.ARVOT.length; j++) {
                lisaaKortti(new Kortti(i, j));
            }
        }
    }

    public void sekoitaKortit() {
        Collections.shuffle(kortit);
    }

    public void siirraEnsimmainenKortti(Korttijoukko mihin) throws Exception {
        siirraKortti(this.viimeisinKortti(), mihin);
    }
}
