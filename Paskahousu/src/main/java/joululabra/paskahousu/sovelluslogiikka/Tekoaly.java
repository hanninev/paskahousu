package joululabra.paskahousu.sovelluslogiikka;

import java.util.ArrayList;
import java.util.List;
import joululabra.paskahousu.domain.Kortti;

public class Tekoaly {

    private Siirtojenkasittelija sk;

    public Tekoaly(Siirtojenkasittelija sk) {
        this.sk = sk;
    }

    public void valitseToiminto() throws Exception {
        try {
        if (!kaykoMikaanKortti()) {
            sk.kokeileOnnea();
        } else {
            while (kaykoMikaanKortti()) {
                laitaKortti();
            }
        }
        } catch (Exception e) {
            
        }
    }

    public void laitaKortti() throws Exception {
        List<Kortti> kortitKadessa = new ArrayList<>(sk.nykyinenVuoro().getPelaaja().getKasi().getKortit());

        for (Kortti kortti : kortitKadessa) {
            if (!sk.nykyinenVuoro().getPelaaja().getKasi().onTyhja()) {
                sk.siirraKorttiPinoon(kortti);
            }
        }
    }

    public boolean kaykoMikaanKortti() throws Exception {
        boolean onnistui = false;

        for (Kortti kortti : sk.nykyinenVuoro().getPelaaja().getKasi().getKortit()) {
            if (!sk.nykyinenVuoro().getPelaaja().getKasi().onTyhja()) {
                if (Saannot.korttiSopii(sk.getPino(), sk.getPakka(), sk.nykyinenVuoro(), kortti)) {
                    onnistui = true;
                }
            }
        }
        return onnistui;
    }
}