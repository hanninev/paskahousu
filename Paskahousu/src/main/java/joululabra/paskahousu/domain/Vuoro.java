package joululabra.paskahousu.domain;

public class Vuoro {

    private Pelaaja pelaaja;
    private Korttijoukko nostetut;
    private Korttijoukko laitetut;
    private boolean jatkuu;

    public Vuoro(Pelaaja pelaaja) {
        this.pelaaja = pelaaja;
        this.nostetut = new Korttijoukko();
        this.laitetut = new Korttijoukko();
        this.jatkuu = true;
    }

    public Pelaaja getPelaaja() {
        return pelaaja;
    }

    public Korttijoukko getNostetut() {
        return nostetut;
    }

    public Korttijoukko getLaitetut() {
        return laitetut;
    }

    /**
     * Metodilla voidaan lisätä vuorossa olevan pelaajan käteen haluttu kortti.
     *
     * @param kortti Käteen lisättävä kortti
     *
     */
    public void lisaaKateen(Kortti kortti) {
        this.nostetut.lisaa(kortti);
        this.pelaaja.lisaaKateen(kortti);
    }

    /**
     * Metodilla voidaan ottaa vuorossa olevan pelaajan kädestä haluttu kortti.
     *
     * @param kortti Kädestä otettava kortti
     *
     */
    public Kortti otaKadesta(Kortti kortti) throws Exception {
        this.laitetut.lisaa(kortti);
        return this.pelaaja.otaKadesta(kortti);
    }

    public boolean jatkuu() {
        return jatkuu;
    }

    public void setJatkuu(boolean jatkuu) {
        this.jatkuu = jatkuu;
    }
}
