package dk.sdu.group5.common.data.collision;

public class SquareCollider implements ICollider {

    private final boolean trigger;
    private final AABB bounds;

    public SquareCollider(boolean trigger, AABB bounds) {

        this.trigger = trigger;
        this.bounds = bounds;
    }

    @Override
    public AABB getBounds() {
        return bounds;
    }

    @Override
    public boolean notTrigger() {
        return !trigger;
    }
}
