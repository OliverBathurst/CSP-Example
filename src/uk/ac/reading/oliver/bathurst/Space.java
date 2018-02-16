package uk.ac.reading.oliver.bathurst;
import java.util.ArrayList;
import java.util.Date;

class Space {
    private boolean isReserved = false, isTaken = false;
    private ArrayList<Pair> reserveTimes = new ArrayList<>();

    private int index;
    private Date start, end;

    Space(int i){this.index = i;}

    int getSpaceNumber(){
        return index;
    }

    void setTaken(boolean taken){
        isTaken = taken;
    }

    boolean isTaken(){
        return isTaken;
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
                success = false;
                break;
            }
        }
        if(success){
            reserveTimes.add(new Pair(start, finish));
        }
        return success;
    }

    Date getStartDate(){
        return start;
    }

    Date getEndDate(){
        return end;
    }
}
