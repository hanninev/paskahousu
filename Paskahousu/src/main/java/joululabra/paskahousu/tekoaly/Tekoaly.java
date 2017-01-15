package joululabra.paskahousu.tekoaly;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import joululabra.paskahousu.domain.Kortti;
import joululabra.paskahousu.domain.Korttijoukko;
import joululabra.paskahousu.sovelluslogiikka.Saannot;
import joululabra.paskahousu.sovelluslogiikka.Siirtojenkasittelija;

/**
 * Luokka tarjoaa tekoälyä käyttävän pelaajan toimintoihin liittyviä metodeita.
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
        try {
            if (sk.getPakka().onTyhja() && !kaykoMikaanKortti()) {
                sk.nostaPino();
                return new Korttijoukko();
            } else if (!kaykoMikaanKortti()) {
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

        return new Korttijoukko();
    }

    private Korttijoukko laitaKortti() throws Exception {

        List<Kortti> kortitKadessa = new ArrayList<>(sk.nykyinenVuoro().getPelaaja().getKasi().getKortit());
        Korttijoukko lisatyt = new Korttijoukko();

        for (Kortti kortti : kortitKadessa) {
            if (!sk.nykyinenVuoro().getPelaaja().getKasi().onTyhja()) {
                try {
                    if ((Saannot.korttiSopii(sk.getPino(), sk.getPakka(), sk.nykyinenVuoro(), kortti)) && kortti.getArvo() != 2) {
                        sk.siirraKorttiPinoon(kortti);
                        lisatyt.lisaa(kortti);
                    }
                } catch (Exception e) {

                }
            }
        }

        if (lisatyt.onTyhja()) {
            Kortti pienin = sk.nykyinenVuoro().getPelaaja().pieninKortti();
            if (pienin.getArvo() == 2) {
                sk.siirraKorttiPinoon(pienin);
                lisatyt.lisaa(pienin);
            }
        }

        return lisatyt;
    }

    private boolean kaykoMikaanKortti() throws Exception {
        boolean onnistui = false;

        if (!sk.nykyinenVuoro().getPelaaja().getKasi().onTyhja()) {
            for (Kortti kortti : sk.nykyinenVuoro().getPelaaja().getKasi().getKortit()) {
                try {
                    if (Saannot.korttiSopii(sk.getPino(), sk.getPakka(), sk.nykyinenVuoro(), kortti)) {
                        onnistui = true;
                    }
                } catch (Exception e) {

                }
            }
        }

        return onnistui;
    }
}
