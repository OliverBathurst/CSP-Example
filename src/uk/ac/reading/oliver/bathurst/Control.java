/*
  Created by Oliver on 05/02/2018.
  Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */
package uk.ac.reading.oliver.bathurst;
import org.jcsp.lang.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

/**
 * This class controls the spaces within the car park, reads from both depart and arrive channels
 * to reduce or increase available spaces.
 */
class Control implements CSProcess {
    private final HashMap<String, Space> bookingReferences = new HashMap<>();//stores booking reference and car regs
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
    private final String[] alphabet = new String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O", "P","Q","R","S","T","U","W","X","Y","Z"};
    private final String[] numbers = new String[]{"0","1","2","3","4","5","6","7","8","9"};
    private final Random rand = new Random();

    private final One2OneChannel arrive, depart, requestChannel, responseChannel;
    private final int initialCapacity = 100;
    private final Space[] spaces = new Space[initialCapacity];
    private int spacesLeft = initialCapacity;

    Control(One2OneChannel arrive, One2OneChannel depart, One2OneChannel request, One2OneChannel responseChannel) {
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
                    String departString = depart.in().read().toString();
                    if(departString.equals("generic depart") && spacesLeft != initialCapacity) {
                        if (depart()) {
                            this.recomputeSpaces();
                        }
                    }else if(!departString.equals("generic depart")){
                        bookingReferences.get(departString).setReserved(false);//set false to false, owner is leaving
                        bookingReferences.remove(departString);//remove the reference, owner of space has left
                    }
                    break;
                case 1:
                    String arriveString = arrive.in().read().toString();
                    if(arriveString.equals("generic arrive") && spacesLeft != 0){
                        if(arrive()){
                            this.recomputeSpaces();
                        }
                    }else if(!arriveString.equals("generic arrive")){//reserved space owner arrives
                        bookingReferences.get(arriveString).setReserved(true);//get space number using booking reference, set to reserved, owner of space has arrived
                    }
                    break;
                case 2:
                    String request = requestChannel.in().read().toString();
                    if(spacesLeft != 0) {
                        int spaceIndex = reserve(request);

                        if (spaceIndex != -1) {
                            String reference = generateBookingID();
                            bookingReferences.put(reference, spaces[spaceIndex]);//store booking

                            responseChannel.out().write(reference + "," + spaceIndex);//randomly chosen int (not 4)
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
    private int reserve(String info){
        String[] information = info.split(",");
        int index = -1;

        Date start = null;
        Date end = null;
        try {
            start = sdf.parse(information[4] + " " + information[5]);
            end = sdf.parse(information[6] + " " + information[7]);
        } catch (ParseException ignored) {}

        if(start != null && end != null) {
            for (int i = 0; i < spaces.length; i++) {
                if (!spaces[i].isSpaceReserved() && !spaces[i].isTaken()) {
                    spaces[i].reserve(start, end);
                    index = i;
                    break;
                } else if (spaces[i].isSpaceReserved()) {
                    if (spaces[i].tryReserve(start, end)) {
                        index = i;
                        break;
                    }
                }
            }
        }
        return index;
    }
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