package joululabra.paskahousu.tekoaly;

import joululabra.paskahousu.domain.Kortti;
import joululabra.paskahousu.domain.Pelaaja;
import joululabra.paskahousu.sovelluslogiikka.Peli;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class TekoalyTest {

    Pelaaja pelaaja;
    Pelaaja vastustaja;
    Peli peli;
    Tekoaly tekoaly;

    @Before
    public void setUp() throws Exception {
        peli = new Peli();
        peli.lisaaPelaaja("Testi");
        peli.lisaaPelaaja("Vastustaja");
        pelaaja = peli.getPelaajat().get(0);
        vastustaja = peli.getPelaajat().get(1);
        tekoaly = new Tekoaly(peli.getSk());

        pelaaja.setTekoaly(true);
        peli.sekoitaPakka();

        pelaaja.lisaaKateen(peli.getSk().getPakka().ota(new Kortti(Kortti.PATA, 3)));
        pelaaja.lisaaKateen(peli.getSk().getPakka().ota(new Kortti(Kortti.HERTTA, 7)));
        pelaaja.lisaaKateen(peli.getSk().getPakka().ota(new Kortti(Kortti.PATA, 12)));
        pelaaja.lisaaKateen(peli.getSk().getPakka().ota(new Kortti(Kortti.RUUTU, 10)));
        pelaaja.lisaaKateen(peli.getSk().getPakka().ota(new Kortti(Kortti.RISTI, 7)));

        vastustaja.lisaaKateen(peli.getSk().getPakka().ota(new Kortti(Kortti.RUUTU, 7)));
        vastustaja.lisaaKateen(peli.getSk().getPakka().ota(new Kortti(Kortti.RUUTU, 13)));
        vastustaja.lisaaKateen(peli.getSk().getPakka().ota(new Kortti(Kortti.RUUTU, 12)));
        vastustaja.lisaaKateen(peli.getSk().getPakka().ota(new Kortti(Kortti.HERTTA, 6)));
        vastustaja.lisaaKateen(peli.getSk().getPakka().ota(new Kortti(Kortti.RUUTU, 14)));
    }

    @Test
    public void testKaykoMikaanKortti() throws Exception {
        peli.getSk().lisaaVuoro(vastustaja);
        peli.getSk().siirraKorttiPinoon(new Kortti(Kortti.RUUTU, 7));
        peli.getSk().lisaaVuoro(pelaaja);
        assertTrue(peli.getTekoaly().kaykoMikaanKortti());
        peli.getTekoaly().laitaKortti();
        assertEquals(peli.getSk().getPino().korttienMaara(), 3);
        peli.getSk().lisaaVuoro(vastustaja);
        peli.getSk().siirraKorttiPinoon(new Kortti(Kortti.RUUTU, 12));
        peli.getSk().lisaaVuoro(pelaaja);
        assertTrue(peli.getTekoaly().kaykoMikaanKortti());
        peli.getSk().lisaaVuoro(vastustaja);
        peli.getSk().siirraKorttiPinoon(new Kortti(Kortti.RUUTU, 13));
        peli.getSk().lisaaVuoro(pelaaja);
        assertFalse(peli.getTekoaly().kaykoMikaanKortti());
    }

    @Test
    public void testValitseToiminto() throws Exception {
        peli.getSk().lisaaVuoro(vastustaja);
        peli.getSk().siirraKorttiPinoon(new Kortti(Kortti.RUUTU, 14));
        peli.getSk().lisaaVuoro(pelaaja);
        tekoaly.valitseToiminto();
        assertEquals(pelaaja.getKasi().getKortit().size(), 6);
    }
}
