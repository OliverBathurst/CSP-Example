package uk.ac.reading.oliver.bathurst;

class Space {
    private boolean isReserved = false;

    Space(){}

    boolean isSpaceReserved(){
        return isReserved;
    }

    void reserve(){
        isReserved = true;
    }
    void release(){
        isReserved = false;
    }
}
