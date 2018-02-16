/*
  Created by Oliver on 16/02/2018.
  Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */
package uk.ac.reading.oliver.bathurst;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannel;

public class Booking implements CSProcess {
    private One2OneChannel booking, eTicket, request, response;

    Booking(One2OneChannel booking, One2OneChannel eTicket, One2OneChannel request, One2OneChannel response){
        this.booking = booking;
        this.eTicket = eTicket;
        this.request = request;
        this.response = response;
    }

    @Override
    public void run() {
        while(true) {
            String bookingString = booking.in().read().toString();

            request.out().write(bookingString);//create booking request
            String spaceNumber = response.in().read().toString();//get space number and reference in response

            if (!spaceNumber.equals("")) {//if booking successful
                eTicket.out().write(bookingString + "," + spaceNumber);//print eticket if successful
            }else{
                System.out.println("Booking available");
            }
        }
    }
}
