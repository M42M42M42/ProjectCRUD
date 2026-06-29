package com.m42.custody.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.m42.custody.common.Result;
import com.m42.custody.dto.ProdQueryDTO;
import com.m42.custody.entity.ProdEntity;
import com.m42.custody.mapper.ProdMapper;
import com.m42.custody.service.ProdService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ProdServiceImpl extends ServiceImpl<ProdMapper, ProdEntity> implements ProdService {

    @Override
    public Result<IPage<ProdEntity>> pageList(ProdQueryDTO dto) {
        Page<ProdEntity> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        LambdaQueryWrapper<ProdEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(dto.getProdCode()), ProdEntity::getProdCode, dto.getProdCode());
        wrapper.like(StringUtils.hasText(dto.getProdName()), ProdEntity::getProdName, dto.getProdName());
        wrapper.eq(StringUtils.hasText(dto.getManagerNo()), ProdEntity::getStatus, dto.getManagerNo());
        if (dto.getStartDate() != null) {
            wrapper.ge(ProdEntity::getGmtCreate, dto.getStartDate());
        }
        if (dto.getEndDate() != null) {
            wrapper.le(ProdEntity::getGmtCreate, dto.getEndDate());
        }
        wrapper.orderByDesc(ProdEntity::getGmtCreate);
        IPage<ProdEntity> pageRes = baseMapper.selectPage(page, wrapper);
        return Result.success(pageRes);
    }

    @Override
    public Result<Void> saveProd(ProdEntity entity) {
        baseMapper.insert(entity);
        return Result.success();
    }

    @Override
    public Result<Void> updateProd(ProdEntity entity) {
        if (entity.getId() == null) {
            return Result.fail("E001", "主键ID不能为空");
        }
        baseMapper.updateById(entity);
        return Result.success();
    }

    @Override
    public Result<ProdEntity> getById(Long id) {
        return Result.success(baseMapper.selectById(id));
    }

    @Override
    public Result<Void> deleteById(Long id) {
        ProdEntity update = new ProdEntity();
        update.setId(id);
        update.setStatus("0"); // 逻辑删除
        baseMapper.updateById(update);
        return Result.success();
    }
}
