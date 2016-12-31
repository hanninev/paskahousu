package joululabra.paskahousu.domain;

import joululabra.paskahousu.domain.Pakka;
import joululabra.paskahousu.domain.Pelaaja;
import joululabra.paskahousu.domain.Kortti;
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
    public void testPieninKortti() throws Exception {
        //   pelaaja.lisaaKorttiKateen(new Kortti(Kortti.HERTTA, 2));
        pelaaja.lisaaKorttiKateen(new Kortti(Kortti.HERTTA, 4));
        pelaaja.lisaaKorttiKateen(new Kortti(Kortti.PATA, 5));

        assertEquals(pelaaja.pieninKortti(), new Kortti(Kortti.HERTTA, 4));
    }
}
