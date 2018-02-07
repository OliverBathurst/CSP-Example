/*
  Created by Oliver on 05/02/2018.
  Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */
package uk.ac.reading.oliver.bathurst;
import jcsp.lang.CSProcess;
import jcsp.lang.One2OneChannel;
import jcsp.lang.ints.One2OneChannelInt;
import javax.swing.*;
import java.awt.*;

/**
 * This class is responsible for displaying the booking GUI and writing
 * to the appropriate channels when a place is booked or released
 * The eticket channel is written to after booking, to produce an electronic ticket for the user
 */
class BookingGUI implements CSProcess {
    private One2OneChannelInt arrive, depart;//for booking and releasing a place
    private One2OneChannel eticketChannel;
    private JPanel mainPanel;
    private JButton book;

    BookingGUI(One2OneChannelInt arrive, One2OneChannelInt depart, One2OneChannel eticketChannel){
        this.arrive = arrive;
        this.depart = depart;
        this.eticketChannel = eticketChannel;
        this.setupListeners();
        this.show();
    }

    void setupListeners(){
        book.addActionListener(e -> book());
    }

    private void show(){
        JFrame frame = new JFrame("Online Booking Application");
        frame.setContentPane(mainPanel);
        frame.setPreferredSize(new Dimension(1500,500));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    private void book(){
        System.out.println("Booking...");
        eticketChannel.write("Sample registration ID");
    }
    @Override
    public void run() {
        while(true){}
    }
}
