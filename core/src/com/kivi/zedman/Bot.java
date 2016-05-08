package com.kivi.zedman;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by 1 on 15.03.2016.
 */
public class Bot {

    public Vector2 positionFromServer;          //Во избежание рантайма
    public boolean updated = true;              //В update обновляются координаты, принятые с сервера

    private Body body;

    public Bot(Body body){
        this.body = body;
    }

    public Bot(ZWorld zWorld){
        this.body = zWorld.createStickman(128, 320, 32, 32);
    }

    public Bot(ZWorld zWorld, Vector2 position){
        this.body = zWorld.createStickman((int) position.x,(int) position.y, 32, 32);
    }

    public void update(float dt){

    }

    public Body getBody() {
        return body;
    }
}
