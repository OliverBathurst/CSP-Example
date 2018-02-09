/*
  Created by Oliver on 05/02/2018.
  Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */
package uk.ac.reading.oliver.bathurst;
import jcsp.lang.CSProcess;
import jcsp.lang.ints.AltingChannelInt;
/**
 * This class infinitely writes a single integer to the depart channel to simulate departing cars
 * The channel is read by Control and spaces managed
 */
class Departs implements CSProcess {
    private final AltingChannelInt depart;

    Departs(AltingChannelInt depart) {
        this.depart = depart;
    }

    @Override
    public void run() {
        while(true){
            depart.write(33);
        }
    }
}