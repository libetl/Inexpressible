package org.toilelibre.libe.inexpressible.object;

public class Strategy {

    private final int  cost;
    private final int  delta;
    private final Node node;

    public Strategy (final int cost, final int delta, final Node node) {
        super ();
        this.cost = cost;
        this.delta = delta;
        this.node = node;
    }

    public Strategy (final Node node) {
        super ();
        this.cost = node.countDigits ();
        this.delta = (int) Expression.valueOfTree (node);
        this.node = node;
    }

    public Strategy (final String expr) {
        this (Expression.buildTree (expr));
    }

    public int getCost () {
        return this.cost;
    }

    public int getDelta () {
        return this.delta;
    }

    public Node getNode () {
        return this.node;
    }

}
