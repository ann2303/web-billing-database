package entities;

import DAO.ClientDAO;
import DAO.ClientDAO;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.Period;

@FilterDefs({
        @FilterDef(name = "number_idFilter", parameters = @ParamDef(name = "number_idParam", type = "java.lang.Long")),
        @FilterDef(name = "nameFilter", parameters = @ParamDef(name = "nameParam", type = "java.lang.String"))
})

@Filter(name = "number_idFilter", condition = "number_id = :number_idParam")

@Entity
@Table(name = "number")
public class Number {

    public Long getId() {
        return id;
    }

    public Long getOwner() {
        return client_id;
    }

    public double getBalance() {
        return balance;
    }

    public double getMaxCredit() {
        return maxCredit;
    }

    @Id
    @Column(name = "number_id")
    private Long id;

    @JoinTable(name = "clients")
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    @FilterJoinTable(name = "nameFilter", condition = "fcn like :nameParam")
    private Long client_id;

    @Column(name = "balance")
    private double balance;

    @Column(name = "max_credit")
    private double maxCredit;

    public Number(Long id, Long clientId, double balance, double maxCredit) {
        this.id = id;
        this.client_id = clientId;
        this.balance = balance;
        this.maxCredit = maxCredit;
    }

    public Number() {}

}
