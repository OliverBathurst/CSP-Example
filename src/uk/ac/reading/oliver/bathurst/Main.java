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
        One2OneChannelInt arrive = Channel.one2oneInt();
        One2OneChannelInt depart = Channel.one2oneInt();
        One2OneChannelInt response = Channel.one2oneInt();
        One2OneChannelInt bookingChannel = Channel.one2oneInt();//arrive and depart channels
        One2OneChannel eticket = Channel.one2one();//eticket channel, written to by GUI thread

        //new BookingGUI(eticket, bookingChannel, response);

        new Parallel(
                new CSProcess[]{new Arrivals(arrive), new Departs(depart), new Control(arrive, depart, bookingChannel, response),
                         //new MailTool(eticket)
                }).run();
    }
}
