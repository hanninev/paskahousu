package joululabra.paskahousu.domain;

import java.util.Collections;
import java.util.List;

public class Kasi extends Korttijoukko {

    @Override
    public List<Kortti> getKortit() {
        Collections.sort(kortit);
        return kortit;
    }
}
