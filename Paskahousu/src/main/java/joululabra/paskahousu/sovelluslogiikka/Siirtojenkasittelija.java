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
        if (vuorot.isEmpty()) {
            return null;
        }
        return vuorot.get(vuorot.size() - 1);
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
     * @throws Exception Jos siirto on vastoin sääntöjä, korttia ei voida lisätä
     * pinoon.
     * @param kortti Siirrettävä kortti
     *
     */
    public void siirraKorttiPinoon(Kortti kortti) throws Exception {
        if (peli.peliJatkuu() && Saannot.korttiSopii(pino, pakka, nykyinenVuoro(), kortti)) {
            pino.lisaa(nykyinenVuoro().getPelaaja().otaKadesta(kortti));
            nykyinenVuoro().laittoiKortinPinoon();
        }
    }

    private void taydennaKasi() throws Exception {
        while (Saannot.kadessaLiianVahanKortteja(nykyinenVuoro(), pakka)) {
            nykyinenVuoro().getPelaaja().lisaaKateen(pakka.otaEnsimmainenKortti());
        }
    }

    /**
     * Jos vuorossa oleva pelaaja saa sääntöjen mukaan kokeilla onnea, metodi
     * nostaa pakasta ensimmäisen kortin ja lisää sen pelaajan käteen.
     *
     * @throws Exception Jos siirrettävää korttia ei ole pakassa, ei siirtoa
     * voida suorittaa.
     */
    public void kokeileOnnea() throws Exception {
        if (Saannot.saaKokeillaOnnea(nykyinenVuoro())) {
            nykyinenVuoro().getPelaaja().lisaaKateen(pakka.otaEnsimmainenKortti());
            nykyinenVuoro().setKokeiliOnnea(true);
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
            nykyinenVuoro().getPelaaja().lisaaKateen(pino.ota(pino.getKortit().get(0)));
        }
        nykyinenVuoro().setNostiPinon(true);
    }

    private void pinoKaatuuJosSaannotSallivat() throws Exception {
        if (Saannot.pinoKaatuu(pino, nykyinenVuoro())) {
            while (!pino.onTyhja()) {
                Kortti kortti = pino.ota(pino.getKortit().get(0));
            }
            nykyinenVuoro().setKaatoiPinon(true);
        }
    }

    /**
     * Metodi viimeistelee vuoron.
     *
     * Jos säännöt sallivat, niin pino kaatuu. Varmistetaan myös, että
     * pelaajalla on sääntöjen mukaan riittävästi kortteja kädessään.
     *
     * @throws Exception Korttia ei voida siirtää, jos siirrettävää korttia ei
     * ole lähtöjoukossa.
     */
    public void kaadaPinoJosKaatuuJaTaydennaKasi() throws Exception {
        pinoKaatuuJosSaannotSallivat();
        taydennaKasi();
    }

    /**
     * Vuoro siirtyy seuraavalle pelaajalle, ellei nykyinen saa jatkaa.
     *
     * Nykyinen pelaaja saa uuden vuoron, jos hän on kaatanut pinon.
     *
     */
    public void vuoronVaihtuminen() {
        if (peli.getSk().nykyinenVuoro().isKaatoiPinon()) {
            peli.getSk().lisaaVuoro(peli.getSk().nykyinenVuoro().getPelaaja());
            peli.getSk().getVuorot().get(vuorot.size() - 2).setJatkuu(false);
        } else {
            peli.asetaSeuraavaPelaajaVuoroon();
        }
    }
}
