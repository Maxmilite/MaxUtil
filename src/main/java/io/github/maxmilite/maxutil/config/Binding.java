package io.github.maxmilite.maxutil.config;

public class Binding {

    public int[] isPressed = new int[512];

    public int getPressed(int x) {
        return this.isPressed[x];
    }

    public void setPressed(int x, int action) {
        isPressed[x] = action;
    }

    public Binding() {

    }

}
