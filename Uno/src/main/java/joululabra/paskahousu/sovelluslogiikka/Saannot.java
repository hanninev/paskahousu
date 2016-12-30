package joululabra.paskahousu.sovelluslogiikka;

import joululabra.paskahousu.domain.Kortti;
import joululabra.paskahousu.domain.Korttijoukko;
import joululabra.paskahousu.domain.Pakka;
import joululabra.paskahousu.domain.Vuoro;

public class Saannot {

    public static boolean joutuuAinaNostamaanPinon(Korttijoukko pino) {
        if ((pino.korttienMaara() == 1)
                && ((pino.viimeisinKortti().getArvo() == 10)
                || (pino.viimeisinKortti().getArvo() == 14))) {
            return true;
        }
        return false;
    }

    public static boolean saaKokeillaOnnea(Vuoro vuoro) {
        if ((vuoro.getNostetutKortit().korttienMaara() == 0) && (vuoro.getLaitettuPinoon().korttienMaara() == 0)) {
            return true;
        }
        return false;
    }

    public static boolean kadessaLiianVahanKortteja(Vuoro vuoro, Pakka pakka) {
        if (vuoro == null) {
            return false;
        }

        if ((!pakka.onTyhja()) && (vuoro.getPelaaja().getKasi().korttienMaara() < 5)) {
            return true;
        }
        return false;
    }

    public static boolean pinoKaatuu(Korttijoukko pino, Vuoro vuoro) {
        if ((pino.korttienMaara() > 1)
                && ((vuoro.getLaitettuPinoon().viimeisinKortti().getArvo() == 10)
                || (vuoro.getLaitettuPinoon().viimeisinKortti().getArvo() == 14))) {
            return true;
        }
        return false;
    }

    public static boolean korttiSopii(Korttijoukko pino, Pakka pakka, Vuoro vuoro, Kortti kortti) {
        if (vuoro.getLaitettuPinoon().onTyhja()) {
            return vuoronEnsimmainenSiirtoOk(pakka, pino, kortti);
        } else {
            return samaArvo(vuoro, kortti);
        }
    }

    private static boolean vuoronEnsimmainenSiirtoOk(Pakka pakka, Korttijoukko pino, Kortti kortti) {
        if (kortti.getArvo() == 2) {
            return true;
        }

        if (pino.onTyhja()) {
            if (pakka.onTyhja()) {
                return true;
            } else if (kortti.getArvo() < 11) {
                return true;
            }
        } else {
            if ((pino.viimeisinKortti().getArvo() != 2) && (pino.viimeisinKortti().getArvo() <= kortti.getArvo())) {
                return true;
            }
        }
        return false;
    }

    private static boolean samaArvo(Vuoro vuoro, Kortti kortti) {
        return (vuoro.getLaitettuPinoon().getKortit().get(0).getArvo() == kortti.getArvo());
    }

}
