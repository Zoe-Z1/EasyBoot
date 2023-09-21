package com.easy.boot.admin.blacklist.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.boot.admin.blacklist.entity.Blacklist;
import com.easy.boot.admin.blacklist.entity.BlacklistQuery;
import com.easy.boot.admin.blacklist.entity.BlacklistCreateDTO;
import com.easy.boot.admin.blacklist.entity.BlacklistUpdateDTO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author zoe
* @date 2023/08/01
* @description 黑名单 服务类
*/
public interface IBlacklistService extends IService<Blacklist> {

    /**
    * 查询黑名单
    * @param query
    * @return
    */
    IPage<Blacklist> selectPage(BlacklistQuery query);

    /**
     * 获取不永久拉黑的黑名单列表
     * @return
     */
    List<Blacklist> selectNotForeverList();

    /**
     * 获取黑名单详情
     * @param id
     * @return
     */
    Blacklist detail(Long id);

    /**
     * 根据用户ID获取拉黑信息
     * @param userId
     * @return
     */
    Blacklist getByUserId(Long userId);

    /**
     * 根据IP获取拉黑信息
     * @param ip
     * @return
     */
    Blacklist getByIp(String ip);

    /**
     * 根据关联信息和类型获取拉黑信息
     * @param relevanceData
     * @param type
     * @return
     */
    Blacklist getByRelevanceDataAndType(String relevanceData, Integer type);

    /**
    * 创建黑名单
    * @param dto
    * @return
    */
    Boolean create(BlacklistCreateDTO dto);

    /**
    * 编辑黑名单
    * @param dto
    * @return
    */
    Boolean updateById(BlacklistUpdateDTO dto);

    /**
     * 删除黑名单
     * @param id
     * @return
     */
    Boolean deleteById(Long id);

    /**
     * 批量删除黑名单
     * @param ids
     * @return
     */
    Boolean deleteBatchByIds(List<Long> ids);

}
