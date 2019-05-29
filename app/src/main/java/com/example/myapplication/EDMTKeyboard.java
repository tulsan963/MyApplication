package com.example.myapplication;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.media.AudioManager;
import android.os.IBinder;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.Toast;

import static android.view.KeyEvent.*;

public class EDMTKeyboard extends InputMethodService implements KeyboardView.OnKeyboardActionListener {

    private KeyboardView kv;
    private Keyboard keyboard;
    int count=0;
    private  boolean isCaps = false;
    private Intent intent;
    public InputConnection myconnection,ic;
    String Receive="";

    @Override
    public void onStart(Intent intent, int startid) {
        //Receive = intent.getStringExtra("Command");
        //Toast.makeText(this, "Service Started "+Receive, Toast.LENGTH_SHORT).show();

        ic = myconnection;
        Receive = intent.getStringExtra("Command");
        if(Receive.equals("SHOOT")){
            Toast.makeText(this, "Service Started "+Receive, Toast.LENGTH_SHORT).show();
            for (int i=0;i<25;i++) {
                ic.sendKeyEvent(new KeyEvent(0, 1, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BUTTON_B, 0, KeyEvent.META_SYM_ON, 0, 0, KeyEvent.FLAG_VIRTUAL_HARD_KEY));
                ic.sendKeyEvent(new KeyEvent(0, 1, KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BUTTON_B, 0, KeyEvent.META_SYM_ON, 0, 0, KeyEvent.FLAG_VIRTUAL_HARD_KEY));
            }
        }
        if (Receive.equals("SWITCH")) {
            Toast.makeText(this, "Service Started "+Receive, Toast.LENGTH_SHORT).show();
            for (int i=0;i<25;i++){
                ic.sendKeyEvent( new KeyEvent(0,1,KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BUTTON_X,0, KeyEvent.META_SYM_ON, 0, 0, KeyEvent.FLAG_VIRTUAL_HARD_KEY));
                ic.sendKeyEvent( new KeyEvent(0,1,KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BUTTON_X,0, KeyEvent.META_SYM_ON, 0, 0, KeyEvent.FLAG_VIRTUAL_HARD_KEY));

            }
        }
        if (Receive.equals("Forward")) {
            Toast.makeText(this, "Service Started "+Receive, Toast.LENGTH_SHORT).show();
            for (int i=0;i<300;i++){
                ic.sendKeyEvent( new KeyEvent(0,1,KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_UP,0, KeyEvent.META_SYM_ON, 0, 0, KeyEvent.FLAG_VIRTUAL_HARD_KEY));

            }
            ic.sendKeyEvent( new KeyEvent(0,1,KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DPAD_UP,0, KeyEvent.META_SYM_ON, 0, 0, KeyEvent.FLAG_VIRTUAL_HARD_KEY));


        }
        if (Receive.equals("Backward")) {
            Toast.makeText(this, "Service Started "+Receive, Toast.LENGTH_SHORT).show();
            for (int i=0;i<300;i++){
                ic.sendKeyEvent( new KeyEvent(0,1,KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_DOWN,0, KeyEvent.META_SYM_ON, 0, 0, KeyEvent.FLAG_VIRTUAL_HARD_KEY));

            }
            ic.sendKeyEvent( new KeyEvent(0,1,KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DPAD_DOWN,0, KeyEvent.META_SYM_ON, 0, 0, KeyEvent.FLAG_VIRTUAL_HARD_KEY));


        }
        if (Receive.equals("Left")) {
            Toast.makeText(this, "Service Started "+Receive, Toast.LENGTH_SHORT).show();
            for (int i=0;i<300;i++){
                ic.sendKeyEvent( new KeyEvent(0,1,KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_LEFT,0, KeyEvent.META_SYM_ON, 0, 0, KeyEvent.FLAG_VIRTUAL_HARD_KEY));

            }
            ic.sendKeyEvent( new KeyEvent(0,1,KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DPAD_LEFT,0, KeyEvent.META_SYM_ON, 0, 0, KeyEvent.FLAG_VIRTUAL_HARD_KEY));


        }
        if (Receive.equals("Right")) {
            Toast.makeText(this, "Service Started "+Receive, Toast.LENGTH_SHORT).show();
            for (int i=0;i<300;i++){
                ic.sendKeyEvent( new KeyEvent(0,1,KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_RIGHT,0, KeyEvent.META_SYM_ON, 0, 0, KeyEvent.FLAG_VIRTUAL_HARD_KEY));

            }
            ic.sendKeyEvent( new KeyEvent(0,1,KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DPAD_RIGHT,0, KeyEvent.META_SYM_ON, 0, 0, KeyEvent.FLAG_VIRTUAL_HARD_KEY));


        }

    }

    public void onBindInput (){
        myconnection =getCurrentInputConnection();
        Toast.makeText(this, " "+myconnection, Toast.LENGTH_LONG).show();


    }
    @Override
    public View onCreateInputView() {
        kv = (KeyboardView)getLayoutInflater().inflate(R.layout.keyboard,null);
        keyboard = new Keyboard(this,R.xml.qwert);
        kv.setKeyboard(keyboard);
        kv.setOnKeyboardActionListener(this);
        //Toast.makeText(this, "Service Started onCreateInputView", Toast.LENGTH_LONG).show();


        return kv;
    }


    @Override
    public void onPress(int i) {
        //Toast.makeText(this, "Service Started onPress", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRelease(int i) {

    }

    @Override
    public void onKey(int i, int[] ints) {

        InputConnection ic = getCurrentInputConnection();
        myconnection=ic;
        playClick(i);
        switch (i)
        {
            case Keyboard.KEYCODE_DELETE:
                ic.deleteSurroundingText(1,0);
                break;
            case Keyboard.KEYCODE_SHIFT:
                isCaps = !isCaps;
                keyboard.setShifted(isCaps);
                kv.invalidateAllKeys();
                break;
            case Keyboard.KEYCODE_DONE:
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN,KeyEvent.KEYCODE_ENTER));
                break;
            default:
                char code = (char)i;
                if(Character.isLetter(code) && isCaps)
                    code = Character.toUpperCase(code);
                ic.commitText(String.valueOf(code),1);
        }

    }

    private void playClick(int i) {

        AudioManager am = (AudioManager)getSystemService(AUDIO_SERVICE);
        switch(i)
        {
            case 32:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
                break;
            case Keyboard.KEYCODE_DONE:
            case 10:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_RETURN);
                break;
            case Keyboard.KEYCODE_DELETE:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_DELETE);
                break;
            default: am.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
        }
    }

    @Override
    public void onText(CharSequence charSequence) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }
}
