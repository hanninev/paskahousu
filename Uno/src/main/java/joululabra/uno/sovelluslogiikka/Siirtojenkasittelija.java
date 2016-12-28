package joululabra.uno.sovelluslogiikka;

import joululabra.uno.domain.Kortti;
import joululabra.uno.domain.Korttijoukko;
import joululabra.uno.domain.Pakka;
import joululabra.uno.domain.Pelaaja;

public class Siirtojenkasittelija {

    Pakka pakka;
    Korttijoukko pino;

    public Siirtojenkasittelija() {
        this.pakka = new Pakka();
        this.pino = new Korttijoukko();
    }

    public Pakka getPakka() {
        return pakka;
    }

    public Korttijoukko getPino() {
        return pino;
    }

    public void siirraPinostaPakkaan() throws Exception {
        if (pakka.onTyhja()) {
            for (int i = 0; i < pino.getKorttienMaara() - 1; i++) {
                pino.siirraKortti(pino.getKortit().get(pino.getKortit().size() - 2), pakka);
            }
        }
    }

    public void teeSiirto(Pelaaja pelaaja, Kortti kortti) throws Exception {
        if (Saannot.siirtoOnLaillinen(pino.viimeisinKortti(), kortti)) {
            pelaaja.getKasi().siirraKortti(kortti, pino);
        }
    }

    public void otaKorttejaPakasta(Pelaaja pelaaja, Integer maara) throws Exception {
        for (int i = 0; i < maara; i++) {
            otaKorttiPakasta(pelaaja);
        }
    }

    public void otaKorttiPakasta(Pelaaja pelaaja) throws Exception {
        pakka.siirraEnsimmainenKortti(pelaaja.getKasi());
    }

    public void nostaPino(Pelaaja pelaaja) throws Exception {
        while (!pino.onTyhja()) {
            pino.siirraKortti(pino.getKortit().get(0), pelaaja.getKasi());
        }
    }
}
