/*
  Created by Oliver on 05/02/2018.
  Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */
package uk.ac.reading.oliver.bathurst;
import jcsp.lang.CSProcess;
import jcsp.lang.ints.One2OneChannelInt;
import javax.swing.*;
import java.awt.*;

class BookingGUI implements CSProcess {
    private One2OneChannelInt arrive = new One2OneChannelInt();
    private One2OneChannelInt depart = new One2OneChannelInt();
    private JPanel mainPanel;
    private JButton book;

    private BookingGUI(){}

    BookingGUI(One2OneChannelInt arrive, One2OneChannelInt depart){
        this.arrive = arrive;
        this.depart = depart;
        this.show();
    }

    private void show(){
        JFrame frame = new JFrame("Online Booking Application");
        frame.setContentPane(new BookingGUI().mainPanel);
        frame.setPreferredSize(new Dimension(1500,500));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void run() {}
}
