package xyz.vixandrade.cbeautyandroidtest;
import java.util.List;
/**
 * Created by Henrique on 23/09/2017.
 */
//nao usado
public class ProfileRepo {


    private int reposository;
    private List<String> ReposositoryList;

//    public ProfileRepo(int reposository) {
//        this.reposository = reposository;
//    }





    public int getReposository() {
        return reposository;
    }

    public void setReposository(int reposository) {
        this.reposository = reposository;
    }
    public void addRepo(String reposository) {
        ReposositoryList.add(reposository);
    }
    public String getRepo(int index) {
        if (ReposositoryList.size() <= index) return null;
        return ReposositoryList.get(index);
    }


}
