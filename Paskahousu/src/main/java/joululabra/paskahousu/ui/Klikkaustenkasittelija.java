package joululabra.paskahousu.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JTextField;

public class Klikkaustenkasittelija implements ActionListener {

    private JButton siirraKortti;
    private JButton kokeileOnnea;
    private JButton nostaPino;
    private JButton valmis;
    private JTextField viestikentta;

    public Klikkaustenkasittelija(JButton siirraKortti, JButton kokeileOnnea, JButton nostaPino, JButton valmis, JTextField viestikentta) {
        this.siirraKortti = siirraKortti;
        this.kokeileOnnea = kokeileOnnea;
        this.nostaPino = nostaPino;
        this.valmis = valmis;
        this.viestikentta = viestikentta;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        try {
            if (ae.getSource() == siirraKortti) {
                // valitaan kortti
            } else if (ae.getSource() == kokeileOnnea) {
                //kokeillaan
            } else if (ae.getSource() == nostaPino) {
                //nostetaan
            } else if (ae.getSource() == valmis) {
                //seuraavan vuoro
            }
        } catch (Exception e) {
            this.viestikentta.setText(e.getMessage());
        }
    }

}
