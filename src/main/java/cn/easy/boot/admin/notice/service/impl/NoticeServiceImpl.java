package cn.easy.boot.admin.notice.service.impl;

import cn.easy.boot.admin.notice.entity.Notice;
import cn.easy.boot.admin.notice.entity.NoticeCreateDTO;
import cn.easy.boot.admin.notice.entity.NoticeQuery;
import cn.easy.boot.admin.notice.entity.NoticeUpdateDTO;
import cn.easy.boot.admin.notice.mapper.NoticeMapper;
import cn.easy.boot.admin.notice.service.INoticeService;
import cn.easy.boot.common.base.BaseEntity;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.easy.boot.utils.BeanUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
* @author zoe
* @date 2023/10/22
* @description 公告 服务实现类
*/
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements INoticeService {

    @Override
    public Notice news() {
        return lambdaQuery()
                .eq(Notice::getStatus, 1)
                .orderByAsc(Notice::getSort)
                .orderByDesc(Notice::getCreateTime)
                .last("limit 1")
                .one();
    }

    @Override
    public IPage<Notice> selectPage(NoticeQuery query) {
        Page<Notice> page = new Page<>(query.getPageNum(), query.getPageSize());
        return lambdaQuery()
                .and(StrUtil.isNotEmpty(query.getKeyword()), keywordQuery -> {
                    keywordQuery
                    .like(Notice::getTitle, query.getKeyword());
                })
                .eq(query.getStatus() != null, Notice::getStatus, query.getStatus())
                .between(Objects.nonNull(query.getStartTime()) && Objects.nonNull(query.getEndTime()),
                        BaseEntity::getCreateTime, query.getStartTime(), query.getEndTime())
                .orderByAsc(Notice::getSort)
                .orderByDesc(Notice::getCreateTime)
                .page(page);
    }

    @Override
    public Notice detail(Long id) {
        return getById(id);
    }

    @Override
    public Boolean create(NoticeCreateDTO dto) {
        Notice notice = BeanUtil.copyBean(dto, Notice.class);
        return save(notice);
    }

    @Override

    public Boolean updateById(NoticeUpdateDTO dto) {
        Notice notice = BeanUtil.copyBean(dto, Notice.class);
        return updateById(notice);
    }

    @Override
    public Boolean deleteById(Long id) {
        return removeById(id);
    }

    @Override
    public Boolean deleteBatchByIds(List<Long> ids) {
        return removeBatchByIds(ids);
    }

}
