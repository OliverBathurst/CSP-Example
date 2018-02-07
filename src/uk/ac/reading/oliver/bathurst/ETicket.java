/*
  Created by Oliver on 05/02/2018.
  Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */
package uk.ac.reading.oliver.bathurst;
import jcsp.lang.CSProcess;
import jcsp.lang.One2OneChannel;

/**
 * This class simulates the production of an electronic ticket after a booking has been made.
 * The booking GUI class writes to the eticket channel once a booking has been placed,
 * this class then reads said channel and produces an eticket with the provided information.
 */
class ETicket implements CSProcess{
    private final One2OneChannel eticketChannel;

    ETicket(One2OneChannel email){
        this.eticketChannel = email;
    }

    @Override
    public void run() {

    }
}
