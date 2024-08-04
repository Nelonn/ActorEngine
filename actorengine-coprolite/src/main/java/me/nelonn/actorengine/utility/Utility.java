package me.nelonn.actorengine.utility;

import org.jetbrains.annotations.Nullable;

public final class Utility {

    @SuppressWarnings("unchecked")
    public static <T> @Nullable T safeCast(@Nullable Object object) {
        try {
            return (T) object;
        } catch (ClassCastException e) {
            return null;
        }
    }

    private Utility() {
        throw new UnsupportedOperationException();
    }
}
