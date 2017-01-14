package joululabra.paskahousu.sovelluslogiikka;

import joululabra.paskahousu.domain.Kortti;
import joululabra.paskahousu.domain.Pelaaja;
import joululabra.paskahousu.domain.Vuoro;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class SiirtojenkasittelijaTest {

    Pelaaja pelaaja;
    Pelaaja vastustaja;
    Peli peli;

    @Before
    public void setUp() throws Exception {
        peli = new Peli();
        peli.lisaaPelaaja("Testi");
        peli.lisaaPelaaja("Vastustaja");
        pelaaja = peli.getPelaajat().get(0);
        vastustaja = peli.getPelaajat().get(1);

        peli.sekoitaPakka();

        pelaaja.lisaaKateen(peli.getSk().getPakka().ota(new Kortti(Kortti.PATA, 3)));
        pelaaja.lisaaKateen(peli.getSk().getPakka().ota(new Kortti(Kortti.HERTTA, 7)));
        pelaaja.lisaaKateen(peli.getSk().getPakka().ota(new Kortti(Kortti.PATA, 12)));
        pelaaja.lisaaKateen(peli.getSk().getPakka().ota(new Kortti(Kortti.RUUTU, 10)));
        pelaaja.lisaaKateen(peli.getSk().getPakka().ota(new Kortti(Kortti.RISTI, 14)));

        vastustaja.lisaaKateen(peli.getSk().getPakka().ota(new Kortti(Kortti.RUUTU, 7)));
        vastustaja.lisaaKateen(peli.getSk().getPakka().ota(new Kortti(Kortti.RUUTU, 13)));
        vastustaja.lisaaKateen(peli.getSk().getPakka().ota(new Kortti(Kortti.RUUTU, 12)));
        vastustaja.lisaaKateen(peli.getSk().getPakka().ota(new Kortti(Kortti.HERTTA, 10)));
        vastustaja.lisaaKateen(peli.getSk().getPakka().ota(new Kortti(Kortti.RUUTU, 14)));
    }

    @Test
    public void testSiirraKorttiPinoon() throws Exception {
        assertEquals(peli.getPelaajat().get(0).getKasi().korttienMaara(), 5);
        peli.getSk().getVuorot().add(new Vuoro(pelaaja));
        assertEquals(peli.getSk().nykyinenVuoro().getLaitettuPinoon(), 0);
        peli.getSk().siirraKorttiPinoon(new Kortti(Kortti.HERTTA, 7));
        assertEquals(peli.getSk().nykyinenVuoro().getLaitettuPinoon(), 1);
        assertEquals(peli.getPelaajat().get(0).getKasi().korttienMaara(), 4);
        assertTrue(peli.getSk().getPino().viimeisinKortti().equals(new Kortti(Kortti.HERTTA, 7)));
    }

    @Test
    public void testTaydennaKasi() throws Exception {
        assertEquals(peli.getPelaajat().get(0).getKasi().korttienMaara(), 5);
        peli.getSk().getVuorot().add(new Vuoro(pelaaja));
        peli.getPelaajat().get(0).otaKadesta(new Kortti(Kortti.HERTTA, 7));
        assertEquals(peli.getPelaajat().get(0).getKasi().korttienMaara(), 4);
        peli.getSk().kaadaPinoJosKaatuuJaTaydennaKasi();
        assertEquals(peli.getPelaajat().get(0).getKasi().korttienMaara(), 5);
        assertTrue(peli.getSk().getPakka().korttienMaara() == 41);
    }

    @Test
    public void testKokeileOnnea() throws Exception {
        peli.getSk().getVuorot().add(new Vuoro(pelaaja));
        assertFalse(peli.getSk().nykyinenVuoro().isKokeiliOnnea());
        peli.getSk().kokeileOnnea();
        assertEquals(peli.getSk().nykyinenVuoro().getPelaaja().getKasi().korttienMaara(), 6);
        assertTrue(peli.getSk().nykyinenVuoro().isKokeiliOnnea());
    }

    @Test
    public void testNostaPino() throws Exception {
        peli.getSk().getPino().lisaa(new Kortti(Kortti.HERTTA, 3));
        peli.getSk().getVuorot().add(new Vuoro(pelaaja));
        assertFalse(peli.getSk().nykyinenVuoro().isNostiPinon());
        peli.getSk().nostaPino();
        assertEquals(peli.getSk().nykyinenVuoro().getPelaaja().getKasi().korttienMaara(), 6);
        assertTrue(peli.getSk().nykyinenVuoro().isNostiPinon());
    }

    @Test
    public void testPinoKaatuuJosSaannotSallivat() throws Exception {
        peli.getSk().getVuorot().add(new Vuoro(pelaaja));
        peli.getSk().siirraKorttiPinoon(new Kortti(Kortti.HERTTA, 7));
        peli.getSk().kaadaPinoJosKaatuuJaTaydennaKasi();
        peli.getSk().getVuorot().add(new Vuoro(vastustaja));
        assertFalse(peli.getSk().nykyinenVuoro().isKaatoiPinon());
        peli.getSk().siirraKorttiPinoon(new Kortti(Kortti.HERTTA, 10));
        peli.getSk().kaadaPinoJosKaatuuJaTaydennaKasi();
        assertTrue(peli.getSk().nykyinenVuoro().isKaatoiPinon());
        peli.getSk().getVuorot().add(new Vuoro(pelaaja));
        peli.getSk().kaadaPinoJosKaatuuJaTaydennaKasi();
        assertTrue(peli.getSk().getPino().onTyhja());

        peli.getSk().getVuorot().add(new Vuoro(vastustaja));
        peli.getSk().siirraKorttiPinoon(new Kortti(Kortti.RUUTU, 7));
        peli.getSk().kaadaPinoJosKaatuuJaTaydennaKasi();
        peli.getSk().getVuorot().add(new Vuoro(pelaaja));
        assertFalse(peli.getSk().nykyinenVuoro().isKaatoiPinon());
        peli.getSk().siirraKorttiPinoon(new Kortti(Kortti.RISTI, 14));
        peli.getSk().kaadaPinoJosKaatuuJaTaydennaKasi();
        assertTrue(peli.getSk().nykyinenVuoro().isKaatoiPinon());
        peli.getSk().getVuorot().add(new Vuoro(pelaaja));
        peli.getSk().kaadaPinoJosKaatuuJaTaydennaKasi();
        assertTrue(peli.getSk().getPino().onTyhja());
    }

    @Test
    public void testNykyinenVuoroOnTilassaJatkuu() {
        assertEquals(peli.getSk().nykyinenVuoro(), null);
        peli.getSk().getVuorot().add(new Vuoro(vastustaja));
        assertTrue(peli.getSk().nykyinenVuoro().isJatkuu());
    }

    @Test
    public void testVuoronVaihtuminenKaadonJalkeenJaIlmanKaatoa() throws Exception {
        peli.getSk().getVuorot().add(new Vuoro(pelaaja));
        peli.getSk().getPino().lisaa(new Kortti(Kortti.HERTTA, 7));
        peli.getSk().kaadaPinoJosKaatuuJaTaydennaKasi();
        assertFalse(peli.getSk().nykyinenVuoro().isKaatoiPinon());
        peli.getSk().vuoronVaihtuminen();
        assertEquals(peli.getSk().nykyinenVuoro().getPelaaja(), vastustaja);
        assertFalse(peli.getSk().getVuorot().get(0).isJatkuu());
        assertTrue(peli.getSk().getVuorot().get(1).isJatkuu());

        peli.getSk().getPino().lisaa(new Kortti(Kortti.PATA, 14));
        peli.getSk().kaadaPinoJosKaatuuJaTaydennaKasi();
        assertTrue(peli.getSk().nykyinenVuoro().isKaatoiPinon());
        peli.getSk().vuoronVaihtuminen();
        assertEquals(peli.getSk().nykyinenVuoro().getPelaaja(), vastustaja);
        assertFalse(peli.getSk().getVuorot().get(1).isJatkuu());
        assertTrue(peli.getSk().getVuorot().get(2).isJatkuu());
    }

}
