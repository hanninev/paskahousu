package joululabra.uno.sovelluslogiikka;

import java.util.ArrayList;
import java.util.List;
import joululabra.uno.domain.Pelaaja;

public class Peli {

    private Siirtojenkasittelija siirtojenkasittelija;
    private List<Pelaaja> pelaajat;

    public Peli() {
        siirtojenkasittelija = new Siirtojenkasittelija();
        pelaajat = new ArrayList<>();
    }

    public Siirtojenkasittelija getS() {
        return siirtojenkasittelija;
    }

    public List<Pelaaja> getPelaajat() {
        return pelaajat;
    }

    public void lisaaPelaaja(Pelaaja pelaaja) {
        pelaajat.add(pelaaja);
    }

    public void alusta() throws Exception {
        siirtojenkasittelija.getPakka().sekoitaKortit();
        for (Pelaaja pelaaja : pelaajat) {
            for (int i = 0; i < 5; i++) {
                siirtojenkasittelija.getPakka().siirraEnsimmainenKortti(pelaaja.getKasi());
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
