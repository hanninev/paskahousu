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

    public Klikkaustenkasittelija(Peli peli, JList list, DefaultListModel model, JButton siirraKortti, JButton kokeileOnnea, JButton nostaPino, JButton valmis, JTextField viestikentta) {
        this.peli = peli;
        this.siirraKortti = siirraKortti;
        this.kokeileOnnea = kokeileOnnea;
        this.nostaPino = nostaPino;
        this.valmis = valmis;
        this.viestikentta = viestikentta;
        this.model = model;
        this.list = list;
        paivitaKortit();
    }

    public void paivitaKortit() {
        model.clear();

        for (Kortti kortti : peli.getPelaajat().get(0).getKasi().getKortit()) {
            model.addElement(kortti);
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        try {
            if (!peli.peliJatkuu()) {
                siirraKortti.setEnabled(false);
                valmis.setEnabled(false);
                kokeileOnnea.setEnabled(false);
                nostaPino.setEnabled(false);
            } else {

                valmis.setEnabled(true);
                kokeileOnnea.setEnabled(true);
                nostaPino.setEnabled(true);

                if (ae.getSource() == siirraKortti) {
                    kortinSiirtaminen();
                } else if (ae.getSource() == kokeileOnnea) {
                    peli.getSk().kokeileOnnea();
                    pelaajanVuoroPaattyy();
                } else if (ae.getSource() == nostaPino) {
                    peli.getSk().nostaPino();
                    pelaajanVuoroPaattyy();
                } else if (ae.getSource() == valmis) {
                    peli.getSk().taydennaKasi();
                    pelaajanVuoroPaattyy();
                }

                this.viestikentta.setText("pinon ylin: " + peli.getSk().getPino().viimeisinKortti() + " \n vastustajan käsi: " + peli.getPelaajat().get(1).getKasi().getKortit());
            }
        } catch (Exception e) {
            this.viestikentta.setText(e.getMessage());
        }
    }

    public void kortinSiirtaminen() throws Exception {
        int index = list.getSelectedIndex();
        Kortti kortti = (Kortti) model.getElementAt(index);
        peli.getSk().siirraKorttiPinoon(kortti);
        model.removeElementAt(index);
        valmis.setEnabled(true);
        kokeileOnnea.setEnabled(false);
        nostaPino.setEnabled(false);
    }

    public void pelaajanVuoroPaattyy() throws Exception {
        paivitaKortit();
        peli.asetaSeuraavaPelaajaVuoroon();
        vastustajaSiirtaa();
    }

    public void vastustajaSiirtaa() throws Exception {
        if (Saannot.joutuuAinaNostamaanPinon(peli.getSk().getPino())) {
            peli.getSk().nostaPino();
            peli.asetaSeuraavaPelaajaVuoroon();
        }

        if (!peli.getSk().nykyinenVuoro().getPelaaja().isTekoaly()) {
            return;
        }

        peli.getTekoaly().valitseToiminto();
        peli.getSk().taydennaKasi();
        this.viestikentta.setText("vastustaja laittoi: " + peli.getSk().nykyinenVuoro().getLaitetut().getKortit());

        peli.asetaSeuraavaPelaajaVuoroon();
        vastustajaSiirtaa();
    }
}
