package joululabra.paskahousu.sovelluslogiikka;

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
    public void testSaaKokeillaOnnea() throws Exception {
        assertTrue(Saannot.saaKokeillaOnnea(vuoro));
        vuoro.laittoiKortinPinoon();
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
        assertFalse(Saannot.pinoKaatuu(pino, vuoro));
        pino.lisaa(new Kortti(Kortti.HERTTA, 3));
        pelaaja.lisaaKateen(new Kortti(Kortti.HERTTA, 10));
        vuoro.getPelaaja().otaKadesta(new Kortti(Kortti.HERTTA, 10));
        pino.lisaa(new Kortti(Kortti.HERTTA, 10));
        assertTrue(Saannot.pinoKaatuu(pino, vuoro));
        assertTrue(pino.korttienMaara() > 1);

        pino = new Korttijoukko();
        vuoro = new Vuoro(pelaaja);

        pino.lisaa(new Kortti(Kortti.HERTTA, 3));
        pelaaja.lisaaKateen(new Kortti(Kortti.HERTTA, 14));
        vuoro.getPelaaja().otaKadesta(new Kortti(Kortti.HERTTA, 14));
        pino.lisaa(new Kortti(Kortti.HERTTA, 14));
        assertTrue(Saannot.pinoKaatuu(pino, vuoro));
        assertTrue(pino.korttienMaara() > 1);
    }

    @Test
    public void testKuvaEiSoviTyhjaanPoytaan() throws Exception {
        assertTrue(Saannot.korttiSopii(pino, pakka, vuoro, new Kortti(Kortti.HERTTA, 2)));
        assertTrue(Saannot.korttiSopii(pino, pakka, vuoro, new Kortti(Kortti.HERTTA, 10)));
        assertTrue(Saannot.korttiSopii(pino, pakka, vuoro, new Kortti(Kortti.HERTTA, 14)));

        boolean thrown = false;
        try {
            Saannot.korttiSopii(pino, pakka, vuoro, new Kortti(Kortti.HERTTA, 11));
        } catch (Exception e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    @Test
    public void testKakkonenSopiiMinkaTahansaPaalle() throws Exception {
        pino.lisaa(new Kortti(Kortti.HERTTA, 4));
        assertTrue(Saannot.korttiSopii(pino, pakka, vuoro, new Kortti(Kortti.PATA, 2)));
        pino.lisaa(new Kortti(Kortti.HERTTA, 13));
        assertTrue(Saannot.korttiSopii(pino, pakka, vuoro, new Kortti(Kortti.PATA, 2)));
        pino.lisaa(new Kortti(Kortti.HERTTA, 2));
        assertTrue(Saannot.korttiSopii(pino, pakka, vuoro, new Kortti(Kortti.RUUTU, 2)));

    }

    @Test
    public void testPienempiKorttiEiSoviIsommanPaalle() throws Exception {
        pino.lisaa(new Kortti(Kortti.HERTTA, 4));
        assertTrue(Saannot.korttiSopii(pino, pakka, vuoro, new Kortti(Kortti.PATA, 4)));
        assertTrue(Saannot.korttiSopii(pino, pakka, vuoro, new Kortti(Kortti.RUUTU, 5)));

        boolean thrown = false;
        try {
            Saannot.korttiSopii(pino, pakka, vuoro, new Kortti(Kortti.HERTTA, 3));
        } catch (Exception e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    @Test
    public void testKakkosenPaalleEiKayMuuKortti() throws Exception {
        pino.lisaa(new Kortti(Kortti.HERTTA, 2));

        boolean hertta3 = false;
        try {
            Saannot.korttiSopii(pino, pakka, vuoro, new Kortti(Kortti.HERTTA, 3));
        } catch (Exception e) {
            hertta3 = true;
        }
        assertTrue(hertta3);

        boolean hertta10 = false;
        try {
            Saannot.korttiSopii(pino, pakka, vuoro, new Kortti(Kortti.HERTTA, 10));
        } catch (Exception e) {
            hertta10 = true;
        }
        assertTrue(hertta10);

        assertTrue(Saannot.korttiSopii(pino, pakka, vuoro, new Kortti(Kortti.PATA, 2)));
    }

    @Test
    public void testPinoJaPakkaTyhjaKaikkiKay() throws Exception {
        while (!pino.onTyhja()) {
            pino.ota(pino.viimeisinKortti());
        }
        while (!pakka.onTyhja()) {
            pakka.otaEnsimmainenKortti();
        }
        assertTrue(Saannot.korttiSopii(pino, pakka, vuoro, new Kortti(Kortti.PATA, 11)));
    }

    @Test
    public void testSamanVuoronAikanaVainSamanArvoisiaKortteja() throws Exception {
        pino.lisaa(new Kortti(Kortti.HERTTA, 4));
        vuoro.laittoiKortinPinoon();
        assertTrue(Saannot.korttiSopii(pino, pakka, vuoro, new Kortti(Kortti.PATA, 4)));

        boolean thrown = false;
        try {
            Saannot.korttiSopii(pino, pakka, vuoro, new Kortti(Kortti.HERTTA, 5));
        } catch (Exception e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    @Test
    public void testEiKahtaKakkostaTaiKaatokorttiaSamallaVuorolla() {
        int[] arvot = {2, 10, 14};

        for (int i : arvot) {
            pino.lisaa(new Kortti(Kortti.HERTTA, i));
            vuoro.laittoiKortinPinoon();

            boolean thrown = false;
            try {
                Saannot.korttiSopii(pino, pakka, vuoro, new Kortti(Kortti.PATA, i));
            } catch (Exception e) {
                thrown = true;
            }
            assertTrue(thrown);
        }
    }

}
