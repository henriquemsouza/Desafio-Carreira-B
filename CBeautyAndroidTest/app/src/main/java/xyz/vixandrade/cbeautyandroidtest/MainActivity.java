package xyz.vixandrade.cbeautyandroidtest;

import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URL;

import android.graphics.BitmapFactory;
import android.graphics.Bitmap;

import com.bumptech.glide.Glide;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import org.json.JSONArray;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button searchButton = (Button) findViewById(R.id.search_button);
        final EditText searchProfile = (EditText) findViewById(R.id.search_profile);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(searchProfile.getText())) {
                    Toast.makeText(getApplicationContext(), R.string.msgVazio, Toast.LENGTH_SHORT).show();
                } else {
                    fillProfileInfo(searchProfile.getText().toString());
                }
            }
        });
    }

    void fillProfileInfo(String profile)
    {
        final TextView userName = (TextView) findViewById(R.id.user_name);
        TextView userBio = (TextView) findViewById(R.id.user_bio);
        TextView userFollowers = (TextView) findViewById(R.id.user_folowers);
        TextView userRepos = (TextView) findViewById(R.id.user_repos);
        ImageView userAvatar = (ImageView) findViewById((R.id.user_avatar));
        String Repo ;

        AsyncTask<String, Void, JSONObject> task = new AsyncTask<String, Void, JSONObject>() {
            @Override
            public JSONObject doInBackground(String... args) {
                try {
                    String requestUrl = "https://api.github.com/users/" + args[0];
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .get()
                            .url(requestUrl)
                            .build();
                    Call call = client.newCall(request);

                    ResponseBody body = call.execute().body();

                    return new JSONObject(body.string());
                } catch (IOException | JSONException e)
                {
                    throw new RuntimeException(e);
                }

            }
        };
        CardView userCard = (CardView) findViewById(R.id.card_user_info);
        try {
            JSONObject rootObj = task.execute(profile).get();
            userName.setText(rootObj.getString("login"));
            userBio.setText(rootObj.getString("bio"));
            userFollowers.setText(rootObj.getInt("followers") + " Seguidores");
            userRepos.setText(rootObj.getInt("public_repos") + " Repositórios");
            Repo = rootObj.getString("repos_url");


            Glide.with(MainActivity.this).load(rootObj.getString("avatar_url"))
                    .asBitmap()
                    .fitCenter()
                    .into(userAvatar);
            userCard.setVisibility(View.VISIBLE);
            fillRepoInfo(Repo);

        } catch (InterruptedException | ExecutionException | JSONException e) {
            userCard.setVisibility(View.INVISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    void fillRepoInfo(String repovar) {
        //
        final CardView rc1 = (CardView) findViewById(R.id.card_repo1);
        final CardView rc2 = (CardView) findViewById(R.id.card_repo2);
        final CardView rc3 = (CardView) findViewById(R.id.card_repo3);
        //1 repository
        final TextView rc1Watchers = (TextView) findViewById(R.id.repo1_watchers);
        final TextView rc1Issues = (TextView) findViewById(R.id.repo1_issues);
        final TextView rc1Name = (TextView) findViewById(R.id.repo1_name);
        final TextView rc1Url = (TextView) findViewById(R.id.repo1_url);
        //2 repository
        final TextView rc2Watchers = (TextView) findViewById(R.id.repo2_watchers);
        final TextView rc2Issues = (TextView) findViewById(R.id.repo2_issues);
        final TextView rc2Name = (TextView) findViewById(R.id.repo2_name);
        final TextView rc22Url = (TextView) findViewById(R.id.repo2_url);
        //3 repository
        final TextView rc3Watchers = (TextView) findViewById(R.id.repo3_watchers);
        final TextView rc3Issues = (TextView) findViewById(R.id.repo3_issues);
        final TextView rc3Name = (TextView) findViewById(R.id.repo3_name);
        final TextView rc3Url = (TextView) findViewById(R.id.repo3_url);


        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .url(repovar)
                .build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e)
            {
                throw new RuntimeException(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {

                final String Jdata = response.body().string();

                if (response.isSuccessful()) {
                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {

                            try {

                                JSONArray rootObj = new JSONArray(Jdata);

                                if(rootObj != null)
                                //if(rootObj.length()>=0)
                                {

                                    JSONObject repository1 = rootObj.getJSONObject(0);
                                    rc1Watchers.setText(repository1.getInt("watchers_count") + " Watchers");
                                    rc1Issues.setText(repository1.getInt("open_issues_count") + " Issues");
                                    rc1Name.setText(repository1.getString("name"));
                                    rc1Url.setText(repository1.getString("html_url"));
                                    rc1.setVisibility(View.VISIBLE);
                                }

                                 if(rootObj != null)
                                //if(rootObj.length()>=1)
                                {

                                    JSONObject repository2 = rootObj.getJSONObject(1);
                                    rc2Watchers.setText(repository2.getInt("watchers_count") + " Watchers");
                                    rc2Issues.setText(repository2.getInt("open_issues_count") + " Issues");
                                    rc2Name.setText(repository2.getString("name"));
                                    rc22Url.setText(repository2.getString("html_url"));
                                    rc2.setVisibility(View.VISIBLE);
                                }

                                if(rootObj != null)
                                //if(rootObj.length()>=2)
                                {

                                    JSONObject repository3 = rootObj.getJSONObject(2);
                                    rc3Name.setText(repository3.getString("name"));
                                    rc3Url.setText(repository3.getString("html_url"));
                                    rc3Watchers.setText(repository3.getInt("watchers_count") + " Watchers");
                                    rc3Issues.setText(repository3.getInt("open_issues_count") + " Issues");
                                    rc3.setVisibility(View.VISIBLE);
                                }

                            }
                             catch (JSONException e)
                            {
                                rc1.setVisibility(View.INVISIBLE);
                                rc2.setVisibility(View.INVISIBLE);
                                rc3.setVisibility(View.INVISIBLE);
                                Toast.makeText(getApplicationContext(), R.string.msgVazioRep, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        //
    }
}

