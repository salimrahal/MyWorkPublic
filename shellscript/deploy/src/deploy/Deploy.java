/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deploy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author salim the
 */
public class Deploy {

    public static void main(String[] args) {
        // TODO code application logic here
        String absoluteDir = "/home/salim/Development/projets-siISAE/resources/dist/";
        String appToDeploy = "resources.war";
        String scrpt = "./src/deploy/deployToDeploy.sh" + " " + absoluteDir + appToDeploy;
        runSrcipt(scrpt);
    }

    public static void runSrcipt(String scrptName) {
        try {
            Process proc = Runtime.getRuntime().exec(scrptName); //Whatever you want to execute
            System.out.println("exec done");

            BufferedReader read = new BufferedReader(new InputStreamReader(
                    proc.getInputStream()));

            System.out.println("get input stream");

            /*passing pssd not working                
            System.out.println("write the pssd to outstream");
               
                             OutputStream procStdIn = proc.getOutputStream();
                String pd = ">>>>>>";
                byte[] pdb = pd.getBytes();
                procStdIn.write(pdb);
             */
            try {
                System.out.println("waitfor");
                proc.waitFor();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            while (read.ready()) {
                System.out.println("inside read.ready()");
                System.out.println(read.readLine());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
