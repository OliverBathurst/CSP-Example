package uk.ac.reading.oliver.bathurst;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannel;

class ETicketMailBag implements CSProcess {
    private final One2OneChannel eticketChannel, unit, receipt;

    ETicketMailBag(One2OneChannel eticketReceipt, One2OneChannel unit, One2OneChannel receipt){
        this.eticketChannel = eticketReceipt;
        this.unit = unit;
        this.receipt = receipt;
    }
    @Override
    public void run() {
        while(true){
            String eticket = eticketChannel.in().read().toString();
            unit.out().write(eticket);//for printing to GUI
            receipt.out().write(eticket);//receipt for customer
        }
    }
}
