package entities;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;

@FilterDefs({
                @FilterDef(name = "timeFilter",
                    parameters = {
                        @ParamDef(name = "minParam", type = "java.sql.Timestamp"),
                        @ParamDef(name = "maxParam", type = "java.sql.Timestamp")
                }),
                @FilterDef(name = "numberFilter", parameters =
                    @ParamDef(name = "numberParam", type = "java.lang.Long"))
        })

@Filters({
        @Filter(name = "timeFilter", condition = "transaction_time between :minParam and :maxParam"),
        @Filter(name = "numberFilter", condition = "number = :numberParam")
})


@Entity
@Table(name = "transactions")
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "sum")
    private Double sum;

    @JoinTable(name = "number")
    @JoinColumn(name = "number", referencedColumnName = "number_id")
    @Column(name = "number")
    Long number;

    public Long getId() {
        return id;
    }

    public Long getNumber() {
        return number;
    }

    @Column(name = "transaction_time")
    private Timestamp time;

    public Transactions(Long id, Long number, String type, Double sum, Timestamp time) {
        this.id = id;
        this.number = number;
        this.type = type;
        this.sum = sum;
        this.time = time;
    }

    public Transactions() {}

    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj.getClass() != this.getClass()) { return false; }
        final Transactions other = (Transactions) obj;
        return (this.id.equals(other.id)) &&
                (this.type.equals(other.type)) &&
                (this.sum.equals(other.sum)) &&
                (this.number.equals(other.number)) &&
                (this.time.equals(other.time));
    }

    public String getType() {
        return type;
    }

    public Double getSum() {
        return sum;
    }

    public Timestamp getTime() {
        return time;
    }

}
