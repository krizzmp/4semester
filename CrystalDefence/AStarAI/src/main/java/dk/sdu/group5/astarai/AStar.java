package dk.sdu.group5.astarai;

import java.util.*;
import java.util.stream.Collectors;

public class AStar {

    /*
    * g is the cost of the path from the current node to the root node.
    * h is the heuristic function.
     */
    public static Node aStar(Vec startPos, Vec goalPos, List<LineSegment> lineSegments) {
        PriorityQueue<Node> openNodeList = new PriorityQueue<>();
        Node currentNode = new Node(startPos, null, 0, distance(startPos, goalPos));
        openNodeList.add(currentNode);
        Set<Node> closedNodeList = new HashSet<>();
        while (openNodeList.size() != 0) {
            currentNode = openNodeList.poll();
            List<Vec> successorsPoints = connections(currentNode, lineSegments);
            for (Vec successorPoint : successorsPoints) {
                double g = currentNode.getG() + distance(successorPoint, currentNode.getItem());
                double h = distance(successorPoint, goalPos);
                Node node = new Node(successorPoint, currentNode, g, h);
                if (node.getItem().equals(goalPos)) {
                    return node;
                }
                if (containsCheaper(openNodeList, node))
                    continue;
                if (containsCheaper(closedNodeList, node))
                    continue;
                openNodeList.add(node);
            }
            closedNodeList.add(currentNode);
        }
        return currentNode;
    }

    private static boolean containsCheaper(Collection<Node> nodes, Node node) {
        return nodes.stream().filter(n -> n.getItem().equals(node.getItem())).anyMatch(n -> n.getF() <= node.getF());
    }

    private static List<Vec> connections(Node node, List<LineSegment> lineSegments) {
        return lineSegments.stream().map(ls -> getConnectedNodePoint(node, ls)).filter(x -> x != null)
                .collect(Collectors.toList());
    }

    private static Vec getConnectedNodePoint(Node node, LineSegment lineSegment) {
        Vec point1 = lineSegment.getStartPos();
        Vec point2 = lineSegment.getStartPos().plus(lineSegment.getDirection());
        if (node.getItem().equals(point1)) {
            return point2;
        }
        if (node.getItem().equals(point2)) {
            return point1;
        }
        return null;
    }

    private static double distance(Vec startPos, Vec goalPos) {
        return goalPos.minus(startPos).length();
    }
}

