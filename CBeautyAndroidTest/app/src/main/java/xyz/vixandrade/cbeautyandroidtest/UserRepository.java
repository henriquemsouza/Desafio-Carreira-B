package xyz.vixandrade.cbeautyandroidtest;

/**
 * Created by Henrique on 23/09/2017.
 */
//nao usado
public class UserRepository {
    private int watchers;


    private int issues;
    private String url;
    private String name;

    public int getWatchers() {
        return watchers;
    }

    public void setWatchers(int watchers) {
        this.watchers = watchers;
    }

    public int getIssues() {
        return issues;
    }

    public void setIssues(int issues) {
        this.issues = issues;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public UserRepository(int watchers, int issues, String url, String name) {
        this.watchers = watchers;
        this.issues = issues;
        this.url = url;
        this.name = name;
    }




}
