package dk.sdu.group5.astarai;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Collectors;

public class AStar {
    
    /*
    * g is the cost of the path from the current node to the root node.
    * h is the heuristic function.
     */
    public static Node aStar(Vec startPos, Vec goalPos, List<LineSegment> lineSegments) {        
        PriorityQueue<Node> fringe = new PriorityQueue<>();
        Node currentNode = new Node(startPos, null, 0, distance(startPos, goalPos));
        fringe.add(currentNode);
        Set<Node> closedNodeList = new HashSet<>();
        
        while (fringe.size() != 0) {
            currentNode = fringe.poll();
            
            if (currentNode.getItem().equals(goalPos)) {
                    return currentNode;
            }
            
            List<Vec> successorsPoints = expand(currentNode, lineSegments);
            
            for (Vec successorPoint : successorsPoints) {
                double g = currentNode.getG() + distance(successorPoint, currentNode.getItem());
                double h = distance(successorPoint, goalPos);
                Node node = new Node(successorPoint, currentNode, g, h);
                
                if (containsCheaper(fringe, node))
                    continue;
                if (containsCheaper(closedNodeList, node))
                    continue;
                    
                fringe.add(node);
            }
            closedNodeList.add(currentNode);
        }
        return currentNode;
    }

    private static boolean containsCheaper(Collection<Node> nodes, Node node) {
        return nodes.stream().filter(n -> n.getItem().equals(node.getItem()))
                .anyMatch(n -> n.getF() <= node.getF());
    }

    private static List<Vec> expand(Node node, List<LineSegment> lineSegments) {
        return lineSegments.stream().map(ls -> getConnectedNodePoint(node, ls))
                .filter(x -> x != null)
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

