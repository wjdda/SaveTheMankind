package com.test.savethemankind.bin;

enum State {
    GAME_PAUSE,
    GAME_RUNNING,
    GAME_OVER
}

enum Choice {
    EXIT_IMMEDIATELY,
    EXIT,
    PAUSE,
    RESUME,
    CANCEL
}

public class GameState {

    private State state; // 0 - paused (or not started yet - the same), 1 - running, 2 - game over

    public void setState (State newState) {
            this.state = newState;
    }

    public State getState() {
        return this.state;
    }
}
