package joululabra.paskahousu;

import java.util.Scanner;
import javax.swing.SwingUtilities;
import joululabra.paskahousu.domain.Kortti;
import joululabra.paskahousu.domain.Pelaaja;
import joululabra.paskahousu.sovelluslogiikka.Peli;
import joululabra.paskahousu.gui.Kayttoliittyma;

public class Paaohjelma {

    public static void main(String[] args) {
        Kayttoliittyma kayttoliittyma = new Kayttoliittyma();
        SwingUtilities.invokeLater(kayttoliittyma);
    }

 /*   public static void main(String[] args) throws Exception {
        // väliaikainen käyttöliittymä, jolla voi testata toiminnallisuutta

        Scanner lukija = new Scanner(System.in);
        Peli peli = new Peli();

        peli.lisaaPelaaja("Ville");
        peli.lisaaPelaaja("Anni");
        peli.getPelaajat().get(1).setTekoaly(true);

        peli.sekoitaPakka();
        peli.jaaKortit();

        while (peli.peliJatkuu()) {
            for (Pelaaja pelaaja : peli.getPelaajat()) {
                peli.getSk().lisaaVuoro(pelaaja);
                peli.getSk().nostaPinoJosOnPakko();

                while (peli.getSk().nykyinenVuoro().jatkuu()) {
                    if (pelaaja.isTekoaly()) {
                        peli.getTekoaly().valitseToiminto();
                        System.out.println("Vastustaja laittoi: " + peli.getSk().nykyinenVuoro().getLaitetut().getKortit());
                        break;
                    } else {

                        System.out.println("Pakkaa jäljellä: " + peli.getSk().getPakka().korttienMaara());
                        System.out.println("Kortteja pinossa: " + peli.getSk().getPino().korttienMaara());
                        System.out.println("Pinon ylin kortti: " + peli.getSk().getPino().viimeisinKortti());
                        System.out.println("Kortit kädessä: " + pelaaja.getKasi().getKortit());
                        System.out.println(pelaaja.getNimi() + ", valitse toiminto.");
                        System.out.println("Komennot:");
                        System.out.println("a = lisää kortti pinoon");
                        System.out.println("b = kokeile onnea pakasta");
                        System.out.println("c = nosta pino");
                        System.out.println("x = olen valmis, seuraavan vuoro");
                        String komento = lukija.nextLine();

                        if (komento.equals("a")) {
                            System.out.println("valitse kortti:");
                            int maa = Integer.parseInt(lukija.nextLine());
                            int arvo = Integer.parseInt(lukija.nextLine());
                            peli.getSk().siirraKorttiPinoon(new Kortti(maa, arvo));

                        } else if (komento.equals("b")) {
                            peli.getSk().kokeileOnnea();
                            break;

                        } else if (komento.equals("c")) {
                            peli.getSk().nostaPino();
                            break;

                        } else if (komento.equals("x")) {
                            peli.getSk().nykyinenVuoro().setJatkuu(false);
                            peli.getSk().taydennaKasi();
                            break;
                        }
                    }
                }
            }
        }
    } */
}
