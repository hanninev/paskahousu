package joululabra.paskahousu.sovelluslogiikka;

import joululabra.paskahousu.domain.Pakka;
import joululabra.paskahousu.domain.Pelaaja;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PeliTest {
    
    Peli peli;
    
    @Before
    public void setUp() throws Exception {
        
        peli = new Peli();
        peli.lisaaPelaaja("pelaaja1");
        peli.lisaaPelaaja("pelaaja2");
        peli.lisaaPelaaja("pelaaja3");
        peli.sekoitaPakka();
        peli.jaaKortit();
    }
    
    @Test
    public void testKaikillaPelaajillaAlussaViisiKorttia() throws Exception {
        for (Pelaaja pelaaja : peli.getPelaajat()) {
            assertEquals(pelaaja.getKasi().korttienMaara(), 5);
        }
    }
    
    @Test
    public void testPelaajiaLiikaaPakkaLoppuuKesken() {
        boolean thrown = false;
        try {
            for (int i = 0; i < 10; i++) {
                peli.lisaaPelaaja("pelaaja-" + i);
            }
            peli.jaaKortit();
        } catch (Exception e) {
            thrown = true;
        }
        assertTrue(thrown);
    }
    
    @Test
    public void testLisaaPelaajaToimii() {
        int pelaajienMaara = peli.getPelaajat().size();
        peli.lisaaPelaaja("Testi");
        assertTrue(peli.getPelaajat().size() == pelaajienMaara + 1);
    }
    
    @Test
    public void testPeliJatkuu() throws Exception {
        while (!peli.getPelaajat().get(0).getKasi().onTyhja()) {
            peli.getPelaajat().get(0).otaKadesta(peli.getPelaajat().get(0).pieninKortti());
        }
        assertTrue(peli.peliJatkuu());
        
        while (!peli.getPelaajat().get(1).getKasi().onTyhja()) {
            peli.getPelaajat().get(1).otaKadesta(peli.getPelaajat().get(1).pieninKortti());
        }
        assertFalse(peli.peliJatkuu());
    }
    
    @Test
    public void testSekoitaPakka() {
        // Testi ei mene läpi, jos sekoittamattoman ja sekoitetun pakan kaikki viisi ensimmäistä korttia ovat
        // täsmälleen samat ja samassa järjestyksessä. Toki tämä on mahdollista, mutta erittäin epätodennäköistä.

        Pakka sekoittamaton = new Pakka();
        assertFalse((sekoittamaton.getKortit().get(0) == peli.getSk().getPakka().getKortit().get(0))
                && (sekoittamaton.getKortit().get(1) == peli.getSk().getPakka().getKortit().get(1))
                && (sekoittamaton.getKortit().get(2) == peli.getSk().getPakka().getKortit().get(2))
                && (sekoittamaton.getKortit().get(3) == peli.getSk().getPakka().getKortit().get(3))
                && (sekoittamaton.getKortit().get(4) == peli.getSk().getPakka().getKortit().get(4)));
    }
    
    @Test
    public void testAsetaSeuraavaPelaajaVuoroon() {
        peli.getSk().lisaaVuoro(peli.getPelaajat().get(0));
        assertEquals(peli.getSk().nykyinenVuoro().getPelaaja(), peli.getPelaajat().get(0));
        for (int i = 0; i < 5; i++) {
            peli.asetaSeuraavaPelaajaVuoroon();
            for (int j = 0; j < peli.getSk().getVuorot().size() - 2; j++) {
                assertFalse(peli.getSk().getVuorot().get(j).isJatkuu());
            }
            assertEquals(peli.getSk().nykyinenVuoro().getPelaaja(), peli.getSk().getVuorot().get(i + 1).getPelaaja());
            assertTrue(peli.getSk().getVuorot().get(i + 1).isJatkuu());
        }
    }
}
