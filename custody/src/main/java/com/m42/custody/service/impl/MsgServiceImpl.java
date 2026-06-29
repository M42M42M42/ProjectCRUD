package com.m42.custody.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.m42.custody.common.Result;
import com.m42.custody.dto.MsgDTO;
import com.m42.custody.entity.MsgEntity;
import com.m42.custody.mapper.MsgMapper;
import com.m42.custody.service.MsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class MsgServiceImpl extends ServiceImpl<MsgMapper, MsgEntity> implements MsgService {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private MsgMapper msgMapper;

    private static final String PREFIX = "IDEMP:MSG:";
    private static final long EXPIRE = 7L;

    @Override
    public Result<?> dealMsg(MsgDTO dto) {
        String key = PREFIX + dto.getMsgNo();
        Boolean hasKey = redisTemplate.opsForValue().setIfAbsent(key, "1", EXPIRE, TimeUnit.DAYS);
        if (Boolean.FALSE.equals(hasKey)) {
            return Result.fail("E001", "报文已处理过");
        }
        try {
            MsgEntity entity = new MsgEntity();
            entity.setMsgNo(dto.getMsgNo());
            entity.setProdCode(dto.getProdCode());
            entity.setMsgBody(dto.getMsgBody());
            msgMapper.insert(entity);
            return Result.success("报文处理完成");
        } catch (Exception e) {
            redisTemplate.delete(key);
            return Result.fail("E003", "报文处理失败");
        }
    }
}
