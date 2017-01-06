package joululabra.paskahousu.sovelluslogiikka;

import joululabra.paskahousu.sovelluslogiikka.Saannot;
import joululabra.paskahousu.domain.Kortti;
import joululabra.paskahousu.domain.Korttijoukko;
import joululabra.paskahousu.domain.Pakka;
import joululabra.paskahousu.domain.Pelaaja;
import joululabra.paskahousu.domain.Vuoro;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class SaannotTest {

    Pakka pakka;
    Korttijoukko pino;
    Vuoro vuoro;
    Pelaaja pelaaja;

    @Before
    public void setUp() {
        pakka = new Pakka();
        pino = new Korttijoukko();
        pelaaja = new Pelaaja("Testi");
        vuoro = new Vuoro(pelaaja);
    }

    @Test
    public void testJoutuuAinaNostamaanPinonJosTyhjallaPoydallaKymppiTaiAssa() throws Exception {
        pino.lisaa(new Kortti(Kortti.HERTTA, 10));
        assertTrue(Saannot.pakkoNostaaPino(pino));
        pino.ota(new Kortti(Kortti.HERTTA, 10));
        pino.lisaa(new Kortti(Kortti.HERTTA, 14));
        assertTrue(Saannot.pakkoNostaaPino(pino));
        pino.ota(new Kortti(Kortti.HERTTA, 14));
        pino.lisaa(new Kortti(Kortti.PATA, 3));
        pino.lisaa(new Kortti(Kortti.HERTTA, 10));
        assertFalse(Saannot.pakkoNostaaPino(pino));
    }

    @Test
    public void testSaaKokeillaOnnea() throws Exception {
        assertTrue(Saannot.saaKokeillaOnnea(vuoro));
        vuoro.lisaaKateen(new Kortti(Kortti.HERTTA, 3));
        assertFalse(Saannot.saaKokeillaOnnea(vuoro));
    }

    @Test
    public void testKadessaLiianVahanKortteja() throws Exception {
        vuoro = new Vuoro(pelaaja);

        while (!pakka.onTyhja()) {
            pakka.otaEnsimmainenKortti();
        }
        pelaaja.lisaaKateen(new Kortti(Kortti.RISTI, 3));
        assertFalse(Saannot.kadessaLiianVahanKortteja(vuoro, pakka));
        pakka = new Pakka();
        pelaaja.lisaaKateen(new Kortti(Kortti.RISTI, 6));
        pelaaja.lisaaKateen(new Kortti(Kortti.RISTI, 7));
        pelaaja.lisaaKateen(new Kortti(Kortti.RISTI, 8));
        assertTrue(Saannot.kadessaLiianVahanKortteja(vuoro, pakka));
        pelaaja.lisaaKateen(new Kortti(Kortti.RISTI, 9));
        assertFalse(Saannot.kadessaLiianVahanKortteja(vuoro, pakka));
    }

    @Test
    public void testPinoKaatuu() throws Exception {
        pino.lisaa(new Kortti(Kortti.HERTTA, 3));
        pelaaja.lisaaKateen(new Kortti(Kortti.HERTTA, 10));
        vuoro.otaKadesta(new Kortti(Kortti.HERTTA, 10));
        pino.lisaa(new Kortti(Kortti.HERTTA, 10));
        assertTrue(Saannot.pinoKaatuu(pino, vuoro));
        assertTrue(pino.korttienMaara() > 1);

        pino = new Korttijoukko();
        vuoro = new Vuoro(pelaaja);

        pino.lisaa(new Kortti(Kortti.HERTTA, 3));
        pelaaja.lisaaKateen(new Kortti(Kortti.HERTTA, 14));
        vuoro.otaKadesta(new Kortti(Kortti.HERTTA, 14));
        pino.lisaa(new Kortti(Kortti.HERTTA, 14));
        assertTrue(Saannot.pinoKaatuu(pino, vuoro));
        assertTrue(pino.korttienMaara() > 1);
    }

    @Test
    public void testVuoronEnsimmainenSiirtoOk() {
        assertTrue(Saannot.vuoronEnsimmainenSiirtoOk(pakka, pino, new Kortti(Kortti.HERTTA, 2)));
        assertTrue(Saannot.vuoronEnsimmainenSiirtoOk(pakka, pino, new Kortti(Kortti.HERTTA, 10)));
        assertFalse(Saannot.vuoronEnsimmainenSiirtoOk(pakka, pino, new Kortti(Kortti.HERTTA, 11)));
        assertTrue(Saannot.vuoronEnsimmainenSiirtoOk(pakka, pino, new Kortti(Kortti.HERTTA, 14)));
        pino.lisaa(new Kortti(Kortti.HERTTA, 4));
        assertTrue(Saannot.vuoronEnsimmainenSiirtoOk(pakka, pino, new Kortti(Kortti.HERTTA, 5)));
        assertFalse(Saannot.vuoronEnsimmainenSiirtoOk(pakka, pino, new Kortti(Kortti.HERTTA, 3)));
        pino.lisaa(new Kortti(Kortti.HERTTA, 2));
        assertFalse(Saannot.vuoronEnsimmainenSiirtoOk(pakka, pino, new Kortti(Kortti.HERTTA, 3)));
    }

    @Test
    public void testSamaArvo() {
        pino.lisaa(new Kortti(Kortti.HERTTA, 4));
        vuoro.getLaitetut().lisaa(new Kortti(Kortti.HERTTA, 4));
        assertTrue(Saannot.vuoronToinenTaiUseampiSiirtoOk(vuoro, new Kortti(Kortti.PATA, 4)));
        assertFalse(Saannot.vuoronToinenTaiUseampiSiirtoOk(vuoro, new Kortti(Kortti.HERTTA, 5)));
    }

    @Test
    public void testPinoJaPakkaTyhjaKaikkiKay() throws Exception {
        while (!pino.onTyhja()) {
            pino.ota(pino.viimeisinKortti());
        }
        while (!pakka.onTyhja()) {
            pakka.otaEnsimmainenKortti();
        }
        assertTrue(Saannot.vuoronEnsimmainenSiirtoOk(pakka, pino, new Kortti(Kortti.PATA, 11)));
    }

}
