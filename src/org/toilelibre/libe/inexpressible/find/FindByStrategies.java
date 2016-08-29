package org.toilelibre.libe.inexpressible.find;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observer;
import java.util.TreeMap;

import org.toilelibre.libe.inexpressible.object.Node;
import org.toilelibre.libe.inexpressible.object.Solution;
import org.toilelibre.libe.inexpressible.object.Strategy;

public class FindByStrategies implements IFind {

    public FindByStrategies () {
    }

    public List<String> find (final int digit, final int numTerms, final Observer... observers) {
        final List<Strategy> firstStrategies = this.strategies ("" + digit);
        boolean found = false;
        final List<String> equations = new ArrayList<String> ();
        int i = 0;
        while (!found) {
            final Node res = this.findAnExpressionFor (firstStrategies, new Node ("" + i), digit, numTerms);
            if (res.foundADigitNotEqualsToDigit (digit)) {
                found = true;
            } else {
                equations.add (thisExpression (i + " = " + res.toString (), observers));
                i++;
            }
        }
        return equations;
    }

    private Node findAnExpressionFor (final List<Strategy> strategies, final Node n, final int digit, final int maxCost) {
        final List<Solution> bestOnes = this.orderSolutions (strategies, Integer.parseInt (n.getLetter ()), maxCost);
        final Node result = new Node (n.getLetter ());
        final Iterator<Solution> itS = bestOnes.iterator ();
        boolean found = false;
        while (itS.hasNext () && !found) {
            final Solution solution = itS.next ();
            if ((solution.remaining == 0) && (((solution.s.getCost () - maxCost) == 0) && ((solution.op == '+') || (solution.op == '-')))) {
                Node result1 = result;
                if (solution.op == '-') {
                    result.setLetter ("-");
                    result1 = new Node ();
                    result.setRight (result1);
                }
                solution.s.getNode ().copyInto (result1);
                found = true;
            } else {
                result.setLetter ("" + solution.op);
                result.setRight (solution.s.getNode ());
                result.setLeft (this.findAnExpressionFor (strategies, new Node ("" + solution.remaining), digit, maxCost - solution.s.getCost ()));
                found |= ((result.countDigits () <= maxCost) && !result.foundADigitNotEqualsToDigit (digit) && (!("" + digit).equals (result.getLeft ().toString ()) || ((maxCost - solution.s.getCost ()) == 1)));
            }
        }
        return result;
    }

    public List<Strategy> strategies (final String n) {
        final List<Strategy> strategies = new ArrayList<Strategy> ();
        strategies.add (new Strategy (n.replaceAll ("n", n)));// N
        strategies.add (new Strategy ("n * n / n".replaceAll ("n", n)));// N
                                                                  // coût
                                                                  // 3
        strategies.add (new Strategy ("n / n".replaceAll ("n", n)));// 1
        strategies.add (new Strategy ("n - n".replaceAll ("n", n)));// 0
        strategies.add (new Strategy ("(n - n)  / n".replaceAll ("n", n)));// 0
        // coût
        // 3
        strategies.add (new Strategy ("n * n".replaceAll ("n", n)));// N^2
        strategies.add (new Strategy ("n + n".replaceAll ("n", n)));// 2*N
        strategies.add (new Strategy ("( n + n ) /  n".replaceAll ("n", n)));// 2
        strategies.add (new Strategy ("( n + n + n ) /  n".replaceAll ("n", n)));// 3
        strategies.add (new Strategy ("( n + n + n + n ) /  n".replaceAll ("n", n)));// 4
        strategies.add (new Strategy ("( n + n + n + n + n ) /  n".replaceAll ("n", n)));// 5
        strategies.add (new Strategy ("( n + n + n + n + n + n ) /  n".replaceAll ("n", n)));// 6
        strategies.add (new Strategy ("( n + n + n + n + n + n + n ) /  n".replaceAll ("n", n)));// 7
        strategies.add (new Strategy ("( n + n + n + n + n + n + n + n ) /  n".replaceAll ("n", n)));// 8
        strategies.add (new Strategy ("n - ( n - n )".replaceAll ("n", n)));// n
                                                                         // coût
                                                                         // 3
        strategies.add (new Strategy ("( n - ( n - n )) / n".replaceAll ("n", n)));// 1
                                                                                       // coût
                                                                                       // 4
        strategies.add (new Strategy ("n * ( n - n )".replaceAll ("n", n)));// 0
                                                                         // coût
                                                                         // 3
        strategies.add (new Strategy ("n * n * (n - n)".replaceAll ("n", n)));// 0
                                                                                     // coût
                                                                                     // 4
        return strategies;
    }

    private List<Solution> orderSolutions (final List<Strategy> strategies, final int num, final int maxCost) {
        final List<Solution> bestSolutions = new LinkedList<Solution> ();
        final Map<Integer, Solution> remaining = new TreeMap<Integer, Solution> ();
        for (final Strategy strategy : strategies) {
            if (strategy.getCost () <= maxCost) {
                final int sum = (num - strategy.getDelta ());
                final int dif = (num + strategy.getDelta ());
                final int mul = (strategy.getDelta () == 0 ? Integer.MAX_VALUE : (num / strategy.getDelta ()));
                final int div = (num * strategy.getDelta ());

                remaining.put (new Integer ((int) (Math.pow (sum, 2) + Math.pow (maxCost - strategy.getCost (), 2))), new Solution (strategy, '+', sum));
                remaining.put (new Integer ((int) (Math.pow (dif, 2) + Math.pow (maxCost - strategy.getCost (), 2))), new Solution (strategy, '-', dif));
                if ((strategy.getDelta () != 0) && ((num % strategy.getDelta ()) == 0)) {
                    remaining.put (new Integer ((int) (Math.pow (mul, 2) + Math.pow (maxCost - strategy.getCost (), 2))), new Solution (strategy, '*', mul));
                }
                if (strategy.getDelta () != 0) {
                    remaining.put (new Integer ((int) (Math.pow (div, 2) + Math.pow (maxCost - strategy.getCost (), 2))), new Solution (strategy, '/', div));
                }
            }
        }
        bestSolutions.addAll (remaining.values ());

        return bestSolutions;
    }

    private String thisExpression (String expression, Observer... observers) {
        for (Observer observer : observers) {
            observer.update (null, expression);
        }
        return expression;
    }
}