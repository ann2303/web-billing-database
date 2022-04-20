package DAO;

import com.google.common.collect.Lists;
import entities.Transactions;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TransactionsDAO implements DAO<Transactions, Long> {
    public List<Map<Timestamp, Map<Double, String>>> getReport(Long number,
                                                               Timestamp start,
                                                               Timestamp end) {

        List<Transactions> transactions =
                this.filter(Map.of("numberFilter", Lists.newArrayList(number)),
                        Transactions.class);

        return transactions.stream()
                .filter(tr -> tr.getTime().before(end) && tr.getTime().after(start))
                .map(tr -> Map.of(tr.getTime(), Map.of(tr.getSum(), tr.getType())))
                .collect(Collectors.toList());

    }
}
