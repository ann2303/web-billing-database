package entities;

import javax.persistence.*;
import java.time.Period;

@Entity
@Table(name = "number")
public class Number {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "number")
    private long id;

    @ManyToOne(targetEntity = Client.class)
    @JoinColumn(name = "id")
    @Column(name = "client_id")
    private long clientId;

    @Column(name = "balance")
    private double balance;

    @Column(name = "max_credit")
    private double maxCredit;

    @Column(name = "payment_period")
    private Period duePeriod;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getMaxCredit() {
        return maxCredit;
    }

    public void setMaxCredit(double maxCredit) {
        this.maxCredit = maxCredit;
    }

    public Period getDuePeriod() {
        return duePeriod;
    }

    public void setDuePeriod(Period duePeriod) {
        this.duePeriod = duePeriod;
    }
}
