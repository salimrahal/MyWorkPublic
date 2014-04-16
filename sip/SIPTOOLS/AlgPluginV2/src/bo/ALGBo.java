/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo;

/**
 *
 * @author salim
 */
public class ALGBo {
    /* TODO: config file extraction BO
    In V2 prtocol/port combination should be dynamic and read from a configuration file
    */
    static public String comb1Proto = "UDP";
    static public Integer comb1SrcPort = 5062;
    static public Integer comb1DestPort = 5060;
    
    public String comb2Proto = "TCP";
    public Integer comb2SrcPort = 5062;
    public Integer comb2DestPort = 5060;
    
    public  String comb3Proto = "UDP";
    public Integer comb3SrcPort = 5060;
    public Integer comb3DestPort = 5060;
    
    public String comb4Proto = "TCP";
    public Integer comb4SrcPort = 5060;
    public Integer comb4DestPort = 5060;
    
}
