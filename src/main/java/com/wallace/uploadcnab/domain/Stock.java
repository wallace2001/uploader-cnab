package com.wallace.uploadcnab.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity
@Getter @Setter
@AllArgsConstructor
public class Stock implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_store")
    private String storeName;

    @Column(name = "owner_store")
    private String ownerStore;

    @Column(name = "document")
    private String document;

    @Column(name = "balance")
    private Double balance;

    @OneToMany(mappedBy = "stock")
    private List<Operation> operations;

    public Stock() {}

    public Stock(String storeName) {
        this.storeName = storeName;
        this.balance = 0.0;
    }
}
