/*
  Created by Oliver on 05/02/2018.
  Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */
package uk.ac.reading.oliver.bathurst;
import jcsp.lang.CSProcess;
import jcsp.lang.ints.One2OneChannelInt;

class Departs implements CSProcess {
    private One2OneChannelInt depart;

    Departs(One2OneChannelInt depart) {
        this.depart = depart;
    }

    public void run() {
        while(true){
            depart.write(1);
        }
    }
}