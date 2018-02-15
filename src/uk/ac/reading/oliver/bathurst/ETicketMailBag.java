package uk.ac.reading.oliver.bathurst;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannel;

class ETicketMailBag implements CSProcess {
    private final One2OneChannel eticketChannel, unit;

    ETicketMailBag(One2OneChannel eticketReceipt, One2OneChannel unit){
        this.eticketChannel = eticketReceipt;
        this.unit = unit;
    }
    @Override
    public void run() {
        while(true){
            unit.out().write(eticketChannel.in().read());
        }
    }
}
