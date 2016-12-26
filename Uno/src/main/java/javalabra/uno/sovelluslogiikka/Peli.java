package javalabra.uno.sovelluslogiikka;

import java.util.ArrayList;
import java.util.List;
import javalabra.uno.domain.Kortti;
import javalabra.uno.domain.Korttijoukko;
import javalabra.uno.domain.Pakka;
import javalabra.uno.domain.Pelaaja;

public class Peli {

    private Pakka pakka;
    private Korttijoukko pino;
    private List<Pelaaja> pelaajat;

    public Peli() {
        pakka = new Pakka();
        pino = new Korttijoukko();
        pelaajat = new ArrayList<>();
    }

    public List<Pelaaja> getPelaajat() {
        return pelaajat;
    }

    public Korttijoukko getPino() {
        return pino;
    }

    public Pakka getPakka() {
        return pakka;
    }

    public void lisaaPelaaja(Pelaaja pelaaja) {
        pelaajat.add(pelaaja);
    }

    public void alusta() throws Exception {
        pakka.sekoitaKortit();
        for (Pelaaja pelaaja : pelaajat) {
            for (int i = 0; i < 5; i++) {
                pakka.siirraEnsimmainenKortti(pelaaja.getKasi());
            }
        }
    }

    public void kukaAloittaa() {
        Pelaaja aloittava = pelaajat.get(0);
        for (Pelaaja pelaaja : pelaajat) {
            if (pelaaja.pieninKortti().getArvo() < aloittava.pieninKortti().getArvo()) {
                aloittava = pelaaja;
            }
        }
        aloittava.asetaVuoroon();
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
        if (!pakka.onTyhja()) {
            pakka.siirraEnsimmainenKortti(pelaaja.getKasi());
        }
    }

    public boolean peliPaattynyt() {
        for (Pelaaja pelaaja : pelaajat) {
            if (pelaaja.getKasi().getKorttienMaara() == 0) {
                return true;
            }
        }
        return false;
    }

    public void seuraavanVuoro() {
        for (int i = 0; i < pelaajat.size(); i++) {
            if (pelaajat.get(i).onVuorossa()) {
                pelaajat.get(i).asetaPoisVuorosta();
                if (i == pelaajat.size() - 1) {
                    pelaajat.get(0).asetaVuoroon();
                } else {
                    pelaajat.get(i + 1).asetaVuoroon();
                }
                return;
            }
        }
    }

}
