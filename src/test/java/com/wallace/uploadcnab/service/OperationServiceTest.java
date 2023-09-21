package com.wallace.uploadcnab.service;

import com.wallace.uploadcnab.domain.Operation;
import com.wallace.uploadcnab.domain.TypeOperation;
import com.wallace.uploadcnab.dto.OperationDto;
import com.wallace.uploadcnab.dto.ResponseOperationDto;
import com.wallace.uploadcnab.fixture.OperationFixture;
import com.wallace.uploadcnab.repository.OperationRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OperationServiceTest {
    private Operation operation;
    private final OperationFixture operationFixture;

    @Mock
    private OperationRepository operationRepository;
    @InjectMocks
    private OperationService operationService;

    public OperationServiceTest(){
        this.operationFixture = new OperationFixture();
    }

    @BeforeEach
    void init() {
        operation = operationFixture.create();
    }

    @Test
    void should_SaveOperation_When_OperationServiceSavedCalled() {
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

    @Test
    void should_ReturnPageOperations_When_OperationServiceFindAllCalled() {
        List<Operation> operations = Collections.singletonList(
                operation
        );
        Page<Operation> pagedOperations = new PageImpl<>(operations);
        when(operationRepository.findAll(PageRequest.of(1, 1))).thenReturn(pagedOperations);
        when(operationRepository.findAll()).thenReturn(operations);

        ResponseOperationDto response = operationService.findAll(PageRequest.of(1, 1));

        assertNotNull(response);
        assertEquals(response.getTotalPages(), 1);
        assertEquals(response.getSize(), 1);
        assertNotNull(response.getTotal());
        assertEquals(response.getOperations().size(), 1);
        assertEquals(response.getNumber(), 0);
        assertNotNull(response.getOperations());

        for (OperationDto operationSaved: response.getOperations()) {
            assertNotNull(operationSaved.getId());
            assertEquals(operationSaved.getNameStore(), operation.getNameStore());
            assertEquals(operationSaved.getDocument(), Utils.formatDocumento(operation.getDocument()));
            assertEquals(operationSaved.getStoreOwner(), operation.getStoreOwner());
            assertEquals(operationSaved.getCardNumber(), Utils.formatCardNumber(operation.getCardNumber()));
            assertEquals(operationSaved.getValue(), Utils.formatValue(operation.getValue()));
            assertEquals(operationSaved.getDate(), Utils.formatTimestamp(operation.getDate()));
            assertEquals(operationSaved.getTypeOperation(), operation.getType().getDescription());
            assertEquals(operationSaved.getTypeNature(), operation.getType().getNature());
            assertEquals(operationSaved.getClass(), OperationDto.class);
        };

        assertEquals(ResponseOperationDto.class, response.getClass());

    }
}
