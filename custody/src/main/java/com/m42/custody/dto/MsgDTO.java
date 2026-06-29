package com.m42.custody.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MsgDTO {
    private String msgNo;
    private String prodCode;
    private String msgBody;
}
