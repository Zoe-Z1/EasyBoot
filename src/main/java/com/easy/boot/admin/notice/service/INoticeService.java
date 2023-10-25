package com.easy.boot.admin.notice.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.easy.boot.admin.notice.entity.Notice;
import com.easy.boot.admin.notice.entity.NoticeCreateDTO;
import com.easy.boot.admin.notice.entity.NoticeQuery;
import com.easy.boot.admin.notice.entity.NoticeUpdateDTO;

import java.util.List;

/**
* @author zoe
* @date 2023/10/22
* @description 公告 服务类
*/
public interface INoticeService extends IService<Notice> {

    /**
     * 获取最新公告
     * @return
     */
    Notice news();

    /**
     * 分页查询公告
     * @param query
     * @return
     */
    IPage<Notice> selectPage(NoticeQuery query);

    /**
     * 获取公告详情
     * @param id
     * @return
     */
    Notice detail(Long id);

    /**
     * 创建公告
     * @param dto
     * @return
     */
    Boolean create(NoticeCreateDTO dto);

    /**
     * 编辑公告
     * @param dto
     * @return
     */
    Boolean updateById(NoticeUpdateDTO dto);

    /**
     * 删除公告
     * @param id
     * @return
     */
    Boolean deleteById(Long id);

    /**
     * 批量删除公告
     * @param ids
     * @return
     */
    Boolean deleteBatchByIds(List<Long> ids);

}
