import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import perf.data.SimpleDataCSV;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Eric Yu on 2017/2/24.
 */
class SimpleDataCSVTest {
    @Test
    void getHeaders() {
        assertEquals(16,csv.getHeaders().size());
    }

    @Test
    void getParser() {
        assertNotNull(csv.getParser());
    }

    SimpleDataCSV csv;
    @BeforeEach
    void setUp() {
        csv = new SimpleDataCSV("resources\\50.jtl");
    }

    @AfterEach
    void tearDown() {
        csv.close();
    }

    @Test
    void getRecords() {
        int size = csv.getRecords().size();
        assertEquals(9273, size);
    }

    @Test
    void getFileName() {
        assertEquals("resources\\50.jtl", csv.getFileName());
    }

}