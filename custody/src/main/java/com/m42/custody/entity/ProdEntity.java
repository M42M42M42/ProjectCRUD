package com.m42.custody.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("t_prod_base_info")
public class ProdEntity {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String prodCode;
    private String prodName;
    private String managerNo;
    private BigDecimal scale;
    private String status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtModified;
}
