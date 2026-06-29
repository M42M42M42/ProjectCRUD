package com.m42.custody.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_product_msg")
public class MsgEntity {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String msgNo;
    private String prodCode;
    private String msgBody;
}
