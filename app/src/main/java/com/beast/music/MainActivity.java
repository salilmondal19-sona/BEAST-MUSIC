package com.beast.music;

import android.app.*;
import android.os.*;
import android.Manifest;
import android.content.*;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.MediaStore;
import android.media.MediaPlayer;
import android.graphics.Color;
import android.view.*;
import android.widget.*;
import java.util.ArrayList;

public class MainActivity extends Activity {

    LinearLayout root;
    ListView listView;
    TextView title, nowPlaying;
    Button playButton;
    ArrayList<String> songs = new ArrayList<>();
    ArrayList<String> paths = new ArrayList<>();
    MediaPlayer player;
    int current = -1;

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);

        getWindow().setStatusBarColor(Color.BLACK);
        getWindow().setNavigationBarColor(Color.BLACK);

        root = new LinearLayout(this);
        root.setOrientation(1);
        root.setPadding(20, 25, 20, 10);
        root.setBackgroundColor(Color.rgb(10,10,15));

        title = new TextView(this);
        title.setText("🎧 BEAST MUSIC");
        title.setTextSize(28);
        title.setTextColor(Color.MAGENTA);
        title.setGravity(Gravity.CENTER);
        root.addView(title);

        nowPlaying = new TextView(this);
        nowPlaying.setText("Select a song");
        nowPlaying.setTextColor(Color.WHITE);
        nowPlaying.setTextSize(16);
        nowPlaying.setGravity(Gravity.CENTER);
        nowPlaying.setPadding(10,25,10,25);
        root.addView(nowPlaying);

        LinearLayout controls = new LinearLayout(this);
        controls.setGravity(Gravity.CENTER);

        Button prev = new Button(this);
        prev.setText("⏮");
        controls.addView(prev);

        playButton = new Button(this);
        playButton.setText("▶");
        controls.addView(playButton);

        Button next = new Button(this);
        next.setText("⏭");
        controls.addView(next);

        root
cd ~/BEAST-MUSIC

cat > app/src/main/java/com/beast/music/MainActivity.java <<'EOF'
package com.beast.music;

import android.app.*;
import android.os.*;
import android.Manifest;
import android.content.*;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.MediaStore;
import android.media.MediaPlayer;
import android.graphics.Color;
import android.view.*;
import android.widget.*;
import java.util.ArrayList;

public class MainActivity extends Activity {

    LinearLayout root;
    ListView listView;
    TextView title, nowPlaying;
    Button playButton;
    ArrayList<String> songs = new ArrayList<>();
    ArrayList<String> paths = new ArrayList<>();
    MediaPlayer player;
    int current = -1;

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);

        getWindow().setStatusBarColor(Color.BLACK);
        getWindow().setNavigationBarColor(Color.BLACK);

        root = new LinearLayout(this);
        root.setOrientation(1);
        root.setPadding(20, 25, 20, 10);
        root.setBackgroundColor(Color.rgb(10,10,15));

        title = new TextView(this);
        title.setText("🎧 BEAST MUSIC");
        title.setTextSize(28);
        title.setTextColor(Color.MAGENTA);
        title.setGravity(Gravity.CENTER);
        root.addView(title);

        nowPlaying = new TextView(this);
        nowPlaying.setText("Select a song");
        nowPlaying.setTextColor(Color.WHITE);
        nowPlaying.setTextSize(16);
        nowPlaying.setGravity(Gravity.CENTER);
        nowPlaying.setPadding(10,25,10,25);
        root.addView(nowPlaying);

        LinearLayout controls = new LinearLayout(this);
        controls.setGravity(Gravity.CENTER);

        Button prev = new Button(this);
        prev.setText("⏮");
        controls.addView(prev);

        playButton = new Button(this);
        playButton.setText("▶");
        controls.addView(playButton);

        Button next = new Button(this);
        next.setText("⏭");
        controls.addView(next);

        root.addView(controls);

        listView = new ListView(this);
        root.addView(listView,
            new LinearLayout.LayoutParams(-1,0,1));

        setContentView(root);

        playButton.setOnClickListener(v -> togglePlay());

        prev.setOnClickListener(v -> playSong(current - 1));
        next.setOnClickListener(v -> playSong(current + 1));

        listView.setOnItemClickListener((p,v,pos,id) -> playSong(pos));

        if (Build.VERSION.SDK_INT >= 33) {
            if (checkSelfPermission(Manifest.permission.READ_MEDIA_AUDIO)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                    new String[]{Manifest.permission.READ_MEDIA_AUDIO}, 100);
            } else loadSongs();
        } else {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
            } else loadSongs();
        }
    }

    void loadSongs() {
        songs.clear();
        paths.clear();

        String[] projection = {
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DATA
        };

        try (Cursor c = getContentResolver().query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,
            MediaStore.Audio.Media.IS_MUSIC + "!= 0",
            null,
            MediaStore.Audio.Media.TITLE + " ASC")) {

            if (c != null) {
                while (c.moveToNext()) {
                    songs.add(c.getString(0));
                    paths.add(c.getString(1));
                }
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
            this,
            android.R.layout.simple_list_item_1,
            songs
        );

        listView.setAdapter(adapter);
    }

    void playSong(int index) {
        if (index < 0 || index >= paths.size()) return;

        try {
            if (player != null) {
                player.release();
            }

            current = index;
            player = new MediaPlayer();
            player.setDataSource(paths.get(index));
            player.setOnCompletionListener(mp -> playSong(current + 1));
            player.prepare();
            player.start();

            nowPlaying.setText("🎵 " + songs.get(index));
            playButton.setText("⏸");

        } catch (Exception e) {
            Toast.makeText(this,
                "Cannot play this song", Toast.LENGTH_SHORT).show();
        }
    }

    void togglePlay() {
        if (player == null) return;

        if (player.isPlaying()) {
            player.pause();
            playButton.setText("▶");
        } else {
            player.start();
            playButton.setText("⏸");
        }
    }

    @Override
    public void onRequestPermissionsResult(
        int requestCode, String[] permissions, int[] results) {
        super.onRequestPermissionsResult(requestCode, permissions, results);

        if (requestCode == 100 && results.length > 0
                && results[0] == PackageManager.PERMISSION_GRANTED) {
            loadSongs();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
            player = null;
        }
    }
}
