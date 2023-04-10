package com.example.keyboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    public static final int doSound = R.raw._do;
    public static final int reSound = R.raw.re;
    public static final int miSound = R.raw.mi;
    public static final int faSound = R.raw.fa;
    public static final int solSound = R.raw.sol;
    public static final int laSound = R.raw.la;
    public static final int siSound = R.raw.si;
    public static final int doOctaveSound = R.raw._do_octave;

    static final int READ_BLOCK_SIZE = 100;

    Button doSoundBtn;
    Button reSoundBtn;
    Button miSoundBtn;
    Button faSoundBtn;
    Button solSoundBtn;
    Button laSoundBtn;
    Button siSoundBtn;
    Button doOctaveSoundBtn;
    EditText inputText;
    private final List<String> soundSequence = new ArrayList<>();
    private List<String> newSoundSequence = new ArrayList<>();
//    String filePath = String.valueOf(inputText.getText());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        doSoundBtn = (Button) findViewById(R.id.doBtn);
        reSoundBtn = (Button) findViewById(R.id.reBtn);
        miSoundBtn = (Button) findViewById(R.id.miBtn);
        faSoundBtn = (Button) findViewById(R.id.faBtn);
        solSoundBtn = (Button) findViewById(R.id.solBtn);
        laSoundBtn = (Button) findViewById(R.id.laBtn);
        siSoundBtn = (Button) findViewById(R.id.siBtn);
        doOctaveSoundBtn = (Button) findViewById(R.id.doOctaveBtn);

        inputText = (EditText) findViewById(R.id.fileAccessInput);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //ok
        } else {
            Toast.makeText(this, "Stary ten system", Toast.LENGTH_LONG).show();
        }
    }

    public void playSound(Context context, int soundID) {
        MediaPlayer mp = MediaPlayer.create(context, soundID);

        mp.start();
    }

    public void PlaySoundOutLoud(View v) {
        int sound = 0;

        if (doSoundBtn.getId() == v.getId()) {
            sound = doSound;
            changeButtonBackgroundColor(doSoundBtn);
            soundSequence.add("DO");
        } else if (reSoundBtn.getId() == v.getId()) {
            sound = reSound;
            changeButtonBackgroundColor(reSoundBtn);
            soundSequence.add("RE");
        } else if (miSoundBtn.getId() == v.getId()) {
            sound = miSound;
            changeButtonBackgroundColor(miSoundBtn);
            soundSequence.add("MI");
        } else if (faSoundBtn.getId() == v.getId()) {
            sound = faSound;
            changeButtonBackgroundColor(faSoundBtn);
            soundSequence.add("FA");
        } else if (solSoundBtn.getId() == v.getId()) {
            sound = solSound;
            changeButtonBackgroundColor(solSoundBtn);
            soundSequence.add("SOL");
        } else if (laSoundBtn.getId() == v.getId()) {
            sound = laSound;
            changeButtonBackgroundColor(laSoundBtn);
            soundSequence.add("LA");
        } else if (siSoundBtn.getId() == v.getId()) {
            sound = siSound;
            changeButtonBackgroundColor(siSoundBtn);
            soundSequence.add("SI");
        } else if (doOctaveSoundBtn.getId() == v.getId()) {
            sound = doOctaveSound;
            changeButtonBackgroundColor(doOctaveSoundBtn);
            soundSequence.add("DO-OCTAVE");
        }

        playSound(this, sound);
    }

    public void Write(View v) {
//        filePath = filePath.replaceAll("\\s", "");

        try {
            FileOutputStream file_out = openFileOutput(inputText.getText() + ".txt", MODE_PRIVATE);
            OutputStreamWriter out_writer = new OutputStreamWriter(file_out);
//            out_writer.write(soundSequence.toString());
            out_writer.close();
            resetAllBtnsBackgroundColor();

            out_writer.write(newSoundSequence.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        soundSequence.clear();
    }

    public void Read(View v) {
        try {
            FileInputStream fileIn = openFileInput(inputText.getText() + ".txt");
            InputStreamReader input_read = new InputStreamReader(fileIn);
            char[] inputBuffer = new char[READ_BLOCK_SIZE];
            StringBuilder s = new StringBuilder();
            int charRead;

            while ((charRead = input_read.read(inputBuffer)) > 0) {
                String readString = String.copyValueOf(inputBuffer, 0, charRead);
                s.append(readString);
            }

            input_read.close();
            Toast.makeText(getBaseContext(), s.toString(), Toast.LENGTH_SHORT).show();

            String[] sounds = s.toString().replaceAll("\\[|\\]|\\s", "").split(",");
            newSoundSequence.clear();
            for (String sound : sounds) {
                newSoundSequence.add(sound);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void PlaySequence(View v) {
        int soundID = 0;
        for (String sound : newSoundSequence) {
            switch (sound) {
                case "DO":
                    changeButtonBackgroundColor(doSoundBtn);
                    soundID = doSound;
                    break;
                case "RE":
                    changeButtonBackgroundColor(reSoundBtn);
                    soundID = reSound;
                    break;
                case "MI":
                    changeButtonBackgroundColor(miSoundBtn);
                    soundID = miSound;
                    break;
                case "FA":
                    changeButtonBackgroundColor(faSoundBtn);
                    soundID = faSound;
                    break;
                case "SOL":
                    changeButtonBackgroundColor(solSoundBtn);
                    soundID = solSound;
                    break;
                case "LA":
                    changeButtonBackgroundColor(laSoundBtn);
                    soundID = laSound;
                    break;
                case "SI":
                    changeButtonBackgroundColor(siSoundBtn);
                    soundID = siSound;
                    break;
                case "DO-OCTAVE":
                    changeButtonBackgroundColor(doOctaveSoundBtn);
                    soundID = doOctaveSound;
                    break;
                default:
                    // handle unrecognized sound
                    break;
            }
            playSound(this, soundID);
            delay(500);
        }
        resetAllBtnsBackgroundColor();

    }

    private void delay(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void resetAllBtnsBackgroundColor() {
        Button[] allBtns = {
                doSoundBtn,
                reSoundBtn,
                miSoundBtn,
                faSoundBtn,
                solSoundBtn,
                laSoundBtn,
                siSoundBtn,
                doOctaveSoundBtn
        };

        for (Button btn : allBtns) {
            btn.setBackgroundColor(Color.rgb(107, 170, 164));
        }
    }

    public void changeButtonBackgroundColor(Button btn) {
        Button[] allBtns = {
                doSoundBtn,
                reSoundBtn,
                miSoundBtn,
                faSoundBtn,
                solSoundBtn,
                laSoundBtn,
                siSoundBtn,
                doOctaveSoundBtn
        };


        for (Button allBtn : allBtns) {
            if (allBtn.getId() == btn.getId()) {
                allBtn.setBackgroundColor(Color.GREEN);
            } else {
                allBtn.setBackgroundColor(Color.RED);

            }
        }


//        resetAllBtnsBackgroundColor(allBtns);
    }

}