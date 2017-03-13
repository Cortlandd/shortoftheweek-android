package com.app.shortoftheweek.activities;

/* Android imports*/
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

/* App imports */

/* Vimeo Imports */
import com.app.shortoftheweek.R;
import com.app.shortoftheweek.ShortOfTheWeek;
import com.app.shortoftheweek.adapter.VideoAdapter;
import com.app.shortoftheweek.classes.VideoRecyclerView;
import com.app.shortoftheweek.event.VideoInfoReceivedEvent;
import com.app.shortoftheweek.task.GetViemoVideosTask;

public class MainActivity extends Activity implements OnClickListener {

    private VideoRecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private VideoAdapter videoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (VideoRecyclerView)findViewById(R.id.recycler_view);
        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.video_list_refresh);

        Toast.makeText(this, "Loading Content..", Toast.LENGTH_SHORT).show();
    }

    private void initRecyclerView() {
        LinearLayoutManager llm = new LinearLayoutManager(getBaseContext());
        recyclerView.setLayoutManager(llm);

        GetViemoVideosTask task = new GetViemoVideosTask();
        task.getVideoInfo();

        refreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.i("Main", "onRefresh called from SwipeRefreshLayout");

                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.
                        GetViemoVideosTask task = new GetViemoVideosTask();
                        task.getVideoInfo();
                    }
                }
        );
    }


    @Override
    protected void onResume() {
        super.onResume();
        ShortOfTheWeek.getEventBus().register(this);

        initRecyclerView();
    }

    @Override
    protected void onPause() {
        ShortOfTheWeek.getEventBus().unregister(this);
        super.onPause();
    }

    @SuppressWarnings("unused")
    public void onEvent(VideoInfoReceivedEvent event){
        if(refreshLayout.isRefreshing())
            refreshLayout.setRefreshing(false);

        if(event.getVideos() != null) {
            videoAdapter = new VideoAdapter(event.getVideos());
            recyclerView.setAdapter(videoAdapter);
        } else {
            Toast.makeText(this, "Error fetching videos :(", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }
    }
}