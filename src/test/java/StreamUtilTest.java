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
}