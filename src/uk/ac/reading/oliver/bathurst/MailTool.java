package uk.ac.reading.oliver.bathurst;
import jcsp.lang.CSProcess;
import jcsp.lang.One2OneChannel;
import jcsp.lang.Parallel;

class MailTool implements CSProcess{
    private final One2OneChannel unitChannel = new One2OneChannel();

    MailTool(One2OneChannel eticket){
        new Parallel(new CSProcess[]{new ETicketMailBag(eticket, unitChannel), new ETicketGUI(unitChannel)}).run();
    }

    @Override
    public void run() {
        while(true){}
    }
}
