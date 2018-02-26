/*
  Created by Oliver on 05/02/2018.
  Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */
package uk.ac.reading.oliver.bathurst;
import org.jcsp.lang.*;
import java.util.HashMap;
import java.util.Random;

/**
 * This class controls the spaces within the car park, reads from both depart and arrive channels
 * to reduce or increase available spaces.
 */
class Control implements CSProcess {
    private final HashMap<String, Space> bookingReferences = new HashMap<>();//stores booking reference and a space index
    private final String[] alphabet = new String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O", "P","Q","R","S","T","U","W","X","Y","Z"};
    private final String[] numbers = new String[]{"0","1","2","3","4","5","6","7","8","9"};//alphabet and numbers for booking references
    private final Random rand = new Random();//randomise for generating booking refs
    private final One2OneChannel arrive, depart, requestChannel, responseChannel;
    private final int initialCapacity = 100;//set initial capacity of car park
    private final Space[] spaces = new Space[initialCapacity];//setup spaces array
    private int spacesLeft = initialCapacity;//initialise spaces left counter

    /**
     * Control is initialised with arrive and depart channels but also request and response channels for bookings
     */
    Control(One2OneChannel arrive, One2OneChannel depart, One2OneChannel request, One2OneChannel responseChannel) {
        this.arrive = arrive;
        this.depart = depart;
        this.requestChannel = request;
        this.responseChannel = responseChannel;
    }

    /**
     * The main run method for control of all spaces within the car park, uses depart and arrive channels,
     * as well as the request channel for bookings.
     */
    @Override
    public void run() {
        for(int i = 0; i < initialCapacity; i++) {
            this.spaces[i] = new Space();//initialise all spaces (so no null exceptions)
        }
        //setup the Alternative Guards to alternate over the required channels
        Alternative alt = new Alternative(new Guard[]{depart.in(),arrive.in(), requestChannel.in()});
        //enter main loop
        while(true) {
            switch(alt.priSelect()){//use pri select such that depart channel is chosen by default (if none others ready) (prevents deadlock)
                case 0:
                    String departString = depart.in().read().toString();//read in request as a string
                    if(departString.equals("generic depart") && spacesLeft != initialCapacity) {//if it's a generic depart and there are cars in car park...
                        if (depart()) {//try to depart, if successful, re evaluate spaces left
                            this.recomputeSpaces();
                        }
                    }else if(!departString.equals("generic depart")){//if it's a booking reference, look up booking ref in hash map and get space index
                        bookingReferences.get(departString).setReserved(false);//set space reserved to false, owner of space is leaving
                        bookingReferences.remove(departString);//remove the reference, owner of space has left
                    }
                    break;
                case 1:
                    String arriveString = arrive.in().read().toString();//read in arrival as a string
                    if(arriveString.equals("generic arrive") && spacesLeft != 0){//if there are spaces and it's a generic car arrival
                        if(arrive()){//try to arrive
                            this.recomputeSpaces();//re evaluate
                        }
                    }else if(!arriveString.equals("generic arrive")){//reserved space owner arrives with booking reference
                        bookingReferences.get(arriveString).setReserved(true);//get space number using booking reference, set to reserved, owner of space has arrived
                    }
                    break;
                case 2:
                    BookingDetailsObject request = (BookingDetailsObject) requestChannel.in().read();//read in from booking channel and cast
                    if(spacesLeft != 0) {//if there are spaces left
                        int spaceIndex = reserve(request);//attempt to get a space (returns a space index)
                        if (spaceIndex != -1) {//if it's not -1 (a failure)
                            String reference = generateBookingID();//get a booking ref
                            bookingReferences.put(reference, spaces[spaceIndex]);//store booking in hash map
                            request.setBookingReference(reference);//set customer booking ref in the object
                            request.setParkingSpace(spaceIndex);//set customer space number in the object
                            responseChannel.out().write("booking successful");//write to response channel it was successful
                            this.recomputeSpaces();
                        } else {
                            responseChannel.out().write("");//write empty (failure) string
                        }
                    }else{
                        responseChannel.out().write("");//write empty (failure) string
                    }
                    break;
            }
        }
    }

    /**
     * Re evaluates the number of spaces left (ones that are not taken or reserved)
     */
    private void recomputeSpaces(){
        int counter = 0;
        for(Space space: spaces){
            if(!space.isTaken() && !space.isSpaceReserved()){
                counter++;
            }
        }
        spacesLeft = counter;
    }

    /**
     * Attempts to find a space that's not reserved (can be taken but not reserved)
     * if successful, set space to un taken, method returns true
     */
    private boolean depart(){
        boolean success = false;
        for (Space space : spaces) {
            if (!space.isSpaceReserved() && space.isTaken()) {
                space.setTaken(false);
                success = true;
                break;
            }
        }
        return success;
    }
    /**
     * Attempts to find an empty space (is not taken or reserved)
     * if successful, set space to taken, method return true
     */
    private boolean arrive(){
        boolean success = false;
        for (Space space : spaces) {
            if (!space.isSpaceReserved() && !space.isTaken()) {
                space.setTaken(true);
                success = true;
                break;
            }
        }
        return success;
    }
    /**
     * Attempts to reserve a space, iterates over all spaces checking if each space is reserved or not
     * if space is not reserved, just reserve it. If space is reserved, try reserving that space for the dates of the booking
     */
    private int reserve(BookingDetailsObject bookingObj){
        int index = -1;//set bad index
        for (int i = 0; i < spaces.length; i++) {//iterate
            if (spaces[i].tryReserve(bookingObj.getFullStartDate(), bookingObj.getFullEndDate())) {//try reserving for the dates
                index = i;//set index
                break;//break
            }
        }
        return index;//return space index (-1 indicates failure)
    }

    /**
     * Generates a unique booking reference
     */
    private String generateBookingID(){
        String toValidate = String.valueOf(alphabet[rand.nextInt(alphabet.length)] + alphabet[rand.nextInt(alphabet.length)] +
                numbers[rand.nextInt(numbers.length)] + numbers[rand.nextInt(numbers.length)] +
                alphabet[rand.nextInt(alphabet.length)] + alphabet[rand.nextInt(alphabet.length)] +
                numbers[rand.nextInt(numbers.length)] + numbers[rand.nextInt(numbers.length)]);

        if(bookingReferences.containsKey(toValidate)){
            generateBookingID();
        }
        return toValidate;
    }
}