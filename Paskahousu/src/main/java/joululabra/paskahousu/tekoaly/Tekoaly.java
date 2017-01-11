package joululabra.paskahousu.tekoaly;

import java.util.ArrayList;
import java.util.List;
import joululabra.paskahousu.domain.Kortti;
import joululabra.paskahousu.domain.Korttijoukko;
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
     * Metodi valitsee vastustajan suorittaman toiminnon.
     * 
     * @return pinoon laitetut kortit
     *
     * @throws Exception Siirtoa ei voida tehdä, jos siirrettävää korttia ei ole
     * vastustajan kädessä.
     */
    public Korttijoukko valitseToiminto() throws Exception {
        Korttijoukko lisatyt = new Korttijoukko();
        try {
            if (sk.getPakka().onTyhja() && !kaykoMikaanKortti()) {
                sk.nostaPino();
            }

            if (!kaykoMikaanKortti()) {
                sk.kokeileOnnea();
            }

            if (kaykoMikaanKortti()) {
                while (kaykoMikaanKortti()) {
                    return laitaKortti();
                }
            } else {
                sk.nostaPino();
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return lisatyt;
    }

    private Korttijoukko laitaKortti() throws Exception {
        List<Kortti> kortitKadessa = new ArrayList<>(sk.nykyinenVuoro().getPelaaja().getKasi().getKortit());
        Korttijoukko lisatyt = new Korttijoukko();

        for (Kortti kortti : kortitKadessa) {
            if (!sk.nykyinenVuoro().getPelaaja().getKasi().onTyhja()) {
                if ((Saannot.korttiSopii(sk.getPino(), sk.getPakka(), sk.nykyinenVuoro(), kortti)) && kortti.getArvo() != 2) {
                    sk.siirraKorttiPinoon(kortti);
                    lisatyt.lisaa(kortti);
                }
            }
        }

        if (lisatyt.onTyhja()) {
            for (Kortti kortti : kortitKadessa) {
                if (!sk.nykyinenVuoro().getPelaaja().getKasi().onTyhja()) {
                    if (Saannot.korttiSopii(sk.getPino(), sk.getPakka(), sk.nykyinenVuoro(), kortti)) {
                        sk.siirraKorttiPinoon(kortti);
                        lisatyt.lisaa(kortti);
                    }
                }
            }
        }
        return lisatyt;
    }

    private boolean kaykoMikaanKortti() {
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
