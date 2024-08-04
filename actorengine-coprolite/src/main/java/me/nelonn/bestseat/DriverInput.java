package me.nelonn.bestseat;

import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class DriverInput {
    public static final DriverInput ZERO = new DriverInput(Direction.ZERO, Direction.ZERO, false, false);

    private final Direction forward;
    private final Direction side;
    private final boolean up;
    private final boolean down;

    public DriverInput(@NotNull DriverInput.Direction forward, @NotNull DriverInput.Direction side, boolean up, boolean down) {
        this.forward = forward;
        this.side = side;
        this.up = up;
        this.down = down;
    }

    public @NotNull DriverInput.Direction getForward() {
        return forward;
    }

    public boolean isForward() {
        return getForward() == Direction.POSITIVE;
    }

    public boolean isBackward() {
        return getForward() == Direction.NEGATIVE;
    }

    public @NotNull DriverInput.Direction getSide() {
        return side;
    }

    public boolean isLeft() {
        return getSide() == Direction.NEGATIVE;
    }

    public boolean isRight() {
        return getSide() == Direction.POSITIVE;
    }

    public boolean isUp() {
        return up;
    }

    public boolean isDown() {
        return down;
    }

    @Override
    public String toString() {
        return "DriverInput{" +
                "forward=" + forward +
                ", side=" + side +
                ", up=" + up +
                ", down=" + down +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DriverInput that)) return false;
        return isUp() == that.isUp() && isDown() == that.isDown() && isForward() == that.isForward() && getSide() == that.getSide();
    }

    @Override
    public int hashCode() {
        return Objects.hash(isForward(), getSide(), isUp(), isDown());
    }

    @Contract("null, _ -> null; !null, _ -> !null")
    public static DriverInput obtain(LivingEntity livingEntity, boolean down) {
        if (livingEntity == null) return null;
        DriverInput.Direction forward = -livingEntity.zza > 0 ? DriverInput.Direction.NEGATIVE :
                -livingEntity.zza < 0 ? DriverInput.Direction.POSITIVE :
                        DriverInput.Direction.ZERO;
        DriverInput.Direction side = livingEntity.xxa > 0 ? DriverInput.Direction.NEGATIVE :
                livingEntity.xxa < 0 ? DriverInput.Direction.POSITIVE :
                        DriverInput.Direction.ZERO;
        return new DriverInput(forward, side, livingEntity.jumping, down);
    }

    public enum Direction {
        NEGATIVE(-1),
        ZERO(0),
        POSITIVE(1);

        private final int number;

        Direction(int number) {
            this.number = number;
        }

        public int number() {
            return number;
        }

        public boolean isZero() {
            return this == ZERO;
        }
    }
}
