package multiThreading.callable;

import multiThreading.*;
import java.lang.management.ManagementFactory;
import java.util.concurrent.Callable;

public class PresenceCallable implements Callable<Presence>
{

    public PresenceCallable() {
    }

    
    @Override
    public Presence call() throws Exception {
        Presence pres = new Presence("salim-onlines");
        return pres;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
