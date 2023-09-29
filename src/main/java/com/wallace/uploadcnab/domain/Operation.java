package com.wallace.uploadcnab.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.UUID;

@Entity
@Getter @Setter
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "operations")
public class Operation implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_type_operation")
    private TypeOperation type;

    @Column(name = "date", nullable = false)
    private Timestamp date;

    @Column(name = "value", nullable = false)
    private String value;
    @Column(name = "document", nullable = false)
    private String document;
    @Column(name = "cardNumber", nullable = false)
    private String cardNumber;
    @Column(name = "name_store", nullable = false)
    private String nameStore;
    @Column(name = "store_owner", nullable = false)
    private String storeOwner;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "name_store_stock", referencedColumnName = "name_store")
    private Stock stock;

    public Operation(String line) throws ParseException {
        String type = line.substring(0, 1);
        this.type = new TypeOperation(type);
        String date = line.substring(1, 9);
        this.value = line.substring(9, 19);
        this.document = line.substring(19, 30);
        this.cardNumber = line.substring(30, 42);
        String hour = line.substring(42, 48);
        this.storeOwner = line.substring(48, 62);
        this.nameStore = line.substring(62, 80);

        String dataString = date + hour;

        this.date = createDateTimestamp(dataString);
    }

    private Timestamp createDateTimestamp(String dataString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        java.util.Date parsedDate = dateFormat.parse(dataString);

        return new Timestamp(parsedDate.getTime());
    }

    public Operation() {
        super();
    }

    public Operation(UUID id) {
        this.setId(id);
    }
}
