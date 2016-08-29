package org.toilelibre.libe.inexpressible.object;

public class Solution {

    public final char     op;
    public final int      remaining;
    public final Strategy s;

    public Solution (final Strategy s, final char op, final int remaining) {
        super ();
        this.s = s;
        this.op = op;
        this.remaining = remaining;
    }

    public char getOp () {
        return this.op;
    }

    public int getRemaining () {
        return this.remaining;
    }

    public Strategy getS () {
        return this.s;
    }
    
    @Override
    public String toString () {
        return this.op + " your number with " + this.s.getNode () + "\nCost :" + this.s.getCost () + "\nRemaining : " + this.remaining + "\n";
    }

}
