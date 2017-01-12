package joululabra.paskahousu.gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import joululabra.paskahousu.domain.Kortti;
import joululabra.paskahousu.domain.Korttijoukko;
import joululabra.paskahousu.sovelluslogiikka.Peli;
import joululabra.paskahousu.sovelluslogiikka.Saannot;

public class Klikkaustenkasittelija implements ActionListener {

    private JButton kokeileOnnea;
    private JButton nostaPino;
    private JButton valmis;
    private JLabel viestikentta;
    private Peli peli;
    private JPanel kortitKadessa;
    private JLabel vastustaja;
    private JLabel pino;
    private JLabel pakka;
    private Map<JButton, Kortti> painikkeetJaKortit;
    private StringBuilder sb;

    public Klikkaustenkasittelija(Peli peli, JLabel vastustaja, JLabel pino, JLabel pakka, JPanel kortitKadessa, JButton kokeileOnnea, JButton nostaPino, JButton valmis, JLabel viestikentta) {
        this.peli = peli;
        this.kokeileOnnea = kokeileOnnea;
        this.nostaPino = nostaPino;
        this.valmis = valmis;
        this.viestikentta = viestikentta;
        this.kortitKadessa = kortitKadessa;
        this.vastustaja = vastustaja;
        this.pino = pino;
        this.pakka = pakka;
        this.painikkeetJaKortit = new HashMap<>();
        paivitaNakyma();
    }

    public void paivitaNakyma() {
        painikkeetJaKortit.clear();
        kortitKadessa.removeAll();

        for (Kortti kortti : peli.getPelaajat().get(0).getKasi().getKortit()) {
            ImageIcon imageIcon = haeKortinKuvaJaMuunnaKoko(kortti);
            JButton korttiPainike = new JButton(imageIcon);
            korttiPainike.setHorizontalAlignment(SwingConstants.LEFT);
            korttiPainike.setBackground(Color.WHITE);
            korttiPainike.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            korttiPainike.addActionListener(this);
            painikkeetJaKortit.put(korttiPainike, kortti);
            kortitKadessa.add(korttiPainike);
        }

        kortitKadessa.setLayout(new GridLayout(1, painikkeetJaKortit.size()));
        kortitKadessa.validate();
        kortitKadessa.repaint();

        vastustaja.setText("<html>" + peli.getPelaajat().get(1).getNimi() + ", kortteja kädessä " + peli.getPelaajat().get(1).getKasi().korttienMaara() + "<br><br>Tässä myös vastustajan kortit<br>testauksen helpottamiseksi:<br>" + peli.getPelaajat().get(1).getKasi().getKortit() + "</html>");
        pino.setText("Pinossa " + peli.getSk().getPino().korttienMaara() + " korttia.");
        pino.setIcon(haeKortinKuvaJaMuunnaKoko(peli.getSk().getPino().viimeisinKortti()));
        pino.setHorizontalTextPosition(JLabel.CENTER);
        pino.setVerticalTextPosition(JLabel.BOTTOM);
        pakka.setText("Kortteja pakassa: " + peli.getSk().getPakka().korttienMaara());

        pakka.repaint();
    }

