package com.wallace.uploadcnab.service;

import com.wallace.uploadcnab.domain.Operation;
import com.wallace.uploadcnab.domain.Stock;
import com.wallace.uploadcnab.domain.TypeOperation;
import com.wallace.uploadcnab.dto.OperationDto;
import com.wallace.uploadcnab.dto.ResponseOperationDto;
import com.wallace.uploadcnab.fixture.OperationFixture;
import com.wallace.uploadcnab.fixture.StockFixture;
import com.wallace.uploadcnab.fixture.TypeOperationFixture;
import com.wallace.uploadcnab.repository.OperationRepository;
import com.wallace.uploadcnab.repository.StockRepository;
import com.wallace.uploadcnab.repository.TypeOperationRepository;
import com.wallace.uploadcnab.util.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OperationServiceTest {
    private Operation operation;
    private final OperationFixture operationFixture;

    private final TypeOperationFixture typeOperationFixture;

    private final StockFixture stockFixture;

    @Mock
    private OperationRepository operationRepository;

    @Mock
    private StockRepository stockRepository;

    @Mock
    private TypeOperationRepository typeOperationRepository;

    @Mock
    private StockService stockService;

    @Mock
    private TypeOperationService typeOperationService;

    @InjectMocks
    private OperationService operationService;

    public OperationServiceTest(){
        this.operationFixture = new OperationFixture();
        this.typeOperationFixture = new TypeOperationFixture();
        this.stockFixture = new StockFixture();
    }

    @BeforeEach
    void init() {
        operation = operationFixture.create();
    }

    @Test
    void should_SaveOperation_When_OperationServiceSavedCalled() {

        TypeOperation typeOperation = typeOperationFixture.create();
        Stock stock = stockFixture.create(List.of(operation));

        when(typeOperationService.findById(any())).thenReturn(Optional.ofNullable(typeOperation));
        when(stockService.findByStoreName(any())).thenReturn(Optional.ofNullable(stock));

        when(operationRepository.save(any(Operation.class))).thenReturn(operation);
        Operation savedOperation = operationService.save(operation);

        assertNotNull(savedOperation);
        assertNotNull(savedOperation.getId());
        assertEquals(savedOperation.getNameStore(), operation.getNameStore());
        assertEquals(savedOperation.getDocument(), operation.getDocument());
        assertEquals(savedOperation.getStoreOwner(), operation.getStoreOwner());
        assertEquals(savedOperation.getCardNumber(), operation.getCardNumber());
        assertEquals(savedOperation.getValue(), operation.getValue());
        assertEquals(savedOperation.getDate(), operation.getDate());
        assertEquals(savedOperation.getType().getId(), operation.getType().getId());
        assertEquals(savedOperation.getType().getDescription(), operation.getType().getDescription());
        assertEquals(savedOperation.getType().getCod(), operation.getType().getCod());
        assertEquals(savedOperation.getType().getSignal(), operation.getType().getSignal());
        assertEquals(savedOperation.getType().getNature(), operation.getType().getNature());
        assertEquals(savedOperation.getClass(), Operation.class);
        assertEquals(savedOperation.getType().getClass(), TypeOperation.class);
    }
}
