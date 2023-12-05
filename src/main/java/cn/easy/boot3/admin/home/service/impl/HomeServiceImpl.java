package cn.easy.boot3.admin.home.service.impl;

import cn.easy.boot3.admin.home.entity.*;
import cn.easy.boot3.admin.home.service.HomeService;
import cn.easy.boot3.utils.Constant;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.easy.boot3.admin.home.entity.*;
import cn.easy.boot3.admin.loginLog.service.ILoginLogService;
import cn.easy.boot3.admin.onlineUser.service.IOnlineUserService;
import cn.easy.boot3.admin.operationLog.service.IOperationLogService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zoe
 * @date 2023/7/23
 * @description
 */
@Slf4j
@Service
public class HomeServiceImpl implements HomeService {

    @Resource
    private IOnlineUserService onlineUserService;

    @Resource
    private ILoginLogService loginLogService;

    @Resource
    private IOperationLogService operationLogService;


    @Override
    public HomeNumberVO getHomeNumber() {
        long onlineNumber = onlineUserService.getOnlineNumber();
        long todayBeginTime = DateUtil.beginOfDay(new Date()).getTime();
        long loginNumber = loginLogService.getLoginNumber(todayBeginTime, null);
        long ipNumber = loginLogService.getIpNumber(todayBeginTime, null);
        long operationNumber = operationLogService.getOperationNumber(todayBeginTime, null);
        HomeNumberVO vo = HomeNumberVO.builder()
                .onlineNumber(onlineNumber)
                .loginNumber(loginNumber)
                .ipNumber(ipNumber)
                .operationNumber(operationNumber)
                .build();
        return vo;
    }

    @Override
    public HomeUserAnalysisVO getUserAnalysis() {
        List<String> dates = new ArrayList<>();
        List<Long> loginNumbers = new ArrayList<>();
        List<Long> ipNumbers = new ArrayList<>();
        List<Long> operationNumbers = new ArrayList<>();
        Date date = new Date();
        DateTime begin = DateUtil.beginOfDay(date);
        while (true) {
            DateTime end = DateUtil.offsetHour(begin, 1);
            if (end.getTime() > date.getTime()) {
                break;
            }
            String dateStr = DateUtil.format(end, Constant.DATA_PARENT_HH_MM);
            long loginNumber = loginLogService.getLoginNumber(begin.getTime(), end.getTime());
            long ipNumber = loginLogService.getIpNumber(begin.getTime(), end.getTime());
            long operationNumber = operationLogService.getOperationNumber(begin.getTime(), end.getTime());
            dates.add(dateStr);
            loginNumbers.add(loginNumber);
            ipNumbers.add(ipNumber);
            operationNumbers.add(operationNumber);
            begin = end;
        }
        HomeUserAnalysisVO vo = HomeUserAnalysisVO.builder()
                .dates(dates)
                .loginNumbers(loginNumbers)
                .ipNumbers(ipNumbers)
                .operationNumbers(operationNumbers)
                .build();
        return vo;
    }

    @Override
    public HomeHotsApiVO getHotsApi() {
        long todayBeginTime = DateUtil.beginOfDay(new Date()).getTime();
        int limit = 30;
        List<HotsApiDO> list = operationLogService.getHotsApi(todayBeginTime, limit);
        List<String> urls = new ArrayList<>();
        List<Long> counts = new ArrayList<>();
        for (HotsApiDO hotsApiDO : list) {
            urls.add(hotsApiDO.getRequestWay() + " " + hotsApiDO.getRequestUrl());
            counts.add(hotsApiDO.getCount());
        }
        HomeHotsApiVO vo = HomeHotsApiVO.builder()
                .urls(urls)
                .counts(counts)
                .build();
        return vo;
    }

    @Override
    public HomeHandlerTimeVO getHandlerTime() {
        long todayBeginTime = DateUtil.beginOfDay(new Date()).getTime();
        int limit = 30;
        List<HandlerTimeDO> list = operationLogService.getHandlerTime(todayBeginTime, limit);
        List<String> urls = new ArrayList<>();
        List<Long> times = new ArrayList<>();
        for (HandlerTimeDO handlerTimeDO : list) {
            urls.add(handlerTimeDO.getRequestWay() + " " + handlerTimeDO.getRequestUrl());
            times.add(handlerTimeDO.getHandleTime());
        }
        HomeHandlerTimeVO vo = HomeHandlerTimeVO.builder()
                .urls(urls)
                .times(times)
                .build();
        return vo;
    }
}
