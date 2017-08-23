package io.github.kjens93.optionals;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author kjensen
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Op2ional<A, B> {

    private final A left;
    private final B right;

    public <T> Optional<T> map(@NonNull BiFunction<A, B, T> function) {
        return (left != null && right != null)
                ? Optional.ofNullable(function.apply(left, right))
                : Optional.empty();
    }

    public Op2ional<A, B> filter(@NonNull BiPredicate<A, B> predicate) {
        return (left != null && right != null)
                ? predicate.test(left, right)
                    ? Op2ional.ofNullable(left, right)
                    : Op2ional.empty()
                : Op2ional.empty();
    }

    public <T> Op2ional<T, B> mapLeft(@NonNull Function<A, T> function) {
        return Op2ional.ofNullable(left == null ? null : function.apply(left), right);
    }

    public <T> Op2ional<A, T> mapRight(@NonNull Function<B, T> function) {
        return Op2ional.ofNullable(left, right == null ? null : function.apply(right));
    }

    public Op2ional<A, B> filterLeft(@NonNull Predicate<A> function) {
        return Op2ional.ofNullable(left != null && function.test(left) ? left : null, right);
    }

    public Op2ional<A, B> filterRight(@NonNull Predicate<B> function) {
        return Op2ional.ofNullable(left, right != null && function.test(right) ? right : null);
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
