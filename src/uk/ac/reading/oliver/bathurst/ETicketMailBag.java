package uk.ac.reading.oliver.bathurst;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannel;

class ETicketMailBag implements CSProcess {
    private final One2OneChannel eticketChannel, unit, receipt;

    /**
     * ETicketMailBag coordinates the transmission of an eticket receipt to both the mail GUI process as well as the customer
     */
    ETicketMailBag(One2OneChannel eticketReceipt, One2OneChannel unit, One2OneChannel receipt){
        this.eticketChannel = eticketReceipt;
        this.unit = unit;
        this.receipt = receipt;
    }

    /**
     * ETicketMailBag = receipt -> unit channel -> receipt channel -> ETicketMailBag
     */
    @Override
    public void run() {
        while(true){
            BookingDetailsObject eticket = (BookingDetailsObject) eticketChannel.in().read();//read in booking object (eticket)
            unit.out().write(eticket);//write to GUI-specific channel for printing to GUI
            receipt.out().write(eticket);//write to receipt channel for Customer process
        }
    }
}
