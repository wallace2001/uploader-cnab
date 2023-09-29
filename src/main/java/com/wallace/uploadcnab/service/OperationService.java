package com.wallace.uploadcnab.service;

import com.wallace.uploadcnab.domain.Operation;
import com.wallace.uploadcnab.domain.Stock;
import com.wallace.uploadcnab.domain.TypeOperation;
import com.wallace.uploadcnab.repository.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class OperationService {

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private StockService stockService;

    @Autowired
    private TypeOperationService typeOperationService;

    @Transactional(readOnly = false)
    public Operation save(Operation operation) {
        Double value = Double.parseDouble(operation.getValue());
        Stock stock = stockService.findByStoreName(operation.getNameStore()).orElse(new Stock(operation.getNameStore()));
        TypeOperation typeOperation = typeOperationService.findById(operation.getType().getId()).orElse(new TypeOperation());

        if (Objects.equals(typeOperation.getSignal(), "+")) {
            stock.setBalance(value + stock.getBalance());
        } else {
            stock.setBalance(stock.getBalance() - value);
        }

        stock.setDocument(operation.getDocument());
        stock.setOwnerStore(operation.getStoreOwner());
        operation.setStock(stock);
        return operationRepository.save(operation);
    }
}
