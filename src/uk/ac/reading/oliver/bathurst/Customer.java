/*
  Created by Oliver on 16/02/2018.
  Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */

package uk.ac.reading.oliver.bathurst;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannel;

public class Customer implements CSProcess{
    private One2OneChannel receipt;

    Customer(One2OneChannel receipt) {
        this.receipt = receipt;
    }

    @Override
    public void run() {
        String bookingReceipt = receipt.in().read().toString();//wait for receipt
        String[] receiptData = bookingReceipt.split(",");
        System.out.println("Receipt collected: " + bookingReceipt);
        System.out.println("Waiting for arrival at: " + receiptData[5] + " on date: " + receiptData[4]);

        //wait for arrive
        //finish
    }
}
