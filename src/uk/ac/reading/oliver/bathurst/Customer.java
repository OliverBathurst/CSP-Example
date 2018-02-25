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
        BookingDetailsObject bookingReceipt = (BookingDetailsObject) receipt.in().read();//wait for receipt
        System.out.println("Receipt collected");
        System.out.println("Waiting for arrival at: " + bookingReceipt.getStartTime() + " on date: " + bookingReceipt.getStartDate());

        long startDateAndTime = bookingReceipt.getFullStartDate().getTime();
        long endDateAndTime = bookingReceipt.getFullEndDate().getTime();

        //wait to arrive
        while (!(startDateAndTime < new Date().getTime())){}

        //arrive with booking ref
        arrive.out().write(bookingReceipt.getBookingReference());

        System.out.println("Waiting for departure at: " + bookingReceipt.getEndTime() + " on date: " + bookingReceipt.getEndDate());

        //wait to depart
        while (!(endDateAndTime > new Date().getTime())){}

        //depart with booking ref
        depart.out().write(bookingReceipt.getBookingReference());

        //finish
    }
}
