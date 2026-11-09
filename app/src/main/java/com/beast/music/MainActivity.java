package com.beast.music;

import android.app.*;
import android.os.*;
import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.MediaStore;
import android.media.MediaPlayer;
import android.graphics.Color;
import android.view.*;
import android.widget.*;
import android.net.Uri;
import java.util.ArrayList;

public class MainActivity extends Activity {

    LinearLayout root;
    ListView listView;
    TextView nowPlaying;
    Button playButton;

    ArrayList<String> songs = new ArrayList<>();
    ArrayList<Uri> paths = new ArrayList<>();

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

        TextView title = new TextView(this);
        title.setText("BEAST MUSIC");
        title.setTextSize(30);
        title.setTextColor(Color.MAGENTA);
        title.setGravity(Gravity.CENTER);
        root.addView(title);

        nowPlaying = new TextView(this);
        nowPlaying.setText("Select a Song");
        nowPlaying.setTextColor(Color.WHITE);
        nowPlaying.setTextSize(18);
        nowPlaying.setGravity(Gravity.CENTER);
        nowPlaying.setPadding(10,30,10,30);
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

        listView.setOnItemClickListener(
            (p,v,pos,id) -> playSong(pos));

        checkPermission();
    }

    void checkPermission() {
        String permission;

        if (Build.VERSION.SDK_INT >= 33) {
            permission = Manifest.permission.READ_MEDIA_AUDIO;
        } else {
            permission = Manifest.permission.READ_EXTERNAL_STORAGE;
        }

        if (checkSelfPermission(permission)
                == PackageManager.PERMISSION_GRANTED) {
            loadSongs();
        } else {
            requestPermissions(new String[]{permission},100);
        }
    }

    void loadSongs() {
        songs.clear();
        paths.clear();

        String[] projection = {
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE
        };

        try (Cursor c = getContentResolver().query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,
            MediaStore.Audio.Media.IS_MUSIC + "!= 0",
            null,
            MediaStore.Audio.Media.TITLE + " ASC")) {

            if (c != null) {
                while (c.moveToNext()) {
                    long id = c.getLong(0);
                    songs.add(c.getString(1));

                    Uri uri = Uricd ~/BEAST-MUSIC

cat > app/src/main/java/com/beast/music/MainActivity.java <<'EOF'
package com.beast.music;

import android.app.*;
import android.os.*;
import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.MediaStore;
import android.media.MediaPlayer;
import android.graphics.Color;
import android.view.*;
import android.widget.*;
import android.net.Uri;
import java.util.ArrayList;

public class MainActivity extends Activity {

    LinearLayout root;
    ListView listView;
    TextView nowPlaying;
    Button playButton;

    ArrayList<String> songs = new ArrayList<>();
    ArrayList<Uri> paths = new ArrayList<>();

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

        TextView title = new TextView(this);
        title.setText("BEAST MUSIC");
        title.setTextSize(30);
        title.setTextColor(Color.MAGENTA);
        title.setGravity(Gravity.CENTER);
        root.addView(title);

        nowPlaying = new TextView(this);
        nowPlaying.setText("Select a Song");
        nowPlaying.setTextColor(Color.WHITE);
        nowPlaying.setTextSize(18);
        nowPlaying.setGravity(Gravity.CENTER);
        nowPlaying.setPadding(10,30,10,30);
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

        listView.setOnItemClickListener(
            (p,v,pos,id) -> playSong(pos));

        checkPermission();
    }

    void checkPermission() {
        String permission;

        if (Build.VERSION.SDK_INT >= 33) {
            permission = Manifest.permission.READ_MEDIA_AUDIO;
        } else {
            permission = Manifest.permission.READ_EXTERNAL_STORAGE;
        }

        if (checkSelfPermission(permission)
                == PackageManager.PERMISSION_GRANTED) {
            loadSongs();
        } else {
            requestPermissions(new String[]{permission},100);
        }
    }

    void loadSongs() {
        songs.clear();
        paths.clear();

        String[] projection = {
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE
        };

        try (Cursor c = getContentResolver().query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,
            MediaStore.Audio.Media.IS_MUSIC + "!= 0",
            null,
            MediaStore.Audio.Media.TITLE + " ASC")) {

            if (c != null) {
                while (c.moveToNext()) {
                    long id = c.getLong(0);
                    songs.add(c.getString(1));

                    Uri uri = Uri.withAppendedPath(
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        String.valueOf(id));

                    paths.add(uri);
                }
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
            this,
            android.R.layout.simple_list_item_1,
            songs);

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

            player.setDataSource(
                this, paths.get(index));

            player.setOnCompletionListener(
                mp -> playSong(current + 1));

            player.prepare();
            player.start();

            nowPlaying.setText(songs.get(index));
            playButton.setText("⏸");

        } catch (Exception e) {
            Toast.makeText(this,
                "Cannot play song",
                Toast.LENGTH_SHORT).show();
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
        int requestCode,
        String[] permissions,
        int[] results) {

        super.onRequestPermissionsResult(
            requestCode, permissions, results);

        if (requestCode == 100 &&
            results.length > 0 &&
            results[0] == PackageManager.PERMISSION_GRANTED) {
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
