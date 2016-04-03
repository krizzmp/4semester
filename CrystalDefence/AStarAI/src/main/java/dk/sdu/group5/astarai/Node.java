package dk.sdu.group5.astarai;

public class Node implements Comparable<Node> {
    final Vec item;
    final Node parent;
    final double g;
    final double h;
    final double f;

    Node(Vec item, Node parent, double g, double h) {
        this.item = item;
        this.parent = parent;
        this.g = g;
        this.h = h;
        f = g + h;
    }

    @Override
    public int compareTo(Node o) {
        return new Double(f).compareTo(o.f);
    }

    @Override
    public String toString() {
        return "Node{" +
                "item=" + item +
                ", f=" + f +
                ", parent=" + parent +
                '}';
    }
}
