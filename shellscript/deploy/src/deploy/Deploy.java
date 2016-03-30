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
 * @author salim
 * the 
 */
public class Deploy {

   public static void main(String[] args) {
        // TODO code application logic here
        String appToDeploy = "resources.war";
        String scrpt = "./src/deploy/deployToDeploy.sh"+" "+appToDeploy;
        runSrcipt(scrpt);
    }

    public static void runSrcipt(String scrptName) {
        try {
            Process proc = Runtime.getRuntime().exec(scrptName); //Whatever you want to execute
            BufferedReader read = new BufferedReader(new InputStreamReader(
                    proc.getInputStream()));
            try {
                proc.waitFor();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            while (read.ready()) {
                System.out.println(read.readLine());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
