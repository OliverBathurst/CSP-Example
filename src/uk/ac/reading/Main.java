package uk.ac.reading;
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
                new CSProcess[]{new Arrivals(arrive), new Departs(depart), new Control(arrive, depart, request, response),
                        new Customer(receipt, arrive, depart),
                        new Booking(bookingChannel, eticket, request, response), new ETicketMailBag(eticket, unitChannel, receipt),
                        new MailInboxGUI(unitChannel), new BookingGUI(bookingChannel, depart)
                }).run();
    }
}
