package joululabra.paskahousu.domain;

public class Pelaaja {

    private String nimi;
    private Korttijoukko kasi;

    public Pelaaja() {
        this.nimi = null;
        this.kasi = new Korttijoukko();
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public String getNimi() {
        return nimi;
    }

    public Korttijoukko getKasi() {
        return kasi;
    }

    public Kortti pieninKortti() {
        Kortti pienin = kasi.getKortit().get(0);
        for (Kortti kortti : kasi.getKortit()) {
            if (kortti.getArvo() <= pienin.getArvo()) {
                pienin = kortti;
            }
        }
        return pienin;
    }

    public void lisaaKorttiKateen(Kortti kortti) {
        kasi.lisaaKortti(kortti);
    }

    public Kortti otaKorttiKadesta(Kortti kortti) throws Exception {
        return kasi.otaKortti(kortti);
    }
}
