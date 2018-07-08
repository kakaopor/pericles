package Main;

import input.KeyboardInput;
import input.MouseInput;

import org.lwjgl.glfw.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*; // NULL

public class Pericles implements Runnable {

    private Thread thread;
    public boolean running = false;

    public long window;

    private GLFWKeyCallback keyCallback;
    private GLFWCursorPosCallback cursorCallback;

    public static void main(String args[] ) {
        Pericles game = new Pericles();
        game.start();
    }

    public void start() {
    running = true;
    thread = new Thread(this, "Pericles");
       thread.start();
    }

    public void init() {
        if(glfwInit() != true) {
            System.err.println("GLFW initalization failed.");
        }

        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);

        window = glfwCreateWindow(1280, 768, "Pericles", NULL, NULL);

        if(window == NULL) {
            System.err.println("Could not create our Window!");
        }

        glfwSetKeyCallback(window, keyCallback = new KeyboardInput() );
        glfwSetCursorPosCallback(window, cursorCallback = new MouseInput() );

        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor() );
        glfwSetWindowPos(window, 0, 0);

        glfwMakeContextCurrent(window);

        glfwShowWindow(window);
    }

    public void update() {
        glfwPollEvents();

        if(KeyboardInput.isKeyDown(GLFW_KEY_SPACE) ) {
            System.out.println("SPACE");
        }

    }

    public void render() {
        glfwSwapBuffers(window);
    }

    // game loop
    @Override
    public void run() {

        init();

        //float deltaTime;
        //float accumulator = 0f;
        //float interval 1f / targetUPS;
        //float alpha;

        final double timeStep = 0.0166;
        long previousTime = System.nanoTime();
        double accumulatedTime = 0;

        while(running) {
            //deltaTime = timer.getDelta();

            long currentTime = System.nanoTime();
            double deltaTime = (currentTime - previousTime) / 1_000_000_000.0;
            accumulatedTime += deltaTime;

            //update();

            while (accumulatedTime > timeStep) {
                update(); //should be: update(timeStep);
            }

            render();

            previousTime = currentTime;

            if(glfwWindowShouldClose(window) == true) {
                running = false;
            }

        }
    }

}