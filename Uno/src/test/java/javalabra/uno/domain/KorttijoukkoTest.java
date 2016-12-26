package javalabra.uno.domain;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class KorttijoukkoTest {

    Korttijoukko korttijoukko;
    Korttijoukko kohde;
    Kortti keltainen1;
    Kortti punainen3;

    @Before
    public void setUp() {
        korttijoukko = new Korttijoukko();
        kohde = new Korttijoukko();
        keltainen1 = new Kortti(Kortti.KELTAINEN, 1);
        punainen3 = new Kortti(Kortti.PUNAINEN, 3);
        korttijoukko.lisaaKortti(keltainen1);
    }

    @Test
    public void testGetKortit() {
        List<Kortti> kortit = new ArrayList<>();
        kortit.add(keltainen1);

        assertEquals(korttijoukko.getKortit(), kortit);
    }

    @Test
    public void testLisaaKortti() {
        korttijoukko.lisaaKortti(punainen3);
        assertTrue(korttijoukko.getKortit().contains(keltainen1));
        assertTrue(korttijoukko.getKortit().contains(punainen3));
    }

    @Test
    public void testSiirraKortti() throws Exception {
        korttijoukko.siirraKortti(keltainen1, kohde);
        assertTrue(korttijoukko.getKortit().isEmpty());
        assertEquals(keltainen1, kohde.getKortit().get(0));

        boolean thrown = false;

        try {
            korttijoukko.siirraKortti(punainen3, kohde);
        } catch (Exception e) {
            thrown = true;
        }

        assertTrue(thrown);
    }

    @Test
    public void testTyhjennaJaOnTyhja() throws Exception {
        assertFalse(korttijoukko.onTyhja());
        korttijoukko.siirraKortti(keltainen1, kohde);
        assertTrue(korttijoukko.onTyhja());
        assertEquals(keltainen1, kohde.getKortit().get(0));
    }
    
    @Test
    public void testGetKorttienMaara() {
        korttijoukko.lisaaKortti(punainen3);
        assertEquals(korttijoukko.getKorttienMaara(), 2);
    }

}
