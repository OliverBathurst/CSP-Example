/*
  Created by Oliver on 16/02/2018.
  Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */

package uk.ac.reading.oliver.bathurst;
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