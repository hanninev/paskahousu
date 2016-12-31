package joululabra.paskahousu.sovelluslogiikka;

import joululabra.paskahousu.domain.Kortti;
import joululabra.paskahousu.domain.Korttijoukko;
import joululabra.paskahousu.domain.Pakka;
import joululabra.paskahousu.domain.Vuoro;

public class Siirtojenkasittelija {

    public void siirraKorttiPinoon(Kortti kortti, Pakka pakka, Korttijoukko kaatuneet, Korttijoukko pino, Vuoro vuoro) throws Exception {
        if (Saannot.korttiSopii(pino, pakka, vuoro, kortti)) {
            Kortti siirrettava = vuoro.otaKadesta(kortti);
            pino.lisaaKortti(siirrettava);
            pinoKaatuu(kaatuneet, pino, vuoro);
        }
    }

    public void taydennaKasi(Pakka pakka, Vuoro vuoro) throws Exception {
        if (Saannot.kadessaLiianVahanKortteja(vuoro, pakka)) {
            Kortti siirrettava = pakka.otaEnsimmainenKortti();
            vuoro.lisaaKateen(siirrettava);
        }
    }

    public void kokeileOnnea(Pakka pakka, Korttijoukko pino, Korttijoukko kaatuneet, Vuoro vuoro) throws Exception {
        if (Saannot.saaKokeillaOnnea(vuoro)) {
            Kortti kortti = pakka.otaEnsimmainenKortti();
            vuoro.lisaaKateen(kortti);
            if (Saannot.korttiSopii(pino, pakka, vuoro, kortti)) {
                siirraKorttiPinoon(kortti, pakka, kaatuneet, pino, vuoro);
            } else {
                nostaPino(pino, vuoro);
            }
        }
        vuoro.setJatkuu(false);
    }

    public void nostaPino(Korttijoukko pino, Vuoro vuoro) throws Exception {
        while (!pino.onTyhja()) {
            Kortti kortti = pino.otaKortti(pino.getKortit().get(0));
            vuoro.lisaaKateen(kortti);
        }
        vuoro.setJatkuu(false);
    }

    public void pinoKaatuu(Korttijoukko kaatuneet, Korttijoukko pino, Vuoro vuoro) throws Exception {
        if (Saannot.pinoKaatuu(pino, vuoro)) {
            while (!pino.onTyhja()) {
                Kortti kortti = pino.otaKortti(pino.getKortit().get(0));
                kaatuneet.lisaaKortti(kortti);
            }
            vuoro.nollaa();
        }
    }
}
