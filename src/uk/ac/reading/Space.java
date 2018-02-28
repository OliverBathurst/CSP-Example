package uk.ac.reading;
import java.util.ArrayList;
import java.util.Date;

class Space {
    private boolean isReserved = false, isTaken = false;//initial flags
    private final ArrayList<Pair> reserveTimes = new ArrayList<>();//pairs of reserved times for that space (start and end)

    Space(){}

    void setTaken(boolean taken){
        isTaken = taken;
    }

    boolean isTaken(){
        return isTaken;
    }

    void setReserved(){
        this.isReserved = true;
    }

    void removeReservation(Date start, Date end){
        for(Pair p: reserveTimes){
            if(p.getFirst().getTime() == start.getTime()
                    && p.getSecond().getTime() == end.getTime()){
                reserveTimes.remove(p);
                break;
            }
        }
        if(reserveTimes.isEmpty()){
            this.isReserved = false;
        }
    }

    boolean isSpaceReserved(){
        return isReserved;
    }

    /**
     * Tries to reserve between two dates, if it conflicts with any this method returns false
     */
    boolean tryReserve(Date start, Date finish){
        boolean success = true;
        if(!isReserved){
            reserveTimes.add(new Pair(start, finish));
            isReserved = true;
            success = true;
        }else{
            for (Pair p : reserveTimes) {//iterate over all bookings for this space
                long startLong = p.getFirst().getTime();//get the start and end times
                long endLong = p.getSecond().getTime();

                if (start.getTime() >= startLong && finish.getTime() <= endLong) {//if it conflicts with another reservation
                    success = false;
                    break;//time unavailable, conflicts
                }
            }
            if (success) {//if successful (no conflictions)...
                reserveTimes.add(new Pair(start, finish));//add to list of reservations
            }
        }
        return success;//return the flag
    }
}
