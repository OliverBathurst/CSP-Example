/*
  Created by Oliver on 05/02/2018.
  Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */
package uk.ac.reading.oliver.bathurst;
import jcsp.lang.*;
import jcsp.lang.ints.One2OneChannelInt;

/**
 * Starts all processes to run in parallel (starts parallel composition), assigns each necessary channels
 */
class Main {
    public static void main(String arg[]) {
        One2OneChannelInt arrive = new One2OneChannelInt();//arrive and depart channels
        One2OneChannelInt depart = new One2OneChannelInt();
        One2OneChannelInt response = new One2OneChannelInt();
        One2OneChannelInt bookingChannel = new One2OneChannelInt();
        One2OneChannel eticket = new One2OneChannel();//eticket channel, written to by GUI thread

        new Parallel(

                new CSProcess[]{new Arrivals(arrive), new Departs(depart), new Control(arrive, depart, bookingChannel, response)
                        //, new BookingGUI(eticket, bookingChannel, response), new MailTool(eticket)}
                }
                ).run();//create new parallel and run
    }
}
