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
class ETicketGUI implements CSProcess {
    private final DefaultListModel<String> listModel = new DefaultListModel<>();
    private final One2OneChannel unitChannel;
    private JPanel panel1;
    private JList<String> inbox;
    private JButton Delete;
    private JButton previousButton;
    private JButton Next;
    private JScrollPane scroll;

    ETicketGUI(One2OneChannel unitChannel){
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
    private void showGUI(){
        JFrame frame = new JFrame("E-Ticket Mail Box");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    @Override
    public void run() {

        while(true) {//wait for incoming booking references and add to the mail bag (list)
            listModel.addElement(parse((BookingDetailsObject) unitChannel.in().read()));
        }
    }
    private String parse(BookingDetailsObject obj){
        return "Name: " + obj.getFirstName() + ", " + obj.getLastName() +
                " Car Registration: " + obj.getCarReg() + ", Email: " + obj.getEmail() +
                " Booking reference: " + obj.getBookingReference() + ", Space number: " + obj.getParkingSpace();
    }
}
