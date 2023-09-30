package com.wallace.uploadcnab.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "type_operation")
public class TypeOperation implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "type", fetch = FetchType.EAGER)
    private List<Operation> operations;
    private Long cod;
    private String description;
    private String nature;
    private String signal;

    public TypeOperation(String id) {
        this.id = Long.valueOf(id);
    }

    public TypeOperation() {
        super();
    }

    public TypeOperation(Long id) {
        this.setId(id);
    }
}
