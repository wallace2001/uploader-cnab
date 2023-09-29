package com.wallace.uploadcnab.repository;

import com.wallace.uploadcnab.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    Optional<Stock> findByStoreName(String storeName);
}
