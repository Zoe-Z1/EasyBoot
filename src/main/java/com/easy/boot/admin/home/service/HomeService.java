package com.easy.boot.admin.home.service;


import com.easy.boot.admin.home.entity.HomeDTO;
import com.easy.boot.admin.home.entity.HomeVO;

/**
 * @author zoe
 * @date 2023/7/23
 * @description
 */
public interface HomeService {

    /**
     * 获取首页统计信息
     * @param dto
     * @return
     */
    HomeVO getStatistics(HomeDTO dto);
}
