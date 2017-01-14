package joululabra.paskahousu.domain;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class PelaajaTest {

    Pelaaja pelaaja;
    Pakka pakka;

    public PelaajaTest() {
        pelaaja = new Pelaaja("Testi");
        pakka = new Pakka();
    }

    @Before
    public void setUp() {
        pelaaja.lisaaKateen(new Kortti(Kortti.HERTTA, 4));
        pelaaja.lisaaKateen(new Kortti(Kortti.PATA, 5));
        pelaaja.lisaaKateen(new Kortti(Kortti.RUUTU, 11));
        pelaaja.lisaaKateen(new Kortti(Kortti.RISTI, 7));
    }

    @Test
    public void testPieninKortti() throws Exception {
        assertEquals(pelaaja.pieninKortti(), new Kortti(Kortti.HERTTA, 4));
    }

    @Test
    public void testSuurinKortti() throws Exception {
        assertEquals(pelaaja.suurinKortti(), new Kortti(Kortti.RUUTU, 11));
    }

    @Test
    public void testLisaaKortti() {
        pelaaja.lisaaKateen(new Kortti(Kortti.RUUTU, 8));
        assertTrue(pelaaja.getKasi().getKortit().contains(new Kortti(Kortti.RUUTU, 8)));
    }

    @Test
    public void testOtaKadesta() throws Exception {
        pelaaja.otaKadesta(new Kortti(Kortti.RUUTU, 11));
        assertFalse(pelaaja.getKasi().getKortit().contains(new Kortti(Kortti.RUUTU, 11)));
    }

}
