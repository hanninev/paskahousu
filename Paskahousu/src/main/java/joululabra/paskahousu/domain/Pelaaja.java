package joululabra.paskahousu.domain;

import java.util.Objects;

/**
 * Luokka tarjoaa pelaajaan liittyviä metodeita.
 */
public class Pelaaja {

    private String nimi;
    private Kasi kasi;
    private boolean tekoaly;

    /**
     * Metodi tekee uuden olioilmentymän luokasta Pelaaja.
     *
     * @param nimi Pelaajan nimi
     *
     */
    public Pelaaja(String nimi) {
        this.nimi = nimi;
        this.kasi = new Kasi();
        this.tekoaly = false;
    }

    public String getNimi() {
        return nimi;
    }

    public Kasi getKasi() {
        return kasi;
    }

    public boolean isTekoaly() {
        return tekoaly;
    }

    public void setTekoaly(boolean tekoaly) {
        this.tekoaly = tekoaly;
    }

    /**
     * Metodi kertoo, mikä pelaajan kädessä olevista korteista on arvoltaan
     * pienin.
     *
     * @return pienin kortti
     */
    public Kortti pieninKortti() {
        Kortti pienin = kasi.getKortit().get(0);
        for (Kortti kortti : kasi.getKortit()) {
            if (kortti.getArvo() <= pienin.getArvo()) {
                pienin = kortti;
            }
        }
        return pienin;
    }

    /**
     * Metodilla voidaan lisätä kyseisen käyttäjän käteen haluttu kortti.
     *
     * @param kortti Käteen lisättävä kortti
     */
    public void lisaaKateen(Kortti kortti) {
        kasi.lisaa(kortti);
    }

    /**
     * Metodilla voidaan ottaa kyseisen pelaajan kädestä haluttu kortti.
     *
     * @param kortti Kädestä otettava kortti
     *
     * @throws Exception Korttia ei voi ottaa kädestä, jos kädessä ei ole
     * otettavaa korttia.
     *
     * @return Kädestä otettu kortti
     */
    public Kortti otaKadesta(Kortti kortti) throws Exception {
        return kasi.ota(kortti);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.nimi);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pelaaja other = (Pelaaja) obj;
        if (!Objects.equals(this.nimi, other.nimi)) {
            return false;
        }
        return true;
    }

    /**
     * Metodi kertoo, mikä pelaajan kädessä olevista korteista on arvoltaan
     * suurin.
     *
     * @return suurin kortti
     */
    public Kortti suurinKortti() {
        Kortti suurin = kasi.getKortit().get(0);
        for (Kortti kortti : kasi.getKortit()) {
            if (kortti.getArvo() >= suurin.getArvo()) {
                suurin = kortti;
            }
        }
        return suurin;
    }

}
