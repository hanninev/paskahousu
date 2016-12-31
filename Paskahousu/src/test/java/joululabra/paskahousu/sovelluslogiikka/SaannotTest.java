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
        vuoro = new Vuoro();
        pelaaja = new Pelaaja();
        vuoro.setPelaaja(pelaaja);
    }

    @Test
    public void testJoutuuAinaNostamaanPinonJosTyhjallaPoydallaKymppiTaiAssa() throws Exception {
        pino.lisaaKortti(new Kortti(Kortti.HERTTA, 10));
        assertTrue(Saannot.joutuuAinaNostamaanPinon(pino));
        pino.otaKortti(new Kortti(Kortti.HERTTA, 10));
        pino.lisaaKortti(new Kortti(Kortti.HERTTA, 14));
        assertTrue(Saannot.joutuuAinaNostamaanPinon(pino));
        pino.otaKortti(new Kortti(Kortti.HERTTA, 14));
        pino.lisaaKortti(new Kortti(Kortti.PATA, 3));
        pino.lisaaKortti(new Kortti(Kortti.HERTTA, 10));
        assertFalse(Saannot.joutuuAinaNostamaanPinon(pino));
    }

    @Test
    public void testSaaKokeillaOnnea() throws Exception {
        assertTrue(Saannot.saaKokeillaOnnea(vuoro));
        vuoro.lisaaKateen(new Kortti(Kortti.HERTTA, 3));
        assertFalse(Saannot.saaKokeillaOnnea(vuoro));
    }

    @Test
    public void testKadessaLiianVahanKortteja() throws Exception {
        vuoro.setPelaaja(pelaaja);

        while (!pakka.onTyhja()) {
            pakka.otaEnsimmainenKortti();
        }
        pelaaja.lisaaKorttiKateen(new Kortti(Kortti.RISTI, 3));
        assertFalse(Saannot.kadessaLiianVahanKortteja(vuoro, pakka));
        pakka = new Pakka();
        pelaaja.lisaaKorttiKateen(new Kortti(Kortti.RISTI, 6));
        pelaaja.lisaaKorttiKateen(new Kortti(Kortti.RISTI, 7));
        pelaaja.lisaaKorttiKateen(new Kortti(Kortti.RISTI, 8));
        assertTrue(Saannot.kadessaLiianVahanKortteja(vuoro, pakka));
        pelaaja.lisaaKorttiKateen(new Kortti(Kortti.RISTI, 9));
        assertFalse(Saannot.kadessaLiianVahanKortteja(vuoro, pakka));
    }

    @Test
    public void testPinoKaatuu() throws Exception {
        pino.lisaaKortti(new Kortti(Kortti.HERTTA, 3));
        pelaaja.lisaaKorttiKateen(new Kortti(Kortti.HERTTA, 10));
        vuoro.otaKadesta(new Kortti(Kortti.HERTTA, 10));
        pino.lisaaKortti(new Kortti(Kortti.HERTTA, 10));
        assertTrue(Saannot.pinoKaatuu(pino, vuoro));
        assertTrue(pino.korttienMaara() > 1);

        pino = new Korttijoukko();
        vuoro = new Vuoro();
        pelaaja = new Pelaaja();
        vuoro.setPelaaja(pelaaja);

        pino.lisaaKortti(new Kortti(Kortti.HERTTA, 3));
        pelaaja.lisaaKorttiKateen(new Kortti(Kortti.HERTTA, 14));
        vuoro.otaKadesta(new Kortti(Kortti.HERTTA, 14));
        pino.lisaaKortti(new Kortti(Kortti.HERTTA, 14));
        assertTrue(Saannot.pinoKaatuu(pino, vuoro));
        assertTrue(pino.korttienMaara() > 1);
    }

    @Test
    public void testKorttiSopii() {
        vuoro.setPelaaja(pelaaja);

        pino.lisaaKortti(new Kortti(Kortti.HERTTA, 4));
        assertTrue(Saannot.korttiSopii(pino, pakka, vuoro, new Kortti(Kortti.PATA, 5)));
        pino.lisaaKortti(new Kortti(Kortti.PATA, 4));
        assertTrue(Saannot.korttiSopii(pino, pakka, vuoro, new Kortti(Kortti.PATA, 5)));
    }

    @Test
    public void testVuoronEnsimmainenSiirtoOk() {
        assertTrue(Saannot.vuoronEnsimmainenSiirtoOk(pakka, pino, new Kortti(Kortti.HERTTA, 2)));
        assertTrue(Saannot.vuoronEnsimmainenSiirtoOk(pakka, pino, new Kortti(Kortti.HERTTA, 10)));
        assertFalse(Saannot.vuoronEnsimmainenSiirtoOk(pakka, pino, new Kortti(Kortti.HERTTA, 11)));
        assertTrue(Saannot.vuoronEnsimmainenSiirtoOk(pakka, pino, new Kortti(Kortti.HERTTA, 14)));
        pino.lisaaKortti(new Kortti(Kortti.HERTTA, 4));
        assertTrue(Saannot.vuoronEnsimmainenSiirtoOk(pakka, pino, new Kortti(Kortti.HERTTA, 5)));
        assertFalse(Saannot.vuoronEnsimmainenSiirtoOk(pakka, pino, new Kortti(Kortti.HERTTA, 3)));
        pino.lisaaKortti(new Kortti(Kortti.HERTTA, 2));
        assertFalse(Saannot.vuoronEnsimmainenSiirtoOk(pakka, pino, new Kortti(Kortti.HERTTA, 3)));
    }

    @Test
    public void testSamaArvo() {
        pino.lisaaKortti(new Kortti(Kortti.HERTTA, 4));
        vuoro.getLaitettuPinoon().lisaaKortti(new Kortti(Kortti.HERTTA, 4));
        assertTrue(Saannot.samaArvo(vuoro, new Kortti(Kortti.PATA, 4)));
        assertFalse(Saannot.samaArvo(vuoro, new Kortti(Kortti.HERTTA, 5)));
    }

}
