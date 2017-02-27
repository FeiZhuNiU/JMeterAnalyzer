package perf.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Created by Eric Yu on 2017/2/24.
 */
class SimpleDataCSVTest {

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
    void getHeaders() {
        assertEquals(16,csv.getHeaders().size());
    }

    @Test
    void getParser() {
        assertNotNull(csv.getParser());
    }
    @Test
    void getRecords() {
        int size = csv.getRecords().size();
        assertEquals(9273, size);
    }

    @Test
    void getFileName() {
        assertEquals("50.jtl", csv.getFileName());
    }

}