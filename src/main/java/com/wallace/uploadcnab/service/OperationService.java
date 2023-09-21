package com.wallace.uploadcnab.service;

import com.wallace.uploadcnab.domain.Operation;
import com.wallace.uploadcnab.dto.OperationDto;
import com.wallace.uploadcnab.dto.ResponseOperationDto;
import com.wallace.uploadcnab.repository.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class OperationService {

    @Autowired
    private OperationRepository operationRepository;

    @Transactional(readOnly = false)
    public Operation save(Operation operation) {
        return operationRepository.save(operation);
    }

    @Transactional(readOnly = true)
    public ResponseOperationDto findAll(PageRequest pageRequest) {
        Page<Operation> operationsPage = operationRepository.findAll(pageRequest);
        List<Operation> allOperations = operationRepository.findAll();

        AtomicLong total = new AtomicLong();
        allOperations
                .forEach(operation -> {
                    if (Objects.equals(operation.getType().getSignal(), "+")) {
                        total.getAndAdd(Long.parseLong(operation.getValue()));
                    } else {
                        total.set(total.get() - Long.parseLong(operation.getValue()));
                    }
                });

        List<OperationDto> operationsDto = operationsPage.getContent()
                .stream()
                .map(OperationDto::new)
                .distinct()
                .collect(Collectors.toList());

        ResponseOperationDto responseOperationDto = new ResponseOperationDto();
        responseOperationDto.setOperations(operationsDto);
        responseOperationDto.setNumber(Optional.of(operationsPage.getNumber()).orElse(1));
        responseOperationDto.setSize(operationsPage.getSize());
        responseOperationDto.setTotalPages(operationsPage.getTotalPages());
        responseOperationDto.setTotal(total.get());

        return responseOperationDto;
    }
}
