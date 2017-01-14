package joululabra.paskahousu.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import joululabra.paskahousu.sovelluslogiikka.Peli;

public class Kayttoliittyma implements Runnable {

    private JFrame frame;
    private Peli peli;
    private JLabel viestikentta;
    private JPanel kortitKadessa;
    private JLabel vastustaja;
    private JLabel pino;
    private JLabel pakka;

    @Override
    public void run() {
        this.kortitKadessa = new JPanel();
        this.peli = new Peli();

        peli.sekoitaPakka();
        peli.lisaaPelaaja("Pelaaja");
        peli.lisaaPelaaja("Vastustaja");
        peli.getPelaajat().get(1).setTekoaly(true);

        try {
            peli.jaaKortit();
        } catch (Exception e) {
            System.out.println(e);
        }
        peli.getSk().lisaaVuoro(peli.getPelaajat().get(0));

        frame = new JFrame("Paskahousu");
        frame.setPreferredSize(new Dimension(800, 600));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        luoKomponentit(frame.getContentPane());
        frame.pack();
        frame.setVisible(true);
    }

    private void luoKomponentit(Container container) {

        viestikentta = new JLabel("<html><br>Sinä aloitat. Valitse pinoon laitettavat kortit klikkaamalla niitä.<br><br>"
                + "Tärkeimmät säännöt:<br>"
                + "* Voit laittaa vuorollasi pinoon useamman saman arvoisen kortin.<br> "
                + "* Kuvakortit eivät sovi tyhjään pöytään.<br>"
                + "* Kaikki kakkoset ovat kovia, ja niiden päälle ei käy mikään muu kortti.<br>"
                + "* Kymppi ja ässä ovat kaatokortteja.<br><br>"
                + "<center>Peli ei anna sinun tehdä virheellisiä siirtoja. <br>"
                + "Jos yrität tehdä virheellisen siirron, niin peli opastaa sinua.<br></center></html>");
        container.add(this.vastustajanJaPakanJaPinonTiedot(), BorderLayout.NORTH);
        container.add(this.valikko(), BorderLayout.CENTER);
        container.add(kortitKadessa, BorderLayout.SOUTH);

    }

    private JPanel vastustajanJaPakanJaPinonTiedot() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        vastustaja = new JLabel();
        pino = new JLabel();
        pakka = new JLabel();
        panel.add(vastustaja);
        panel.add(pino);
        panel.add(pakka);

        return panel;
    }

    private JPanel valikko() {

        JButton kokeileOnnea = new JButton("Kokeile onnea pakasta");
        JButton nostaPino = new JButton("Nosta pino");
        JButton valmis = new JButton("Valmis, vastustajan vuoro");

        valmis.setEnabled(false);
        kokeileOnnea.setEnabled(false);
        nostaPino.setEnabled(false);

        Klikkaustenkasittelija kasittelija = new Klikkaustenkasittelija(peli, vastustaja, pino, pakka, kortitKadessa, kokeileOnnea, nostaPino, valmis, viestikentta);
        kokeileOnnea.addActionListener(kasittelija);
        nostaPino.addActionListener(kasittelija);
        valmis.addActionListener(kasittelija);

        JPanel painikkeet = new JPanel(new FlowLayout());
        painikkeet.add(kokeileOnnea);
        painikkeet.add(nostaPino);
        painikkeet.add(valmis);
        JPanel tekstit = new JPanel(new FlowLayout());
        tekstit.add(viestikentta);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(painikkeet, BorderLayout.NORTH);
        panel.add(tekstit, BorderLayout.CENTER);

        return panel;
    }

    public JFrame getFrame() {
        return frame;
    }
}
