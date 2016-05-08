package com.kivi.zedman.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Affine2;

import static com.kivi.zedman.utils.Constants.*;

public class ScreenControlRendererAndroid implements ScreenControlRenderer{
	SpriteBatch batch;
	TextureRegion arrow;
	TextureRegion minus;
	TextureRegion plus;
	TextureRegion reload;

	public ScreenControlRendererAndroid() {
		batch = new SpriteBatch();
		batch.getProjectionMatrix().setToOrtho2D(0, 0,STANDARD_BATCH_WIDTH, STANDARD_BATCH_HEIGHT); //Создает batch а потом растягивает его до нужных размеров
		loadAssets();
	}

	private void loadAssets () {
		Texture texture = new Texture(Gdx.files.internal("data/controlsMobile.png"));
		TextureRegion[][] buttons = TextureRegion.split(texture, 128, 128);
		arrow = buttons[0][0];
		plus = buttons[0][1];
		minus = buttons[0][2];
		reload = buttons[0][3];
	}

	public void render () {
		batch.begin();
		batch.draw(arrow, 0, 0);
		//I don't understand how this rotate works!
		batch.draw(arrow, 2*arrow.getRegionWidth(),arrow.getRegionHeight(),0,0,arrow.getRegionWidth(),arrow.getRegionHeight(),1,1,180);
		batch.draw(arrow, STANDARD_BATCH_WIDTH - arrow.getRegionWidth(), arrow.getRegionHeight(), 0, 0,
				arrow.getRegionWidth(), arrow.getRegionHeight(), 1, 1, -90);
		batch.draw(plus,STANDARD_BATCH_WIDTH-plus.getRegionWidth(),STANDARD_BATCH_HEIGHT-plus.getRegionHeight());
		batch.draw(minus,STANDARD_BATCH_WIDTH-plus.getRegionWidth(),STANDARD_BATCH_HEIGHT-2*plus.getRegionHeight());
		batch.draw(arrow, STANDARD_BATCH_WIDTH-arrow.getRegionWidth(), STANDARD_BATCH_HEIGHT-3*plus.getRegionHeight(),0,0,
				arrow.getRegionWidth(),arrow.getRegionHeight(),0.5f,0.5f,0);
		batch.draw(arrow, STANDARD_BATCH_WIDTH, STANDARD_BATCH_HEIGHT-2.5f*plus.getRegionHeight(),0,0,
				arrow.getRegionWidth(),arrow.getRegionHeight(),(float)0.5,(float)0.5,180);
		batch.draw(arrow, STANDARD_BATCH_WIDTH-0.25f*arrow.getRegionWidth(), STANDARD_BATCH_HEIGHT-3.5f*plus.getRegionHeight(),0,0,
				arrow.getRegionWidth(),arrow.getRegionHeight(),(float)0.5,(float)0.5,90);
		batch.draw(arrow, STANDARD_BATCH_WIDTH-0.75f*arrow.getRegionWidth(), STANDARD_BATCH_HEIGHT-2f*plus.getRegionHeight(),0,0,
				arrow.getRegionWidth(),arrow.getRegionHeight(),(float)0.5,(float)0.5,-90);
		batch.draw(reload,STANDARD_BATCH_WIDTH - 2*reload.getRegionWidth(),0);
		batch.end();
	}

	public void dispose () {
		batch.dispose();
	}
}