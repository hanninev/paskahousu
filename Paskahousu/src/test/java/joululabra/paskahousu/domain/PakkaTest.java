package joululabra.paskahousu.domain;

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
        assertEquals(pakka.korttienMaara(), 52);
    }
}
