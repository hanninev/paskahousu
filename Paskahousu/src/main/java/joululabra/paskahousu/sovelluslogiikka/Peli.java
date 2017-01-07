package joululabra.paskahousu.sovelluslogiikka;

import java.util.ArrayList;
import java.util.List;
import joululabra.paskahousu.domain.Pelaaja;

public class Peli {

    private Siirtojenkasittelija sk;
    private Tekoaly tekoaly;
    private List<Pelaaja> pelaajat;

    public Peli() {
        sk = new Siirtojenkasittelija(this);
        pelaajat = new ArrayList<>();
        tekoaly = new Tekoaly(sk);
    }

    public Siirtojenkasittelija getSk() {
        return sk;
    }

    public List<Pelaaja> getPelaajat() {
        return pelaajat;
    }

    public Tekoaly getTekoaly() {
        return tekoaly;
    }

    public void lisaaPelaaja(String nimi) {
        this.pelaajat.add(new Pelaaja(nimi));
    }

    public void jaaKortit() {
        for (Pelaaja pelaaja : pelaajat) {
            for (int i = 0; i < 5; i++) {
                try {
                    pelaaja.lisaaKateen(sk.getPakka().otaEnsimmainenKortti());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public void sekoitaPakka() {
        sk.getPakka().sekoitaKortit();
    }

    /**
     * Metodi kertoo, että peli loppuu, jos vain yhdellä pelaajalla on kädessään
     * kortteja.
     *
     * @return boolean
     */
    public boolean peliJatkuu() {
        int pelissaMukana = 0;
        for (Pelaaja pelaaja : pelaajat) {
            if (!pelaaja.getKasi().onTyhja()) {
                pelissaMukana++;
            }
        }
        return pelissaMukana != 1;
    }

    public void asetaSeuraavaPelaajaVuoroon() {
        for (int i = 0; i < pelaajat.size(); i++) {
            if (pelaajat.get(i).equals(sk.nykyinenVuoro().getPelaaja())) {
                sk.nykyinenVuoro().setJatkuu(false);
                if (i == pelaajat.size() - 1) {
                    sk.lisaaVuoro(pelaajat.get(0));
                    break;
                } else {
                    sk.lisaaVuoro(pelaajat.get(i + 1));
                    break;
                }
            }
        }
    }
}
