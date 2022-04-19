package entities;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;


@FilterDef(name = "nameFilter", parameters = @ParamDef(name = "nameParam", type = "java.lang.String"))
@Filter(name = "nameFilter", condition = "name like :nameParam")
@FilterDef(name = "typeFilter", parameters = @ParamDef(name = "typeParam", type = "java.lang.String"))
@Filter(name = "typeFilter", condition = "structure like '%:typeParam%'")

@Entity
@Table(name = "service")
public class Service {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "subscription_fee_month")
    private Double payPerMounth;

    @Column(name = "subscription_fee_day")
    private Double payPerDay;

    @Column(name = "connection_cost")
    private double startCost;

    @Column(name = "structure")
    private String structure;

    public Service(Long id, String name, double payPerMounth, double payPerDay,
                   double startCost, String structure) {
        this.id = id;
        this.name = name;
        this.payPerMounth = payPerMounth;
        this.payPerDay = payPerDay;
        this.startCost = startCost;
        this.structure = structure.replaceAll("\"", "")
                .replaceAll("\":", " :")
                .replaceAll("\\{", "")
                .replaceAll("}", "");
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj.getClass() != this.getClass()) { return false; }
        final Service other = (Service) obj;
        return (this.id == other.id) &&
                (this.name.equals(other.name)) &&
                (this.payPerMounth.equals(other.payPerMounth)) &&
                (this.payPerDay.equals(other.payPerDay)) &&
                (this.startCost == startCost) &&
                (this.structure.equals(other.structure));
    }

    public Service() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPayPerMounth() {
        return payPerMounth;
    }

    public void setPayPerMounth(double payPerMounth) {
        this.payPerMounth = payPerMounth;
    }

    public double getPayPerDay() {
        return payPerDay;
    }

    public void setPayPerDay(double payPerDay) {
        this.payPerDay = payPerDay;
    }

    public double getStartCost() {
        return startCost;
    }

    public void setStartCost(double startCost) {
        this.startCost = startCost;
    }

    public String getStructure() {
        return structure;
    }

    public void setStructure(String structure) {
        this.structure = structure;
    }

}

