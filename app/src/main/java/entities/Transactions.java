package entities;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "Транзакции")
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @ManyToOne(targetEntity = Number.class)
    @JoinColumn(name = "number")
    @Column(name = "number")
    private long number;

    @Column(name = "type")
    private String type;

    @Column(name = "sum")
    private double sum;

    @Column(name = "transaction_time")
    private Time time;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}
