/*
  Created by Oliver on 05/02/2018.
  Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */
package uk.ac.reading.oliver.bathurst;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannel;
import javax.swing.*;
import java.awt.*;

/**
 * This class simulates the production of an electronic ticket after a booking has been made.
 * The booking GUI class writes to the eticket channel once a booking has been placed,
 * this class then reads said channel and produces an eticket with the provided information.
 */
public class ETicketGUI implements CSProcess {
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
        frame.setPreferredSize(new Dimension(400,200));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    @Override
    public void run() {
        while (true) {//wait for incoming booking references and add to the mail bag (list)
            listModel.addElement(parse(unitChannel.in().read().toString()));
        }
    }
    String parse(String str){
        String[] splitted = str.split(",");
        return "Booking reference: " + splitted[8] + ", Space number: " + splitted[9];

    }
}
