package com.kivi.zedman.controller;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import static com.kivi.zedman.controller.PlayerController.Controls.*;

import java.util.ArrayList;

/**
 * Created by Kirill on 09.03.2016.
 */

public class PlayerController { //ready

    public static ArrayList<Boolean> controls;

    static {
        controls = new ArrayList<Boolean>();
        for (Controls cs : Controls.values()){
            controls.add(cs.getValue(),false);
        }
    }

    public static boolean isPressed(Controls cs){
        return controls.get(cs.getValue());
    }

    public static void setControl(Controls cs, boolean b){
        controls.set(cs.getValue(),b);
    }

    public void update (float deltaTime) {
        for (boolean b : controls){
            b = false;
        }

        if (Gdx.app.getType()== Application.ApplicationType.Android) {
            processTouches();
        }
        if (Gdx.app.getType()== Application.ApplicationType.Desktop) {
            processKeys();
        }
    }

    private void processKeys () {
        setControl(runLeft, Gdx.input.isKeyPressed(Input.Keys.LEFT));
        setControl(runRight, Gdx.input.isKeyPressed(Input.Keys.RIGHT));
        setControl(jump, Gdx.input.isKeyJustPressed(Input.Keys.UP));
        setControl(teleport, Gdx.input.isKeyJustPressed(Input.Keys.Y));
        setControl(fire, Gdx.input.isKeyPressed(Input.Keys.SPACE));
    }

    private void processTouches () {
        if (Gdx.input.isTouched(0)){
            float x;
            float y;
            for (int i=0;Gdx.input.isTouched(i);i++) {
                x=Gdx.input.getX(i);
                y=Gdx.input.getY(i);
                x=x/(float)Gdx.graphics.getWidth()*1280;
                y=720-y/(float)Gdx.graphics.getHeight()*720;
                setControl(runLeft, (x > 0 && x < 128 && y > 0 && y < 128));
                setControl(runRight, (x > 128 && x < 256 && y >0 && y < 128));
                setControl(jump, (x > 1280-128 && x < 1280 && y >0 && y < 128));
                setControl(teleport, (x > 1280-256 && x < 1280-128 && y >0 && y < 128));
            }
        }
    }


    public enum Controls {
        jump, runLeft, runRight, teleport, fire;
        static int size = 0;
        private int value;

        private Controls() {
            this.value = getSize();
            incSize();
        }

        private static void incSize(){
            size++;
        }

        public static int getSize(){
            return size;
        }

        public int getValue(){
            return value;
        }
    }
}
