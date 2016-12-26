package javalabra.uno.sovelluslogiikka;

import javalabra.uno.domain.Pelaaja;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PeliTest {

    Peli peli;

    @Before
    public void setUp() throws Exception {
        Pelaaja pelaaja1 = new Pelaaja();
        Pelaaja pelaaja2 = new Pelaaja();
        peli = new Peli();
        peli.lisaaPelaaja(pelaaja1);
        peli.lisaaPelaaja(pelaaja2);
        peli.alusta();
        peli.kukaAloittaa();
    }

    @Test
    public void kummallakinPelaajallaAlussaViisiKorttia() throws Exception {
        assertEquals(peli.getPelaajat().get(0).getKasi().getKorttienMaara(), 5);
        assertEquals(peli.getPelaajat().get(1).getKasi().getKorttienMaara(), 5);
    }

    @Test
    public void pienimmanKortinOmaavaAloittaa() {
        if (peli.getPelaajat().get(0).pieninKortti().getArvo() < peli.getPelaajat().get(1).pieninKortti().getArvo()) {
            assertTrue(peli.getPelaajat().get(0).onVuorossa());
        }
        if (peli.getPelaajat().get(0).pieninKortti().getArvo() > peli.getPelaajat().get(1).pieninKortti().getArvo()) {
            assertTrue(peli.getPelaajat().get(1).onVuorossa());
        }
    }

}
