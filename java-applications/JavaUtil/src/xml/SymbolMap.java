/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xmlkeyvalue;
import java.io.File;
import java.util.Properties;
/**
 *
 * @author salim
 *
 * A simple key:value map reader populated by an external XML stream
 */
public class SymbolMap {
       private Properties symbolmap;
       
    public SymbolMap(File file) {
        symbolmap = new Properties();
        try {
            //Populate the symbol map from the XML file
            symbolmap.loadFromXML(file.toURI().toURL().openStream() );
        }
        catch (Exception e) {
        }
    }
    //variable length arguments are packed into an array
    //which can be accessed and passed just like any array
    public String lookupSymbol(String symbol, String... variables) {
        //Retrieve the value of the associated key
        String message = symbolmap.getProperty(symbol);
        if(message == null)
            return "";
        //Interpolate parameters if necessary
        //and return the message
        return String.format(message, variables);
    }
    
    
}
