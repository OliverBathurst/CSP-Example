/*
  Created by Oliver on 05/02/2018.
  Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */
package uk.ac.reading.oliver.bathurst;
import jcsp.lang.CSProcess;
import jcsp.lang.ints.One2OneChannelInt;

/**
 * This class infinitely writes a single integer to the arrive channel to simulate arriving cars
 * The channel is read by Control and spaces managed
 */
class Arrivals implements CSProcess {
    private final One2OneChannelInt arrive;

    Arrivals(One2OneChannelInt arrive){
        this.arrive = arrive;
    }

    public void run() {
        while(true){
            arrive.write(1);
        }
    }
}