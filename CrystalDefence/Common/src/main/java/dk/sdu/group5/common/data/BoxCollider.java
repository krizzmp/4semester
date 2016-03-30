package dk.sdu.group5.common.data;

public class BoxCollider {

    private boolean trigger;
    private AABB AABB;

    public BoxCollider(boolean trigger, AABB AABB) {

        this.trigger = trigger;
        this.AABB = AABB;
    }

    public boolean isTrigger() {
        return trigger;
    }

    public AABB getAABB() {
        return AABB;
    }
}
