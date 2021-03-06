package org.toilelibre.libe.inexpressible;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.toilelibre.libe.inexpressible.find.IFind;

public class App {

    public static class SysoutObserver implements Observer {
        @Override
        public void update (final Observable o, final Object arg) {
            System.out.println (arg);
        }
    }

    public static void main (final String [] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        if (args.length != 3) {
            System.out.println ("args : (FindByGeneratingEachPossibleExpression|FindByStrategies) digit exprlength");
            return;
        }

        final IFind finder = (IFind) Class.forName (IFind.class.getPackage ().getName () + "." + args [0]).newInstance ();

        final List<String> exprs = finder.find (Integer.parseInt (args [1]), Integer.parseInt (args [2]), new SysoutObserver ());
        System.out.println ("Result : " + (exprs.size ()));
    }
}
