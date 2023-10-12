package com.easy.boot.admin.post.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easy.boot.admin.post.entity.Post;
import com.easy.boot.admin.post.entity.PostCreateDTO;
import com.easy.boot.admin.post.entity.PostQuery;
import com.easy.boot.admin.post.entity.PostUpdateDTO;
import com.easy.boot.admin.post.mapper.PostMapper;
import com.easy.boot.admin.post.service.IPostService;
import com.easy.boot.admin.userPost.entity.UserPost;
import com.easy.boot.admin.userPost.service.IUserPostService;
import com.easy.boot.common.base.BaseEntity;
import com.easy.boot.common.excel.entity.ImportExcelError;
import com.easy.boot.exception.BusinessException;
import com.easy.boot.utils.BeanUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
* @author zoe
* @date 2023/07/29
* @description 岗位 服务实现类
*/
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements IPostService {

    @Resource
    private IUserPostService userPostService;

    @Override
    public List<Post> selectAll() {
        return lambdaQuery()
                .select(BaseEntity::getId, Post::getCode,  Post::getName)
                .eq(Post::getStatus, 1)
                .orderByAsc(Post::getSort)
                .orderByDesc(BaseEntity::getCreateTime)
                .list();
    }

    @Override
    public IPage<Post> selectPage(PostQuery query) {
        Page<Post> page = new Page<>(query.getPageNum(), query.getPageSize());
        return lambdaQuery()
                .and(StrUtil.isNotEmpty(query.getKeyword()), keywordQuery -> {
                    keywordQuery
                            .like(Post::getCode, query.getKeyword()).or()
                            .like(Post::getName, query.getKeyword());
                })
                .like(StrUtil.isNotEmpty(query.getCode()), Post::getCode, query.getCode())
                .like(StrUtil.isNotEmpty(query.getName()), Post::getName, query.getName())
                .eq(Objects.nonNull(query.getStatus()), Post::getStatus, query.getStatus())
                .between(Objects.nonNull(query.getStartTime()) && Objects.nonNull(query.getEndTime()),
                        BaseEntity::getCreateTime, query.getStartTime(), query.getEndTime())
                .orderByAsc(Post::getSort)
                .orderByDesc(BaseEntity::getCreateTime)
                .page(page);
    }

    @Override
    public Post detail(Long id) {
        return getById(id);
    }

    @Override
    public Boolean create(PostCreateDTO dto) {
        Post post = lambdaQuery().eq(Post::getCode, dto.getCode()).one();
        if (post != null) {
            throw new BusinessException("岗位编码已存在");
        }
        Post entity = BeanUtil.copyBean(dto, Post.class);
        return save(entity);
    }

    @Override
    public Boolean updateById(PostUpdateDTO dto) {
        Post post = lambdaQuery().eq(Post::getCode, dto.getCode()).one();
        if (post != null && !post.getId().equals(dto.getId())) {
            throw new BusinessException("岗位编码已存在");
        }
        Post entity = BeanUtil.copyBean(dto, Post.class);
        return updateById(entity);
    }

    @Override
    public Boolean deleteById(Long id) {
        List<Long> ids = new ArrayList<>();
        ids.add(id);
        List<UserPost> list = userPostService.selectListByPostIds(ids);
        if (CollUtil.isNotEmpty(list)) {
            throw new BusinessException("已有用户绑定选中的岗位，不允许删除");
        }
        return removeById(id);
    }

    @Override
    public Boolean deleteBatchByIds(List<Long> ids) {
        List<UserPost> list = userPostService.selectListByPostIds(ids);
        if (CollUtil.isNotEmpty(list)) {
            throw new BusinessException("已有用户绑定选中的岗位，不允许删除");
        }
        return removeBatchByIds(ids);
    }

    @Override
    public void importExcel(List<Post> list, List<Post> errorList, List<ImportExcelError> errors) {
        if (CollUtil.isEmpty(list)) {
            return;
        }
        List<String> codes = list.stream().map(Post::getCode).distinct().collect(Collectors.toList());
        List<Post> posts = lambdaQuery().in(Post::getCode, codes).list();
        codes = posts.stream().map(Post::getCode).distinct().collect(Collectors.toList());
        // 去除表头 行数从1起算
        int rowIndex = 1;
        Iterator<Post> iterator = list.iterator();
        while (iterator.hasNext()) {
            Post post = iterator.next();
            boolean isError = false;
            ImportExcelError.ImportExcelErrorBuilder builder = ImportExcelError.builder();
            if (StrUtil.isEmpty(post.getCode())) {
                isError = true;
                builder.columnIndex(0).rowIndex(rowIndex).msg("岗位编码不能为空");
                errors.add(builder.build());
            } else if (post.getCode().length() < 1 || post.getCode().length() > 20) {
                isError = true;
                builder.columnIndex(1).rowIndex(rowIndex).msg("岗位编码在1-20个字符之间");
                errors.add(builder.build());
            }
            if (codes.contains(post.getCode())) {
                isError = true;
                builder.columnIndex(0).rowIndex(rowIndex).msg("岗位编码已存在");
                errors.add(builder.build());
            }
            if (StrUtil.isEmpty(post.getName())) {
                isError = true;
                builder.columnIndex(1).rowIndex(rowIndex).msg("岗位名称不能为空");
                errors.add(builder.build());
            } else if (post.getName().length() < 1 || post.getName().length() > 20) {
                isError = true;
                builder.columnIndex(1).rowIndex(rowIndex).msg("岗位名称在1-20个字符之间");
                errors.add(builder.build());
            }
            if (post.getStatus() == null) {
                isError = true;
                builder.columnIndex(2).rowIndex(rowIndex).msg("岗位状态不能为空");
                errors.add(builder.build());
            }
            // 这一行有错误，行数增加，错误数据加到list，删除原list的数据
            if (isError) {
                rowIndex++;
                errorList.add(post);
                iterator.remove();
            } else {
                // 没有错误，会进行新增，加进去不存在的，防止后续存在
                codes.add(post.getCode());
            }
        }
        saveBatch(list);
    }

}
