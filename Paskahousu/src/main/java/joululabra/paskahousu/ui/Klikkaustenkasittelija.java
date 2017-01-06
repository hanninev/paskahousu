package joululabra.paskahousu.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JTextField;
import joululabra.paskahousu.domain.Kortti;
import joululabra.paskahousu.sovelluslogiikka.Peli;
import joululabra.paskahousu.sovelluslogiikka.Saannot;

public class Klikkaustenkasittelija implements ActionListener {

    private JButton siirraKortti;
    private JButton kokeileOnnea;
    private JButton nostaPino;
    private JButton valmis;
    private JTextField viestikentta;
    private Peli peli;
    private JList list;
    private DefaultListModel model;
    private JTextField vastustaja;
    private JTextField pino;
    private JTextField pakka;

    public Klikkaustenkasittelija(Peli peli, JTextField vastustaja, JTextField pino, JTextField pakka, JList list, DefaultListModel model, JButton siirraKortti, JButton kokeileOnnea, JButton nostaPino, JButton valmis, JTextField viestikentta) {
        this.peli = peli;
        this.siirraKortti = siirraKortti;
        this.kokeileOnnea = kokeileOnnea;
        this.nostaPino = nostaPino;
        this.valmis = valmis;
        this.viestikentta = viestikentta;
        this.model = model;
        this.list = list;
        this.vastustaja = vastustaja;
        this.pino = pino;
        this.pakka = pakka;
        paivitaNakyma();
    }

    public void paivitaNakyma() {
        model.clear();

        for (Kortti kortti : peli.getPelaajat().get(0).getKasi().getKortit()) {
            model.addElement(kortti);
        }

        vastustaja.setText(peli.getPelaajat().get(1).getNimi() + ", kortteja kädessä " + peli.getPelaajat().get(1).getKasi().korttienMaara());
        pino.setText("Kortteja pinossa: " + peli.getSk().getPino().korttienMaara() + "\n Pinon ylin: " + peli.getSk().getPino().viimeisinKortti());
        pakka.setText("Kortteja pakassa: " + peli.getSk().getPakka().korttienMaara());
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        try {

            if (ae.getSource() == siirraKortti) {
                kortinSiirtaminen();
                paivitaNakyma();
                viestikentta.setText("");
            } else if (ae.getSource() == kokeileOnnea) {
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

        } catch (Exception e) {
            this.viestikentta.setText(e.getMessage());
        }
    }

    public void kortinSiirtaminen() throws Exception {
        int index = list.getSelectedIndex();
        Kortti kortti = (Kortti) model.getElementAt(index);
        peli.getSk().siirraKorttiPinoon(kortti);
        valmis.setEnabled(true);
        kokeileOnnea.setEnabled(false);
        nostaPino.setEnabled(false);
    }

    public void pelaajanVuoroPaattyy() throws Exception {
        peli.asetaSeuraavaPelaajaVuoroon();
        kasittelePainikkeet();
    }

    public void kasittelePainikkeet() throws Exception {
        if (!peli.peliJatkuu()) {
            viestikentta.setText(peli.getSk().nykyinenVuoro().getPelaaja().getNimi().toString() + " voitti!");
            siirraKortti.setEnabled(false);
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
