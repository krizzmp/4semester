package dk.sdu.group5.common.data.collision;

public class SquareCollider implements ICollider {

    private boolean trigger;
    private AABB bounds;

    public SquareCollider(boolean trigger, AABB bounds) {

        this.trigger = trigger;
        this.bounds = bounds;
    }

    @Override
    public AABB getBounds() {
        return bounds;
    }

    public boolean isTrigger() {
        return trigger;
    }
}
