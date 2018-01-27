package uk.ac.reading.oliver.bathurst;
import jcsp.lang.CSProcess;

class Departs implements CSProcess {
    private Control control;

    Departs(Control c) {
        this.control = c;
    }

    public void run() {
        while(true){
            try {
                control.depart();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
