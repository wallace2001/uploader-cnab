package com.wallace.uploadcnab.service;

import com.wallace.uploadcnab.domain.TypeOperation;
import com.wallace.uploadcnab.repository.TypeOperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TypeOperationService {

    @Autowired
    private TypeOperationRepository typeOperationRepository;

    public Optional<TypeOperation> findById(Long id) {
        return typeOperationRepository.findById(id);
    }
}
