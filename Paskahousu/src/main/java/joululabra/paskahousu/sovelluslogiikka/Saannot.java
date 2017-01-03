package joululabra.paskahousu.sovelluslogiikka;

import joululabra.paskahousu.domain.Kortti;
import joululabra.paskahousu.domain.Korttijoukko;
import joululabra.paskahousu.domain.Pakka;
import joululabra.paskahousu.domain.Vuoro;

public class Saannot {

    public static boolean joutuuAinaNostamaanPinon(Korttijoukko pino) {
        return ((pino.korttienMaara() == 1)
                && ((pino.viimeisinKortti().getArvo() == 10)
                || (pino.viimeisinKortti().getArvo() == 14)));
    }

    public static boolean saaKokeillaOnnea(Vuoro vuoro) {
        return ((vuoro.getNostetut().onTyhja()) && (vuoro.getLaitetut().onTyhja()));
    }

    public static boolean kadessaLiianVahanKortteja(Vuoro vuoro, Pakka pakka) {
        return ((!pakka.onTyhja()) && (vuoro.getPelaaja().getKasi().korttienMaara() < 5));
    }

    public static boolean pinoKaatuu(Korttijoukko pino, Vuoro vuoro) {
        return ((pino.korttienMaara() > 1)
                && ((pino.viimeisinKortti().getArvo() == 10)
                || (pino.viimeisinKortti().getArvo() == 14)));
    }

    public static boolean korttiSopii(Korttijoukko pino, Pakka pakka, Vuoro vuoro, Kortti kortti) {
        if (vuoro.getLaitetut().onTyhja()) {
            return vuoronEnsimmainenSiirtoOk(pakka, pino, kortti);
        } else {
            return samaArvo(vuoro, kortti);
        }
    }

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

    public static boolean samaArvo(Vuoro vuoro, Kortti kortti) {
        return (vuoro.getLaitetut().getKortit().get(0).getArvo() == kortti.getArvo());
    }

}
