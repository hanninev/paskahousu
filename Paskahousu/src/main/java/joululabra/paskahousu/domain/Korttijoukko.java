package joululabra.paskahousu.domain;

import java.util.ArrayList;
import java.util.List;

public class Korttijoukko {

    protected List<Kortti> kortit;

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
     * @param kortti Otettava kortti
     *
     * @return Otettu kortti
     */
    public Kortti ota(Kortti kortti) throws Exception {
        if (kortit.contains(kortti)) {
            kortit.remove(kortti);
            return kortti;
        }
        throw new Exception("Korttia ei löytynyt tästä korttijoukosta");
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

    public boolean onTyhja() {
        return kortit.isEmpty();
    }

    public int korttienMaara() {
        return kortit.size();
    }

}
