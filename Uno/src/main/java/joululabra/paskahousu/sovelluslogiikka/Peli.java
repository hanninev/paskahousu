package joululabra.paskahousu.sovelluslogiikka;

import java.util.ArrayList;
import java.util.List;
import joululabra.paskahousu.domain.Korttijoukko;
import joululabra.paskahousu.domain.Pakka;
import joululabra.paskahousu.domain.Pelaaja;
import joululabra.paskahousu.domain.Vuoro;

public class Peli {

    private Pakka pakka;
    private Korttijoukko pino;
    private Korttijoukko kaatuneetKortit;
    private List<Pelaaja> pelaajat;
    private List<Vuoro> vuorot;

    public Peli() {
        pakka = new Pakka();
        pino = new Korttijoukko();
        kaatuneetKortit = new Korttijoukko();
        pelaajat = new ArrayList<>();
        vuorot = new ArrayList<>();
    }

    public Pakka getPakka() {
        return pakka;
    }

    public Korttijoukko getKaatuneetKortit() {
        return kaatuneetKortit;
    }

    public Korttijoukko getPino() {
        return pino;
    }

    public List<Pelaaja> getPelaajat() {
        return pelaajat;
    }

    public List<Vuoro> getVuorot() {
        return vuorot;
    }

    public void lisaaPelaaja(Pelaaja pelaaja) {
        this.pelaajat.add(pelaaja);
    }

    public void alusta() throws Exception {
        pakka.sekoitaKortit();
        for (Pelaaja pelaaja : pelaajat) {
            for (int i = 0; i < 5; i++) {
                pelaaja.lisaaKorttiKateen(pakka.otaEnsimmainenKortti());
            }
        }
    }

    public void kukaAloittaa() {
        Pelaaja aloittava = pelaajat.get(0);
        for (Pelaaja pelaaja : pelaajat) {
            if (pelaaja.pieninKortti().getArvo() < aloittava.pieninKortti().getArvo()) {
                aloittava = pelaaja;
            }
            // Tähän vielä toiminta jos kahdella pelaajalla on saman kokoinen pienin kortti.
        }
        lisaaVuoro(aloittava);
    }

    public Vuoro lisaaVuoro(Pelaaja pelaaja) {
        Vuoro vuoro = new Vuoro();
        vuorot.add(vuoro);
        vuoro.setId(vuorot.size() + 1);
        vuoro.setPelaaja(pelaaja);
        vuoro.setJatkuu(true);
        return vuoro;
    }

    public Vuoro edellinenVuoro() {
        if (this.vuorot.size() > 1) {
            return this.vuorot.get(vuorot.size() - 2);
        }
        return null;
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
