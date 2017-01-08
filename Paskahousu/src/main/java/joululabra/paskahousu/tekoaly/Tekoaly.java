package joululabra.paskahousu.tekoaly;

import java.util.ArrayList;
import java.util.List;
import joululabra.paskahousu.domain.Kortti;
import joululabra.paskahousu.sovelluslogiikka.Saannot;
import joululabra.paskahousu.sovelluslogiikka.Siirtojenkasittelija;

/**
 * Luokka tarjoaa tekoälyyn liittyviä metodeita.
 */
public class Tekoaly {

    private Siirtojenkasittelija sk;

    /**
     * Konstruktori tekee uuden olioilmentymän luokasta Tekoaly.
     *
     * @param sk Tekoälyyn liitettävä siirtojenkäsittelijä.
     */
    public Tekoaly(Siirtojenkasittelija sk) {
        this.sk = sk;
    }

    /**
     * Metodi valitsee toiminnon.
     *
     * @throws Exception Siirtoa ei voida tehdä, jos siirrettävää korttia ei ole
     * kädessä.
     */
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

    /**
     * Metodi laittaa pinoon kortteja.
     *
     * @throws Exception Siirtoa ei voida tehdä, jos siirrettävää korttia ei ole
     * kädessä.
     */
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

    /**
     * Metodi kertoo, käykö mikään kädessä oleva kortti pinoon.
     * 
     * @return boolean
     */
    public boolean kaykoMikaanKortti() {
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
