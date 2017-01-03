package joululabra.paskahousu.sovelluslogiikka;

import java.util.ArrayList;
import java.util.List;
import joululabra.paskahousu.domain.Pelaaja;

public class Peli {

    private Siirtojenkasittelija sk;
    private Tekoaly tekoaly;
    private List<Pelaaja> pelaajat;

    public Peli() {
        sk = new Siirtojenkasittelija();
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

    public void jaaKortit() throws Exception {
        for (Pelaaja pelaaja : pelaajat) {
            for (int i = 0; i < 5; i++) {
                pelaaja.lisaaKateen(sk.getPakka().otaEnsimmainenKortti());
            }
        }
    }

    public void sekoitaPakka() {
        sk.getPakka().sekoitaKortit();
    }

    public boolean peliJatkuu() {
        int pelissaMukana = 0;
        for (Pelaaja pelaaja : pelaajat) {
            if (!pelaaja.getKasi().onTyhja()) {
                pelissaMukana++;
            }
        }
        return pelissaMukana != 1;
    }
}