    public ImageIcon haeKortinKuvaJaMuunnaKoko(Kortti kortti) {
        if (kortti == null) {
            kortti = new Kortti(Kortti.HERTTA, 0);
        }
        ImageIcon imageIcon = new ImageIcon(Klikkaustenkasittelija.class.getResource("images/" + Kortti.MAAT[kortti.getMaa()].toLowerCase() + kortti.getArvo() + ".png"));
        Image image = imageIcon.getImage();
        Image newimg = image.getScaledInstance(100, 145, java.awt.Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(newimg);
        return imageIcon;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        try {
            sb = new StringBuilder();

            for (JButton jbutton : painikkeetJaKortit.keySet()) {
                if (ae.getSource() == (JButton) jbutton) {
                    peli.getSk().siirraKorttiPinoon(painikkeetJaKortit.get(jbutton));
                    valmis.setEnabled(true);
                    kokeileOnnea.setEnabled(false);
                    nostaPino.setEnabled(false);
                }
            }

            if (ae.getSource() == kokeileOnnea) {
                peli.getSk().kokeileOnnea();
                kokeileOnnea.setEnabled(false);
                valmis.setEnabled(false);
                sb.append("Sait pakasta kortin " + peli.getSk().nykyinenVuoro().getPelaaja().getKasi().viimeisinKortti().toString() + ". <br>Jos mikään kortti ei käy pinoon, sinun on pakko nostaa pino.");
            } else if (ae.getSource() == nostaPino) {
                peli.getSk().nostaPino();
                sb.append("Nostit pinon. Vuoro siirtyi vastustajalle.<br>");
                kasittelePainikkeet();
            } else if (ae.getSource() == valmis) {
                peli.getSk().valmis();
                if (peli.getSk().nykyinenVuoro().isKaatoiPinon()) {
                    sb.append("Kaadoit pinon. Saat uuden vuoron.<br>");
                }
                kasittelePainikkeet();
            }

            paivitaViestikentta();
            paivitaNakyma();

        } catch (Exception e) {
            sb.append(e.getMessage().toString());
            paivitaViestikentta();
        }
    }

    private void paivitaViestikentta() {
        viestikentta.setText("<html><br><br><center>" + sb.toString() + "</center></html>");
    }

    public void kasittelePainikkeet() throws Exception {
        if (!peli.peliJatkuu()) {
            ilmoitaPelinPaattyminen();
            return;
        } else {
            peli.getSk().vuoronVaihtuminen();
            paivitaViestikentta();
        }

        valmis.setEnabled(false);
        kokeileOnnea.setEnabled(true);
        nostaPino.setEnabled(true);

        if ((peli.getSk().getPakka().onTyhja()) || (!Saannot.saaKokeillaOnnea(peli.getSk().nykyinenVuoro()))) {
            kokeileOnnea.setEnabled(false);
        }
        if (peli.getSk().getPino().onTyhja()) {
            nostaPino.setEnabled(false);
        }
        if ((peli.getSk().getPino().onTyhja()) && (!peli.getSk().getPakka().onTyhja()) && (peli.getSk().nykyinenVuoro().getPelaaja().pieninKortti().getArvo() >= 11) && (peli.getSk().nykyinenVuoro().getPelaaja().suurinKortti().getArvo() < 14)) {
            toiminnanTeksti("Pakkaa on jäljellä, joten mikään kädessä olevista korteistasi ei sovi tyhjään pöytään.<br>Voit luopua vuorostasi tai kokeilla onneasi pakasta, jos et jo tehnyt sitä.<br>", "");
            valmis.setEnabled(true);
        }

        paivitaNakyma();

        if (!peli.getSk().nykyinenVuoro().getPelaaja().isTekoaly()) {
            sb.append("Sinun vuorosi.<br>");
            paivitaViestikentta();
            return;
        }
        vastustajaSiirtaa();
    }

    private void ilmoitaPelinPaattyminen() {
        toiminnanTeksti("Sinä voitit, onnea!", "Vastustaja voitti!");
        valmis.setEnabled(false);
        kokeileOnnea.setEnabled(false);
        nostaPino.setEnabled(false);
        kortitKadessa.setEnabled(false);
    }

    public void vastustajaSiirtaa() throws Exception {
        Integer pinonKoko = peli.getSk().getPino().korttienMaara();
        Korttijoukko lisatyt = peli.getTekoaly().valitseToiminto();
        peli.getSk().valmis();
        if (peli.getSk().nykyinenVuoro().getLaitettuPinoon() != 0) {
            sb.append("Vastustaja laittoi pinoon " + lisatyt.toString() + " <br>");
        }
        tekstitVastustajanTekemisesta(pinonKoko);

        paivitaViestikentta();
        kasittelePainikkeet();
    }

    private void tekstitVastustajanTekemisesta(Integer pinonKoko) {

        if (peli.getSk().nykyinenVuoro().isKokeiliOnnea()) {
            sb.append("Vastustaja kokeili onneaan, ja hän ");
            if (peli.getSk().getPino().korttienMaara() >= pinonKoko + 1) {
                sb.append("laittoi pinoon sopivan kortin.<br>");
            } else {
                sb.append("joutui nostamaan pinon.<br>");
            }
        } else if (peli.getSk().nykyinenVuoro().isNostiPinon()) {
            sb.append("Vastustaja nosti pinon. <br>");
        }

        if (peli.getSk().nykyinenVuoro().isKaatoiPinon()) {
            sb.append("Vastustaja kaatoi pinon ja sai uuden vuoron.<br>");
        }
    }

    private void toiminnanTeksti(String kayttaja, String vastustaja) {
        if (peli.getSk().nykyinenVuoro().getPelaaja().equals(peli.getPelaajat().get(0))) {
            sb.append(kayttaja);
        } else if (peli.getSk().nykyinenVuoro().getPelaaja().equals(peli.getPelaajat().get(1))) {
            sb.append(vastustaja);
        }
    }
}
