package com.m42.custody.dto;

import jakarta.validation.constraints.Min;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProdQueryDTO {
    @Min(value = 1, message = "页码最小为1")
    private Long pageNum = 1L;
    @Min(value = 1, message = "页大小最小为1")
    private Long pageSize = 10L;

    private String prodCode;
    private String prodName;
    private String managerNo;
    private LocalDate startDate;
    private LocalDate endDate;
}
