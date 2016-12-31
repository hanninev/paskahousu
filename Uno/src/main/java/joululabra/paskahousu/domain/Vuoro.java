package joululabra.paskahousu.domain;

public class Vuoro {

    private Integer id;
    private Pelaaja pelaaja;
    private Korttijoukko nostetutKortit;
    private Korttijoukko otettuKadesta;
    private boolean jatkuu;

    public Vuoro() {
        this.id = null;
        this.pelaaja = null;
        this.nostetutKortit = new Korttijoukko();
        this.otettuKadesta = new Korttijoukko();
        this.jatkuu = true;
    }

    public void nollaa() {
        this.nostetutKortit = new Korttijoukko();
        this.otettuKadesta = new Korttijoukko();
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

    public void lisaaKateen(Kortti kortti) {
        this.nostetutKortit.lisaaKortti(kortti);
        this.pelaaja.lisaaKorttiKateen(kortti);
    }

    public Korttijoukko getLaitettuPinoon() {
        return otettuKadesta;
    }

    public Kortti otaKadesta(Kortti kortti) throws Exception {
        this.otettuKadesta.lisaaKortti(kortti);
        return this.pelaaja.otaKorttiKadesta(kortti);
    }

    public boolean jatkuu() {
        return jatkuu;
    }

    public void setJatkuu(boolean jatkuu) {
        this.jatkuu = jatkuu;
    }
}
