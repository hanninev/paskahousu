package joululabra.paskahousu.sovelluslogiikka;

import joululabra.paskahousu.domain.Kortti;
import joululabra.paskahousu.domain.Korttijoukko;
import joululabra.paskahousu.domain.Pakka;
import joululabra.paskahousu.domain.Vuoro;

/**
 * Luokka tarjoaa boolean-arvon palauttavia metodeja, jotka määrittävät pelin
 * säännöt.
 */
public class Saannot {

    /**
     * Metodi kertoo, saako pelaaja kokeilla onneaan pakasta.
     *
     * Pelaaja saa kokeilla onneaan pakasta, jos ei ole saman vuoron aikana
     * nostanut kortteja pakasta, nostanut pinoa tai laittanut kortteja pinoon.
     *
     * @param vuoro Käsiteltävä vuoro
     *
     * @return boolean
     */
    public static boolean saaKokeillaOnnea(Vuoro vuoro) {
        return ((vuoro.getLaitettuPinoon() == 0) && (!vuoro.isKokeiliOnnea()) && (!vuoro.isNostiPinon()));
    }

    /**
     * Metodi kertoo, onko vuorossa olevan pelaajan kädessä liian vähän
     * kortteja.
     *
     * Kädessä on liian vähän kortteja, jos pakkaa on jäljellä ja kädessä on
     * alle viisi korttia.
     *
     * @param vuoro Käsiteltävä vuoro
     * @param pakka Nykyinen pakka
     *
     * @return boolean
     */
    public static boolean kadessaLiianVahanKortteja(Vuoro vuoro, Pakka pakka) {
        return ((!pakka.onTyhja()) && (vuoro.getPelaaja().getKasi().korttienMaara() < 5));
    }

    /**
     * Metodi kertoo, jos pino kaatuu.
     *
     * Jos pinon ylimmän kortin arvo on 10 tai 14 (ässä), pino kaatuu.
     *
     * @param pino Nykyinen pino
     * @param vuoro Käsiteltävä vuoro
     *
     * @return boolean
     */
    public static boolean pinoKaatuu(Korttijoukko pino, Vuoro vuoro) {
        if (pino.onTyhja()) {
            return false;
        }

        return ((pino.viimeisinKortti().getArvo() == 10)
                || (pino.viimeisinKortti().getArvo() == 14));
    }

    /**
     * Metodi kertoo, sopiiko kortti pinoon.
     *
     * Jos vuoron aikana laitettujen korttien joukko on tyhjä, sovelletaan
     * vuoron ensimmäiseen siirtoon liittyviä sääntöjä. Muussa tapauksessa
     * lisättävän kortin arvon tulee olla sama kuin edellisen kortin arvo.
     *
     * Vuoron ensimmäinen siirto sopii aina pinoon, jos lisättävän kortin arvo
     * on 2 tai jos sekä pakka että pino ovat tyhjiä.
     *
     * Jos pakassa on kortteja, mutta pino on tyhjä, sopii tyhjään pinoon
     * kortit, joiden arvo on pienempi kuin 11 (jätkä) tai tasan 14 (ässä).
     *
     * Jos sekä pakassa että pinossa on kortteja ja pinon ylin kortti ei ole
     * arvoltaan 2, pinoon sopii kaikki kortit, joiden arvo on yhtä suuri tai
     * suurempi kuin pinon ylin kortti.
     *
     * @param pino Nykyinen pino
     * @param vuoro Käsiteltävä vuoro
     * @param kortti Kokeiltava kortti
     * @param pakka Nykyinen pakka
     *
     * @return boolean
     */
    public static boolean korttiSopii(Korttijoukko pino, Pakka pakka, Vuoro vuoro, Kortti kortti) throws Exception {
        if (vuoro.getLaitettuPinoon() == 0) {
            return vuoronEnsimmainenSiirtoOk(pakka, pino, kortti);
        } else {
            return vuoronToinenTaiUseampiSiirtoOk(pino, kortti);
        }
    }

    private static boolean vuoronEnsimmainenSiirtoOk(Pakka pakka, Korttijoukko pino, Kortti kortti) throws Exception {
        if (pino.onTyhja()) {
            if ((!pakka.onTyhja()) && (kortti.getArvo() > 10) && (kortti.getArvo() < 14)) {
                throw new Exception("Pakkaa on jäljellä, joten et voi laittaa tyhjään pöytään kuvakorttia!<br>");
            }
        } else if ((pino.viimeisinKortti().getArvo() > kortti.getArvo()) && (kortti.getArvo() != 2)) {
            throw new Exception("Laittamasi kortin täytyy olla arvoltaan vähintään yhtä suuri kuin pinon ylin.<br>"
                    + "Ainoastaan kakkonen sopii minkä tahansa kortin päälle.");
        } else if ((pino.viimeisinKortti().getArvo() == 2) && (kortti.getArvo() != 2)) {
            throw new Exception("Kakkosen päälle käy vain kakkonen!");
        }

        return true;
    }

    private static boolean vuoronToinenTaiUseampiSiirtoOk(Korttijoukko pino, Kortti kortti) throws Exception {
        if ((pino.viimeisinKortti().getArvo() == 2) && (kortti.getArvo() == 2)) {
            throw new Exception("Et voi laittaa pinoon kahta kakkosta samalla vuorolla!");
        } else if (((pino.viimeisinKortti().getArvo() == 10) && (kortti.getArvo() == 10))
                || ((pino.viimeisinKortti().getArvo() == 14) && (kortti.getArvo() == 14))) {
            throw new Exception("Et voi laittaa pinoon kahta kaatokorttia samalla vuorolla!");
        } else if (pino.viimeisinKortti().getArvo() != kortti.getArvo()) {
            throw new Exception("Voit laittaa saman vuoron aikana pinoon vain saman arvoisia kortteja!<br>Lopeta vuoro valitsemalla valmis.");
        }

        return true;
    }
}
