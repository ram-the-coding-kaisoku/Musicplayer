package com.example.musicplayer;


import static java.util.concurrent.TimeUnit.MILLISECONDS;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    //buttons
    Button play,pause,forward,backward;
    //Textview
    TextView time_text,textView3;
    //seekbar
    SeekBar seekBar;

    //mediaplayer
    MediaPlayer mediaPlayer;

    Handler handler = new Handler();

    //variables
    double starttime;
    double finaltime;
    int forwardtime=10000;
    int backwardtime=10000;
    int onetimeonly=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //creating instances for
        //buttons
        play=findViewById(R.id.play);
        pause=findViewById(R.id.pause);
        forward=findViewById(R.id.foward);
        backward=findViewById(R.id.backward);

        //textviews
        time_text=findViewById(R.id.textView2);
        textView3=findViewById(R.id.textView3);

        //seekbar
        seekBar = findViewById(R.id.seekBar);

        //creating media player
        mediaPlayer= MediaPlayer.create(this,R.raw.song);
        //seekbar clickable
        seekBar.setClickable(false);

        //funcionality
        //play button
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playaudio();
            }
        });
        //pause button to pause the music
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.pause();
            }
        });
        //forward
        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp=(int) starttime;
                if(temp+forwardtime<=finaltime){
                    starttime=starttime+forwardtime;
                    mediaPlayer.seekTo((int) starttime);
                }
                else{
                    Toast.makeText(MainActivity.this, "The song gonna end", Toast.LENGTH_SHORT).show();
                }
            }
        });
        backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp= (int) starttime;
                if(temp-backwardtime>0){
                    starttime =starttime-backwardtime;
                    mediaPlayer.seekTo((int) starttime);
                }

            }
        });


    }

    private void playaudio() {
        mediaPlayer.start();
        //whole duration of the song
        finaltime=mediaPlayer.getDuration();
        //current duration of that song is playing
        starttime=mediaPlayer.getCurrentPosition();

        if(onetimeonly==0){
            seekBar.setMax((int)finaltime);
            onetimeonly=1;
        }
        time_text.setText(String.format("%d min %d sec", MILLISECONDS.toMinutes((long) finaltime),
                MILLISECONDS.toSeconds((long) finaltime)-MILLISECONDS.toSeconds(MILLISECONDS.toMinutes((long) finaltime))
        ));

        seekBar.setProgress((int) starttime);
        handler.postDelayed(UpdateSongTime,100);

    }

    private  Runnable UpdateSongTime = new Runnable() {
        @Override
        public void run() {
            starttime=mediaPlayer.getCurrentPosition();
            time_text.setText(String.format("%d min %d sec", MILLISECONDS.toMinutes((long) finaltime),
                    MILLISECONDS.toSeconds((long) finaltime)-MILLISECONDS.toSeconds(MILLISECONDS.toMinutes((long) finaltime))));

            seekBar.setProgress((int)starttime);
            handler.postDelayed(this,100);

        }
    };



    //function that will play audio

}