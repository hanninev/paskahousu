package joululabra.uno.sovelluslogiikka;

import java.util.ArrayList;
import java.util.List;
import joululabra.uno.domain.Korttijoukko;
import joululabra.uno.domain.Pakka;
import joululabra.uno.domain.Pelaaja;

public class Peli {

    private Pakka pakka;
    private Korttijoukko pino;
    private List<Pelaaja> pelaajat;

    public Peli() {
        pakka = new Pakka();
        pino = new Korttijoukko();
        pelaajat = new ArrayList<>();
    }

    public Korttijoukko getPino() {
        return pino;
    }

    public Pakka getPakka() {
        return pakka;
    }

    public List<Pelaaja> getPelaajat() {
        return pelaajat;
    }

    public void lisaaPelaaja(Pelaaja pelaaja) {
        pelaajat.add(pelaaja);
    }

    public void siirraPinostaPakkaan() throws Exception {
        if (pakka.onTyhja()) {
            for (int i = 0; i < pino.getKorttienMaara() - 1; i++) {
                pino.siirraKortti(pino.getKortit().get(pino.getKortit().size() - 2), pakka);
            }
        }
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

    public boolean peliPaattynyt() {
        int pelaajatJoillaKortteja = 0;
        for (Pelaaja pelaaja : pelaajat) {
            if (!pelaaja.getKasi().onTyhja()) {
                pelaajatJoillaKortteja++;
            }
        }
        return (pelaajatJoillaKortteja == 1);
    }

    public Pelaaja getSeuraavaPelaaja() {
        for (int i = 0; i < pelaajat.size(); i++) {
            if (pelaajat.get(i).onVuorossa()) {
                if (i == pelaajat.size() - 1) {
                    return pelaajat.get(0);
                } else {
                    return pelaajat.get(i + 1);
                }
            }
        }
        return null;
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
