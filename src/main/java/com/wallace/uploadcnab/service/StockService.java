package com.wallace.uploadcnab.service;

import com.wallace.uploadcnab.domain.Stock;
import com.wallace.uploadcnab.dto.StockDto;
import com.wallace.uploadcnab.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    public List<StockDto> findAll() {
        return stockRepository.findAll()
                .stream()
                .map(StockDto::new)
                .toList();
    }

    public Optional<Stock> findByStoreName(String nameStore) {
        return stockRepository.findByStoreName(nameStore);
    }
}
