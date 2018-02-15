package uk.ac.reading.oliver.bathurst;
import jcsp.lang.CSProcess;
import jcsp.lang.One2OneChannel;
import jcsp.lang.Parallel;

class MailTool implements CSProcess{

    MailTool(One2OneChannel eticket){
        One2OneChannel unitChannel = new One2OneChannel();
        new Parallel(new CSProcess[]{new ETicketMailBag(eticket, unitChannel), new ETicketGUI(unitChannel)}).run();
    }

    @Override
    public void run() {
        while(true){}
    }
}
