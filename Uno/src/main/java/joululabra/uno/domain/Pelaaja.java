package joululabra.uno.domain;

public class Pelaaja {

    private String nimi;
    private Korttijoukko kasi;
    private boolean vuorossa;

    public Pelaaja() {
        this.nimi = null;
        this.kasi = new Korttijoukko();
        this.vuorossa = false;
    }

    public boolean onVuorossa() {
        return vuorossa;
    }

    public void asetaVuoroon() {
        this.vuorossa = true;
    }

    public void asetaPoisVuorosta() {
        this.vuorossa = false;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public Korttijoukko getKasi() {
        return kasi;
    }

    public String getNimi() {
        return nimi;
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
}
