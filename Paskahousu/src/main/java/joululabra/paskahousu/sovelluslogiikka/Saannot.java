package joululabra.paskahousu.sovelluslogiikka;

import joululabra.paskahousu.domain.Kortti;
import joululabra.paskahousu.domain.Korttijoukko;
import joululabra.paskahousu.domain.Pakka;
import joululabra.paskahousu.domain.Vuoro;

public class Saannot {

    /**
     * Metodi kertoo, että pelaaja joutuu aina nostamaan pinon.
     *
     * Pelaaja joutuu aina nostamaan pinon, jos pinossa on tasan yksi kortti ja
     * pinon ylimmän kortin arvo on 10 tai 14 (ässä).
     *
     * @param pino Nykyinen pino
     *
     * @return boolean
     */
    public static boolean pakkoNostaaPino(Korttijoukko pino) {
        return ((pino.korttienMaara() == 1)
                && ((pino.viimeisinKortti().getArvo() == 10)
                || (pino.viimeisinKortti().getArvo() == 14)));
    }

    /**
     * Metodi kertoo, saako pelaaja kokeilla onneaan pakasta.
     *
     * Pelaaja saa kokeilla onneaan pakasta, jos ei ole saman vuoron aikana
     * nostanut kortteja pakasta tai laittanut kortteja pinoon.
     *
     * @param vuoro Käsiteltävä vuoro
     *
     * @return boolean
     */
    public static boolean saaKokeillaOnnea(Vuoro vuoro) {
        return ((vuoro.getNostetut().onTyhja()) && (vuoro.getLaitetut().onTyhja()));
    }

    /**
     * Metodi kertoo, onko vuorossa olevan pelaajan kädessä liian vähän
     * kortteja.
     *
     * Kädessä on liian vähän kortteja, jos pakkaa on jäljellä ja kädessä on
     * alle viisi korttia.
     *
     * @param vuoro Käsiteltävä vuoro
     *
     * @return boolean
     */
    public static boolean kadessaLiianVahanKortteja(Vuoro vuoro, Pakka pakka) {
        return ((!pakka.onTyhja()) && (vuoro.getPelaaja().getKasi().korttienMaara() < 5));
    }

    /**
     * Metodi kertoo, jos pino kaatuu.
     *
     * Jos pinossa olevien korttien määrä on enemmän kuin yksi ja pinon
     * viimeisimmän kortin arvo on 10 tai 14 (ässä), pino kaatuu.
     *
     * @param pino Nykyinen pino
     * @param vuoro Käsiteltävä vuoro
     *
     * @return boolean
     */
    public static boolean pinoKaatuu(Korttijoukko pino, Vuoro vuoro) {
        return ((pino.korttienMaara() > 1)
                && ((pino.viimeisinKortti().getArvo() == 10)
                || (pino.viimeisinKortti().getArvo() == 14)));
    }

    /**
     * Metodi kertoo, sopiiko kortti pinoon.
     *
     * Jos vuoron aikana laitettujen korttien joukko on tyhjä, sovelletaan
     * vuoron ensimmäiseen siirtoon liittyviä sääntöjä. Muussa tapauksessa
     * lisättävän kortin arvon tulee olla sama kuin edellisen kortin arvo.
     *
     * @param pino Nykyinen pino
     * @param vuoro Käsiteltävä vuoro
     * @param kortti Kokeiltava kortti
     * @param pakka Nykyinen pakka
     *
     * @return boolean
     */
    public static boolean korttiSopii(Korttijoukko pino, Pakka pakka, Vuoro vuoro, Kortti kortti) {
        if (vuoro.getLaitetut().onTyhja()) {
            return vuoronEnsimmainenSiirtoOk(pakka, pino, kortti);
        } else {
            return vuoronToinenTaiUseampiSiirtoOk(vuoro, kortti);
        }
    }

    /**
     * Metodi kertoo, sopiiko vuoron ensimmäisenä siirrettävä kortti pinoon.
     *
     * Vuoron ensimmäinen siirto sopii aina pinoon, jos lisättävän kortin arvo
     * on 2 tai jos sekä pakka että pino ovat tyhjiä.
     *
     * Jos pakassa on kortteja, mutta pino on tyhjä, sopii tyhjään pinoon
     * kortit, joiden arvo on pienempi kuin 11 (jätkä) tai tasan 14 (ässä).
     *
     * Jos sekä pakassa että pinossa on kortteja ja pinon viimeisin kortti ei
     * ole arvoltaan 2, pinoon sopii kaikki kortit, joiden arvo on yhtä suuri
     * tai suurempi kuin pinon ylin kortti.
     *
     * @param pino Nykyinen pino
     * @param kortti Kokeiltava kortti
     * @param pakka Nykyinen pakka
     *
     * @return boolean
     */
    public static boolean vuoronEnsimmainenSiirtoOk(Pakka pakka, Korttijoukko pino, Kortti kortti) {
        if (kortti.getArvo() == 2) {
            return true;
        }

        if (pino.onTyhja()) {
            if (pakka.onTyhja()) {
                return true;
            } else if (kortti.getArvo() < 11 || kortti.getArvo() == 14) {
                return true;
            }
        } else {
            if ((pino.viimeisinKortti().getArvo() != 2) && (pino.viimeisinKortti().getArvo() <= kortti.getArvo())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Metodi kertoo, sopiiko saman vuoron aikana laitettu seuraava kortti
     * pakkaan, jos vuoron ensimmäinen kortti on laitettu.
     *
     * @param vuoro Käsiteltävä vuoro
     * @param kortti Kokeiltava kortti
     *
     * @return boolean
     */
    public static boolean vuoronToinenTaiUseampiSiirtoOk(Vuoro vuoro, Kortti kortti) {
        return ((vuoro.getLaitetut().getKortit().get(0).getArvo() == kortti.getArvo()) && (kortti.getArvo() != 2) && (kortti.getArvo() != 10) && (kortti.getArvo() != 14));
    }

}
