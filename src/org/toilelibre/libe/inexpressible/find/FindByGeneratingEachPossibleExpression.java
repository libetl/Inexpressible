package org.toilelibre.libe.inexpressible.find;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observer;

import org.toilelibre.libe.inexpressible.object.Expression;
import org.toilelibre.libe.inexpressible.object.Node;
import org.toilelibre.libe.inexpressible.util.StringUtils;

public class FindByGeneratingEachPossibleExpression implements IFind {

    private final String ops = "+-*/";

    public List<String> find (final int digit, final int numTerms, final Observer... observers) {
        final Map<Integer, String> equations = new HashMap<Integer, String> ();

        final int loops = (int) Math.pow (this.ops.length (), numTerms);

        final String [] brackets = new String [numTerms];

        for (int i = 0; i < numTerms; i++) {
            brackets [i] = "";
        }

        for (int i = 0; i < loops; i++) {
            equations.putAll (this.loopWithBrackets (digit, numTerms, i, loops, 1, 0, brackets, observers));
        }
        return new ArrayList<String> (
                keepStrictlyIncreasingValues(equations));
    }

    private List<String> keepStrictlyIncreasingValues (Map<Integer, String> equations) {
        List<String> result = new ArrayList<String> ();
        
        int i = 0;
        while (equations.containsKey (i)) {
            result.add (equations.get (i));
            i++;
        }
        
        return result;
    }

    private Map<Integer, String> loopWithBrackets (final int digit, final int numTerms, final int i, final int loops, final int length, final int pos, final String [] brackets, final Observer [] observers) {
        if (length == numTerms) {
            return this.oneLoop (digit, numTerms, i, loops, brackets, observers);
        } else if ((pos + length) >= numTerms) {
            return this.loopWithBrackets (digit, numTerms, i, loops, length + 1, 0, brackets, observers);
        }
        final char op = this.takeOp (numTerms, i, pos, loops);
        final Map<Integer, String> listPart1 = this.loopWithBrackets (digit, numTerms, i, loops, length, pos + 1, brackets, observers);
        Map<Integer, String> listPart2 = Collections.emptyMap ();
        if ('+' != op) {
            brackets [pos] += "(";
            brackets [pos + length] += ")";
            listPart2 = this.loopWithBrackets (digit, numTerms, i, loops, length, pos + 1, brackets, observers);
            brackets [pos] = brackets [pos].replaceFirst ("\\(", "");
            brackets [pos + length] = brackets [pos + length].replaceFirst ("\\)", "");
        }
        return this.join (listPart1, listPart2);
    }

    private Map<Integer, String> oneLoop (final int digit, final int numTerms, final int i, final int loops, final String [] brackets, final Observer [] observers) {
        String myExpr = "";

        for (int k = 0; k < numTerms; k++) {
            final char op = this.takeOp (numTerms, i, k, loops);
            final int openingBrackets = StringUtils.countMatches (brackets [k], "(");
            final int closingBrackets = StringUtils.countMatches (brackets [k], ")");

            for (int l = 0; l < openingBrackets; l++) {
                myExpr += "(";
            }

            myExpr += digit;

            for (int l = 0; l < closingBrackets; l++) {
                myExpr += ")";
            }

            myExpr += " " + op + " ";
        }

        myExpr = myExpr.substring (0, myExpr.length () - 2);

        final Node n = Expression.buildTree (myExpr);
        final double val = Expression.valueOfTree (n);
        return Collections.singletonMap (Integer.valueOf ((int)val), thisExpression ((int) val + " = " + myExpr, observers));
    }

    private char takeOp (final int numTerms, final int i, final int k, final int loops) {
        final int divideBy = (int) Math.pow (this.ops.length (), numTerms - k - 1);
        final int which = i / divideBy;
        return this.ops.charAt (which % this.ops.length ());
    }

    private Map<Integer, String> join (final Map<Integer, String> listPart1, final Map<Integer, String> listPart2) {
        final Map<Integer, String> result = new HashMap<Integer, String> (listPart1.size () + listPart2.size ());
        result.putAll (listPart1);
        result.putAll (listPart2);
        return result;
    }

    private String thisExpression (String expression, Observer... observers) {
        for (Observer observer : observers) {
            observer.update (null, expression);
        }
        return expression;
    }

}
