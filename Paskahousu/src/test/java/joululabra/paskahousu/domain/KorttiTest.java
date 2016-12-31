package joululabra.paskahousu.domain;

import joululabra.paskahousu.domain.Kortti;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class KorttiTest {

    Kortti hertta2;
    Kortti pata2;
    Kortti hertta5;

    @Before
    public void setUp() {
        hertta2 = new Kortti(Kortti.HERTTA, 2);
        pata2 = new Kortti(Kortti.PATA, 2);
        hertta5 = new Kortti(Kortti.HERTTA, 5);
    }

    @Test
    public void testGetArvo() {
        assertEquals(2, hertta2.getArvo());
    }

    @Test
    public void testGetMaa() {
        assertEquals(Kortti.HERTTA, hertta2.getMaa());
    }

    @Test
    public void testToString() {
        assertEquals("Hertta 2", hertta2.toString());
    }

    @Test
    public void testCompareTo() {
        List<Kortti> kortit = new ArrayList<>();
        kortit.add(hertta5);
        kortit.add(hertta2);
        kortit.add(pata2);

        Collections.sort(kortit);

        assertTrue(kortit.get(0) == pata2);
        assertTrue(kortit.get(1) == hertta2);
        assertTrue(kortit.get(2) == hertta5);
    }

    @Test
    public void testEqualsJaHashCode() {
        assertTrue(new Kortti(Kortti.HERTTA, 2).equals(new Kortti(Kortti.HERTTA, 2)));
    }

}
