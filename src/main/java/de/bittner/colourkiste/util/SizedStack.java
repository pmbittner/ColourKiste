package de.bittner.colourkiste.util;

import java.util.Stack;
import java.util.function.Predicate;

/**
 * TODO: Move to Functjonal
 * @param <T>
 */
public class SizedStack<T> extends Stack<T> {
    private final int capacity;
    private int size;
    private final Predicate<T> countTowardsCapacity;
    public SizedStack(int capacity) {
        this(capacity, t -> true);
    }

    public SizedStack(int capacity, final Predicate<T> countTowardsCapacity) {
        super();
        this.size = 0;
        this.capacity = capacity;
        this.countTowardsCapacity = countTowardsCapacity;
    }

    @Override
    public T push(T item) {
        if (countTowardsCapacity.test(item)) {
            ++size;

            if (size > capacity) {
                Assert.assertTrue(size == capacity + 1);

                // remove the deepest counted element and everything before it
                while (!countTowardsCapacity.test(get(0))) {
                    this.removeElementAt(0);
                }
                this.removeElementAt(0);

                --size;
            }
        }

        return super.push(item);
    }

    @Override
    public synchronized T pop() {
        final T popped = super.pop();

        if (countTowardsCapacity.test(popped)) {
            --size;
        }

        return popped;
    }

    @Override
    public void clear() {
        size = 0;
        super.clear();
    }

    public int getSize() {
        return size;
    }
}
