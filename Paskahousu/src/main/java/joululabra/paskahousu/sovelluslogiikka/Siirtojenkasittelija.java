package joululabra.paskahousu.sovelluslogiikka;

import java.util.ArrayList;
import java.util.List;
import joululabra.paskahousu.domain.Kortti;
import joululabra.paskahousu.domain.Korttijoukko;
import joululabra.paskahousu.domain.Pakka;
import joululabra.paskahousu.domain.Pelaaja;
import joululabra.paskahousu.domain.Vuoro;

public class Siirtojenkasittelija {

    private Pakka pakka;
    private Korttijoukko pino;
    private Korttijoukko kaatuneet;
    private List<Vuoro> vuorot;

    public Siirtojenkasittelija() {
        this.pakka = new Pakka();
        this.pino = new Korttijoukko();
        this.kaatuneet = new Korttijoukko();
        this.vuorot = new ArrayList<>();
    }

    public Korttijoukko getKaatuneet() {
        return kaatuneet;
    }

    public Pakka getPakka() {
        return pakka;
    }

    public Korttijoukko getPino() {
        return pino;
    }

    public List<Vuoro> getVuorot() {
        return vuorot;
    }

    public Vuoro nykyinenVuoro() {
        if (!vuorot.isEmpty()) {
            return vuorot.get(vuorot.size() - 1);
        }
        return null;
    }

    public void lisaaVuoro(Pelaaja pelaaja) {
        vuorot.add(new Vuoro(pelaaja));
    }

    public void siirraKorttiPinoon(Kortti kortti) throws Exception {
        if (Saannot.korttiSopii(pino, pakka, nykyinenVuoro(), kortti)) {
            Kortti siirrettava = nykyinenVuoro().otaKadesta(kortti);
            pino.lisaa(siirrettava);
            pinoKaatuuJosSaannotSallivat();
        }
    }

    public void taydennaKasi() throws Exception {
        if (Saannot.kadessaLiianVahanKortteja(nykyinenVuoro(), pakka)) {
            Kortti siirrettava = pakka.otaEnsimmainenKortti();
            nykyinenVuoro().lisaaKateen(siirrettava);
        }
    }

    public void kokeileOnnea() throws Exception {
        if (Saannot.saaKokeillaOnnea(nykyinenVuoro())) {
            Kortti kortti = pakka.otaEnsimmainenKortti();
            nykyinenVuoro().lisaaKateen(kortti);
            if (Saannot.korttiSopii(pino, pakka, nykyinenVuoro(), kortti)) {
                siirraKorttiPinoon(kortti);
            } else {
                nostaPino();
            }
        }
        nykyinenVuoro().setJatkuu(false);
    }

    public void nostaPino() throws Exception {
        while (!pino.onTyhja()) {
            Kortti kortti = pino.ota(pino.getKortit().get(0));
            nykyinenVuoro().lisaaKateen(kortti);
        }
        nykyinenVuoro().setJatkuu(false);
    }
    
    public void siirraKaatuneetKortitPakkaan() {
        if (pakka.onTyhja()) {
            while (!kaatuneet.onTyhja()) {
                Kortti kortti = kaatuneet.getKortit().get(0);
                pakka.lisaa(kortti);
            }
            pakka.sekoitaKortit();
        }
    }
    
    public void nostaPinoJosOnPakko() throws Exception {
        if (Saannot.joutuuAinaNostamaanPinon(pino)) {
            nostaPino();
        }
    }

    public void pinoKaatuuJosSaannotSallivat() throws Exception {
        if (Saannot.pinoKaatuu(pino, nykyinenVuoro())) {
            while (!pino.onTyhja()) {
                Kortti kortti = pino.ota(pino.getKortit().get(0));
                kaatuneet.lisaa(kortti);
            }
            lisaaVuoro(nykyinenVuoro().getPelaaja());
        }
    }
}
