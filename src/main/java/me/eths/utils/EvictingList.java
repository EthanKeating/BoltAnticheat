package me.eths.utils;

import java.util.LinkedList;

public class EvictingList<E> extends LinkedList<E> {
    private final int limit;

    public EvictingList(int limit) {
        this.limit = limit;
    }

    @Override
    public boolean add(E o) {
        boolean value = super.add(o);

        while (size() > limit) {
            super.remove();
        }

        return value;
    }

    public int limit() {
        return limit;
    }

    public E getOldest() {
        return get(0);
    }

    public E getLastest() {
        return get(size() - 1);
    }
}
