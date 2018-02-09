package uk.ac.reading.oliver.bathurst;

import jcsp.lang.CSProcess;
import jcsp.lang.One2OneChannel;

class ETicketMailBag implements CSProcess{
    private final One2OneChannel eticketChannel, unit;

    ETicketMailBag(One2OneChannel eticketReceipt, One2OneChannel unit){
        this.eticketChannel = eticketReceipt;
        this.unit = unit;
    }
    @Override
    public void run() {
        while(true){
            unit.write(eticketChannel.read());
        }
    }
}
