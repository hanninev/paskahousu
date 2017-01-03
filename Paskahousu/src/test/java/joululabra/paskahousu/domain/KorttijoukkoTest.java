package joululabra.paskahousu.domain;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class KorttijoukkoTest {

    Korttijoukko korttijoukko;
    Korttijoukko kohde;
    Kortti hertta5;
    Kortti pata3;

    @Before
    public void setUp() {
        korttijoukko = new Korttijoukko();
        kohde = new Korttijoukko();
        hertta5 = new Kortti(Kortti.HERTTA, 5);
        pata3 = new Kortti(Kortti.PATA, 3);
        korttijoukko.lisaa(hertta5);
    }

    @Test
    public void testGetKortit() {
        List<Kortti> kortit = new ArrayList<>();
        kortit.add(hertta5);

        assertEquals(korttijoukko.getKortit(), kortit);
    }

    @Test
    public void testLisaaKortti() {
        korttijoukko.lisaa(pata3);
        assertTrue(korttijoukko.getKortit().contains(hertta5));
        assertTrue(korttijoukko.getKortit().contains(pata3));
    }

}
