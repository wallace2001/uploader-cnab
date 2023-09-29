package com.wallace.uploadcnab.service;

import com.wallace.uploadcnab.domain.TypeOperation;
import com.wallace.uploadcnab.fixture.TypeOperationFixture;
import com.wallace.uploadcnab.repository.TypeOperationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TypeOperationServiceTest {
    private final TypeOperationFixture typeOperationFixture;

    @Mock
    private TypeOperationRepository typeOperationRepository;
    @InjectMocks
    private TypeOperationService typeOperationService;

    public TypeOperationServiceTest(){
        this.typeOperationFixture = new TypeOperationFixture();
    }

    @Test
    void should_ReturnTypeById_When_TypeOperationServiceFindAllCalled() {
        TypeOperation typeOperation = typeOperationFixture.create();
        when(typeOperationRepository.findById(any())).thenReturn(Optional.ofNullable(typeOperation));

        Optional<TypeOperation> typeO = typeOperationService.findById(typeOperation.getId());

        assertNotNull(typeO);
        assertTrue(typeO.isPresent());
        assertNotNull(typeO.get().getId());
        assertEquals(typeO.get().getDescription(), typeOperation.getDescription());
        assertEquals(typeO.get().getNature(), typeOperation.getNature());
        assertEquals(typeO.get().getCod(), typeOperation.getCod());
        assertEquals(typeO.get().getSignal(), typeOperation.getSignal());

        assertEquals(typeO.get().getClass(), TypeOperation.class);
    }
}
