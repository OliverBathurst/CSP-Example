package uk.ac.reading;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannel;

/**
 * This class infinitely writes a single integer to the depart channel to simulate departing cars
 * The channel is read by Control and spaces managed
 */
class Departs implements CSProcess {
    private final One2OneChannel depart;

    /**
     * Depart simply writes to the depart channel which is read by the Control process to control spaces
     */
    Departs(One2OneChannel depart) {
        this.depart = depart;
    }

    /**
     * Depart -> depart -> Depart
     */
    @Override
    public void run() {
        while(true){
            depart.out().write("generic depart");
        }
    }
}