package com.kivi.zedman.controller;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Created by Kirill on 18.03.2016.
 */
public class MapViewerController {
    private float xCoor;
    private float yCoor;
    private OrthographicCamera cam;

    boolean zoomIn=false;
    boolean zoomOut=false;
    boolean left=false;
    boolean right=false;
    boolean up=false;
    boolean down=false;

    public MapViewerController(OrthographicCamera cam){
        this.cam = cam;
    }

    public void update (float deltaTime) {
        process();
    }

    private void process(){
        zoomIn=false;
        zoomOut=false;
        left=false;
        right=false;
        up=false;
        down=false;

        if (Gdx.app.getType()== ApplicationType.Android) {
            processTouches();
        }
        if (Gdx.app.getType()== ApplicationType.Desktop) {
            processKeys();
        }

        if (zoomIn) {
            xCoor = cam.position.x;
            yCoor = cam.position.y;
            if (cam.viewportWidth * 0.95f > 0 && cam.viewportHeight * 0.95f > 0) {
                cam.setToOrtho(false, cam.viewportWidth * 0.95f, cam.viewportHeight * 0.95f);
            }
            cam.position.set(xCoor, yCoor, 0);
        }
        if (zoomOut) {
            xCoor = cam.position.x;
            yCoor = cam.position.y;
            cam.setToOrtho(false, cam.viewportWidth * 1.05f, cam.viewportHeight * 1.05f);
            cam.position.set(xCoor, yCoor, 0);
        }
        if (down) {
            cam.translate(0, -10);
        }
        if (up) {
            cam.translate(0, 10);
        }
        if (left) {
            cam.translate(-10, 0);
        }
        if (right) {
            cam.translate(10, 0);
        }
        cam.update();
    }

    private void processKeys () {
        up = Gdx.input.isKeyPressed(Input.Keys.W);
        down = Gdx.input.isKeyPressed(Input.Keys.S);
        left = Gdx.input.isKeyPressed(Input.Keys.A);
        right = Gdx.input.isKeyPressed(Input.Keys.D);
        zoomOut = Gdx.input.isKeyPressed(Input.Keys.NUM_9);
        zoomIn = Gdx.input.isKeyPressed(Input.Keys.NUM_0);
    }


    private void processTouches() {
        if (Gdx.input.isTouched(0)){
            float x;
            float y;
            for (int i=0;Gdx.input.isTouched(i);i++) {
                x=Gdx.input.getX(i);
                y=Gdx.input.getY(i);
                x=x/(float)Gdx.graphics.getWidth()*1280;
                y=720-y/(float)Gdx.graphics.getHeight()*720;
                zoomIn = (x > 1280 - 128 && x < 1280 && y > 720 - 128 && y < 720)||zoomIn;
                zoomOut = (x > 1280 - 128 && x < 1280 && y > 720 - 256 && y < 720 - 128)||zoomOut;
                left = (x > 1280 - 128 && x < 1280 - 64 && y > 720 - 256 - 128 && y < 720 - 256 - 64)||left;
                right = (x > 1280 - 64 && x < 1280 && y > 720 - 256 - 128 && y < 720 - 256 - 64)||right;
                up = (x > 1280 - 64 - 32 && x < 1280 - 32 && y > 720 - 256 - 64 && y < 720 - 256)||up;
                down = (x > 1280 - 64 - 32 && x < 1280 - 32 && y > 720 - 256 - 128 - 64 && y < 720 - 256 - 128)||down;
            }
        }
    }
}
