package uk.ac.reading;
import java.util.Date;

/**
 * Simple pair class used to store store start and end dates/times in a tuple object
 */
class Pair {
    private final Date first, second;

    Pair(Date first, Date second) {
        this.first = first;
        this.second = second;
    }

    Date getFirst(){
        return first;
    }

    Date getSecond(){
        return second;
    }
}