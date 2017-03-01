package perf.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Created by Eric Yu on 2017/2/24.
 */
class SimpleDataWriterCSVTest {

    SimpleDataWriterCSV csv;
    @BeforeEach
    void setUp() {
        csv = new SimpleDataWriterCSV("resources\\50.jtl");
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void getHeaders() {
        assertEquals(16,csv.getHeaders().size());
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