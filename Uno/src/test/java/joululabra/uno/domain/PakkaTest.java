package joululabra.uno.domain;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class PakkaTest {

    Pakka pakka;

    @Before
    public void setUp() {
        pakka = new Pakka();
    }

    @Test
    public void uudessaPakassaOnOikeaMaaraKortteja() {
        assertEquals(pakka.getKorttienMaara(), 44);
    }

    @Test
    public void testSekoitaKortit() {
        pakka.sekoitaKortit();

        assertFalse(pakka.getKortit().get(0).getArvo() == 1
                && pakka.getKortit().get(1).getArvo() == 2
                && pakka.getKortit().get(2).getArvo() == 3
                && pakka.getKortit().get(3).getArvo() == 4
                && pakka.getKortit().get(4).getArvo() == 5);
    }

}
