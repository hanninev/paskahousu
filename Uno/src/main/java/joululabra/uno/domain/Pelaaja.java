package joululabra.uno.domain;

import joululabra.uno.sovelluslogiikka.Saannot;

public class Pelaaja {

    private String nimi;
    private Korttijoukko kasi;
    private boolean vuorossa;
    private Vuoro vuoro;

    public Pelaaja() {
        this.nimi = null;
        this.kasi = new Korttijoukko();
        this.vuorossa = false;
    }

    public Vuoro getVuoro() {
        return vuoro;
    }

    public boolean onVuorossa() {
        return vuorossa;
    }

    public void asetaVuoroon() {
        this.vuorossa = true;
        this.vuoro = new Vuoro();
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

    public void teeSiirto(Kortti kortti, Korttijoukko pino) throws Exception {
        if (!vuoro.onTehtyYksiSiirto()) {
            if (Saannot.siirtoOnLaillinen(pino.viimeisinKortti(), kortti)) {
                this.kasi.siirraKortti(kortti, pino);
                vuoro.asetaTehdyksiYksiSiirto();
            }
        } else {
            if (Saannot.samaArvo(pino.viimeisinKortti(), kortti)) {
                this.kasi.siirraKortti(kortti, pino);
            }
        }
    }

    public void nostaPakasta(Pakka pakka) throws Exception {
        pakka.siirraEnsimmainenKortti(kasi);
        vuoro.lisaaNostokerta();
    }

    public void nostaKorttejaPakasta(Pakka pakka, Integer monta) throws Exception {
        for (int i = 0; i < monta; i++) {
            nostaPakasta(pakka);
        }
    }

    public void nostaPino(Korttijoukko pino) throws Exception {
        while (!pino.onTyhja()) {
            pino.siirraKortti(pino.getKortit().get(0), kasi);
        }
    }
}
