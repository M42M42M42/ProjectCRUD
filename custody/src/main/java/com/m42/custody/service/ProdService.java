package com.m42.custody.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.m42.custody.common.Result;
import com.m42.custody.dto.ProdQueryDTO;
import com.m42.custody.entity.ProdEntity;

public interface ProdService extends IService<ProdEntity> {
    Result<IPage<ProdEntity>> pageList(ProdQueryDTO dto);

    Result<Void> saveProd(ProdEntity entity);

    Result<Void> updateProd(ProdEntity entity);

    Result<ProdEntity> getById(Long id);

    Result<Void> deleteById(Long id);
//    void exportExcel(ProdQueryDTO dto, HttpServletResponse response) throws Exception;
//    Result<String> importExcel(org.springframework.web.multipart.MultipartFile file) throws Exception;
}
