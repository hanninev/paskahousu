package joululabra.paskahousu;

import javax.swing.SwingUtilities;
import joululabra.paskahousu.gui.Kayttoliittyma;

public class Paaohjelma {

    public static void main(String[] args) {
        Kayttoliittyma kayttoliittyma = new Kayttoliittyma();
        SwingUtilities.invokeLater(kayttoliittyma);
    }

}
