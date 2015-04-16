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
        client.setOAuth2Token("2b1066522c320d190c6fd9912b43ee2debca825d");
//getting the repos of a given username        
//Methods.getRepoandWatchersFromUser(client, "salimrahal");

        //getting tht user Object by calling github api
        //UserBo.getUserbyUserName(client, "elia");
        UserBo.getUsefulUsers(client,2,1000);//returns only 255 users, decrease the follower numbers
        //UserBo.getAllUsers(client, 1);
        //UserBo.getFollowersbyUserName(client, "defunkt");
        System.out.println("client.getRemainingRequests=" + client.getRemainingRequests());
        // Methods.getHeadersbyUserName(client, "salimrahal");
        //Methods.getUsers(client);
    }

}
