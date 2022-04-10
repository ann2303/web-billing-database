package DAO;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TransactionsDAOTest {

    TransactionsDAOImpl transactionsDAOImpl = new TransactionsDAOImpl();

    @Test
    void getReportTest() {

        Timestamp start = Timestamp.valueOf("2020-05-10 12:00:00");
        Timestamp end = Timestamp.valueOf("2022-10-06 13:00:00");
        start.toLocalDateTime();
        end.toLocalDateTime();

        List<Map<Timestamp, Map<Double, String>>> report = transactionsDAOImpl
                .getReport(89373233449L,
                        start,
                        end);
        report.stream()
                .map(Map::keySet)
                .flatMap(Collection::stream)
                .forEach(key -> assertTrue(key.after(start) && key.before(end)));
    }

}