/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * NB:------------------> go to XMLManipulation projects <--------------------------------
 */
package xmlkeyvalue;

import java.io.File;
public class Main {
public static void main(String[] args) throws Exception {
    SymbolMap symbolmap = new SymbolMap(new File("./conf/symbols.xml"));
    print("> " + symbolmap.lookupSymbol("MOTD"));
    print("> " + symbolmap.lookupSymbol("NOT_FOUND"));
     print("> " + symbolmap.lookupSymbol("FAVOURITE_FRUIT"));
    
    print("> " + symbolmap.lookupSymbol("THE_COW_SAYS", "mooooo"));
}
public static void print(String s) {
    System.out.println(s);
}
}
