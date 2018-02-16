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
    private final String[] alphabet = new String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O", "P","Q","R","S","T","U","W","X","Y","Z"};
    private final String[] numbers = new String[]{"0","1","2","3","4","5","6","7","8","9"};
    private final Random rand = new Random();
    private final HashMap<String, String> bookingReferences = new HashMap<>();//stores booking reference and car regs

    private final One2OneChannelInt arrive, depart;
    private final One2OneChannel requestChannel, responseChannel;
    private final int initialCapacity = 100;
    private final Space[] spaces = new Space[initialCapacity];
    private int spacesLeft = initialCapacity;

    Control(One2OneChannelInt arrive, One2OneChannelInt depart, One2OneChannel request, One2OneChannel responseChannel) {
        this.arrive = arrive;
        this.depart = depart;
        this.requestChannel = request;
        this.responseChannel = responseChannel;
    }

    @Override
    public void run() {
        for(int i = 0; i < initialCapacity; i++) {
            this.spaces[i] = new Space();
        }

        Alternative alt = new Alternative(new Guard[]{depart.in(),arrive.in(), requestChannel.in()});

        while(true) {
            switch(alt.priSelect()){
                case 0:
                    if(depart.in().read() == 1 && spacesLeft != initialCapacity) {
                        if (depart()) {
                            this.recomputeSpaces();
                        }
                    }
                    break;
                case 1:
                    if(arrive.in().read() == 2 && spacesLeft != 0){
                        if(arrive()){
                            this.recomputeSpaces();
                        }
                    }
                    break;
                case 2:
                    if(!requestChannel.in().read().toString().equals("")){
                        if(spacesLeft != 0) {
                            if (reserve()) {
                                responseChannel.out().write("Successful reservation");//randomly chosen int (not 4)
                                this.recomputeSpaces();
                            }else{
                                responseChannel.out().write("");//write empty (failure) string
                            }
                        }else{
                            responseChannel.out().write("");//write empty (failure) string
                        }
                    }
                    break;
            }
        }
    }

    private void recomputeSpaces(){
        int counter = 0;
        for(Space space: spaces){
            if(!space.isTaken() && !space.isSpaceReserved()){
                counter++;
            }
        }
        spacesLeft = counter;
        //System.out.println("Spaces left: " + spacesLeft);
    }

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
    private boolean reserve(){
        boolean success = false;
        for (Space space : spaces) {
            if (!space.isSpaceReserved() && !space.isTaken()) {
                space.reserve();
                success = true;
                break;
            }
        }
        return success;
    }
    /*private String generateBookingID(){
        String toValidate = String.valueOf(alphabet[rand.nextInt(alphabet.length)] + alphabet[rand.nextInt(alphabet.length)] +
                numbers[rand.nextInt(numbers.length)] + numbers[rand.nextInt(numbers.length)] +
                alphabet[rand.nextInt(alphabet.length)] + alphabet[rand.nextInt(alphabet.length)] +
                numbers[rand.nextInt(numbers.length)] + numbers[rand.nextInt(numbers.length)]);

        if(bookingReferences.containsKey(toValidate)){
            generateBookingID();
        }
        bookingReferences.put(toValidate, carReg.getText());
        return toValidate;
    }*/
}