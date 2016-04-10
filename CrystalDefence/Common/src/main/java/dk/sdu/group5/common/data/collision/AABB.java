package dk.sdu.group5.common.data.collision;

// Axis-aligned bounding box
public class AABB {

    private final float minX;
    private final float minY;
    private final float maxX;
    private final float maxY;

    public AABB(float minX, float minY, @SuppressWarnings("SameParameterValue") float maxX, @SuppressWarnings("SameParameterValue") float maxY) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    public float getMinX() {
        return minX;
    }

    public float getMinY() {
        return minY;
    }

    public float getMaxX() {
        return maxX;
    }

    public float getMaxY() {
        return maxY;
    }
}
