package com.wallace.uploadcnab.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

@Data
@Getter @Setter
public class ResponseOperationDto {

    private String total;
    private Integer totalPages;
    private Integer number;
    private Integer size;
    private List<OperationDto> operations;

    public void setTotal(Long total) {
        this.total = formatValue(total);
    }

    private String formatValue(Long value) {
        double valorNumerico = Double.parseDouble(value.toString()) / 100;
        NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        return formatoMoeda.format(valorNumerico);
    }

}
