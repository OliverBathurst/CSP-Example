package uk.ac.reading.oliver.bathurst;

class Space {
    private boolean isReserved = false, isTaken = false;

    Space(){}

    void setTaken(boolean taken){
        isTaken = taken;
    }

    boolean isTaken(){
        return isTaken;
    }

    boolean isSpaceReserved(){
        return isReserved;
    }

    void reserve(){
        isReserved = true;
    }
}
