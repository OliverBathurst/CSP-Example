/*
  Created by Oliver on 05/02/2018.
  Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */
package uk.ac.reading.oliver.bathurst;
import jcsp.lang.CSProcess;
import jcsp.lang.One2OneChannel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;

/**
 * This class simulates the production of an electronic ticket after a booking has been made.
 * The booking GUI class writes to the eticket channel once a booking has been placed,
 * this class then reads said channel and produces an eticket with the provided information.
 */
class ETicket implements CSProcess{
    private DefaultListModel<String> listModel = new DefaultListModel<>();
    private final One2OneChannel eticketChannel;
    private JPanel panel1;
    private JList<String> inbox;
    private JButton Delete;
    private JButton previousButton;
    private JButton Next;

    ETicket(One2OneChannel email){
        this.eticketChannel = email;
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
    void showGUI(){
        JFrame frame = new JFrame("E-Ticket Mail Box");
        frame.setContentPane(panel1);
        frame.setPreferredSize(new Dimension(400,200));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    @Override
    public void run() {
        while(true){
            listModel.addElement(eticketChannel.read().toString());
        }
    }
}
