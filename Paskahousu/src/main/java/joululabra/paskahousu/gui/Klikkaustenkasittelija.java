package joululabra.paskahousu.gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import joululabra.paskahousu.domain.Kortti;
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
    private Map<JButton, Kortti> buttonitJaKortit;
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
        this.buttonitJaKortit = new HashMap<>();
        paivitaNakyma();
    }

    public void paivitaNakyma() {
        buttonitJaKortit.clear();
        kortitKadessa.removeAll();

        for (Kortti kortti : peli.getPelaajat().get(0).getKasi().getKortit()) {
            ImageIcon imageIcon = haeKortinKuvaJaMuunnaKoko(kortti);
            JButton korttiButton = new JButton(imageIcon);
            korttiButton.setBackground(Color.WHITE);
            korttiButton.addActionListener(this);
            buttonitJaKortit.put(korttiButton, kortti);
            kortitKadessa.add(korttiButton);
        }

        kortitKadessa.setLayout(new GridLayout(1, buttonitJaKortit.size()));
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
        ImageIcon imageIcon = new ImageIcon(Kortti.MAAT[kortti.getMaa()].toLowerCase() + kortti.getArvo() + ".png");
        Image image = imageIcon.getImage();
        Image newimg = image.getScaledInstance(100, 145, java.awt.Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(newimg);
        return imageIcon;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        try {
            sb = new StringBuilder();

            for (JButton jbutton : buttonitJaKortit.keySet()) {
                if (ae.getSource() == (JButton) jbutton) {
                    peli.getSk().siirraKorttiPinoon(buttonitJaKortit.get(jbutton));
                    valmis.setEnabled(true);
                    kokeileOnnea.setEnabled(false);
                    nostaPino.setEnabled(false);
                }
            }

            if (ae.getSource() == kokeileOnnea) {
                peli.getSk().kokeileOnnea();
                if (peli.getSk().nykyinenVuoro().getNostetut().getKortit().size() > 1) {
                    sb.append("Sait pakasta kortin " + peli.getSk().nykyinenVuoro().getNostetut().getKortit().get(0) + ". Kortti ei käynyt pinoon, joten jouduit nostamaan pinon.<br>");
                } else {
                    sb.append("Sait pakasta kortin " + peli.getSk().nykyinenVuoro().getNostetut().getKortit().get(0) + ". Sinulla kävi onni. Kortti oli sopiva pinoon.<br>");
                }
                kasitteleVuoro();
            } else if (ae.getSource() == nostaPino) {
                peli.getSk().nostaPino();
                kasitteleVuoro();
            } else if (ae.getSource() == valmis) {
                peli.getSk().taydennaKasi();
                peli.asetaSeuraavaPelaajaVuoroon();
                kasitteleVuoro();
            }

            paivitaNakyma();

        } catch (Exception e) {
            viestikentta.setText("<html><br><br><br><center>" + e.getMessage() + "</center></html>");
        }
    }

    public void paivitaPainikkeetJaViestikentta() {
        this.kokeileOnnea.repaint();
        this.nostaPino.repaint();
        this.viestikentta.repaint();
        this.valmis.repaint();
    }

    public void kasitteleVuoro() throws Exception {
        if (!peli.peliJatkuu()) {
            sb.append(peli.getSk().nykyinenVuoro().getPelaaja().getNimi().toString() + " hävisi!<br>");
            valmis.setEnabled(false);
            kokeileOnnea.setEnabled(false);
            nostaPino.setEnabled(false);
            kortitKadessa.setEnabled(false);
        } else {

            if (Saannot.pakkoNostaaPino(peli.getSk().getPino())) {
                if (peli.getSk().nykyinenVuoro().getPelaaja().equals(peli.getPelaajat().get(0))) {
                    sb.append("Sinä jouduit nostamaan kortin.<br>");
                } else {
                    sb.append(peli.getSk().nykyinenVuoro().getPelaaja().getNimi() + " joutui nostamaan kortin.<br>");
                }
                peli.getSk().nostaPino();
                kortitKadessa.setEnabled(false);
                kokeileOnnea.setEnabled(false);
                nostaPino.setEnabled(true);
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

            paivitaNakyma();

            if (!peli.getSk().nykyinenVuoro().getPelaaja().isTekoaly()) {
                return;
            }
            vastustajaSiirtaa();
        }
    }

    public void vastustajaSiirtaa() throws Exception {
        Integer pinonKoko = peli.getSk().getPino().korttienMaara();
        peli.getTekoaly().valitseToiminto();
        if ((peli.getSk().nykyinenVuoro().getNostetut().korttienMaara() == pinonKoko) && (pinonKoko != 0)) {
            sb.append("Vastustaja nosti pinon. ");
        } else if (peli.getSk().nykyinenVuoro().getNostetut().korttienMaara() == 1) {
            sb.append("Vastustaja kokeili onneaan ja kortti oli sopiva. <br>");
        } else if (peli.getSk().nykyinenVuoro().getNostetut().korttienMaara() == 1 + pinonKoko) {
            sb.append("Vastustaja kokeili onneaan ja joutui nostamaan pinon. <br>");
        }
        if (!peli.getSk().nykyinenVuoro().getLaitetut().onTyhja()) {
            sb.append("Vastustaja laittoi pinoon kortit: " + peli.getSk().nykyinenVuoro().getLaitetut().getKortit() + " <br>");
        }
        sb.append("Sinun vuorosi.");
        viestikentta.setText("<html><br><center>" + sb.toString() + "</center></html>");
        peli.getSk().taydennaKasi();
        peli.asetaSeuraavaPelaajaVuoroon();
        kasitteleVuoro();
    }
}
