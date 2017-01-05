package joululabra.paskahousu.sovelluslogiikka;

import joululabra.paskahousu.domain.Kortti;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class SiirtojenkasittelijaTest {

    Peli peli;

    @Before
    public void setUp() throws Exception {
        peli = new Peli();
        peli.lisaaPelaaja("Pelaaja1");
        peli.sekoitaPakka();
        peli.jaaKortit();
        peli.getSk().lisaaVuoro(peli.getPelaajat().get(0));
    }

    @Test
    public void testSiirraKorttiPinoon() throws Exception {
        peli.jaaKortit();
        assertEquals(peli.getPelaajat().get(0).getKasi().korttienMaara(), 10);

        Kortti kokeiltava = null;
        for (Kortti kortti : peli.getPelaajat().get(0).getKasi().getKortit()) {
            if (Saannot.korttiSopii(peli.getSk().getPino(), peli.getSk().getPakka(), peli.getSk().nykyinenVuoro(), kortti)) {
                kokeiltava = kortti;
            }
        }
        peli.getSk().siirraKorttiPinoon(kokeiltava);
        assertEquals(peli.getPelaajat().get(0).getKasi().korttienMaara(), 9);

        assertTrue(peli.getSk().getPino().viimeisinKortti().equals(kokeiltava));
    }

    @Test
    public void testTaydennaKasi() throws Exception {
        assertEquals(peli.getPelaajat().get(0).getKasi().korttienMaara(), 5);
        peli.getPelaajat().get(0).otaKadesta(peli.getPelaajat().get(0).getKasi().getKortit().get(0));
        assertEquals(peli.getPelaajat().get(0).getKasi().korttienMaara(), 4);
        peli.getSk().taydennaKasi();
        assertEquals(peli.getPelaajat().get(0).getKasi().korttienMaara(), 5);
        assertTrue(peli.getSk().getPakka().korttienMaara() == 46);
    }

    @Test
    public void testKokeileOnneaKorttiSopii() throws Exception {
        for (int i = 0; i < 10; i++) {
            if (Saannot.korttiSopii(peli.getSk().getPino(), peli.getSk().getPakka(), peli.getSk().nykyinenVuoro(), peli.getSk().getPakka().viimeisinKortti())) {
                peli.getSk().kokeileOnnea();
                assertEquals(peli.getSk().nykyinenVuoro().getNostetut().korttienMaara(), 1);
                assertEquals(peli.getSk().nykyinenVuoro().getPelaaja().getKasi().korttienMaara(), 5);
                assertFalse(peli.getSk().nykyinenVuoro().jatkuu());
            }
        }
    }

    @Test
    public void testKokeileOnneaKorttiEiSovi() throws Exception {
        int maara = 5;
        for (int i = 0; i < 10; i++) {
            if (!Saannot.korttiSopii(peli.getSk().getPino(), peli.getSk().getPakka(), peli.getSk().nykyinenVuoro(), peli.getSk().getPakka().viimeisinKortti())) {
                peli.getSk().kokeileOnnea();
                maara++;
                assertEquals(peli.getSk().nykyinenVuoro().getPelaaja().getKasi().korttienMaara(), maara);
                assertFalse(peli.getSk().nykyinenVuoro().jatkuu());
                assertTrue(peli.getSk().getPino().onTyhja());
            }
        }
    }

    @Test
    public void testNostaPino() throws Exception {
        peli.getSk().siirraKorttiPinoon(peli.getPelaajat().get(0).pieninKortti());
        assertEquals(peli.getSk().getVuorot().get(0).getPelaaja().getKasi().korttienMaara(), 4);
        peli.getSk().nostaPino();
        assertEquals(peli.getSk().getVuorot().get(0).getPelaaja().getKasi().korttienMaara(), 5);
        assertFalse(peli.getSk().getVuorot().get(0).jatkuu());
    }

    /*   @Test
    public void testNostaPinoJosOnPakko() throws Exception {
        peli.getSk().getPakka().getKortit().clear();

        peli.getSk().getPino().lisaa(new Kortti(Kortti.PATA, 10));
        peli.getSk().nostaPinoJosOnPakko();
        assertEquals(peli.getSk().nykyinenVuoro().getPelaaja().getKasi().korttienMaara(), 6);
        assertTrue(peli.getSk().getPino().onTyhja());
        peli.getSk().getPino().lisaa(new Kortti(Kortti.PATA, 14));
        peli.getSk().nostaPinoJosOnPakko();
        assertEquals(peli.getSk().nykyinenVuoro().getPelaaja().getKasi().korttienMaara(), 7);
        peli.getSk().getPino().lisaa(new Kortti(Kortti.PATA, 9));
        peli.getSk().nostaPinoJosOnPakko();
        assertEquals(peli.getSk().nykyinenVuoro().getPelaaja().getKasi().korttienMaara(), 7);
    } */
    @Test
    public void testPinoKaatuuJosSaannotSallivat() throws Exception {
        peli.getSk().getPino().lisaa(new Kortti(Kortti.PATA, 4));
        peli.getSk().getPino().lisaa(new Kortti(Kortti.PATA, 10));
        peli.getSk().pinoKaatuuJosSaannotSallivat();
        assertTrue(peli.getSk().getPino().onTyhja());

        peli.getSk().getPino().lisaa(new Kortti(Kortti.PATA, 7));
        peli.getSk().getPino().lisaa(new Kortti(Kortti.PATA, 11));
        peli.getSk().getPino().lisaa(new Kortti(Kortti.PATA, 14));
        peli.getSk().pinoKaatuuJosSaannotSallivat();
        assertTrue(peli.getSk().getPino().onTyhja());
    }

}
