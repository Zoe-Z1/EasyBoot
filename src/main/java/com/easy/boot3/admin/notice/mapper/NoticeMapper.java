package com.easy.boot3.admin.notice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easy.boot3.admin.notice.entity.Notice;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zoe
 * @date 2023/10/22
 * @description 公告 Mapper接口
 */
@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {

}
