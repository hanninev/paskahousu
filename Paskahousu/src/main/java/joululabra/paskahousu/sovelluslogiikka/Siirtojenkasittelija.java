package joululabra.paskahousu.sovelluslogiikka;

import java.util.ArrayList;
import java.util.List;
import joululabra.paskahousu.domain.Kortti;
import joululabra.paskahousu.domain.Korttijoukko;
import joululabra.paskahousu.domain.Pakka;
import joululabra.paskahousu.domain.Pelaaja;
import joululabra.paskahousu.domain.Vuoro;

/**
 * Luokka tarjoaa useita korttien liikutteluun tarvittavia metodeja.
 */
public class Siirtojenkasittelija {

    private Pakka pakka;
    private Korttijoukko pino;
    private List<Vuoro> vuorot;
    private Peli peli;

    /**
     * Konstruktori tekee uuden olioilmentymän luokasta Siirtojenkasittelija.
     *
     * @param peli Siirtojenkäsittelijään liitettävä peli
     */
    public Siirtojenkasittelija(Peli peli) {
        this.pakka = new Pakka();
        this.pino = new Korttijoukko();
        this.vuorot = new ArrayList<>();
        this.peli = peli;
    }

    public Pakka getPakka() {
        return pakka;
    }

    public Korttijoukko getPino() {
        return pino;
    }

    public List<Vuoro> getVuorot() {
        return vuorot;
    }

    /**
     * Metodi kertoo viimeisimmän vuoron.
     *
     * @return Nykyinen vuoro
     */
    public Vuoro nykyinenVuoro() {
        if (!vuorot.isEmpty()) {
            return vuorot.get(vuorot.size() - 1);
        }
        return null;
    }

    /**
     * Metodi lisää uuden vuoron ja asettaa vuoroon pelaajan.
     *
     * @param pelaaja Vuoroon asetettava pelaaja
     */
    public void lisaaVuoro(Pelaaja pelaaja) {
        vuorot.add(new Vuoro(pelaaja));
    }

    /**
     * Metodi siirtää halutun kortin pinoon, jos se on sääntöjen mukaan
     * mahdollista.
     *
     * @throws Exception Jos kortti ei sovi pinoon sääntöjen mukaan, korttia ei
     * voida lisätä pinoon.
     * @param kortti Siirrettävä kortti
     *
     */
    public void siirraKorttiPinoon(Kortti kortti) throws Exception {
        if (Saannot.korttiSopii(pino, pakka, nykyinenVuoro(), kortti)) {
            Kortti siirrettava = nykyinenVuoro().otaKadesta(kortti);
            pino.lisaa(siirrettava);
            pinoKaatuuJosSaannotSallivat();
        } else {
            throw new Exception(kortti.toString() + " ei ole sopiva pinoon.");
        }
    }

    /**
     * Metodi täydentää vuorossa olevan pelaajan käden, jos kädessä on sääntöjen
     * mukaan liian vähän kortteja.
     *
     * @throws Exception Jos siirrettävää korttia ei ole pakassa, ei siirtoa
     * voida suorittaa.
     *
     */
    public void taydennaKasi() throws Exception {
        while (Saannot.kadessaLiianVahanKortteja(nykyinenVuoro(), pakka)) {
            Kortti siirrettava = pakka.otaEnsimmainenKortti();
            nykyinenVuoro().lisaaKateen(siirrettava);
        }
    }

    /**
     * Jos vuorossa oleva pelaaja saa sääntöjen mukaan kokeilla onnea, metodi
     * nostaa pakasta ensimmäisen kortin ja lisää sen pelaajan käteen. Jos
     * kortti sopii pinon ylimmäiseksi kortiksi, kortti siirretään pinoon.
     * Muussa tapauksessa pelaaja joutuu nostamaan pinon.
     *
     * @throws Exception Jos siirrettävää korttia ei ole pakassa, ei siirtoa
     * voida suorittaa.
     */
    public void kokeileOnnea() throws Exception {
        if (Saannot.saaKokeillaOnnea(nykyinenVuoro())) {
            Kortti kortti = pakka.otaEnsimmainenKortti();
            nykyinenVuoro().lisaaKateen(kortti);
            if (Saannot.korttiSopii(pino, pakka, nykyinenVuoro(), kortti)) {
                siirraKorttiPinoon(kortti);
                peli.asetaSeuraavaPelaajaVuoroon();
            } else {
                nostaPino();
            }
        }
    }

    /**
     * Metodi siirtää pinon kaikki kortit vuorossa olevan pelaajan käteen.
     *
     * @throws Exception Korttia ei voida siirtää, jos kyseistä korttia ei ole
     * pinossa.
     */
    public void nostaPino() throws Exception {
        while (!pino.onTyhja()) {
            Kortti kortti = pino.ota(pino.getKortit().get(0));
            nykyinenVuoro().lisaaKateen(kortti);
        }
        peli.asetaSeuraavaPelaajaVuoroon();
    }

    /**
     * Metodi kutsuu nostaPino()-metodia, jos pelaajan on sääntöjen mukaan pakko
     * nostaa pino.
     *
     * @throws Exception Korttia ei voida siirtää, jos siirrettävää korttia ei
     * ole pinossa.
     */
    public void nostaPinoJosOnPakko() throws Exception {
        if (Saannot.pakkoNostaaPino(pino)) {
            nostaPino();
        }
    }

    /**
     * Metodi siirtää pinossa olevan kortit kaatuneisiin kortteihin, jos säännöt
     * sallivat.
     *
     * @throws Exception Korttia ei voida siirtää, jos siirrettävää korttia ei
     * ole pinossa.
     */
    public void pinoKaatuuJosSaannotSallivat() throws Exception {
        if (Saannot.pinoKaatuu(pino, nykyinenVuoro())) {
            while (!pino.onTyhja()) {
                Kortti kortti = pino.ota(pino.getKortit().get(0));
            }
            lisaaVuoro(nykyinenVuoro().getPelaaja());
        }
    }
}
