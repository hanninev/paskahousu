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
    private DefaultListModel model;
    private JLabel vastustaja;
    private JLabel pino;
    private JLabel pakka;
    private Map<JButton, Kortti> buttonitJaKortit;

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
            ImageIcon imageIcon = muunnaKuvaksi(kortti);
            JButton korttiButton = new JButton(imageIcon);
            korttiButton.setBackground(Color.WHITE);
            korttiButton.addActionListener(this);
            buttonitJaKortit.put(korttiButton, kortti);
            kortitKadessa.add(korttiButton);
        }

        kortitKadessa.setLayout(new GridLayout(1, buttonitJaKortit.size()));
        kortitKadessa.validate();
        kortitKadessa.repaint();

        vastustaja.setText(peli.getPelaajat().get(1).getNimi() + ", kortteja kädessä " + peli.getPelaajat().get(1).getKasi().korttienMaara());
        pino.setText("Pinossa " + peli.getSk().getPino().korttienMaara() + " korttia.");
        pino.setIcon(muunnaKuvaksi(peli.getSk().getPino().viimeisinKortti()));
        pino.setHorizontalTextPosition(JLabel.CENTER);
        pino.setVerticalTextPosition(JLabel.BOTTOM);
        pakka.setText("Kortteja pakassa: " + peli.getSk().getPakka().korttienMaara());
        
        pakka.repaint();
    }

    public ImageIcon muunnaKuvaksi(Kortti kortti) {
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
            for (JButton jbutton : buttonitJaKortit.keySet()) {
                if (ae.getSource() == (JButton) jbutton) {
                    peli.getSk().siirraKorttiPinoon(buttonitJaKortit.get(jbutton));
                    valmis.setEnabled(true);
                    kokeileOnnea.setEnabled(false);
                    nostaPino.setEnabled(false);
                    break;
                }

            }

            if (ae.getSource() == kokeileOnnea) {
                peli.getSk().kokeileOnnea();
                pelaajanVuoroPaattyy();
                paivitaNakyma();
            } else if (ae.getSource() == nostaPino) {
                peli.getSk().nostaPino();
                pelaajanVuoroPaattyy();
                paivitaNakyma();
            } else if (ae.getSource() == valmis) {
                peli.getSk().taydennaKasi();
                pelaajanVuoroPaattyy();
                paivitaNakyma();
            }

            paivitaNakyma();

        } catch (Exception e) {
            this.viestikentta.setText(e.getMessage());
        }
    }

    public void pelaajanVuoroPaattyy() throws Exception {
        peli.asetaSeuraavaPelaajaVuoroon();
        kasittelePainikkeet();
    }

    public void kasittelePainikkeet() throws Exception {
        if (!peli.peliJatkuu()) {
            viestikentta.setText(peli.getSk().nykyinenVuoro().getPelaaja().getNimi().toString() + " hävisi!");
            valmis.setEnabled(false);
            kokeileOnnea.setEnabled(false);
            nostaPino.setEnabled(false);
        } else {

            if (Saannot.pakkoNostaaPino(peli.getSk().getPino())) {
                viestikentta.setText(peli.getSk().nykyinenVuoro().getPelaaja().toString() + " joutui nostamaan pinon.");
                peli.getSk().nostaPino();
                peli.asetaSeuraavaPelaajaVuoroon();
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

            if (!peli.getSk().nykyinenVuoro().getPelaaja().isTekoaly()) {
                return;
            }
            vastustajaSiirtaa();
        }
    }

    public void vastustajaSiirtaa() throws Exception {
        Integer pinonKoko = peli.getSk().getPino().korttienMaara();
        peli.getTekoaly().valitseToiminto();
        StringBuilder sb = new StringBuilder();
        if (peli.getSk().nykyinenVuoro().getNostetut().korttienMaara() == pinonKoko) {
            sb.append("Vastustaja nosti pinon. ");
        } else if (peli.getSk().nykyinenVuoro().getNostetut().korttienMaara() == 1) {
            sb.append("Vastustaja kokeili onneaan ja kortti oli sopiva. ");
        } else if (peli.getSk().nykyinenVuoro().getNostetut().korttienMaara() == 1 + pinonKoko) {
            sb.append("Vastustaja kokeili onneaan ja joutui nostamaan pinon. ");
        }
        if (!peli.getSk().nykyinenVuoro().getLaitetut().onTyhja()) {
            sb.append("Vastustaja laittoi pinoon kortit: " + peli.getSk().nykyinenVuoro().getLaitetut().getKortit() + " ");
        }
        sb.append("Sinun vuorosi.");
        viestikentta.setText(sb.toString());
        peli.getSk().taydennaKasi();
        peli.asetaSeuraavaPelaajaVuoroon();
        kasittelePainikkeet();
    }
}
