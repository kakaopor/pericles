package hu.pericles.kakaopor.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import hu.pericles.kakaopor.Pericles;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 800;
		config.height = 600;
		config.fullscreen = false;
		config.title = "/iklész";
		new LwjglApplication(new Pericles(), config);
	}
}
