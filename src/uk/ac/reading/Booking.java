package uk.ac.reading;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannel;

class Booking implements CSProcess {
    private final One2OneChannel booking, eTicket, request, response;

    /**
     * Booking takes a booking channel written to by the GUI,
     * the eTicket channel to write to if booking successful,
     * and request and response channels for communicating with Control for a space allocation
     */
    Booking(One2OneChannel booking, One2OneChannel eTicket, One2OneChannel request, One2OneChannel response){
        this.booking = booking;
        this.eTicket = eTicket;
        this.request = request;
        this.response = response;
    }

    /**
     * Booking -> book -> Booking
     * book = request -> response -> eticket | request -> response
     */
    @Override
    public void run() {
        while(true) {
            BookingDetailsObject requestObj = (BookingDetailsObject) booking.in().read();//read from booking channel, cast to obj

            if(!requestObj.isCustomerCancelling()) {
                request.out().write(requestObj);//create booking request by writing to request channel which is shared with Control
                if (!response.in().read().toString().equals("")) {//if booking successful...
                    eTicket.out().write(requestObj);//write booking object to eticket channel for display in GUI and customer receipt
                } else {
                    System.out.println("Booking unavailable");//print none available
                }
            }else if(requestObj.isCustomerCancelling()){//if the request is a cancellation
                request.out().write(requestObj);//write the object in the request
                System.out.println(response.in().read().toString());//print response
            }
        }
    }
}
