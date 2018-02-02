package uk.ac.reading.oliver.bathurst;
import jcsp.lang.CSProcess;
import jcsp.lang.Parallel;
import jcsp.lang.ints.One2OneChannelInt;

class CarPark {
    public static void main(String arg[]) {
        One2OneChannelInt arrive = new One2OneChannelInt();
        One2OneChannelInt depart = new One2OneChannelInt();

        new Parallel( new CSProcess[] {
                new Arrivals(arrive), new Departs(depart), new Control(arrive,depart)}).run();
    }
}
