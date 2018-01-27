package uk.ac.reading.oliver.bathurst;
import jcsp.lang.CSProcess;

class Control implements CSProcess {
    private int availableSpaces = 100, capacity = 100;

    Control(int availableSpaces, int capacity) {
        this.availableSpaces = availableSpaces;
        this.capacity = capacity;
    }
    //Run indefinitely
    public void run(){
        while(true){}
    }

    synchronized void arrive() throws InterruptedException {
        if(availableSpaces == 0) {
            while (availableSpaces == 0) {
                wait();
                availableSpaces--;
                notifyAll();
            }
        }
    }

    synchronized void depart() throws InterruptedException {
        if(availableSpaces == capacity) {//if empty car park
            while (availableSpaces == capacity) {//wait until one is in car park
                wait();
                availableSpaces++;
                notifyAll();
            }
        }
    }
}