package com.kivi.zedman.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import static com.kivi.zedman.utils.Constants.*;

/**
 * Created by Kirill on 19.03.2016.
 */
public class ScreenControlRendererDesktop implements ScreenControlRenderer{
    SpriteBatch batch;

    public ScreenControlRendererDesktop() {
        batch = new SpriteBatch();
        batch.getProjectionMatrix().setToOrtho2D(0, 0, STANDARD_BATCH_WIDTH, STANDARD_BATCH_HEIGHT); //Создает batch а потом растягивает его до нужных размеров
        loadAssets();
    }

    private void loadAssets() {
    }

    public void render() {
    }

    public void dispose() {
        batch.dispose();
    }
}