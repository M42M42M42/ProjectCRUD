package com.m42.custody.controller;

import com.m42.custody.common.Result;
import com.m42.custody.dto.MsgDTO;
import com.m42.custody.service.MsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MsgController {
    @Autowired
    private MsgService msgService;
//    @Autowired
//    private BatchTaskService batchTaskService;

    @PostMapping("/msg/receive")
    public Result<?> receiveMsg(@RequestBody MsgDTO dto) {
        return msgService.dealMsg(dto);
    }

//    @PostMapping("/batch/valuation/run")
//    public Result<String> manualRun(@RequestParam String busiDate) {
//        return batchTaskService.manualRunBatch(busiDate);
//    }
}
