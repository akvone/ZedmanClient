package com.kivi.zedman.utils;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;

/**
 * Class for loading .tmx maps and parsing their "Collision layer" as physical objects
 */
public class MapLoader implements Disposable {

    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private TiledMap map;

    String layerName = "Collision layer";

    public TiledMap getMap() {
        return map;
    }
    public OrthogonalTiledMapRenderer getTiledMapRenderer() {
        return tiledMapRenderer;
    }


    public MapLoader(String mapPath, World world){
        map = new TmxMapLoader().load(mapPath);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(map);
        TiledObjectUtil.parseTiledObjectLayer(world, map.getLayers().get(layerName).getObjects()); //
    }

    @Override
    public void dispose() {
        map.dispose();
        tiledMapRenderer.dispose();
    }
}
