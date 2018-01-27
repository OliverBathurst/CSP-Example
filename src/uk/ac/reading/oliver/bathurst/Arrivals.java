package uk.ac.reading.oliver.bathurst;
import jcsp.lang.CSProcess;

class Arrivals implements CSProcess {
    private Control control;

    Arrivals(Control c){
        this.control = c;
    }

    public void run() {
        while(true){
            try {
                control.arrive();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
