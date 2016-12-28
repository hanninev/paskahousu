package joululabra.uno;

import java.util.Scanner;
import joululabra.uno.domain.Kortti;
import joululabra.uno.domain.Pelaaja;
import joululabra.uno.sovelluslogiikka.Peli;
import joululabra.uno.sovelluslogiikka.Saannot;

public class Paaohjelma {

    public static void main(String[] args) throws Exception {
        // tällä voi testata toiminnallisuutta

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
                    System.out.println("Pakkaa jäljellä: " + peli.getPakka().getKorttienMaara());
                    System.out.println("Pinon ylin:" + peli.getPino().viimeisinKortti());
                    System.out.println("Kädessäsi olevat: " + pelaaja.getKasi().getKortit());
                    System.out.println(pelaaja.getNimi() + ", valitse kortti. Jos ei käy, kirjoita 0 ja 0");
                    int vari = Integer.parseInt(lukija.nextLine());
                    int arvo = Integer.parseInt(lukija.nextLine());
                    if (vari == 0 && arvo == 0) {
                        peli.otaKorttiPakasta(pelaaja);
                    } else {
                        peli.teeSiirto(pelaaja, new Kortti(vari, arvo));
                    }
                /*    if (Saannot.vastustajaNostaaKaksi(peli.getPino().viimeisinKortti())) {
                        for (Pelaaja p : peli.getPelaajat()) {
                            if (!p.onVuorossa()) {
                                peli.otaKorttejaPakasta(pelaaja, 2);
                            }
                        }
                    }
                    if (!Saannot.vuoroJatkuu(peli.getPino().viimeisinKortti())) {
                        peli.seuraavanVuoro();
                    } */
                }
            }
        }
    }
}
