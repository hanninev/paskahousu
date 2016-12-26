package javalabra.uno.domain;

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

    public void lisaaKortti(Kortti kortti) {
        kortit.add(kortti);
    }

    public void siirraKortti(Kortti kortti, Korttijoukko kohde) throws Exception {
        if (kortit.contains(kortti)) {
            kortit.remove(kortti);
            kohde.lisaaKortti(kortti);
        } else {
            throw new Exception("Siirrettävää korttia ei löytynyt tästä korttijoukosta.");
        }
    }

    public boolean onTyhja() {
        if (kortit.isEmpty()) {
            return true;
        }

        return false;
    }

    public Kortti viimeisinKortti() {
        if (kortit.isEmpty()) {
            return null;
        }
        return kortit.get(kortit.size() - 1);
    }
    
    public int getKorttienMaara() {
        return this.kortit.size();
    }

}
