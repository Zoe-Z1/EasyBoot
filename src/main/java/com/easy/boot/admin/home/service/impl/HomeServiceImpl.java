package com.easy.boot.admin.home.service.impl;

import com.easy.boot.admin.home.entity.HomeDTO;
import com.easy.boot.admin.home.entity.HomeVO;
import com.easy.boot.admin.home.service.HomeService;
import com.easy.boot.admin.user.service.AdminUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zoe
 * @date 2023/7/23
 * @description
 */
@Slf4j
@Service
public class HomeServiceImpl implements HomeService {

    @Resource
    private AdminUserService adminUserService;


    @Override
    public HomeVO getStatistics(HomeDTO dto) {
        return null;
    }
}
