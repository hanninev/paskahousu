package joululabra.paskahousu.tekoaly;

import joululabra.paskahousu.domain.Kortti;
import joululabra.paskahousu.domain.Pelaaja;
import joululabra.paskahousu.sovelluslogiikka.Peli;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class TekoalyTest {

    Pelaaja tekoalyaKayttava;
    Pelaaja pelaaja;
    Peli peli;
    Tekoaly tekoaly;

    @Before
    public void setUp() throws Exception {
        peli = new Peli();
        peli.lisaaPelaaja("Testi");
        peli.lisaaPelaaja("Vastustaja");
        tekoalyaKayttava = peli.getPelaajat().get(0);
        pelaaja = peli.getPelaajat().get(1);
        tekoaly = new Tekoaly(peli.getSk());

        tekoalyaKayttava.setTekoaly(true);
        peli.sekoitaPakka();

        tekoalyaKayttava.lisaaKateen(peli.getSk().getPakka().ota(new Kortti(Kortti.PATA, 3)));
        tekoalyaKayttava.lisaaKateen(peli.getSk().getPakka().ota(new Kortti(Kortti.HERTTA, 7)));
        tekoalyaKayttava.lisaaKateen(peli.getSk().getPakka().ota(new Kortti(Kortti.PATA, 12)));
        tekoalyaKayttava.lisaaKateen(peli.getSk().getPakka().ota(new Kortti(Kortti.RUUTU, 10)));
        tekoalyaKayttava.lisaaKateen(peli.getSk().getPakka().ota(new Kortti(Kortti.RISTI, 7)));

        pelaaja.lisaaKateen(peli.getSk().getPakka().ota(new Kortti(Kortti.RUUTU, 7)));
        pelaaja.lisaaKateen(peli.getSk().getPakka().ota(new Kortti(Kortti.RUUTU, 2)));
        pelaaja.lisaaKateen(peli.getSk().getPakka().ota(new Kortti(Kortti.RUUTU, 12)));
        pelaaja.lisaaKateen(peli.getSk().getPakka().ota(new Kortti(Kortti.HERTTA, 6)));
        pelaaja.lisaaKateen(peli.getSk().getPakka().ota(new Kortti(Kortti.RUUTU, 14)));
    }

    @Test
    public void testLoytyySamanKokoinenJaLaittaaSen() throws Exception {
        peli.getSk().lisaaVuoro(pelaaja);
        peli.getSk().siirraKorttiPinoon(new Kortti(Kortti.RUUTU, 7));
        peli.getSk().lisaaVuoro(tekoalyaKayttava);
        peli.getTekoaly().valitseToiminto();
        assertEquals(peli.getSk().getPino().korttienMaara(), 3);
    }

    @Test
    public void testEiKayNiinKokeileeOnnea() throws Exception {
        peli.getSk().lisaaVuoro(pelaaja);
        peli.getSk().siirraKorttiPinoon(new Kortti(Kortti.RUUTU, 2));
        peli.getSk().lisaaVuoro(tekoalyaKayttava);
        peli.getTekoaly().valitseToiminto();
        assertTrue((tekoalyaKayttava.getKasi().korttienMaara() == 5) || (tekoalyaKayttava.getKasi().korttienMaara() == 7));
    }

    @Test
    public void testJosPakkaOnTyhjaJaMikaanEiKayNiinNostaaPinon() throws Exception {
        while (!peli.getSk().getPakka().onTyhja()) {
            peli.getSk().getPakka().otaEnsimmainenKortti();
        }

        peli.getSk().lisaaVuoro(pelaaja);
        peli.getSk().siirraKorttiPinoon(new Kortti(Kortti.RUUTU, 2));
        assertEquals(peli.getSk().getPino().korttienMaara(), 1);
        peli.getSk().lisaaVuoro(tekoalyaKayttava);
        peli.getTekoaly().valitseToiminto();
        assertEquals(tekoalyaKayttava.getKasi().korttienMaara(), 6);
    }
}
