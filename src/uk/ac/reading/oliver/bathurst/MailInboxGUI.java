/*
  Created by Oliver on 05/02/2018.
  Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */
package uk.ac.reading.oliver.bathurst;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannel;
import javax.swing.*;

/**
 * This class simulates the production of an electronic ticket after a booking has been made.
 * The booking GUI class writes to the eticket channel once a booking has been placed,
 * this class then reads said channel and produces an eticket with the provided information.
 */
class MailInboxGUI implements CSProcess {
    private final DefaultListModel<String> listModel = new DefaultListModel<>();
    private final One2OneChannel unitChannel;
    private JPanel panel1;
    private JList<String> inbox;
    private JButton Delete;
    private JButton previousButton;
    private JButton Next;
    private JScrollPane scroll;

    /**
     * Sets up the channel for eticket interception and the list used to display
     * Also sets up listeners for filtering through bookings such as previous, next and delete
     */
    MailInboxGUI(One2OneChannel unitChannel){
        this.unitChannel = unitChannel;
        this.showGUI();
        this.inbox.setModel(listModel);
        Delete.addActionListener(e -> {
            for(int i = 0; i < inbox.getSelectedIndices().length; i++){
                listModel.remove(i);
            }
        });
        Next.addActionListener(e -> {
            int prospect = inbox.getSelectedIndex() + 1;
            if(prospect <= inbox.getModel().getSize() - 1) {
                inbox.setSelectedIndex(prospect);
            }
        });
        previousButton.addActionListener(e -> {
            int prospect = inbox.getSelectedIndex() - 1;
            if(prospect >= 0 && inbox.getModel().getSize() > 0){
                inbox.setSelectedIndex(prospect);
            }
        });
    }

    /**
     * Shows the GUI
     */
    private void showGUI(){
        JFrame frame = new JFrame("E-Ticket Mail Box");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Waits for a booking object on the unit channel, adds to the list (simulated mail inbox)
     * MailInboxGUI -> display receipt -> MailInboxGUI
     */
    @Override
    public void run() {
        while(true) {//wait for incoming booking reference and add to the mail bag (list)
            listModel.addElement(parse((BookingDetailsObject) unitChannel.in().read()));
        }
    }

    /**
     * Extracts a sample of information from object for display on GUI
     */
    private String parse(BookingDetailsObject obj){
        return "Name: " + obj.getFirstName() + ", " + obj.getLastName() +
                " Car Registration: " + obj.getCarReg() + ", Email: " + obj.getEmail() +
                " Booking reference: " + obj.getBookingReference() + ", Space number: " + obj.getParkingSpace();
    }
}
