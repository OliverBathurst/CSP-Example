package uk.ac.reading.oliver.bathurst;
import jcsp.lang.CSProcess;
import jcsp.lang.Parallel;

class CarPark {
    public static void main(String arg[]) {
        Control c = new Control(100,200);
        Arrivals a = new Arrivals(c);
        Departs d = new Departs(c);

        new Parallel( new CSProcess[] {d, a, c}).run();
    }
}
