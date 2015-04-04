package tweetspeak.divisions;

import java.util.*;
import tweetspeak.objects.TokenNode;
import tweetspeak.collections.TreeTraversalOrder;

public class Tree {
    private TokenNode root;
    
    //constructors
    public Tree() { super(); }

    //getters
    public TokenNode getRoot() { return this.root; }

    //setters
    public void setRoot(TokenNode root) { this.root = root; }

    public int getNumberOfNodes() {
        int numberOfNodes = 0;
        if (root != null) { numberOfNodes = auxiliaryGetNumberOfNodes(root) + 1; } //1 for the root! 
        return numberOfNodes;
    }

    private int auxiliaryGetNumberOfNodes(TokenNode node) {
        int numberOfNodes = node.getNumberOfChildren();
        for (TokenNode child : node.getChildren()) numberOfNodes += auxiliaryGetNumberOfNodes(child);
        return numberOfNodes;
    }

    public boolean isEmpty() {
        return (root == null);
    }

    public List<TokenNode> build(TreeTraversalOrder traversalOrder) {
        List<TokenNode> returnList = null;
        if (root != null) { returnList = build(root, traversalOrder); }
        return returnList;
    }

    public List<TokenNode> build(TokenNode node, TreeTraversalOrder traversalOrder) {
        List<TokenNode> traversalResult = new ArrayList<TokenNode>();
        if (traversalOrder == TreeTraversalOrder.PRE_ORDER) { buildPreOrder(node, traversalResult); }
        else if (traversalOrder == TreeTraversalOrder.POST_ORDER) { buildPostOrder(node, traversalResult); }
        return traversalResult;
    }

    private void buildPreOrder(TokenNode node, List<TokenNode> traversalResult) {
        traversalResult.add(node);
        for (TokenNode child : node.getChildren()) buildPreOrder(child, traversalResult);
    }

    private void buildPostOrder(TokenNode node, List<TokenNode> traversalResult) {
        for (TokenNode child : node.getChildren()) buildPostOrder(child, traversalResult);
        traversalResult.add(node);
    }

    public Map<TokenNode, Integer> buildWithDepth(TreeTraversalOrder traversalOrder) {
        Map <TokenNode, Integer> returnMap = null;

        if (root != null) returnMap = buildWithDepth(root, traversalOrder);
        return returnMap;
    }

    public Map<TokenNode, Integer> buildWithDepth(TokenNode node, TreeTraversalOrder traversalOrder) {
        Map<TokenNode, Integer> traversalResult = new LinkedHashMap<TokenNode, Integer>();

        if (traversalOrder == TreeTraversalOrder.PRE_ORDER) buildPreOrderWithDepth(node, traversalResult, 0);
        else if(traversalOrder == TreeTraversalOrder.POST_ORDER) buildPostOrderWithDepth(node, traversalResult, 0);
        return traversalResult;
    }

    private void buildPreOrderWithDepth(TokenNode node, Map<TokenNode, Integer> traversalResult, int depth) {
        traversalResult.put(node, depth);
        for (TokenNode child : node.getChildren()) buildPreOrderWithDepth(child, traversalResult, depth + 1);
    }

    private void buildPostOrderWithDepth(TokenNode node, Map<TokenNode, Integer> traversalResult, int depth) {
        for (TokenNode child : node.getChildren()) buildPostOrderWithDepth(child, traversalResult, depth + 1);
        traversalResult.put(node, depth);
    }

    public String toString() {
        String stringRepresentation = "";

        if (root != null) stringRepresentation = build(TreeTraversalOrder.PRE_ORDER).toString();
        return stringRepresentation;
    }

    public String toStringWithDepth() {
        String stringRepresentation = "";

        if (root != null) stringRepresentation = buildWithDepth(TreeTraversalOrder.PRE_ORDER).toString();
        return stringRepresentation;
    }
}
