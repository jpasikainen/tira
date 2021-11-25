package com.jpasikainen.tira.util;

public class AlphaKey<A, K> {
    public final A alpha;
    public final K key;

    public AlphaKey(A a, K k) {
        this.alpha = a;
        this.key = k;
    }
}
