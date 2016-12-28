package joululabra.uno.sovelluslogiikka;

import joululabra.uno.domain.Kortti;
import joululabra.uno.domain.Pelaaja;

public class Saannot {

    public static boolean samaVari(Kortti pinonYlin, Kortti yritetaanLaittaa) {
        return (pinonYlin.getVari() == yritetaanLaittaa.getVari());
    }

    public static boolean samaArvo(Kortti pinonYlin, Kortti yritetaanLaittaa) {
        return (pinonYlin.getArvo() == yritetaanLaittaa.getArvo());
    }

    public static boolean siirtoOnLaillinen(Kortti pinonYlin, Kortti yritetaanLaittaa) {
        if (pinonYlin == null) {
            return true;
        }
        return (samaArvo(pinonYlin, yritetaanLaittaa) || samaVari(pinonYlin, yritetaanLaittaa));
    }

    public static boolean seuraavaNostaaKaksi(Kortti pinonYlin) {
        if (pinonYlin == null) {
            return false;
        }

        if (pinonYlin.getArvo() == Kortti.PLUSKAKSI) {
            return true;
        }
        return false;
    }

    public static boolean seuraavaJaaValista(Kortti pinonYlin) {
        if (pinonYlin == null) {
            return false;
        }

        if (pinonYlin.getArvo() == Kortti.UUDESTAAN || pinonYlin.getArvo() == Kortti.PLUSKAKSI) {
            return true;
        }
        return false;
    }

    public static boolean kadessaSopiviaKortteja(Pelaaja pelaaja, Kortti pinonYlin) {
        boolean arvo = false;
        for (Kortti kortti : pelaaja.getKasi().getKortit()) {
            if (siirtoOnLaillinen(pinonYlin, kortti)) {
                arvo = true;
            }
        }
        return arvo;
    }

    public static boolean joutuuNostamaanPinon(Pelaaja pelaaja) {
        if (pelaaja.getVuoro().getNostokerrat() >= 3) {
            return true;
        }
        return false;
    }

    public static boolean saaJatkaa(Pelaaja pelaaja) {
        if (pelaaja.getVuoro().getNostokerrat() > 3) {
            return false;
        }
        return true;
    }

}
