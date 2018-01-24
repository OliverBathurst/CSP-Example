package uk.ac.reading.oliver.bathurst;
import jcsp.lang.CSProcess;
import jcsp.lang.ints.One2OneChannelInt;

class Control implements CSProcess {
    private final One2OneChannelInt arrive, depart;

    Control(One2OneChannelInt arrive, One2OneChannelInt depart){
        this.arrive = arrive;
        this.depart = depart;
    }

    @Override
    public void run() {

    }
}
