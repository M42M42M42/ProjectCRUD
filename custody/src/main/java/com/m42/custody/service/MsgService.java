package com.m42.custody.service;

import com.m42.custody.common.Result;
import com.m42.custody.dto.MsgDTO;

public interface MsgService {
    Result<?> dealMsg(MsgDTO dto);
}
