package com.example.android.hogwarts;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer player;
    private MediaPlayer.OnCompletionListener completionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView phoneNumber = findViewById(R.id.phone_number);
        final String numToUse = phoneNumber.getText().toString();

        ImageButton callButton = findViewById(R.id.call_button);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + numToUse));
                startActivity(callIntent);

            }
        });

        ImageButton msgButton = findViewById(R.id.msg_button);
        msgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent messageIntent = new Intent(Intent.ACTION_SENDTO);
                messageIntent.setData(Uri.parse("sms:" + numToUse));
                startActivity(messageIntent);
            }
        });

        player = null;

        Button play = findViewById(R.id.play_button);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cancelToast();

                if(player != null) {
                    toast= Toast.makeText(getApplicationContext(), R.string.play_button_pressedagain_toast, Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    player = MediaPlayer.create(getApplicationContext(), R.raw.harrypotter);
                    player.start();
                    toast = Toast.makeText(getApplicationContext(), R.string.play_button_toast, Toast.LENGTH_SHORT);
                    toast.show();
                }
                player.setOnCompletionListener(completionListener);
            }
        });

        Button stop = findViewById(R.id.stop_button);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(player != null && player.isPlaying()) {
                    player.stop();
                    releaseMediaPlayer();
                }
            }
        });


    }

    private void cancelToast() {
        if(toast != null)
        {
            toast.cancel();
            toast = null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    public void releaseMediaPlayer () {
        cancelToast();
        if(player != null){
            player.release();
            player = null;
        }
    }
}
