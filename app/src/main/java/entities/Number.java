package entities;

import javax.persistence.*;
import java.time.Period;

@Entity
@Table(name = "Номер")
public class Number {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "номер")
    private long id;

    @ManyToOne(targetEntity = Client.class)
    @JoinColumn(name = "id")
    @Column(name = "id клиента")
    private long clientId;

    @Column(name = "баланс")
    private double balance;

    @Column(name = "максимальный кредит")
    private double maxCredit;

    @Column(name = "срок погашения")
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
