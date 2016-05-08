package com.kivi.zedman.utils;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

import static com.kivi.zedman.utils.Constants.MICRO_SIDE;
import static com.kivi.zedman.utils.Constants.MICRO_SIDE_BOX;
import static com.kivi.zedman.utils.Constants.PPM;

/**
 *  Supportive class for MapLoader. Why don't we make it inner class?
 */
public class TiledObjectUtil {

    public static void parseTiledObjectLayer(World world, MapObjects objects){


        for (MapObject object : objects) {
            Vector2 center = new Vector2();
            float width = 0;
            float height = 0;
            Shape shape;
            if (object instanceof PolylineMapObject) {
                shape = createPolyline((PolylineMapObject) object);
                Body body;
                BodyDef def = new BodyDef();
                def.type = BodyDef.BodyType.StaticBody;
                body = world.createBody(def);
                FixtureDef fixtureDef = new FixtureDef();
                fixtureDef.filter.categoryBits = Constants.FILTER_WALL;
                fixtureDef.filter.maskBits = Constants.FILTER_PLAYER;
                fixtureDef.shape = shape;
                fixtureDef.density = 1.0f;
                body.createFixture(fixtureDef);
                shape.dispose();
            } else {
                if (object instanceof EllipseMapObject){
                    CircleShape cs = new CircleShape();
                    cs.setRadius(((EllipseMapObject) object).getEllipse().height);
                    cs.setPosition(new Vector2(((EllipseMapObject) object).getEllipse().x,
                            ((EllipseMapObject) object).getEllipse().y));
                    shape = cs;
                    Body body;
                    BodyDef def = new BodyDef();
                    def.type = BodyDef.BodyType.StaticBody;
                    body = world.createBody(def);
                    FixtureDef fixtureDef = new FixtureDef();
                    fixtureDef.filter.categoryBits = Constants.FILTER_WALL;
                    fixtureDef.filter.maskBits = Constants.FILTER_PLAYER;
                    fixtureDef.shape = shape;
                    fixtureDef.density = 1.0f;
                    body.createFixture(fixtureDef);
                    shape.dispose();
                } else {
                    if (object instanceof RectangleMapObject){
                        ((RectangleMapObject) object).getRectangle().getCenter(center);
//                        center.x /= Constants.PPM;
//                        center.y /= Constants.PPM;
                        height = ((RectangleMapObject) object).getRectangle().getHeight()/2;
                        width = ((RectangleMapObject) object).getRectangle().getWidth()/2;
                        for (int x =(int) (center.x - width + MICRO_SIDE); x < (int)(center.x + width); x += MICRO_SIDE){
                            for(int y = (int)(center.y - height + MICRO_SIDE); y< (int)(center.y + height); y+=MICRO_SIDE ){
                                Body body;
                                PolygonShape ps = new PolygonShape();
                                BodyDef def = new BodyDef();
                                def.type = BodyDef.BodyType.StaticBody;
                                ps.setAsBox(MICRO_SIDE_BOX/2, MICRO_SIDE_BOX/2, new Vector2(x/PPM, y/PPM), 0);
                                body = world.createBody(def);
                                FixtureDef fixtureDef = new FixtureDef();
                                fixtureDef.filter.categoryBits = Constants.FILTER_WALL;
                                fixtureDef.filter.maskBits = Constants.FILTER_PLAYER;
                                fixtureDef.shape = ps;
                                fixtureDef.density = 1.0f;
                                body.createFixture(fixtureDef);
                                ps.dispose();
                            }
                        }
//                        ps.setAsBox(((RectangleMapObject) object).getRectangle().getWidth()/Constants.PPM/2 ,
//                                ((RectangleMapObject) object).getRectangle().getHeight()/Constants.PPM/2,
//                                center, 0);
//                        shape = ps;
                    } else {
                        continue;
                    }

                }
            }

        }
    }

    private static ChainShape createPolyline(PolylineMapObject polyline){
        float[] vertices = polyline.getPolyline().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length/2];

        for (int i = 0; i < worldVertices.length; i++){
            worldVertices[i] = new Vector2(vertices[2*i]/Constants.PPM, vertices[2*i+1]/Constants.PPM);
        }
        ChainShape cs = new ChainShape();
        cs.createChain(worldVertices);
        return cs;

    }
}
