package joululabra.uno.sovelluslogiikka;

import joululabra.uno.domain.Kortti;
import joululabra.uno.domain.Pelaaja;
import org.junit.Test;
import static org.junit.Assert.*;

public class SaannotTest {

    @Test
    public void testSamaVari() {
        assertTrue(Saannot.samaVari(new Kortti(Kortti.KELTAINEN, 1), new Kortti(Kortti.KELTAINEN, 2)));
        assertFalse(Saannot.samaVari(new Kortti(Kortti.SININEN, 1), new Kortti(Kortti.KELTAINEN, 1)));
    }

    @Test
    public void testSamaArvo() {
        assertTrue(Saannot.samaArvo(new Kortti(Kortti.SININEN, 2), new Kortti(Kortti.KELTAINEN, 2)));
        assertFalse(Saannot.samaArvo(new Kortti(Kortti.KELTAINEN, 2), new Kortti(Kortti.KELTAINEN, 1)));
    }

    @Test
    public void testSiirtoOnLaillinen() {
        assertTrue(Saannot.siirtoOnLaillinen(new Kortti(Kortti.SININEN, 2), new Kortti(Kortti.KELTAINEN, 2)));
        assertTrue(Saannot.siirtoOnLaillinen(new Kortti(Kortti.SININEN, 2), new Kortti(Kortti.SININEN, 2)));
        assertTrue(Saannot.siirtoOnLaillinen(new Kortti(Kortti.KELTAINEN, 3), new Kortti(Kortti.KELTAINEN, 2)));
        assertTrue(Saannot.siirtoOnLaillinen(null, new Kortti(Kortti.KELTAINEN, 2)));
    }

    @Test
    public void testSeuraavaNostaaKaksi() {
        assertTrue(Saannot.seuraavaNostaaKaksi(new Kortti(Kortti.KELTAINEN, Kortti.PLUSKAKSI)));
        assertFalse(Saannot.seuraavaNostaaKaksi(new Kortti(Kortti.KELTAINEN, 3)));
    }

    @Test
    public void testSeuraavaJaaValista() {
        assertTrue(Saannot.seuraavaJaaValista(new Kortti(Kortti.KELTAINEN, Kortti.PLUSKAKSI)));
        assertTrue(Saannot.seuraavaJaaValista(new Kortti(Kortti.KELTAINEN, Kortti.UUDESTAAN)));
        assertFalse(Saannot.seuraavaJaaValista(new Kortti(Kortti.KELTAINEN, 2)));
    }

    @Test
    public void testJoutuuNostamaanPinon() {
        Pelaaja pelaaja = new Pelaaja();
        pelaaja.asetaVuoroon();
        pelaaja.getVuoro().lisaaNostokerta();
        pelaaja.getVuoro().lisaaNostokerta();
        pelaaja.getVuoro().lisaaNostokerta();
        assertTrue(Saannot.joutuuNostamaanPinon(pelaaja));
    }

}
