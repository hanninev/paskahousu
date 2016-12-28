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
                    peli.getS().siirraPinostaPakkaan();
                    System.out.println("Pakkaa jäljellä: " + peli.getS().getPakka().getKorttienMaara());
                    System.out.println("Pinon ylin:" + peli.getS().getPino().viimeisinKortti());
                    System.out.println("Kädessäsi olevat: " + pelaaja.getKasi().getKortit());

                    System.out.println(pelaaja.getNimi() + ", valitse kortti. Jos ei käy, nosta pakasta 0, nosta pino 20");
                    int vari = Integer.parseInt(lukija.nextLine());
                    int arvo = Integer.parseInt(lukija.nextLine());
                    if (vari == 0) {
                        peli.getS().otaKorttiPakasta(pelaaja);
                    } else if (vari == 20) {
                        peli.getS().nostaPino(pelaaja);
                    } else {
                        peli.getS().teeSiirto(pelaaja, new Kortti(vari, arvo));
                    }

                    if (Saannot.seuraavaNostaaKaksi(peli.getS().getPino().viimeisinKortti())) {
                        peli.getS().otaKorttejaPakasta(peli.getSeuraavaPelaaja(), 2);
                    }

                    if (Saannot.seuraavaJaaValista(peli.getS().getPino().viimeisinKortti())) {
                        peli.seuraavanVuoro();
                    }

                    peli.seuraavanVuoro();
                }
            }
        }
    }
}
