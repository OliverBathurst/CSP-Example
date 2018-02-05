/*
  Created by Oliver on 05/02/2018.
  Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */
package uk.ac.reading.oliver.bathurst;
import jcsp.lang.CSProcess;
import jcsp.lang.ints.One2OneChannelInt;

class Control implements CSProcess {
    private One2OneChannelInt arrive, depart;
    private int spacesLeft = 100, initialCapacity = 100;

    Control(One2OneChannelInt arrive, One2OneChannelInt depart) {
        this.arrive = arrive;
        this.depart = depart;
    }
    public void run(){
        while(true){
            if(arrive.read() == 2 && spacesLeft != 0){
                spacesLeft--;
                System.out.println("Spaces: " + spacesLeft);
            }
            if(depart.read() == 1 && spacesLeft != initialCapacity){
                spacesLeft++;
                System.out.println("Spaces: " + spacesLeft);
            }
        }
    }
}