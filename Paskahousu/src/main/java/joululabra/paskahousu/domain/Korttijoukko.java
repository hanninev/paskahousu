package joululabra.paskahousu.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Luokka tarjoaa korttijoukkoon liittyviä metodeita.
 */
public class Korttijoukko {

    protected List<Kortti> kortit;

    /**
     * Metodi tekee uuden olioilmentymän luokasta Korttijoukko.
     *
     */
    public Korttijoukko() {
        kortit = new ArrayList<>();
    }

    public List<Kortti> getKortit() {
        return kortit;
    }

    /**
     * Metodi lisää korttijoukkoon halutun kortin.
     *
     * @param kortti Korttijoukkoon lisättävä kortti
     *
     */
    public void lisaa(Kortti kortti) {
        kortit.add(kortti);
    }

    /**
     * Metodi ottaa korttijoukosta halutun kortin.
     *
     * @throws Exception Jos haluttua korttia ei ole korttijoukossa
     *
     * @param kortti Otettava kortti
     *
     * @return Otettu kortti
     */
    public Kortti ota(Kortti kortti) throws Exception {
        if (kortit.contains(kortti)) {
            kortit.remove(kortti);
            return kortti;
        }
        throw new Exception("Korttia ei löytynyt tästä lähtöjoukosta");
    }

    /**
     * Metodi kertoo viimeisimpänä korttijoukkoon lisätyn kortin.
     *
     * @return Viimeisin kortti
     */
    public Kortti viimeisinKortti() {
        if (kortit.isEmpty()) {
            return null;
        }
        return kortit.get(kortit.size() - 1);
    }

    /**
     * Metodi kertoo, onko korttijoukko tyhjä.
     *
     * @return boolean
     */
    public boolean onTyhja() {
        return kortit.isEmpty();
    }

    /**
     * Metodi kertoo korttijoukon korttien määrän.
     *
     * @return korttien määrä
     */
    public int korttienMaara() {
        return kortit.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (kortit.isEmpty()) {
            sb.append("ei ainuttakaan korttia.");
        } else if (kortit.size() == 1) {
            sb.append("kortin " + kortit.get(0));
        } else {
            sb.append("kortit ");

            for (int i = 0; i < kortit.size(); i++) {
                if (i == kortit.size() - 1) {
                    sb.append(kortit.get(i).toString());
                } else if (i == kortit.size() - 2) {
                    sb.append(kortit.get(i).toString() + " ja ");
                } else {
                    sb.append(kortit.get(i).toString() + ", ");
                }
            }
        }
        return sb.toString();
    }
}
