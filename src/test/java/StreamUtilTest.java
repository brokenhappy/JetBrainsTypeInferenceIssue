import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class StreamUtilTest {
    @Test
    public void testIfThereAreNoElements_theMaximumTwoAreEmpty() {
        final var emptyResult = Stream.<Integer>of().collect(StreamUtil.twoMax());
        assertTrue(emptyResult.getMax().isEmpty());
        assertTrue(emptyResult.getSecondMax().isEmpty());
    }

    @Test
    public void testIfItContainsOneValue_secondMaxIsEmpty() {
        final var emptyResult = Stream.of(1).collect(StreamUtil.twoMax());
        assertEquals(emptyResult.getMax().orElseThrow(), 1);
        assertTrue(emptyResult.getSecondMax().isEmpty());
    }

    @Test
    public void testIfTheMaxOccursTwice_theyAreBothGiven() {
        final var emptyResult = Stream.of(9, 3, 1, 2, 4, 7, 9, 0).collect(StreamUtil.twoMax());
        assertEquals(emptyResult.getMax().orElseThrow(), 9);
        assertEquals(emptyResult.getSecondMax().orElseThrow(), 9);
    }

    @Test
    public void testTowMaxParallelExecutionWhereItIsMadeSureThatTwoDifferentMaximumsAreCombinedToAMaxAndSecondMax() {
        final var emptyResult = Stream.of(
            1, 2, 8, 4, 6, 1, 5, 3, 15, 7, 9, 3, 4,
            1, 3, 7, 9, 13, 10, 1, 19, 10, 11, 10
        ).parallel().collect(StreamUtil.twoMax());
        assertEquals(emptyResult.getMax().orElseThrow(), 19);
        assertEquals(emptyResult.getSecondMax().orElseThrow(), 15);
    }}