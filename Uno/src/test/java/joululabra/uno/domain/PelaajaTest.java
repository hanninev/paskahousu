package joululabra.uno.domain;

import org.junit.Test;
import static org.junit.Assert.*;

public class PelaajaTest {

    Pelaaja pelaaja;
    Pakka pakka;

    public PelaajaTest() {
        pelaaja = new Pelaaja();
        pakka = new Pakka();
    }

    @Test
    public void testVuoroonAsettaminenToimii() {
        assertFalse(pelaaja.onVuorossa());
        pelaaja.asetaVuoroon();
        assertTrue(pelaaja.onVuorossa());
        pelaaja.asetaPoisVuorosta();
        assertFalse(pelaaja.onVuorossa());
    }

    @Test
    public void testPieninKortti() throws Exception {
        pakka.siirraKortti(new Kortti(Kortti.KELTAINEN, 3), pelaaja.getKasi());
        pakka.siirraKortti(new Kortti(Kortti.SININEN, 2), pelaaja.getKasi());
        assertEquals(pelaaja.pieninKortti(), new Kortti(Kortti.SININEN, 2));
    }

}
