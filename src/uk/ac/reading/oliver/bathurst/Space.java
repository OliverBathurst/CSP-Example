package uk.ac.reading.oliver.bathurst;
import java.util.ArrayList;
import java.util.Date;

class Space {
    private boolean isReserved = false, isTaken = false;
    private final ArrayList<Pair> reserveTimes = new ArrayList<>();

    Space(){}

    void setTaken(boolean taken){
        isTaken = taken;
    }

    boolean isTaken(){
        return isTaken;
    }

    void setReserved(boolean value){
        this.isReserved = value;
    }

    boolean isSpaceReserved(){
        return isReserved;
    }

    void reserve(Date start, Date finish){
        reserveTimes.add(new Pair(start, finish));
        this.isReserved = true;
    }

    boolean tryReserve(Date start, Date finish){
        boolean success = true;
        for(Pair p : reserveTimes){
            long startLong = p.getFirst().getTime();
            long endLong = p.getSecond().getTime();

            if (start.getTime() >= startLong && finish.getTime() <= endLong) {
                success = false;//time unavailable, conflicts
                break;
            }
        }
        if(success){
            reserveTimes.add(new Pair(start, finish));
        }
        return success;
    }
}
