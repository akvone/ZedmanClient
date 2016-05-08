package com.kivi.zedman;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import java.util.Random;

/**
 * Created by 1 on 31.03.2016.
 */
public class Bullet {

    float x;
    float y;
    float vx;
    float vy;

    Vector2 position;
    Vector2 direction;

    public Bullet(Vector2 pos, Vector2 dir){
//        this.x = x;
//        this.y = y;
//        this.vx = vx;
//        this.vy = vy;
        position = pos;
        direction = dir;
    }

    public Bullet(float x, float y, float vx, float vy){
//        this.x = x;
//        this.y = y;
//        this.vx = vx;
//        this.vy = vy;
        position = new Vector2(x,y);
        direction = new Vector2(vx, vy);
    }

    public Body create(ZWorld zWorld){
        Body bullet;
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(position);
        def.bullet = true;
        def.fixedRotation = true;

        bullet = zWorld.getWorld().createBody(def);

        CircleShape shape = new CircleShape();
        shape.setRadius(1 / 20f);

        FixtureDef fixture = new FixtureDef();
        fixture.shape = shape;

        bullet.createFixture(fixture);
        bullet.applyLinearImpulse(direction, bullet.getWorldCenter(), true);
        return bullet;
    }
}
