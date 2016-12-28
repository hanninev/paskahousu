package joululabra.uno.sovelluslogiikka;

import java.util.ArrayList;
import java.util.List;
import joululabra.uno.domain.Kortti;
import joululabra.uno.domain.Pakka;
import joululabra.uno.domain.Pelaaja;
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
    public void kaikillaPelaajillaAlussaViisiKorttiaJaKortitSekoitettuEnnenJakoa() throws Exception {
        Pakka pakka = new Pakka();
        assertFalse(pakka.getKortit().get(0) == peli.getPakka().getKortit().get(0)
                && pakka.getKortit().get(1) == peli.getPakka().getKortit().get(1)
                && pakka.getKortit().get(2) == peli.getPakka().getKortit().get(2)
                && pakka.getKortit().get(3) == peli.getPakka().getKortit().get(3));

        for (Pelaaja pelaaja : peli.getPelaajat()) {
            assertEquals(pelaaja.getKasi().getKorttienMaara(), 5);
        }
    }

    @Test
    public void pienimmanKortinOmaavaAloittaa() {
        for (Pelaaja pelaaja : peli.getPelaajat()) {
            if (pelaaja.onVuorossa()) {
                for (Pelaaja pelaaja1 : peli.getPelaajat()) {
                    assertTrue(pelaaja.pieninKortti().getArvo() <= pelaaja1.pieninKortti().getArvo());
                }
            }
        }
    }

    @Test
    public void lisaaPelaajaToimii() {
        int pelaajienMaara = peli.getPelaajat().size();
        peli.lisaaPelaaja(new Pelaaja());
        assertTrue(peli.getPelaajat().size() == pelaajienMaara + 1);
    }

    @Test
    public void korttiSiirretaanJosSiirtoLaillinen() throws Exception {
        Kortti kortti = pelaaja1.getKasi().getKortit().get(0);
        peli.teeSiirto(pelaaja1, kortti);
        assertEquals(peli.getPino().viimeisinKortti(), kortti);
    }

    @Test
    public void pelaajanKortitLisaantyyKunOtetaanKorttejaPakasta() throws Exception {
        assertEquals(pelaaja1.getKasi().getKorttienMaara(), 5);
        peli.otaKorttejaPakasta(pelaaja1, 3);
        assertEquals(pelaaja1.getKasi().getKorttienMaara(), 8);
    }

    @Test
    public void pelaajaSaaPakastaOttamansaKortin() throws Exception {
        Kortti kortti = peli.getPakka().viimeisinKortti();
        peli.otaKorttiPakasta(pelaaja1);
        assertEquals(pelaaja1.getKasi().viimeisinKortti(), kortti);
        assertEquals(pelaaja1.getKasi().getKorttienMaara(), 6);
    }

    @Test
    public void josPelaajaltaLoppuuKortitPeliOnPaattynyt() throws Exception {
        while (pelaaja1.getKasi().getKorttienMaara() > 0) {
            assertFalse(peli.peliPaattynyt());
            pelaaja1.asetaVuoroon();

            int kortteja = pelaaja1.getKasi().getKorttienMaara();
            List<Kortti> kortitKadessa = new ArrayList<>(pelaaja1.getKasi().getKortit());

            for (Kortti kortti : kortitKadessa) {
                if (pelaaja1.getKasi().getKorttienMaara() > 0) {
                    peli.teeSiirto(pelaaja1, kortti);
                }
            }

            if (kortteja == pelaaja1.getKasi().getKorttienMaara()) {
                peli.otaKorttiPakasta(pelaaja1);
            }
        }
        assertTrue(peli.peliPaattynyt());
    }

    @Test
    public void seuraavanVuoroNiinAinoastaanYksiOnVuorossa() {
        for (Pelaaja pelaaja : peli.getPelaajat()) {
            pelaaja.asetaPoisVuorosta();
        }
        pelaaja1.asetaVuoroon();
        peli.seuraavanVuoro();
        assertTrue(pelaaja2.onVuorossa());
        assertFalse(pelaaja1.onVuorossa());
        assertFalse(pelaaja3.onVuorossa());

        for (Pelaaja pelaaja : peli.getPelaajat()) {
            pelaaja.asetaPoisVuorosta();
        }
        pelaaja3.asetaVuoroon();
        peli.seuraavanVuoro();
        assertTrue(pelaaja1.onVuorossa());
        assertFalse(pelaaja2.onVuorossa());
        assertFalse(pelaaja3.onVuorossa());

    }
}
