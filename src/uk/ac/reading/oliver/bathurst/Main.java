/*
  Created by Oliver on 05/02/2018.
  Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */
package uk.ac.reading.oliver.bathurst;
import org.jcsp.lang.*;

/**
 * Starts all processes to run in parallel (starts parallel composition), assigns each necessary channels
 */
class Main {
    public static void main(String arg[]) {
        One2OneChannel arrive = Channel.one2one();
        One2OneChannel depart = Channel.one2one();

        One2OneChannel bookingChannel = Channel.one2one();
        One2OneChannel eticket = Channel.one2one();//eticket channel, written to by GUI thread
        One2OneChannel receipt = Channel.one2one();

        One2OneChannel request = Channel.one2one();
        One2OneChannel response = Channel.one2one();

        One2OneChannel unitChannel = Channel.one2one();

        new Parallel(
                new CSProcess[]{new Customer(receipt, arrive, depart), new Arrivals(arrive), new Departs(depart), new Control(arrive, depart, request, response),
                        new Booking(bookingChannel, eticket, request, response), new ETicketMailBag(eticket, unitChannel, receipt),
                        new MailInboxGUI(unitChannel), new BookingGUI(bookingChannel)
                }).run();
    }
}
