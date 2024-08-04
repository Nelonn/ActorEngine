package me.nelonn.bestseat;

public class DismountState {
    private boolean preventDismount = false;
    private boolean lastDismount = false;

    public boolean isPreventDismount() {
        return preventDismount;
    }

    public void setPreventDismount(boolean preventDismount) {
        this.preventDismount = preventDismount;
    }

    public boolean isLastDismount() {
        return lastDismount;
    }

    public void setLastDismount(boolean lastDismount) {
        this.lastDismount = lastDismount;
    }
}
