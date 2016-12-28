package joululabra.uno;

import java.util.Scanner;
import joululabra.uno.domain.Kortti;
import joululabra.uno.domain.Pelaaja;
import joululabra.uno.sovelluslogiikka.Peli;
import joululabra.uno.sovelluslogiikka.Saannot;

public class Paaohjelma {

    public static void main(String[] args) throws Exception {
        // väliaikainen käyttöliittymä, jolla voi testata toiminnallisuutta

        Scanner lukija = new Scanner(System.in);
        Peli peli = new Peli();

        Pelaaja ville = new Pelaaja();
        ville.setNimi("Ville");
        peli.lisaaPelaaja(ville);

        Pelaaja janne = new Pelaaja();
        janne.setNimi("Janne");
        peli.lisaaPelaaja(janne);

        peli.alusta();
        peli.kukaAloittaa();
        while (!peli.peliPaattynyt()) {
            for (Pelaaja pelaaja : peli.getPelaajat()) {
                while (pelaaja.onVuorossa()) {
                    while (Saannot.saaJatkaa(pelaaja)) {
                        peli.siirraPinostaPakkaan();
                        System.out.println("Pinon ylin:" + peli.getPino().viimeisinKortti());
                        System.out.println("");
                        System.out.println("Kädessäsi olevat: " + pelaaja.getKasi().getKortit());

                        System.out.println(pelaaja.getNimi() + ", valitse toiminto.");
                        System.out.println("Komennot:");
                        System.out.println("a = lisää kortti pinoon");
                        System.out.println("b = nosta pakasta");
                        System.out.println("c = nosta pino");
                        System.out.println("x = olen valmis, seuraavan vuoro");

                        String komento = lukija.nextLine();
                        if (Saannot.joutuuNostamaanPinon(pelaaja)) {
                            pelaaja.nostaPino(peli.getPino());
                            peli.seuraavanVuoro();
                            break;
                        } else if (komento.equals("a")) {
                            System.out.println("valitse kortti:");
                            int vari = Integer.parseInt(lukija.nextLine());
                            int arvo = Integer.parseInt(lukija.nextLine());
                            pelaaja.teeSiirto(new Kortti(vari, arvo), peli.getPino());
                        } else if (komento.equals("b")) {
                            pelaaja.nostaPakasta(peli.getPakka());
                        } else if (komento.equals("c")) {
                            pelaaja.nostaPino(peli.getPino());
                            peli.seuraavanVuoro();
                            break;
                        } else if (komento.equals("x")) {
                            peli.seuraavanVuoro();
                            break;
                        }
                    }

                    if (Saannot.seuraavaNostaaKaksi(peli.getPino().viimeisinKortti())) {
                        peli.getSeuraavaPelaaja().nostaKorttejaPakasta(peli.getPakka(), 2);
                    }

                    if (Saannot.seuraavaJaaValista(peli.getPino().viimeisinKortti())) {
                        peli.seuraavanVuoro();
                    }
                }
            }
        }
    }
}
