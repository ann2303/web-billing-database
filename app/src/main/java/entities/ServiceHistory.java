package entities;

import javax.persistence.*;

public class ServiceHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @OneToMany(mappedBy = "Service")
    private Long id;


}
