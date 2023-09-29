package com.wallace.uploadcnab.repository;

import com.wallace.uploadcnab.domain.TypeOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeOperationRepository extends JpaRepository<TypeOperation, Long> {
}
