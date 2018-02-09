/*
  Created by Oliver on 05/02/2018.
  Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */
package uk.ac.reading.oliver.bathurst;
import jcsp.lang.CSProcess;
import jcsp.lang.ints.One2OneChannelInt;

/**
 * This class controls the spaces within the car park, reads from both depart and arrive channels
 * to reduce or increase available spaces.
 */
class Control implements CSProcess {
    private final One2OneChannelInt arrive, depart, response;
    private final int initialCapacity = 100;
    private final Space[] spaces = new Space[initialCapacity];
    private int spacesLeft = initialCapacity;

    Control(One2OneChannelInt arrive, One2OneChannelInt depart, One2OneChannelInt response) {
        this.arrive = arrive;
        this.depart = depart;
        this.response = response;
    }

    @Override
    public void run() {
        for(int i = 0; i < initialCapacity; i++) {
            this.spaces[i] = new Space();
        }
        while(true) {
            if (spacesLeft != spaces.length && depart.read() == 33) {
                System.out.println("releasing: " + spacesLeft);
                spaces[spacesLeft].release();
                spacesLeft++;
                System.out.println("spaces left: " + spacesLeft);
            }
            if (spacesLeft != 0 && arrive.read() == 66) {
                System.out.println("reserving: " + spacesLeft);
                spaces[spacesLeft-1].reserve();
                spacesLeft--;
                System.out.println("spaces left: " + spacesLeft);
            }
        }
    }
}