package joululabra.paskahousu.ui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class Kayttoliittyma implements Runnable {

    private JFrame frame;
    private JTextField viestikentta;

    @Override
    public void run() {
        frame = new JFrame("Paskahousu");
        frame.setPreferredSize(new Dimension(800, 600));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        luoKomponentit(frame.getContentPane());
        frame.pack();
        frame.setVisible(true);
    }

    private void luoKomponentit(Container container) {
        GridLayout layout = new GridLayout(4, 1);
        container.setLayout(layout);

        viestikentta = new JTextField("");
        viestikentta.setEnabled(false);

        container.add(viestikentta);
        container.add(this.valikko());

    }

    private JPanel valikko() {
        JPanel panel = new JPanel(new GridLayout(1, 5));
        JButton siirraKortti = new JButton("Siirrä kortti");
        JButton kokeileOnnea = new JButton("Kokeile onnea");
        JButton nostaPino = new JButton("Nosta pino");
        JButton valmis = new JButton("Valmis");
        JButton testi = new JButton(new ImageIcon("kortti.png"));

        valmis.setEnabled(false);

        Klikkaustenkasittelija kasittelija = new Klikkaustenkasittelija(siirraKortti, kokeileOnnea, nostaPino, valmis, viestikentta);
        siirraKortti.addActionListener(kasittelija);
        kokeileOnnea.addActionListener(kasittelija);
        nostaPino.addActionListener(kasittelija);
        valmis.addActionListener(kasittelija);

        panel.add(testi);
        panel.add(siirraKortti);
        panel.add(kokeileOnnea);
        panel.add(nostaPino);
        panel.add(valmis);
        return panel;
    }

    public JFrame getFrame() {
        return frame;
    }
}
