package sbt.edu.sharedcounter.combiningtree;

import sbt.edu.sharedcounter.SharedCounter;

import java.util.Stack;

public class CombiningTreeCounter implements SharedCounter {
    private Node[] leaf;
    //int threadID = -1;
    private int width = 0;
    private Node root;

    private int getThreadId() {

        int threadID = 0;
        String[] tokens = Thread.currentThread().getName().split("-");

        if (!tokens[tokens.length - 1].equals("main")) {
            threadID = Integer.parseInt(tokens[tokens.length - 1].trim());
        }
        return threadID;
    }

    public CombiningTreeCounter(int width) {
        this.width = width;
        Node[] nodes = new Node[width - 1];
        nodes[0] = new Node();
        root = nodes[0];
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = new Node(nodes[(i - 1) / 2]);  //stores tree into array
        }
        leaf = new Node[(width + 1) / 2];
        for (int i = 0; i < leaf.length; i++) {
            leaf[i] = nodes[nodes.length - i - 1];
        }
    }

    public int get() {
        return root.getResult();
    }

    public int getAndIncrement() throws Exception {

        Stack<Node> stack = new Stack<Node>();
        int threadID = getThreadId();
        Node myLeaf = leaf[(threadID) / 2];
        Node node = myLeaf;
        // precombining phase
        while (node.precombine()) {
            node = node.getParent();
        }
        Node stop = node;
        // combining phase
        node = myLeaf;
        int combined = 1;
        while (node != stop) {
            combined = node.combine(combined);
            stack.push(node);
            node = node.getParent();
        }
        // operation phase
        int prior = stop.op(combined);
        // distribution phase
        while (!stack.empty()) {
            node = stack.pop();
            node.distribute(prior);
        }
        return prior;
    }

    @Override
    public void reset() {
        Node[] nodes = new Node[width - 1];
        nodes[0] = new Node();
        root = nodes[0];
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = new Node(nodes[(i - 1) / 2]);  //stores tree into array
        }
        leaf = new Node[(width + 1) / 2];
        for (int i = 0; i < leaf.length; i++) {
            leaf[i] = nodes[nodes.length - i - 1];
        }
    }
}