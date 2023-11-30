package com.easy.boot3.admin.home.service;


import com.easy.boot3.admin.home.entity.HomeHandlerTimeVO;
import com.easy.boot3.admin.home.entity.HomeHotsApiVO;
import com.easy.boot3.admin.home.entity.HomeNumberVO;
import com.easy.boot3.admin.home.entity.HomeUserAnalysisVO;

/**
 * @author zoe
 * @date 2023/7/23
 * @description
 */
public interface HomeService {

    /**
     * 获取数量信息
     * @return
     */
    HomeNumberVO getHomeNumber();

    /**
     * 获取用户分析
     * @return
     */
    HomeUserAnalysisVO getUserAnalysis();

    /**
     * 获取热点接口
     * @return
     */
    HomeHotsApiVO getHotsApi();

    /**
     * 获取接口处理时长
     * @return
     */
    HomeHandlerTimeVO getHandlerTime();
}
