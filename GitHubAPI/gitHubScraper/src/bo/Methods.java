/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Date;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.RepositoryId;
import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.CommitService;
import org.eclipse.egit.github.core.service.GitHubService;
import org.eclipse.egit.github.core.service.UserService;

/**
 *
 * @author salim
 */
public class Methods {

    //get user repositories
    public static void getRepoandWatchersFromUser(GitHubClient client, String username) throws IOException {
        RepositoryService serviceRepos = new RepositoryService(client);
        for (Repository repo : serviceRepos.getRepositories(username)) {
            System.out.println("RepoName=" + repo.getName() + " Watchers: " + repo.getWatchers() + " createdAt: " + repo.getCreatedAt());
        }
    }

    /* Json -> Java
     it returns the user as java by calling https://api.github.com/users/$user
     {hireable=true, createdAt=Mon Dec 10 22:45:23 EET 2012, collaborators=1, diskUsage=308435, followers=1, following=2, id=3010848, ownedPrivateRepos=1, privateGists=0, publicGists=0, publicRepos=4, totalPrivateRepos=1, avatarUrl=https://avatars.githubusercontent.com/u/3010848?v=3, bio=null, blog=http://www.safirasoft.com, company=ISAE-Cnam, email=salim.rahal@isae.edu.lb, gravatarId=, htmlUrl=https://github.com/salimrahal, location=Beirut, login=salimrahal, name=salim, 
     type=User, url=https://api.github.com/users/salimrahal, plan=org.eclipse.egit.github.core.UserPlan@11e96e0}
     */
    public static void getUserObjectbyUserName(GitHubClient client, String username) throws IOException {
        UserService serviceUser = new UserService(client);
        User user = serviceUser.getUser(username);
        System.out.println("user =" + user.toString());
    }

    /*
    GitHubClient{baseUri=https://api.github.com, prefix=null, gson={serializeNulls:truefactories:[Factory[typeHierarchy=com.google.gson.JsonElement,adapter=com.google.gson.internal.bind.TypeAdapters$25@fcc268], com.google.gson.internal.bind.ObjectTypeAdapter$1@3f4395, com.google.gson.internal.Excluder@16df388, com.google.gson.TreeTypeAdapter$SingleTypeFactory@18f7af7, com.google.gson.TreeTypeAdapter$SingleTypeFactory@d22860, Factory[type=java.lang.String,adapter=com.google.gson.internal.bind.TypeAdapters$13@158b812], Factory[type=java.lang.Integer+int,adapter=com.google.gson.internal.bind.TypeAdapters$7@19cc014], Factory[type=java.lang.Boolean+boolean,adapter=com.google.gson.internal.bind.TypeAdapters$3@663ec], Factory[type=java.lang.Byte+byte,adapter=com.google.gson.internal.bind.TypeAdapters$5@18a577d], Factory[type=java.lang.Short+short,adapter=com.google.gson.internal.bind.TypeAdapters$6@175deb1], Factory[type=java.lang.Long+long,adapter=com.google.gson.internal.bind.TypeAdapters$8@16f6a81], Factory[type=java.lang.Double+double,adapter=com.google.gson.Gson$3@1b94a3f], Factory[type=java.lang.Float+float,adapter=com.google.gson.Gson$4@1b9ae95], Factory[type=java.lang.Number,adapter=com.google.gson.internal.bind.TypeAdapters$11@174c043], Factory[type=java.lang.Character+char,adapter=com.google.gson.internal.bind.TypeAdapters$12@1faa3c5], Factory[type=java.lang.StringBuilder,adapter=com.google.gson.internal.bind.TypeAdapters$16@15ebf0], Factory[type=java.lang.StringBuffer,adapter=com.google.gson.internal.bind.TypeAdapters$17@135019d], Factory[type=java.math.BigDecimal,adapter=com.google.gson.internal.bind.TypeAdapters$14@fceb09], Factory[type=java.math.BigInteger,adapter=com.google.gson.internal.bind.TypeAdapters$15@aabc2d], Factory[type=java.net.URL,adapter=com.google.gson.internal.bind.TypeAdapters$18@12856b4], Factory[type=java.net.URI,adapter=com.google.gson.internal.bind.TypeAdapters$19@154598e], Factory[type=java.util.UUID,adapter=com.google.gson.internal.bind.TypeAdapters$21@c3ad9b], Factory[type=java.util.Locale,adapter=com.google.gson.internal.bind.TypeAdapters$24@ba9d8e], Factory[typeHierarchy=java.net.InetAddress,adapter=com.google.gson.internal.bind.TypeAdapters$20@1bdfbf6], Factory[type=java.util.BitSet,adapter=com.google.gson.internal.bind.TypeAdapters$2@1e8e4c8], com.google.gson.internal.bind.DateTypeAdapter$1@104273f, Factory[type=java.util.Calendar+java.util.GregorianCalendar,adapter=com.google.gson.internal.bind.TypeAdapters$23@1acc3ad], com.google.gson.internal.bind.TimeTypeAdapter$1@162ae62, com.google.gson.internal.bind.SqlDateTypeAdapter$1@1a6d5e1, com.google.gson.internal.bind.TypeAdapters$22@23b1e1, com.google.gson.internal.bind.ArrayTypeAdapter$1@1736a73, Factory[type=java.lang.Class,adapter=com.google.gson.internal.bind.TypeAdapters$1@51a9e4], com.google.gson.internal.bind.CollectionTypeAdapterFactory@168dbcd, com.google.gson.internal.bind.MapTypeAdapterFactory@1367dca, com.google.gson.internal.bind.JsonAdapterAnnotationTypeAdapterFactory@7c5965, com.google.gson.internal.bind.TypeAdapters$26@1d1068c, com.google.gson.internal.bind.ReflectiveTypeAdapterFactory@4f41c],instanceCreators:{}}, user=null, credentials=token a57b64a26b489d81eeb288de2713ced84aeb89cc, userAgent=GitHubJava/2.1.0, headerAccept=application/vnd.github.v3.full+json, bufferSize=8192, requestLimit=-1, remainingRequests=-1}

    */
    public static void getHeadersbyUserName(GitHubClient client, String username) throws IOException {
        //System.out.println("X-RateLimit-Limit:");
        //client.getRequestLimit();
        System.out.println("getHeadersbyUserName=" + client.toString());

    }

    public static void printRepos() throws IOException {
        final String user = "defunkt";
        final String format = "{0}) {1}- created on {2}";
        int count = 1;
        RepositoryService service = new RepositoryService();
        for (Repository repo : service.getRepositories(user)) {
            System.out.println(MessageFormat.format(format, count++,
                    repo.getName(), repo.getCreatedAt()));
        }
    }

    public static void pageCommits() {
        /**
         * Print commit authors and dates paged in blocks of 25
         *
         */
        final int size = 25;
        final RepositoryId repo = new RepositoryId("github", "hubot");
        final String message = "   {0} by {1} on {2}";
        final CommitService service = new CommitService();
        int pages = 1;
        for (Collection<RepositoryCommit> commits : service.pageCommits(repo,
                size)) {
            System.out.println("Commit Page " + pages++);
            for (RepositoryCommit commit : commits) {
                String sha = commit.getSha().substring(0, 7);
                String author = commit.getCommit().getAuthor().getName();
                Date date = commit.getCommit().getAuthor().getDate();
                System.out.println(MessageFormat.format(message, sha, author,
                        date));
            }
        }
    }
}
