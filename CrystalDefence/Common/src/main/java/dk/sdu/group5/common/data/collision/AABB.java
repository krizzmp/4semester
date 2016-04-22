package dk.sdu.group5.common.data.collision;

// Axis-aligned bounding box
public class AABB {

    private final float originX;
    private final float originY;
    private final float width;
    private final float height;

    public AABB(float originX, float originY, float width, float height) {
        this.originX = originX;
        this.originY = originY;
        this.width = width;
        this.height = height;
    }

    public float getOriginX() {
        return originX;
    }

    public float getOriginY() {
        return originY;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
