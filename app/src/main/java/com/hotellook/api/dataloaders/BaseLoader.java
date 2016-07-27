package com.hotellook.api.dataloaders;

public class BaseLoader<T> {
    protected State state;

    protected enum State {
        IDLE,
        LOADING,
        LOADED,
        FAILED
    }

    public BaseLoader() {
        this.state = State.IDLE;
    }

    public boolean isInIdle() {
        return this.state == State.IDLE;
    }
}
