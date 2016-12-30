package joululabra.paskahousu.domain;

import java.util.List;

public class Vuoro {

    private Integer id;
    private Pelaaja pelaaja;
    private Korttijoukko nostetutKortit;
    private Korttijoukko laitettuPinoon;
    private boolean jatkuu;

    public Vuoro() {
        this.id = null;
        this.pelaaja = null;
        this.nostetutKortit = new Korttijoukko();
        this.laitettuPinoon = new Korttijoukko();
        this.jatkuu = true;
    }

    public void nollaa() {
        this.id = null;
        this.pelaaja = null;
        this.nostetutKortit = new Korttijoukko();
        this.laitettuPinoon = new Korttijoukko();
        this.jatkuu = true;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Pelaaja getPelaaja() {
        return pelaaja;
    }

    public void setPelaaja(Pelaaja pelaaja) {
        this.pelaaja = pelaaja;
    }

    public Korttijoukko getNostetutKortit() {
        return nostetutKortit;
    }

    public void lisaaNostettuihinKortteihin(Kortti kortti) {
        this.nostetutKortit.lisaaKortti(kortti);
    }

    public Korttijoukko getLaitettuPinoon() {
        return laitettuPinoon;
    }

    public void lisaaPinoonLaitettuihin(Kortti kortti) {
        this.laitettuPinoon.lisaaKortti(kortti);
    }

    public boolean jatkuu() {
        return jatkuu;
    }

    public void setJatkuu(boolean jatkuu) {
        this.jatkuu = jatkuu;
    }
}
