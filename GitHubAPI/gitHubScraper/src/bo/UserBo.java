/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo;

import java.io.IOException;
import java.util.List;
import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.UserService;

/**
 *
 * @author salim
 */
public class UserBo {

    
        public static void getUsefulUsers(GitHubClient client) throws IOException {
        UserService serviceUser = new UserService(client);
        List<User> usefuluseres = serviceUser.getUsefulUsers();
        System.out.println("getUsefulUsers= size:" + usefuluseres.size());
    }
        
    /* Json -> Java
     it returns the user as java by calling https://api.github.com/users/$user
     {hireable=true, createdAt=Mon Dec 10 22:45:23 EET 2012, collaborators=1, diskUsage=308435, followers=1, following=2, id=3010848, ownedPrivateRepos=1, privateGists=0, publicGists=0, publicRepos=4, totalPrivateRepos=1, avatarUrl=https://avatars.githubusercontent.com/u/3010848?v=3, bio=null, blog=http://www.safirasoft.com, company=ISAE-Cnam, email=salim.rahal@isae.edu.lb, gravatarId=, htmlUrl=https://github.com/salimrahal, location=Beirut, login=salimrahal, name=salim, 
     type=User, url=https://api.github.com/users/salimrahal, plan=org.eclipse.egit.github.core.UserPlan@11e96e0}
     */
    public static void getUserObjectbyUserName(GitHubClient client, String username) throws IOException {
        UserService serviceUser = new UserService(client);
        User user = serviceUser.getUser(username);
        System.out.println("getUserObjectbyUserName: user =" + user.toString());
    }

    /*
    it extract the total number of followers, this function loop thru all the pages:
    for example defunkt username has about 12k followers, 141 page each page has 100 followers
    Link=[<https://api.github.com/user/2/followers?per_page=100&page=121>; rel="next", 
    <https://api.github.com/user/2/followers?per_page=100&page=141>; rel="last",
    <https://api.github.com/user/2/followers?per_page=100&page=1>; rel="first", 
    <https://api.github.com/user/2/followers?per_page=100&page=119>; rel="prev"]
    
    */
    public static void getFollowersbyUserName(GitHubClient client, String username) throws IOException {
        UserService serviceUser = new UserService(client);
        List<User> followers = serviceUser.getFollowers(username);
        System.out.println("getFollowersOfUser "+username+" = size:" + followers.size());
    }
}
