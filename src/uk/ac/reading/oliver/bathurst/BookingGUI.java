/*
  Created by Oliver on 05/02/2018.
  Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */
package uk.ac.reading.oliver.bathurst;
import org.jcsp.lang.One2OneChannel;
import org.jcsp.lang.One2OneChannelInt;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Random;

/**
 * This class is responsible for displaying the booking GUI and writing
 * to the appropriate channels when a place is booked or released
 * The eticket channel is written to after booking, to produce an electronic ticket for the user
 */
class BookingGUI {
    private final String[] alphabet = new String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O", "P","Q","R","S","T","U","W","X","Y","Z"};
    private final String[] numbers = new String[]{"0","1","2","3","4","5","6","7","8","9"};
    private final Random rand = new Random();
    private final HashMap<String, String> bookingReferences = new HashMap<>();//stores booking reference and car regs
    private final One2OneChannelInt bookingChannel, responseChannel;
    private final One2OneChannel eticketChannel;
    private JPanel mainPanel;
    private JButton book;
    private JTextField firstName;
    private JTextField lastName;
    private JTextField email;
    private JTextField carReg;

    BookingGUI(One2OneChannel eticketChannel, One2OneChannelInt bookingChannel,
               One2OneChannelInt responseChannel){
        this.eticketChannel = eticketChannel;
        this.bookingChannel = bookingChannel;
        this.responseChannel = responseChannel;
        this.setupListeners();
        this.show();
    }

    private void setupListeners(){
        book.addActionListener(e -> book());
    }

    private void show(){
        JFrame frame = new JFrame("Online Booking Application");
        frame.setContentPane(mainPanel);
        frame.setPreferredSize(new Dimension(250,260));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void book(){
        bookingChannel.out().write(3);

        if(responseChannel.in().read() != 4) {
            System.out.println("Booking");
            eticketChannel.out().write(generateBookingID() + firstName.getText() + "," + lastName.getText() + ","
                    + email.getText() + "," + carReg.getText());
        }else{
            JOptionPane.showMessageDialog(null, "No spaces available");
            System.out.println("Booking failed, no spaces available");
        }
    }

    private String generateBookingID(){
        String toValidate = String.valueOf(alphabet[rand.nextInt(alphabet.length)] + alphabet[rand.nextInt(alphabet.length)] +
                numbers[rand.nextInt(numbers.length)] + numbers[rand.nextInt(numbers.length)] +
                alphabet[rand.nextInt(alphabet.length)] + alphabet[rand.nextInt(alphabet.length)] +
                numbers[rand.nextInt(numbers.length)] + numbers[rand.nextInt(numbers.length)]);

        if(bookingReferences.containsKey(toValidate)){
            generateBookingID();
        }
        bookingReferences.put(toValidate, carReg.getText());
        return toValidate;
    }
}
