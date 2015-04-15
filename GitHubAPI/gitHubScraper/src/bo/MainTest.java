/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo;

import bo.RepoBo;
import bo.UserBo;
import java.io.IOException;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.RepositoryService;

/**
 *
 * @author salim
 *
 *
 */
public class MainTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        GitHubClient client = new GitHubClient();
        client.setOAuth2Token("a5010811be8c4e12aa46a0ef12a7e5c095053fad");
//getting the repos of a given username        
//Methods.getRepoandWatchersFromUser(client, "salimrahal");

        //getting tht user Object by calling github api
        //UserBo.getUserbyUserName(client, "elia");
        System.out.println("client.getRemainingRequests=" + client.getRemainingRequests());
        UserBo.getUsefulUsers(client);//
        //UserBo.getAllUsers(client, 1);
        //UserBo.getFollowersbyUserName(client, "defunkt");
        System.out.println("client.getRemainingRequests=" + client.getRemainingRequests());
        // Methods.getHeadersbyUserName(client, "salimrahal");
        //Methods.getUsers(client);
    }

}
