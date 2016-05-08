package com.kivi.zedman.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.kivi.zedman.ZWorld;

import java.util.ArrayList;

/**
 * Created by Kirill on 04.03.2016.
 */

//Need to rewrite
public class ScreenLogger {
    ZWorld zworld;

    private SpriteBatch batch;
    private BitmapFont font;

    public ScreenLogger(ZWorld zworld) {
        this.zworld=zworld;
        loadAssets();
    }

    private void loadAssets () {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.RED);
    }

    public void render () {
        ArrayList<Object> systemInformation = getInformation();
        batch.begin();
        for (int i = 0; i<systemInformation.size(); i++){
            font.draw(batch, systemInformation.get(i).toString(), 0, Gdx.graphics.getHeight()-20*i);
        }
        batch.end();
    }

    public ArrayList<Object> getInformation() {
        ArrayList<Object> systemInformation = new ArrayList<Object>();
        systemInformation.add("Vir W - "+ Gdx.graphics.getWidth()+
                " Vir H - "+ Gdx.graphics.getHeight()+
                " FPS - "+ Gdx.graphics.getFramesPerSecond());
        systemInformation.add("1 touchX - "+ Gdx.input.getX(0)+" touchY - "+ Gdx.input.getY(0));
        systemInformation.add("2 touchX - "+ Gdx.input.getX(1)+" touchY - "+ Gdx.input.getX(1));
        systemInformation.add("Objects in world - " + zworld.getWorld().getBodyCount());
        systemInformation.add("Time: " + ZWorld.currentTime);

        return systemInformation;
    }

    public void dispose () {
        batch.dispose();
    }
}