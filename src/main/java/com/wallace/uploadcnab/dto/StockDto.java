package com.wallace.uploadcnab.dto;

import com.wallace.uploadcnab.domain.Stock;
import com.wallace.uploadcnab.util.Utils;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class StockDto {
    private Long id;
    private String balance;
    private String storeName;
    private String storeOwner;
    private String document;
    private List<OperationDto> operations;

    public StockDto(Stock stock) {
        List<OperationDto> operationDtos = stock.getOperations()
                .stream()
                .map(OperationDto::new)
                .toList();

        this.id = stock.getId();
        this.setBalance(Utils.formatValue(stock.getBalance().toString()));
        this.setDocument(Utils.formatDocumento(stock.getDocument()));
        this.setStoreOwner(stock.getOwnerStore());
        this.setStoreName(stock.getStoreName());
        this.setOperations(operationDtos);
    }
}
