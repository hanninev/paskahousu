package joululabra.paskahousu.sovelluslogiikka;

import joululabra.paskahousu.sovelluslogiikka.Peli;
import joululabra.paskahousu.domain.Pakka;
import joululabra.paskahousu.domain.Pelaaja;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PeliTest {

    Pelaaja pelaaja1;
    Pelaaja pelaaja2;
    Pelaaja pelaaja3;
    Peli peli;

    @Before
    public void setUp() throws Exception {
        pelaaja1 = new Pelaaja();
        pelaaja2 = new Pelaaja();
        pelaaja3 = new Pelaaja();
        peli = new Peli();
        peli.lisaaPelaaja(pelaaja1);
        peli.lisaaPelaaja(pelaaja2);
        peli.lisaaPelaaja(pelaaja3);
        peli.alusta();
        peli.kukaAloittaa();
    }

    @Test
    public void testKaikillaPelaajillaAlussaViisiKorttiaJaKortitSekoitettuEnnenJakoa() throws Exception {
        Pakka pakka = new Pakka();
        assertFalse(pakka.getKortit().get(0) == peli.getPakka().getKortit().get(0)
                && pakka.getKortit().get(1) == peli.getPakka().getKortit().get(1)
                && pakka.getKortit().get(2) == peli.getPakka().getKortit().get(2)
                && pakka.getKortit().get(3) == peli.getPakka().getKortit().get(3));

        for (Pelaaja pelaaja : peli.getPelaajat()) {
            assertEquals(pelaaja.getKasi().korttienMaara(), 5);
        }
    }

    @Test
    public void testLisaaPelaajaToimii() {
        int pelaajienMaara = peli.getPelaajat().size();
        peli.lisaaPelaaja(new Pelaaja());
        assertTrue(peli.getPelaajat().size() == pelaajienMaara + 1);
    }

    @Test
    public void testPeliJatkuu() throws Exception {
        while (!pelaaja1.getKasi().onTyhja()) {
            pelaaja1.otaKorttiKadesta(pelaaja1.pieninKortti());
        }
        assertTrue(peli.peliJatkuu());
        
        while (!pelaaja2.getKasi().onTyhja()) {
            pelaaja2.otaKorttiKadesta(pelaaja2.pieninKortti());
        }
        assertFalse(peli.peliJatkuu());
    }
}
