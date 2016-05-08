package com.kivi.zedman;

import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.TimeUtils;
import com.kivi.zedman.controller.PlayerController;
import com.kivi.zedman.controller.ZContactListener;
import com.kivi.zedman.utils.MapLoader;
import com.kivi.zedman.utils.SocketUtil;

import static com.kivi.zedman.utils.Constants.FILTER_PLAYER;
import static com.kivi.zedman.utils.Constants.FILTER_WALL;
import static com.kivi.zedman.utils.Constants.PPM;

import static com.kivi.zedman.controller.PlayerController.*;
import static com.kivi.zedman.controller.PlayerController.Controls.*;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Kirill on 06.03.2016.
 */


public class ZWorld {

    PlayerController playerController;

    public Body bodyToDelete;
    public Body bodyToChange;
    public Bullet bulletToCreate;

    private Player player;
    private World world;
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private SocketUtil socket;

    public float pixelSize = 0.5f;
    public long timePastLastCreate = 0;

    public static long startTime;
    public static long currentTime = 0;

    public static HashMap<String, Bot> bots; //Хэш таблица для хранения ботов по ID (cм. в SocketUtil)

    public Player getPlayer() {
        return player;
    }

    public OrthogonalTiledMapRenderer getTiledMapRenderer() {
        return tiledMapRenderer;
    }



    public ZWorld() {

        bots = new HashMap<String, Bot>();

        world = new World(new Vector2(0, -10f), true);  //Arguments: gravity vector, and object's sleep boolean
        world.setContactListener(new ZContactListener(this));
        createWorld();

//        player = new Player(this ,createBox(64, 80 + 320, 32, 32, false));
        player = new Player(this ,createStickman(128, 320, 32, 32));
        startTime = TimeUtils.millis();
        playerController = new PlayerController();
        socket = new SocketUtil(this);
        socket.connectSocket();
        socket.configureSocketEvent();
    }

    public void setPP(float x, float y) {
    }

    private void createWorld() {
        MapLoader mapLoader = new MapLoader("maps/test.tmx", world);
        tiledMapRenderer = mapLoader.getTiledMapRenderer();
    }
    public Body createBox(int x, int y, int width, int height, boolean isStatic) {
        Body pBody;
        BodyDef def = new BodyDef();

        if(isStatic)
            def.type = BodyDef.BodyType.StaticBody;
        else
            def.type = BodyDef.BodyType.DynamicBody;

        def.position.set(x /PPM , y  / PPM);
        def.fixedRotation = true;
        pBody = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2 / PPM, height / 2 / PPM);

        pBody.createFixture(shape, 1.0f);
        shape.dispose();
        return pBody;
    }

    public Body createStickman(int x, int y, int width, int height) {
        Body stickman;
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(x/PPM,y/PPM);
        def.fixedRotation = true;
        stickman = world.createBody(def);

        CircleShape shape = new CircleShape();
//        shape.setAsBox(width/PPM /2, height/PPM/2);
        shape.setRadius(width/PPM/2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 2f;
        fixtureDef.friction = 1f;
        fixtureDef.filter.categoryBits = FILTER_PLAYER;
        fixtureDef.filter.maskBits = FILTER_WALL;

        stickman.createFixture(fixtureDef);
        shape.dispose();
        return stickman;
    }


    public void update(float delta) {
        playerActions();

        if (bulletToCreate != null){
            bulletToCreate.create(this);
            bulletToCreate = null;
        }
        if (bodyToDelete != null) {
            world.destroyBody(bodyToDelete);
            bodyToDelete = null;
        }
        if (bodyToChange != null){
            bodyToChange.setType(BodyType.DynamicBody);
            Filter filter = new Filter();
            filter.categoryBits = 0;
            filter.maskBits = 0;
            bodyToChange.getFixtureList().first().setFilterData(filter);
            bodyToChange = null;
        }
        playerController.update(delta);
    }

    public World getWorld() {
        return world;
    }

    public void dispose() {
        world.dispose();
    }

    public SocketUtil getSocket() {
        return socket;
    }

    public void playerActions(){
        int horizontalForce = 0;

        if(isPressed(jump)) {
            player.getBody().applyForceToCenter(0, 300, false);
        }
        if (isPressed(runLeft)) {
            horizontalForce -= 1;
        }
        if (isPressed(runRight)){
            horizontalForce += 1;
        }
        player.getBody().setLinearVelocity(horizontalForce * 5, player.getBody().getLinearVelocity().y);
        if (isPressed(teleport)){
            player.getBody().setTransform(10,10,0);
        }
        if(isPressed(fire)){
            player.createBullet();
        }
    } //updated
}
