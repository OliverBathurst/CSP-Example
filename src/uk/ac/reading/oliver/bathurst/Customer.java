/*
  Created by Oliver on 16/02/2018.
  Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */

package uk.ac.reading.oliver.bathurst;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

class Customer implements CSProcess{
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
    private final One2OneChannel receipt, arrive, depart;

    Customer(One2OneChannel receipt, One2OneChannel arrive, One2OneChannel depart) {
        this.receipt = receipt;
        this.arrive = arrive;
        this.depart = depart;
    }

    @Override
    public void run() {
        String bookingReceipt = receipt.in().read().toString();//wait for receipt
        String[] receiptData = bookingReceipt.split(",");
        System.out.println("Receipt collected: " + bookingReceipt);
        System.out.println("Waiting for arrival at: " + receiptData[5] + " on date: " + receiptData[4]);

        String ref = receiptData[8];
        Date start = null;
        Date end = null;

        try {
            start = sdf.parse(receiptData[4] + " " + receiptData[5]);
            end = sdf.parse(receiptData[6] + " " + receiptData[7]);
        }catch(Exception ignored){}

        if(start != null && end != null) {
            //wait to arrive
            while (!(start.getTime() <= new Date().getTime())){}

            //arrive with booking ref
            arrive.out().write(ref);

            System.out.println("Waiting for departure at: " + receiptData[7] + " on date: " + receiptData[6]);

            //wait to depart
            while (!(end.getTime() >= new Date().getTime())){}

            //depart with booking ref
            depart.out().write(ref);

            //finish
        }
    }
}
