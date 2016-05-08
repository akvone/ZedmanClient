
package com.kivi.zedman.desktop;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.kivi.zedman.system.Zedman;

public class DesktopLauncher {
	public static void main (String[] argv) {
		//Entry point in desktop application
		new LwjglApplication(new Zedman(), "Zedman", 1280, 720); //Arguments: game, game name, width in pixels, height in pixels

		// After creating the Application instance we can set the log level to
		// show the output of calls to Gdx.app.debug
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
	}
}
