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
import java.util.List;
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
public class RepoBo {

    //get user repositories
    public static void getRepoandWatchersFromUser(GitHubClient client, String username) throws IOException {
        RepositoryService serviceRepos = new RepositoryService(client);
        for (Repository repo : serviceRepos.getRepositories(username)) {
            System.out.println("RepoName=" + repo.getName() + " Watchers: " + repo.getWatchers() + " createdAt: " + repo.getCreatedAt());
        }
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
