package io.github.kjens93.optionals;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.function.*;

/**
 * @author kjensen
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Op2ional<A, B> {

    private final A left;
    private final B right;

    public <T> Optional<T> combine(@NonNull BiFunction<A, B, T> function) {
        return allPresent()
                ? Optional.ofNullable(function.apply(left, right))
                : Optional.empty();
    }

    public Op2ional<A, B> filter(@NonNull BiPredicate<A, B> predicate) {
        return allPresent()
                ? predicate.test(left, right)
                    ? ofNullable(left, right)
                    : empty()
                : empty();
    }

    public <T, V> Op2ional<T, V> map(@NonNull Function<A, T> leftFunction, @NonNull Function<B, V> rightFunction) {
        return ofNullable(leftFunction.apply(left), rightFunction.apply(right));
    }

    public <T> Op2ional<T, B> mapLeft(@NonNull Function<A, T> function) {
        T left = this.left == null ? null : function.apply(this.left);
        return ofNullable(left, right);
    }

    public <V> Op2ional<A, V> mapRight(@NonNull Function<B, V> function) {
        V right = this.right == null ? null : function.apply(this.right);
        return ofNullable(left, right);
    }

    public Op2ional<A, B> filterLeft(@NonNull Predicate<A> predicate) {
        A left = this.left == null ? null : predicate.test(this.left) ? this.left : null;
        return ofNullable(left, right);
    }

    public Op2ional<A, B> filterRight(@NonNull Predicate<B> predicate) {
        B right = this.right == null ? null : predicate.test(this.right) ? this.right : null;
        return ofNullable(left, right);
    }

    public boolean allPresent() {
        return left != null && right != null;
    }

    public void ifPresent(@NonNull BiConsumer<A, B> consumer) {
        if (allPresent()) {
            consumer.accept(left, right);
        }
    }

    public static <A, B> Op2ional<A, B> ofNullable(A a, B b) {
        return new Op2ional<>(a, b);
    }

    public static <A, B> Op2ional<A, B> of(@NonNull A a, @NonNull B b) {
        return new Op2ional<>(a, b);
    }

    public static <A, B> Op2ional<A, B> empty() {
        return new Op2ional<>(null, null);
    }

}
