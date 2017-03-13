package com.app.shortoftheweek;

/* Android imports*/
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;

/* App imports */

/* Vimeo Imports */
import com.app.shortoftheweek.databinding.ActivityMainBinding;
import com.app.shortoftheweek.models.VideoModel;
import com.vimeo.networking.*;
import com.vimeo.networking.callbacks.ModelCallback;
import com.vimeo.networking.model.Video;
import com.vimeo.networking.model.VideoList;
import com.vimeo.networking.model.error.VimeoError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends Activity implements OnClickListener {

    /* Shortoftheweek Vimeo URL */
    public static final String SHORTOFTHEWEEK_VIDEO_URI = "/channels/shortoftheweek/videos";

    private VimeoClient mApiClient = VimeoClient.getInstance();
    // private ProgressDialog mProgressDialog;


    ArrayList<VideoModel> myFilms = new ArrayList<>();
//    ArrayList<Video> myTest = new ArrayList<>();
    // HashMap<String, String> cortFilm = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setVideos(myFilms);
        fetchShorts();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fetch_videos:
                fetchShorts();
                break;
        }
    }

    private void updateUpdateUI(ArrayList<Video> videos) {
        boolean addNewLine = false;
        String videoTitlesString = "";
        for(Video video : videos) {
            if (addNewLine) {
                videoTitlesString += "\n";
            }
            addNewLine = true;
            videoTitlesString += video.name;
        }
    }

    private void fetchShorts() {

        mApiClient.fetchNetworkContent(SHORTOFTHEWEEK_VIDEO_URI, new ModelCallback<VideoList>(VideoList.class) {

            @Override
            public void success(VideoList videoList) {
                if (videoList != null && videoList.data != null) {
                    myFilms.clear();
                    for (Video video : videoList.data) {

                        VideoModel model = new VideoModel();
                        model.setTitle(video.name);
                        model.setLanguage(video.language);
                        myFilms.add(model);
                    }
                }
                toast("Staff Picks Success");
            }

            @Override
            public void failure(VimeoError error) {
                toast("Staff Picks Failure");
            }
        });

    }

    private void toast(String string) {
        Toast.makeText(MainActivity.this, string, Toast.LENGTH_SHORT).show();
    }
}
