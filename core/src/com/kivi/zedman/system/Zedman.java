package com.kivi.zedman.system;

import com.badlogic.gdx.Game;
import com.esotericsoftware.minlog.Log;
import com.kivi.zedman.network.ZedmanClient;
import com.kivi.zedman.screens.GameScreen;

/**
 * Created by Kirill on 04.03.2016.
 */

public class Zedman extends Game {
	boolean serverActive = false;
	ZedmanClient zedmanClient;

	@Override
	public void create () {
		setScreen(new GameScreen(this));

		serverActive = true; //this must be changed in application, but now it's hardcode!
		zedmanClient = new ZedmanClient();
		Log.set(Log.LEVEL_DEBUG); //Only for debug messages!
	}

	@Override
	public void render() {
		super.render();

		if (serverActive){
			zedmanClient.update();
		}

	}
}
