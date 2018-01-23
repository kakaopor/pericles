package hu.pericles.kakaopor.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

public class GameStateManager {

    private Stack<State> states;

    public GameStateManager() {
        states = new Stack<State>();
    }

    public void push(State state){
        states.push(state);
    }

    public void pop() {
        states.pop();
    }

    void set(State state) {
        states.pop();
        states.push(state);
    }

    public void update(float timeDelta) {
        states.peek().update(timeDelta);
    }

    public void render(SpriteBatch batch) {
        states.peek().render(batch);
    }
}
