package joululabra.paskahousu.ui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import joululabra.paskahousu.sovelluslogiikka.Peli;

public class Kayttoliittyma implements Runnable {

    private JFrame frame;
    private Peli peli;
    private JTextField viestikentta;
    private JList list;
    private DefaultListModel model;
    private JScrollPane kortitKadessa;

    @Override
    public void run() {
        this.model = new DefaultListModel();
        this.list = new JList(model);
        this.kortitKadessa = new JScrollPane(list);

        this.peli = new Peli();
        peli.sekoitaPakka();
        peli.lisaaPelaaja("Pelaaja 1");
        peli.lisaaPelaaja("Pelaaja 2");
        peli.getPelaajat().get(1).setTekoaly(true);

        peli.jaaKortit();
        peli.getSk().lisaaVuoro(peli.getPelaajat().get(0));

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

        viestikentta = new JTextField("pinon yli: " + peli.getSk().getPino().viimeisinKortti());
        viestikentta.setEnabled(false);

        container.add(viestikentta);
        container.add(this.valikko());
        container.add(kortitKadessa);
    }

    private JPanel valikko() {

        JPanel panel = new JPanel(new GridLayout(1, 5));
        JButton siirraKortti = new JButton("Siirrä kortti");
        JButton kokeileOnnea = new JButton("Kokeile onnea");
        JButton nostaPino = new JButton("Nosta pino");
        JButton valmis = new JButton("Valmis");

        valmis.setEnabled(false);

        Klikkaustenkasittelija kasittelija = new Klikkaustenkasittelija(peli, list, model, siirraKortti, kokeileOnnea, nostaPino, valmis, viestikentta);
        kokeileOnnea.addActionListener(kasittelija);
        nostaPino.addActionListener(kasittelija);
        valmis.addActionListener(kasittelija);
        siirraKortti.addActionListener(kasittelija);

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
