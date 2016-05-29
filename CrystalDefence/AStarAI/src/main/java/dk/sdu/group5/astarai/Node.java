package dk.sdu.group5.astarai;

    /*
    * g is the cost of the path from the current node to the root node.
    * h is the heuristic function.
    * f is the estimated total cost of the path to the to the node towards the goal.
     */
class Node implements Comparable<Node> {
    private final Vec item;
    private final Node parent;
    private final double g;
    private final double f;

    Node(Vec item, Node parent, double g, double h) {
        this.item = item;
        this.parent = parent;
        this.g = g;
        f = g + h;
    }

    public Vec getItem() {
        return item;
    }

    public Node getParent() {
        return parent;
    }

    public double getG() {
        return g;
    }

    public double getF() {
        return f;
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
