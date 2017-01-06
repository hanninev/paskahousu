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
        
        viestikentta = new JLabel();
        container.add(this.vastustajanJaPakanJaPinonTiedot(), BorderLayout.NORTH);
        container.add(this.valikko(), BorderLayout.CENTER);
        container.add(kortitKadessa, BorderLayout.SOUTH);
        
    }
    
    private JPanel vastustajanJaPakanJaPinonTiedot() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setMinimumSize(new Dimension(0, 400));
        vastustaja = new JLabel();
        pino = new JLabel();
        pakka = new JLabel();
        panel.add(vastustaja);
        panel.add(pino);
        panel.add(pakka);
        
        return panel;
    }
    
    private JPanel valikko() {
        
        JPanel panel = new JPanel(new FlowLayout());
        JButton kokeileOnnea = new JButton("Kokeile onnea");
        JButton nostaPino = new JButton("Nosta pino");
        JButton valmis = new JButton("Valmis");
        
        valmis.setEnabled(false);
        
        Klikkaustenkasittelija kasittelija = new Klikkaustenkasittelija(peli, vastustaja, pino, pakka, kortitKadessa, kokeileOnnea, nostaPino, valmis, viestikentta);
        kokeileOnnea.addActionListener(kasittelija);
        nostaPino.addActionListener(kasittelija);
        valmis.addActionListener(kasittelija);
        
        panel.add(kokeileOnnea);
        panel.add(nostaPino);
        panel.add(valmis);
        panel.add(viestikentta);
        return panel;
    }
    
    public JFrame getFrame() {
        return frame;
    }
}
