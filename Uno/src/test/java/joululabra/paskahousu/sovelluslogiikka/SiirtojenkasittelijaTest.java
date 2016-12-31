package joululabra.paskahousu.sovelluslogiikka;

import joululabra.paskahousu.domain.Kortti;
import joululabra.paskahousu.domain.Korttijoukko;
import joululabra.paskahousu.domain.Pelaaja;
import joululabra.paskahousu.domain.Vuoro;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class SiirtojenkasittelijaTest {

    Vuoro vuoro;
    Peli peli;
    Pelaaja pelaaja;
    Siirtojenkasittelija sk;

    @Before
    public void setUp() throws Exception {
        peli = new Peli();
        pelaaja = new Pelaaja();
        peli.lisaaPelaaja(pelaaja);
        vuoro = peli.lisaaVuoro(pelaaja);
        peli.alusta();
        sk = new Siirtojenkasittelija();
    }

    @Test
    public void testSiirraKorttiPinoon() throws Exception {
        pelaaja.lisaaKorttiKateen(new Kortti(Kortti.HERTTA, 4));
        assertEquals(pelaaja.getKasi().korttienMaara(), 6);
        sk.siirraKorttiPinoon(new Kortti(Kortti.HERTTA, 4), peli.getPakka(), peli.getKaatuneetKortit(), peli.getPino(), vuoro);
        assertEquals(pelaaja.getKasi().korttienMaara(), 5);
        assertTrue(peli.getPino().getKortit().get(0).equals(new Kortti(Kortti.HERTTA, 4)));
    }

    @Test
    public void testTaydennaKasi() throws Exception {
        assertEquals(vuoro.getPelaaja().getKasi().korttienMaara(), 5);
        sk.siirraKorttiPinoon(vuoro.getPelaaja().pieninKortti(), peli.getPakka(), peli.getKaatuneetKortit(), peli.getPino(), vuoro);
        assertEquals(vuoro.getPelaaja().getKasi().korttienMaara(), 4);
        sk.taydennaKasi(peli.getPakka(), vuoro);
        assertTrue(vuoro.getPelaaja().getKasi().korttienMaara() == 5);
        assertTrue(peli.getPakka().korttienMaara() == 46);
    }

    @Test
    public void testKokeileOnneaKorttiSopii() throws Exception {
        for (int i = 0; i < 10; i++) {
            if (Saannot.korttiSopii(peli.getPino(), peli.getPakka(), vuoro, peli.getPakka().viimeisinKortti())) {
                sk.kokeileOnnea(peli.getPakka(), peli.getPino(), peli.getKaatuneetKortit(), vuoro);
                assertEquals(vuoro.getPelaaja().getKasi().korttienMaara(), 5);
                assertFalse(vuoro.jatkuu());
            }
        }
    }

    @Test
    public void testKokeileOnneaKorttiEiSovi() throws Exception {
        int maara = 5;
        for (int i = 0; i < 10; i++) {
            if (!Saannot.korttiSopii(peli.getPino(), peli.getPakka(), vuoro, peli.getPakka().viimeisinKortti())) {
                sk.kokeileOnnea(peli.getPakka(), peli.getPino(), peli.getKaatuneetKortit(), vuoro);
                maara++;
                assertEquals(vuoro.getPelaaja().getKasi().korttienMaara(), maara);
                assertFalse(vuoro.jatkuu());
            }
        }
    }

    @Test
    public void testNostaPino() throws Exception {
        sk.siirraKorttiPinoon(vuoro.getPelaaja().pieninKortti(), peli.getPakka(), peli.getKaatuneetKortit(), peli.getPino(), vuoro);
        assertEquals(vuoro.getPelaaja().getKasi().korttienMaara(), 4);
        sk.nostaPino(peli.getPino(), vuoro);
        assertEquals(vuoro.getPelaaja().getKasi().korttienMaara(), 5);
        assertFalse(vuoro.jatkuu());
    }

//    @Test
//    public void testPinoKaatuu() throws Exception {
//        Integer[] eiKaatavat = {2, 3, 4, 5, 6, 7, 8, 9, 11, 12, 13};
//
//        while (!Saannot.pinoKaatuu(peli.getPino(), vuoro)) {
//            sk.kokeileOnnea(peli.getPakka(), peli.getPino(), peli.getKaatuneetKortit(), vuoro);
//        }
//        
//        sk.pinoKaatuu(peli.getKaatuneetKortit(), peli.getPino(), vuoro);
//        assertEquals(peli.getPino().korttienMaara(), 0);
//        assertTrue(!peli.getKaatuneetKortit().onTyhja());
//    }
}
