package com.wallace.uploadcnab.repository;

import com.wallace.uploadcnab.domain.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation, Long> {
}
