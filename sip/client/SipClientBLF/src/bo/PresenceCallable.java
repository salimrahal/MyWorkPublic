package bo;

import vo.PresenceVO;
import bo.xmlparser.SAXParserPresence;
import java.util.concurrent.Callable;
import javax.sip.message.Request;

public class PresenceCallable implements Callable<PresenceVO> {

    SAXParserPresence saxparser;
    Request request;

    public PresenceCallable(Request request, SAXParserPresence saxparser) {
        this.request = request;
        this.saxparser = saxparser;
    }

    @Override
    public PresenceVO call() throws Exception {
        PresenceVO pres = this.saxparser.ProcessPresence(this.request, this.saxparser);
        return pres;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
