package joululabra.paskahousu;

import java.util.Scanner;
import joululabra.paskahousu.domain.Kortti;
import joululabra.paskahousu.domain.Pelaaja;
import joululabra.paskahousu.domain.Vuoro;
import joululabra.paskahousu.sovelluslogiikka.Peli;
import joululabra.paskahousu.sovelluslogiikka.Saannot;
import joululabra.paskahousu.sovelluslogiikka.Siirtojenkasittelija;

public class Paaohjelma {

    public static void main(String[] args) throws Exception {
        // väliaikainen käyttöliittymä, jolla voi testata toiminnallisuutta

        Scanner lukija = new Scanner(System.in);
        Peli peli = new Peli();
        Siirtojenkasittelija sk = new Siirtojenkasittelija();

        Pelaaja ville = new Pelaaja();
        ville.setNimi("Ville");
        peli.lisaaPelaaja(ville);

        Pelaaja jani = new Pelaaja();
        jani.setNimi("Jani");
        peli.lisaaPelaaja(jani);

        peli.alusta();

        while (peli.peliJatkuu()) {
            for (Pelaaja pelaaja : peli.getPelaajat()) {

                Vuoro vuoro = peli.lisaaVuoro(pelaaja);
                if (Saannot.joutuuAinaNostamaanPinon(peli.getPino())) {
                    sk.nostaPino(peli.getPino(), vuoro);
                    break;
                }
                while (vuoro.jatkuu()) {
                    System.out.println("Pakkaa jäljellä: " + peli.getPakka().korttienMaara());
                    System.out.println("Kortteja pinossa: " + peli.getPino().korttienMaara());
                    System.out.println("Pinon ylin kortti: " + peli.getPino().viimeisinKortti());
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
                        sk.siirraKorttiPinoon(new Kortti(maa, arvo), peli.getPakka(), peli.getKaatuneetKortit(), peli.getPino(), vuoro);

                    } else if (komento.equals("b")) {
                        sk.kokeileOnnea(peli.getPakka(), peli.getPino(), peli.getKaatuneetKortit(), vuoro);
                        break;

                    } else if (komento.equals("c")) {
                        sk.nostaPino(peli.getPino(), vuoro);
                        break;

                    } else if (komento.equals("x")) {
                        vuoro.setJatkuu(false);
                        sk.taydennaKasi(peli.getPakka(), vuoro);
                        break;
                    }
                }
            }
        }
    }
}
