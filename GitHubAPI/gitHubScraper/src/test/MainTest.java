/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import bo.Methods;
import java.io.IOException;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.RepositoryService;

/**
 *
 * @author salim
 * https://github.com/eclipse/egit-github/tree/master/org.eclipse.egit.github.core
 * a57b64a26b489d81eeb288de2713ced84aeb89cc
 */
public class MainTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        GitHubClient client = new GitHubClient();
        client.setOAuth2Token("a57b64a26b489d81eeb288de2713ced84aeb89cc");
//getting the repos of a given username        
//Methods.getRepoandWatchersFromUser(client, "salimrahal");

        //getting tht user Object by calling github api
        Methods.getUserObjectbyUserName(client, "salimrahal");
        System.out.println("client.getRemainingRequests=" + client.getRemainingRequests());
        // Methods.getHeadersbyUserName(client, "salimrahal");
        //Methods.getUsers(client);
    }

}
