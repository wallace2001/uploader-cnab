package com.wallace.uploadcnab.service;

import com.wallace.uploadcnab.domain.Operation;
import com.wallace.uploadcnab.domain.Stock;
import com.wallace.uploadcnab.dto.OperationDto;
import com.wallace.uploadcnab.dto.StockDto;
import com.wallace.uploadcnab.fixture.OperationFixture;
import com.wallace.uploadcnab.fixture.StockFixture;
import com.wallace.uploadcnab.repository.StockRepository;
import com.wallace.uploadcnab.util.Utils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StockServiceTest {

    private final StockFixture stockFixture;
    private final OperationFixture operationFixture;

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private StockService stockService;

    public StockServiceTest(){
        this.stockFixture = new StockFixture();
        this.operationFixture = new OperationFixture();
    }

    @Test
    void should_ReturnStock_When_StockServiceFindAllCalled() {

        Operation operation = operationFixture.create();
        Stock stock = stockFixture.create(Collections.singletonList(operation));

        when(stockRepository.findAll()).thenReturn(Collections.singletonList(stock));

        List<StockDto> response = stockService.findAll();

        assertNotNull(response);

        assertNotNull(response.get(0).getId());
        assertEquals(response.get(0).getBalance(), Utils.formatValue(stock.getBalance().toString()));
        assertEquals(response.get(0).getStoreOwner(), operation.getStoreOwner());
        assertEquals(response.get(0).getStoreName(), operation.getNameStore());

        for (OperationDto operationSaved: response.get(0).getOperations()) {
            assertNotNull(operationSaved.getId());
            assertEquals(operationSaved.getCardNumber(), Utils.formatCardNumber(operation.getCardNumber()));
            assertEquals(operationSaved.getValue(), Utils.formatValue(operation.getValue()));
            assertEquals(operationSaved.getDate(), Utils.formatTimestamp(operation.getDate()));
            assertEquals(operationSaved.getTypeOperation(), operation.getType().getDescription());
            assertEquals(operationSaved.getTypeNature(), operation.getType().getNature());
            assertEquals(operationSaved.getClass(), OperationDto.class);
        };
    }
}
