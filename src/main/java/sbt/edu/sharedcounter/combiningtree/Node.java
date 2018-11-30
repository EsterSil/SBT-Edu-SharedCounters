package sbt.edu.sharedcounter.combiningtree;

class Node {
    private volatile boolean locked = false;
    private volatile Status status;
    private volatile int firstValue, secondValue;
    private volatile int result;
    private final Node parent;

    Node() {
        status = Status.ROOT;
        parent = null;
    }

    Node(Node parent) {
        this.parent = parent;
        status = Status.IDLE;
    }


    synchronized boolean precombine() throws Exception {
        while (locked) {
            wait();
        }
        switch (status) {
            case IDLE:
                status = Status.FIRST;
                return true;
            case FIRST:
                locked = true;
                status = Status.SECOND;
                return false;
            case ROOT:
                return false;
            default:
                throw new Exception("unexpected Node state " + status + " on thread " + Thread.currentThread().getName());
        }
    }

    synchronized int combine(int combined) throws Exception {
        while (locked) {
            wait();
        }
        locked = true;
        firstValue = combined;
        switch (status) {
            case FIRST:
                return firstValue;
            case SECOND:
                return firstValue + secondValue;
            default:
                throw new Exception("unexpected Node state " + status);
        }
    }

    synchronized void distribute(int prior) throws Exception {
        switch (status) {
            case FIRST:
                status = Status.IDLE;
                locked = false;
                break;
            case SECOND:
                result = prior + firstValue;
                status = Status.RESULT;
                break;
            default:
                throw new Exception("unexpected Node state");
        }
        notifyAll();

    }

    synchronized int op(int combined) throws Exception {
        switch (status) {
            case ROOT:
                int prior = result;
                result += combined;
                return prior;
            case SECOND:
                secondValue = combined;
                locked = false;
                notifyAll(); // wake up waiting threads
                while (status != Status.RESULT) {
                    wait();
                }
                locked = false;
                notifyAll();
                status = Status.IDLE;
                return result;
            default:
                throw new Exception("unexpected Node state");
        }
    }

    Node getParent() {
        return parent;
    }

    int getResult() {
        return result;
    }
}