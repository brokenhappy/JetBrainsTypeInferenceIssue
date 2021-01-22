import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collector;

public class StreamUtil {
    public interface MaxTwo<R> {
        @NotNull Optional<R> getMax();
        @NotNull Optional<R> getSecondMax();
    }

    private static class MutableMaxTwo<R extends Comparable<R>> implements MaxTwo<R> {
        private @Nullable R max;
        private @Nullable R secondMax;

        @Override
        public @NotNull Optional<R> getMax() {
            return Optional.ofNullable(max);
        }

        @Override
        public @NotNull Optional<R> getSecondMax() {
            return Optional.ofNullable(secondMax);
        }

        void insert(R value) {
            if (max == null || value.compareTo(max) > 0) {
                secondMax = max;
                max = value;
            } else if (secondMax == null || value.compareTo(secondMax) > 0) {
                secondMax = value;
            }
        }

        MutableMaxTwo<R> combine(MutableMaxTwo<R> other) {
            if (other.max == null)
                return this;
            if (max == null)
                return other;
            final var withSmallestMax = max.compareTo(other.max) > 0 ? other : this;
            final var withHighestMax = withSmallestMax == this ? other : this;
            if (withHighestMax.secondMax == null) {
                withHighestMax.secondMax = withSmallestMax.max;
                return withHighestMax;
            }
            if (withSmallestMax.max.compareTo(withHighestMax.secondMax) > 0)
                withHighestMax.secondMax = withSmallestMax.max;
            return withHighestMax;
        }
    }

    public static @NotNull <R extends Comparable<R>> Collector<R, ?, ? extends MaxTwo<R>> twoMax() {
        return Collector.<R, MutableMaxTwo<R>>of(
            MutableMaxTwo::new,
            MutableMaxTwo::insert,
            MutableMaxTwo::combine
        );
    }
}