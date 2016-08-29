package org.toilelibre.libe.inexpressible.find;

import java.util.Collections;
import java.util.List;
import java.util.Observer;

public class StubFind implements IFind {

    public List<String> find (final int digit, final int numTerms, final Observer... observer) {
        return Collections.emptyList ();
    }

}
