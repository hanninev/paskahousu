package joululabra.paskahousu.tekoaly;

import java.util.ArrayList;
import java.util.List;
import joululabra.paskahousu.domain.Kortti;
import joululabra.paskahousu.sovelluslogiikka.Saannot;
import joululabra.paskahousu.sovelluslogiikka.Siirtojenkasittelija;

public class Tekoaly {

    private Siirtojenkasittelija sk;

    public Tekoaly(Siirtojenkasittelija sk) {
        this.sk = sk;
    }

    public void valitseToiminto() throws Exception {
        try {
            if (Saannot.pakkoNostaaPino(sk.getPino())) {
                sk.nostaPino();
                return;
            }

            if (!kaykoMikaanKortti()) {
                sk.kokeileOnnea();
            } else if (kaykoMikaanKortti()) {
                while (kaykoMikaanKortti()) {
                    laitaKortti();
                }
            } else {
                sk.nostaPino();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void laitaKortti() throws Exception {
        List<Kortti> kortitKadessa = new ArrayList<>(sk.nykyinenVuoro().getPelaaja().getKasi().getKortit());

        for (Kortti kortti : kortitKadessa) {
            if (!sk.nykyinenVuoro().getPelaaja().getKasi().onTyhja()) {
                if (Saannot.korttiSopii(sk.getPino(), sk.getPakka(), sk.nykyinenVuoro(), kortti)) {
                    sk.siirraKorttiPinoon(kortti);
                }
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
