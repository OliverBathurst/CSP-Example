/*
  Created by Oliver on 05/02/2018.
  Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */
package uk.ac.reading.oliver.bathurst;
import org.jcsp.awt.ActiveFrame;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannel;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

/**
 * This class is responsible for displaying the booking GUI and writing
 * to the appropriate channels when a place is booked or released
 * The eticket channel is written to after booking, to produce an electronic ticket for the user
 */
class BookingGUI implements CSProcess{
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private final SimpleDateFormat fullDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
    private final One2OneChannel bookingChannel, depart;
    private ActiveFrame frame;
    private javax.swing.JPanel mainPanel;
    private JTabbedPane tabbedPane;
    private javax.swing.JButton book;
    private javax.swing.JTextArea firstName, lastName, email, carReg;
    private JTextField startTime;
    private JTextField startDate;
    private JTextField endTime;
    private JTextField endDate;
    private JLabel Date;
    private JLabel end;
    private JTabbedPane releaseSpaceTab;
    private JTextField releaseEndTime;
    private JTextField releaseEndDate;
    private JTextField releaseStartTime;
    private JTextField releaseStartDate;
    private JLabel bookinglabel;
    private JTextField bookingreference;
    private JButton releaseSpace;

    BookingGUI(One2OneChannel bookingChannel, One2OneChannel depart){
        this.bookingChannel = bookingChannel;
        this.depart = depart;
        this.setupListeners();
        this.show();
    }

    private void setupListeners(){
        book.addActionListener(e -> book());
        releaseSpace.addActionListener(e -> release());
    }

    /**
     * book() performs input validation and writing to the booking channel with a new BookingDetailsObject with the booking details inputted
     */
    private void book(){
        if(firstName.getText().length() > 0 || lastName.getText().trim().length() > 0){
            if(email.getText().trim().length() > 0 && email.getText().contains("@")){
                if(carReg.getText().trim().length() > 0){
                    if(startDate.getText().trim().length() > 0 && startTime.getText().trim().length() > 0 && endTime.getText().trim().length() > 0){
                        try {
                            bookingChannel.out().write(new BookingDetailsObject(firstName.getText(), lastName.getText(), email.getText(), carReg.getText(),
                                    dateFormat.parse(startDate.getText()), startTime.getText(), dateFormat.parse(endDate.getText()), endTime.getText(),
                                    fullDateFormat.parse(startDate.getText() + " " + startTime.getText()),
                                    fullDateFormat.parse(endDate.getText() + " " + endTime.getText())));
                        }catch (Exception e){
                            JOptionPane.showMessageDialog(null, e.getMessage());
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "Times are required and date in DD/MM/YYYY");
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Enter car registration");
                }
            }else{
                JOptionPane.showMessageDialog(null, "A valid email is required");
            }
        }else{
            JOptionPane.showMessageDialog(null, "At least one name required");
        }
    }

    private void release(){
        try{
            bookingChannel.out().write(new BookingDetailsObject(fullDateFormat.parse(releaseStartDate.getText() + " " + releaseStartTime.getText()), fullDateFormat.parse(releaseEndDate.getText() + " " + releaseEndTime.getText()),
                    bookingreference.getText().trim(), true));
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    /**
     * Show the GUI
     */
    private void show(){
        JFrame frame = new JFrame("Online Booking Application");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * BookingGUI -> book -> BookingGUI
     */
    @Override
    public void run() {
        while(true) {}
    }
}
