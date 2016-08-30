package org.toilelibre.libe.inexpressible.object;

public class Node implements Cloneable {
    private Node   left;
    private String letter;
    private Node   right;

    public Node () {

    }

    public Node (final String letter1) {
        this.letter = letter1;
    }

    public void copyInto (final Node n) {
        n.setLetter (this.letter);
        if (this.left == null) {
            n.left = null;
        } else {
            n.left = new Node ();
            this.left.copyInto (n.left);
        }
        if (this.right == null) {
            n.right = null;
        } else {
            n.right = new Node ();
            this.right.copyInto (n.right);
        }
    }

    public int countDigits () {
        return (this.letter.matches ("[0-9]+") ? 1 : 0) + (this.left == null ? 0 : this.left.countDigits ()) + (this.right == null ? 0 : this.right.countDigits ());
    }

    public boolean foundADigitNotEqualsTo (final int digit) {
        return ((this.letter.matches ("-?[0-9]+") && !("" + digit).equals (this.letter)) || ((this.left != null) && this.left.foundADigitNotEqualsTo (digit)) || ((this.right != null) && this.right.foundADigitNotEqualsTo (digit)));
    }

    public final Node getLeft () {
        return this.left;
    }

    public final String getLetter () {
        return this.letter;
    }

    public final Node getRight () {
        return this.right;
    }

    public final void setLeft (final Node left1) {
        this.left = left1;
    }

    public final void setLetter (final String letter1) {
        this.letter = letter1;
    }

    public final void setRight (final Node right1) {
        this.right = right1;
    }

    @Override
    public String toString () {
        if (this.letter.matches ("-?[0-9]+")) {
            return this.letter;
        } else if ("+".equals (this.letter)) {
            return this.left + " " + this.letter + " " + this.right + " ";
        } else if ("-".equals (this.letter) && (this.left == null)) {
            return this.letter + " (" + this.right + ") ";
        } else {
            return "(" + this.left + ") " + this.letter + " (" + this.right + ") ";
        }
    }
}
