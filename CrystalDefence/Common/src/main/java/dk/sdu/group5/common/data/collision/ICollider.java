package dk.sdu.group5.common.data.collision;

public interface ICollider {
    AABB getBounds();

    boolean notTrigger();
}
