package com.wallace.uploadcnab.fixture;

import com.wallace.uploadcnab.domain.Operation;
import com.wallace.uploadcnab.domain.Stock;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class StockFixture {

    private final Random random = new Random();

    public Stock create(List<Operation> operations) {
        Stock stock = new Stock();
        stock.setId(1L);
        stock.setOwnerStore(operations.get(0).getStoreOwner());
        stock.setDocument(operations.get(0).getDocument());
        stock.setStoreName(operations.get(0).getNameStore());
        stock.setBalance(100D);
        stock.setOperations(operations);

        return stock;
    }
}
