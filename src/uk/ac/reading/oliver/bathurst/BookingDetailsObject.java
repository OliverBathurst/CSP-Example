/**
 * Created by Oliver on 25/02/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */

package uk.ac.reading.oliver.bathurst;
import java.util.Date;

class BookingDetailsObject {
    private final String firstName, lastName, email, carReg, startTime, endTime;
    private final Date startDate, endDate, fullStart, fullEnd;
    private String bookingReference;
    private int spaceNumber;

    BookingDetailsObject(String firstName, String lastName, String email, String carReg, Date start, String startTime, Date end, String endTime, Date fullStart, Date fullEnd){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.carReg = carReg;
        this.startDate = start;
        this.startTime = startTime;
        this.endDate = end;
        this.endTime = endTime;
        this.fullStart = fullStart;
        this.fullEnd = fullEnd;
    }

    String getEmail(){
        return email;
    }

    String getFirstName(){
        return firstName;
    }

    String getLastName(){
        return lastName;
    }

    String getCarReg(){
        return carReg;
    }

    Date getStartDate(){
        return startDate;
    }

    Date getEndDate(){
        return endDate;
    }

    Date getFullStartDate(){
        return fullStart;
    }

    Date getFullEndDate(){
        return fullEnd;
    }

    void setBookingReference(String ref){
        this.bookingReference = ref;
    }

    String getBookingReference(){
        return bookingReference;
    }

    void setParkingSpace(int space){
        this.spaceNumber = space;
    }

    int getParkingSpace(){
        return spaceNumber;
    }

    String getStartTime(){
        return startTime;
    }

    String getEndTime(){
        return endTime;
    }
}
