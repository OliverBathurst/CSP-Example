package uk.ac.reading.oliver.bathurst;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.Channel;
import org.jcsp.lang.One2OneChannel;
import org.jcsp.lang.Parallel;

class MailTool implements CSProcess {

    MailTool(One2OneChannel eticket){
        One2OneChannel unitChannel = Channel.one2one();
        new Parallel(new CSProcess[]{new ETicketMailBag(eticket, unitChannel), new ETicketGUI(unitChannel)}).run();
    }

    @Override
    public void run() {
        while(true){}
    }
}
