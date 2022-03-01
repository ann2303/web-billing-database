package entities;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.FilterJoinTable;
import org.hibernate.annotations.ParamDef;

import javax.persistence.*;
import java.time.Period;

@FilterDef(name = "number_idFilter", parameters = @ParamDef(name = "number_idParam", type = "java.lang.String"))
@Filter(name = "number_idFilter", condition = "number_id = :number_idParam")
@FilterDef(name = "nameFilter", parameters = @ParamDef(name = "nameParam", type = "java.lang.String"))

@Entity
@Table(name = "number")
public class Number {
    @Id
    @Column(name = "number_id")
    private Long id;

    @JoinTable(name = "client")
    @JoinColumn(name = "id")
    @FilterJoinTable(name = "nameFilter", condition = "fcn like :nameParam")
    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "balance")
    private double balance;

    @Column(name = "max_credit")
    private double maxCredit;

    public Number(Long id, Long clientId, double balance, double maxCredit) {
        this.id = id;
        this.clientId = clientId;
        this.balance = balance;
        this.maxCredit = maxCredit;
    }

    public Number() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
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

}
