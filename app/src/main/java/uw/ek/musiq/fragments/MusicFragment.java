package uw.ek.musiq.fragments;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import uw.ek.musiq.MainActivity;
import uw.ek.musiq.R;
import uw.ek.musiq.Webview;
import uw.ek.musiq.models.Song;
import uw.ek.musiq.playback.MusicNotificationManager;
import uw.ek.musiq.playback.MusicService;
import uw.ek.musiq.playback.PlaybackInfoListener;
import uw.ek.musiq.playback.PlayerAdapter;
import uw.ek.musiq.utils.EqualizerUtils;
import uw.ek.musiq.utils.RecyclerAdapter;
import uw.ek.musiq.utils.SongProvider;

public class MusicFragment extends Fragment implements View.OnClickListener,
        RecyclerAdapter.SongClicked {

    private RecyclerView recyclerView;
    private SeekBar seekBar;
    private ImageButton playPause, next, previous;
    private TextView songTitle;
    private MusicService mMusicService;
    private Boolean mIsBound;
    private PlayerAdapter mPlayerAdapter;
    private boolean mUserIsSeeking = false;
    private MusicFragment.PlaybackListener mPlaybackListener;
    private List<Song> mSelectedArtistSongs;
    private MusicNotificationManager mMusicNotificationManager;
    private RecyclerAdapter recyclerAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public MusicFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_music, container, false);

    }
    public void onViewCreated (View view, Bundle savedInstanceState) {
        doBindService();
        setViews();
        initializeSeekBar();

        mSwipeRefreshLayout = (SwipeRefreshLayout) getView().findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(false);
                recyclerAdapter.notifyDataSetChanged();
                setViews();
                SongProvider.getAllDeviceSongs(getContext());
            }
        });

    }

    private final ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

            mMusicService = ((MusicService.LocalBinder) iBinder).getInstance();
            mPlayerAdapter = mMusicService.getMediaPlayerHolder();
            mMusicNotificationManager = mMusicService.getMusicNotificationManager();

            if (mPlaybackListener == null) {
                mPlaybackListener = new MusicFragment.PlaybackListener();
                mPlayerAdapter.setPlaybackInfoListener(mPlaybackListener);
            }
            if (mPlayerAdapter != null && mPlayerAdapter.isPlaying()) {

                restorePlayerStatus();
            }
            checkReadStoragePermissions();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mMusicService = null;
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        doUnbindService();
        if (mPlayerAdapter != null && mPlayerAdapter.isMediaPlayer()) {
            mPlayerAdapter.onPauseActivity();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        doBindService();
        if (mPlayerAdapter != null && mPlayerAdapter.isPlaying()) {
            restorePlayerStatus();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
            Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }
    }

    private void setViews() {
        playPause = getView().findViewById(R.id.buttonPlayPause);
        next = getView().findViewById(R.id.buttonNext);
        previous = getView().findViewById(R.id.buttonPrevious);
        seekBar = getView().findViewById(R.id.seekBar);
        recyclerView = getView().findViewById(R.id.recyclerView);
        songTitle = getView().findViewById(R.id.songTitle);
        //To listen to clicks
        playPause.setOnClickListener(this);
        next.setOnClickListener(this);
        previous.setOnClickListener(this);
        //set adapter
        recyclerAdapter = new RecyclerAdapter(this);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        //get songs
        mSelectedArtistSongs = SongProvider.getAllDeviceSongs(getContext());
        recyclerAdapter.addSongs((ArrayList) mSelectedArtistSongs);
    }

    private void checkReadStoragePermissions() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
    }

    private void updatePlayingInfo(boolean restore, boolean startPlay) {

        if (startPlay) {
            mPlayerAdapter.getMediaPlayer().start();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mMusicService.startForeground(MusicNotificationManager.NOTIFICATION_ID,
                            mMusicNotificationManager.createNotification());
                }
            }, 250);
        }

        final Song selectedSong = mPlayerAdapter.getCurrentSong();

        songTitle.setText(selectedSong.title);
        final int duration = selectedSong.duration;
        seekBar.setMax(duration);

        if (restore) {
            seekBar.setProgress(mPlayerAdapter.getPlayerPosition());
            updatePlayingStatus();


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //stop foreground if coming from pause state
                    if (mMusicService.isRestoredFromPause()) {
                        mMusicService.stopForeground(false);
                        mMusicService.getMusicNotificationManager().getNotificationManager()
                                .notify(MusicNotificationManager.NOTIFICATION_ID,
                                        mMusicService.getMusicNotificationManager().getNotificationBuilder().build());
                        mMusicService.setRestoredFromPause(false);
                    }
                }
            }, 250);
        }
    }


    private void updatePlayingStatus() {
        final int drawable = mPlayerAdapter.getState() != PlaybackInfoListener.State.PAUSED ?
                R.drawable.ic_pause : R.drawable.ic_play;
        playPause.post(new Runnable() {
            @Override
            public void run() {
                playPause.setImageResource(drawable);
            }
        });
    }

    private void restorePlayerStatus() {
        seekBar.setEnabled(mPlayerAdapter.isMediaPlayer());

        //if we are playing and the activity was restarted
        //update the controls panel
        if (mPlayerAdapter != null && mPlayerAdapter.isMediaPlayer()) {

            mPlayerAdapter.onResumeActivity();
            updatePlayingInfo(true, false);
        }
    }

    private void doBindService() {
        // Establish a connection with the service.  We use an explicit
        // class name because we want a specific service implementation that
        // we know will be running in our own process (and thus won't be
        // supporting component replacement by other applications).
        getActivity().bindService(new Intent(getContext(),
                MusicService.class), mConnection, Context.BIND_AUTO_CREATE);
        mIsBound = true;

        final Intent startNotStickyIntent = new Intent(getContext(), MusicService.class);
        getActivity().startService(startNotStickyIntent);
    }

    private void doUnbindService() {
        if (mIsBound) {
            // Detach our existing connection.
            getActivity().unbindService(mConnection);
            mIsBound = false;
        }
    }

    public void onSongSelected(@NonNull final Song song, @NonNull final List<Song> songs) {
        if (!seekBar.isEnabled()) {
            seekBar.setEnabled(true);
        }
        try {
            mPlayerAdapter.setCurrentSong(song, songs);
            mPlayerAdapter.initMediaPlayer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void skipPrev() {
        if (checkIsPlayer()) {
            mPlayerAdapter.instantReset();
        }
    }

    public void resumeOrPause() {
        if (checkIsPlayer()) {
            mPlayerAdapter.resumeOrPause();
        }
    }

    public void skipNext() {
        if (checkIsPlayer()) {
            mPlayerAdapter.skip(true);
        }
    }

    private boolean checkIsPlayer() {

        boolean isPlayer = mPlayerAdapter.isMediaPlayer();
        if (!isPlayer) {
            EqualizerUtils.notifyNoSessionId(getContext());
        }
        return isPlayer;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.buttonPlayPause): {
                resumeOrPause();

            }
            case (R.id.buttonNext): {
                skipNext();
            }
            case (R.id.buttonPrevious): {
                skipPrev();
            }
        }
    }

    private void initializeSeekBar() {
        seekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    int userSelectedPosition = 0;

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        mUserIsSeeking = true;
                    }

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                        if (fromUser) {
                            userSelectedPosition = progress;

                        }

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                        if (mUserIsSeeking) {

                        }
                        mUserIsSeeking = false;
                        mPlayerAdapter.seekTo(userSelectedPosition);
                    }
                });
    }


    @Override
    public void onSongClicked(Song song) {
        onSongSelected(song, mSelectedArtistSongs);
    }


    class PlaybackListener extends PlaybackInfoListener {

        @Override
        public void onPositionChanged(int position) {
            if (!mUserIsSeeking) {
                seekBar.setProgress(position);
            }
        }

        @Override
        public void onStateChanged(@State int state) {

            updatePlayingStatus();
            if (mPlayerAdapter.getState() != State.RESUMED && mPlayerAdapter.getState() != State.PAUSED) {
                updatePlayingInfo(false, true);

            }
        }

        @Override
        public void onPlaybackCompleted() {
            //After playback is complete

        }
    }


}
