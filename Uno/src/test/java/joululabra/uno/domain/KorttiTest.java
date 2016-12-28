package joululabra.uno.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class KorttiTest {

    Kortti punainen2;
    Kortti keltainen2;
    Kortti punainen5;

    @Before
    public void setUp() {
        punainen2 = new Kortti(Kortti.PUNAINEN, 2);
        keltainen2 = new Kortti(Kortti.KELTAINEN, 2);
        punainen5 = new Kortti(Kortti.PUNAINEN, 5);
    }

    @Test
    public void testGetArvo() {
        assertEquals(2, punainen2.getArvo());
    }

    @Test
    public void testGetVari() {
        assertEquals(Kortti.PUNAINEN, punainen2.getVari());
    }

    @Test
    public void testToString() {
        assertEquals("Punainen 2", punainen2.toString());
    }

    @Test
    public void testCompareTo() {
        List<Kortti> kortit = new ArrayList<>();
        kortit.add(punainen5);
        kortit.add(punainen2);
        kortit.add(keltainen2);

        Collections.sort(kortit);

        assertTrue(kortit.get(0) == punainen2);
        assertTrue(kortit.get(1) == keltainen2);
        assertTrue(kortit.get(2) == punainen5);
    }

    @Test
    public void testEqualsJaHashCode() {
        assertTrue(new Kortti(Kortti.PUNAINEN, 2).equals(new Kortti(Kortti.PUNAINEN, 2)));
    }

}
