package uk.ac.reading.oliver.bathurst;
import jcsp.lang.CSProcess;
import jcsp.lang.Parallel;
import jcsp.lang.ints.One2OneChannelInt;
import javax.swing.*;
import java.awt.*;

public class Main extends JFrame{
    private JPanel panelMain;
    private JButton testButton;

    private Main() {
        testButton.addActionListener(e -> createParallel());
    }

    public static void main(String arg[]) {
        JFrame frame = new JFrame("Car Park");
        frame.setContentPane(new Main().panelMain);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500,500));
        frame.pack();
        frame.setVisible(true);
    }

    private static void createParallel(){
        One2OneChannelInt arrive = new One2OneChannelInt();
        One2OneChannelInt depart = new One2OneChannelInt();

        Parallel CarPark = new Parallel(
                new CSProcess[] {new Arrivals(arrive),
                        new Control(arrive,depart),
                        new Departs(depart)}
        );
        CarPark.run();
    }
}
