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
    }

    @Test
    public void testJoutuuAinaNostamaanPinonJosTyhjallaPoydallaKymppiTaiAssa() throws Exception {
        pino.lisaaKortti(new Kortti(Kortti.HERTTA, 10));
        assertTrue(Saannot.joutuuAinaNostamaanPinon(pino));
        pino.otaKortti(new Kortti(Kortti.HERTTA, 10));
        pino.lisaaKortti(new Kortti(Kortti.HERTTA, 14));
        assertTrue(Saannot.joutuuAinaNostamaanPinon(pino));
    }

    @Test
    public void testSaaKokeillaOnnea() {
        assertTrue(Saannot.saaKokeillaOnnea(vuoro));
        vuoro.lisaaNostettuihinKortteihin(new Kortti(Kortti.HERTTA, 3));
        assertFalse(Saannot.saaKokeillaOnnea(vuoro));
        vuoro = new Vuoro();
        vuoro.lisaaPinoonLaitettuihin(new Kortti(Kortti.PATA, 6));
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
    public void testPinoKaatuu() {
        pino.lisaaKortti(new Kortti(Kortti.HERTTA, 3));
        vuoro.lisaaPinoonLaitettuihin(new Kortti(Kortti.HERTTA, 10));
        pino.lisaaKortti(new Kortti(Kortti.HERTTA, 10));
        assertTrue(Saannot.pinoKaatuu(pino, vuoro));

        pino = new Korttijoukko();
        vuoro = new Vuoro();

        pino.lisaaKortti(new Kortti(Kortti.HERTTA, 3));
        vuoro.lisaaPinoonLaitettuihin(new Kortti(Kortti.HERTTA, 14));
        pino.lisaaKortti(new Kortti(Kortti.HERTTA, 14));
        assertTrue(Saannot.pinoKaatuu(pino, vuoro));
    }

    @Test
    public void testKorttiSopii() {
        vuoro.setPelaaja(pelaaja);
        
        pino.lisaaKortti(new Kortti(Kortti.HERTTA, 4));
        assertTrue(Saannot.korttiSopii(pino, pakka, vuoro, new Kortti(Kortti.PATA, 5)));
    }

}
