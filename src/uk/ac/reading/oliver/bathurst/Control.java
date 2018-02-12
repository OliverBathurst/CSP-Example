/*
  Created by Oliver on 05/02/2018.
  Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */
package uk.ac.reading.oliver.bathurst;
import jcsp.lang.CSProcess;
import jcsp.lang.ints.AlternativeInt;
import jcsp.lang.ints.AltingChannelInputInt;
import jcsp.lang.ints.One2OneChannelInt;

/**
 * This class controls the spaces within the car park, reads from both depart and arrive channels
 * to reduce or increase available spaces.
 */
class Control implements CSProcess {
    private final One2OneChannelInt arrive, depart, bookingChannel, responseChannel;
    private final int initialCapacity = 100;
    private final Space[] spaces = new Space[initialCapacity];
    private int spacesLeft = initialCapacity;

    Control(One2OneChannelInt arrive, One2OneChannelInt depart, One2OneChannelInt bookingChannel,
            One2OneChannelInt responseChannel) {
        this.arrive = arrive;
        this.depart = depart;
        this.bookingChannel = bookingChannel;
        this.responseChannel = responseChannel;
    }

    @Override
    public void run() {
        for(int i = 0; i < initialCapacity; i++) {
            this.spaces[i] = new Space();
        }
        AlternativeInt alt = new AlternativeInt();
        AltingChannelInputInt[] altChannels = {arrive, depart, bookingChannel};

        while(true) {
            switch(alt.select(altChannels)){
                case 0:
                    if(altChannels[0].read() == 2 && spacesLeft != 0){
                        if(arrive()){
                            System.out.println("Successful arrival");
                            this.recomputeSpaces();
                        }
                    }
                    break;
                case 1:
                    if(altChannels[1].read() == 1 && spacesLeft != initialCapacity) {
                        if (depart()) {
                            System.out.println("Successful departure");
                            this.recomputeSpaces();
                        }
                    }
                    break;
                case 2:
                    if(altChannels[2].read() == 3){
                        if(spacesLeft != 0) {
                            if (reserve()) {
                                responseChannel.write(99);//randomly chosen int (not 4)
                                System.out.println("Successful reservation");
                                this.recomputeSpaces();
                            }else{
                                responseChannel.write(4);
                            }
                        }else{
                            responseChannel.write(4);
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
        System.out.println("Spaces left: " + spacesLeft);
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
}