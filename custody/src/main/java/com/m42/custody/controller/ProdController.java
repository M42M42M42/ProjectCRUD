package com.m42.custody.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.m42.custody.common.Result;
import com.m42.custody.dto.ProdQueryDTO;
import com.m42.custody.entity.ProdEntity;
import com.m42.custody.service.ProdService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trust/prod")
public class ProdController {

    @Autowired
    private ProdService prodService;

    @GetMapping("/page")
    public Result<IPage<ProdEntity>> page(@Valid ProdQueryDTO dto) {
        return prodService.pageList(dto);
    }

    @PostMapping("/save")
    public Result<Void> save(@RequestBody ProdEntity entity) {
        return prodService.saveProd(entity);
    }

    @PutMapping("/update")
    public Result<Void> update(@RequestBody ProdEntity entity) {
        return prodService.updateProd(entity);
    }

    @GetMapping("/{id}")
    public Result<ProdEntity> getById(@PathVariable Long id) {
        return prodService.getById(id);
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteById(@PathVariable Long id) {
        return prodService.deleteById(id);
    }
}
