package com.kivi.zedman.controller;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;
import com.kivi.zedman.ZWorld;

/**
 * Created by Kirill on 06.03.2016.
 */
public class ZContactListener implements ContactListener {

    ZWorld world;

    public ZContactListener(ZWorld world) {
        this.world = world;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if (bullet_wall(fa, fb)){
            world.bodyToDelete = fb.getBody();
            world.bodyToChange = fa.getBody();
        }

        if(fa == null || fb == null) return;
        ZWorld.currentTime = TimeUtils.millis() - ZWorld.startTime;
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    private boolean bullet_wall(Fixture fa, Fixture fb){

        if(fa.getBody().getType() == BodyDef.BodyType.StaticBody && fb.getBody().isBullet()){
            return true;
        }
        return false;
    }
}
