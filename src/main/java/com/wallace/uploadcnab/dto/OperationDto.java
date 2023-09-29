package com.wallace.uploadcnab.dto;

import com.wallace.uploadcnab.domain.Operation;
import com.wallace.uploadcnab.util.Utils;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter @Setter
public class OperationDto {

    private String id;
    private String cardNumber;
    private String typeOperation;
    private String typeNature;
    private String value;
    private String total;
    private String date;

    public OperationDto(Operation operation) {
        this.id = operation.getId().toString().substring(0, 5) + "...";
        this.setCardNumber(Utils.formatCardNumber(operation.getCardNumber()));
        this.setTypeOperation(operation.getType().getDescription());
        this.setTypeNature(operation.getType().getNature());
        this.setValue(Utils.formatValue(operation.getValue()));
        this.setDate(Utils.formatTimestamp(operation.getDate()));
    }
}
