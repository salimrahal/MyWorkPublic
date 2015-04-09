/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.IOException;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.service.RepositoryService;

/**
 *
 * @author salim
 * https://github.com/eclipse/egit-github/tree/master/org.eclipse.egit.github.core
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        RepositoryService service = new RepositoryService();
//        for (Repository repo : service.getRepositories("defunkt")) {
//            System.out.println(repo.getName() + " Watchers: " + repo.getWatchers());
//        }
        
        for (Repository repo : service.getRepositories("salimrahal")) {
            System.out.println(repo.getName() + " Watchers: " + repo.getWatchers());
        }
    }

}
