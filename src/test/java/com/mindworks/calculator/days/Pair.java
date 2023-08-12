package com.mindworks.calculator.days;

import java.util.Objects;

record Pair<A, B>(A fst, B snd) {

    public String toString() {
        return "Pair[" + fst + "," + snd + "]";
    }

    public boolean equals(Object other) {
        return other instanceof Pair<?, ?> pair &&
                Objects.equals(fst, pair.fst) &&
                Objects.equals(snd, pair.snd);
    }
}
