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
    public void testLisaaKortti() {
        korttijoukko.lisaa(pata3);
        assertTrue(korttijoukko.getKortit().contains(hertta5));
        assertTrue(korttijoukko.getKortit().contains(pata3));
    }

    @Test
    public void testOtaKortti() throws Exception {
        assertEquals(hertta5, korttijoukko.ota(hertta5));
        assertTrue(korttijoukko.onTyhja());

        boolean thrown = false;
        try {
            korttijoukko.ota(new Kortti(Kortti.PATA, 3));
        } catch (Exception e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    @Test
    public void testViimeisinKortti() throws Exception {
        korttijoukko.lisaa(pata3);
        assertEquals(korttijoukko.viimeisinKortti(), pata3);
        korttijoukko.ota(hertta5);
        korttijoukko.ota(pata3);
        assertEquals(korttijoukko.viimeisinKortti(), null);
    }

    @Test
    public void testToString() {
        assertEquals(korttijoukko.toString(), "kortin Hertta 5");
        korttijoukko.lisaa(pata3);
        assertEquals(korttijoukko.toString(), "kortit Hertta 5 ja Pata 3");
        korttijoukko.lisaa(new Kortti(Kortti.RUUTU, 4));
        assertEquals(korttijoukko.toString(), "kortit Hertta 5, Pata 3 ja Ruutu 4");

    }

}
