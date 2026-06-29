package com.m42.custody.service;

import com.m42.custody.BaseUnitTest;
import com.m42.custody.common.Result;
import com.m42.custody.dto.MsgDTO;
import com.m42.custody.entity.MsgEntity;
import com.m42.custody.mapper.MsgMapper;
import com.m42.custody.service.impl.MsgServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class MsgServiceTest extends BaseUnitTest {
    @Mock
    private MsgMapper msgMapper;

    @Mock
    private StringRedisTemplate redisTemplate;

    @Mock
    private ValueOperations<String, String> valueOperations;

    @InjectMocks
    private MsgServiceImpl msgService;

    private final String redisKey = "IDEMP:MSG:MSG_UNIT_001";
    private MsgDTO msgDTO;

    @BeforeEach
    void setUp() {
        msgDTO = new MsgDTO("MSG_UNIT_001", "P001", "单元测试报文");
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    void testDealMsg_FirstTime_Success() {
        when(valueOperations.setIfAbsent(eq(redisKey), eq("1"), eq(7L), eq(TimeUnit.DAYS)))
                .thenReturn(Boolean.TRUE);
        when(msgMapper.insert(any(MsgEntity.class))).thenReturn(1);

        Result<?> result = msgService.dealMsg(msgDTO);

        assertEquals("000000", result.getCode());
        verify(valueOperations, times(1)).setIfAbsent(redisKey, "1", 7L, TimeUnit.DAYS);
        verify(msgMapper, times(1)).insert(any(MsgEntity.class));
    }

    @Test
    void testDealMsg_DuplicateTime_Fail() {
        when(valueOperations.setIfAbsent(eq(redisKey), eq("1"), eq(7L), eq(TimeUnit.DAYS)))
                .thenReturn(Boolean.FALSE);

        Result<?> result = msgService.dealMsg(msgDTO);

        assertEquals("E001", result.getCode());
        assertEquals("报文已处理过", result.getMsg());
        verify(valueOperations, times(1)).setIfAbsent(redisKey, "1", 7L, TimeUnit.DAYS);
        verifyNoInteractions(msgMapper);
    }
}
