package org.toilelibre.libe.inexpressible.find;

import java.util.List;
import java.util.Observer;

public interface IFind {

    public List<String> find (final int digit, final int numTerms, final Observer... observer);
}
