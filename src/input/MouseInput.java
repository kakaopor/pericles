package input;

import org.lwjgl.glfw.GLFWCursorPosCallback;

public class MouseInput extends GLFWCursorPosCallback {

    @Override
    public void invoke(long window, double xpos, double ypos) {
        System.out.println("X: " + xpos + "\nY: " + ypos);
    }

}
