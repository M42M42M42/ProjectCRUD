package com.m42.custody.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.m42.custody.common.Result;
import com.m42.custody.dto.MsgDTO;
import com.m42.custody.entity.MsgEntity;

public interface MsgService extends IService<MsgEntity> {
    Result<?> dealMsg(MsgDTO dto);
}
