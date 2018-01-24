package uk.ac.reading.oliver.bathurst;
import jcsp.lang.CSProcess;
import jcsp.lang.ints.ChannelInputInt;

class Departs implements CSProcess{
    private final ChannelInputInt depart;

    Departs(ChannelInputInt in) {
        depart = in;
    }

    public void run() {
        //while(true) {
            System.out.println("Spaces left" + depart.read());
        //}
    }
}