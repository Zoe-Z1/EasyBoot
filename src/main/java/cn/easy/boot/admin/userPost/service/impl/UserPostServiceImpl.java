package cn.easy.boot.admin.userPost.service.impl;

import cn.easy.boot.admin.userPost.entity.UserPost;
import cn.easy.boot.admin.userPost.mapper.UserPostMapper;
import cn.easy.boot.admin.userPost.service.IUserPostService;
import cn.easy.boot.common.base.BaseEntity;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author zoe
* @date 2023/07/29
* @description 用户岗位关联 服务实现类
*/
@Service
public class UserPostServiceImpl extends ServiceImpl<UserPostMapper, UserPost> implements IUserPostService {


    @Override
    public List<Long> selectIdsByUserId(Long userId) {
        List<UserPost> list = lambdaQuery()
                .select(BaseEntity::getId, UserPost::getPostId)
                .eq(UserPost::getUserId, userId)
                .orderByDesc(BaseEntity::getCreateTime)
                .list();
        return list.stream().map(UserPost::getPostId).distinct().collect(Collectors.toList());
    }

    @Override
    public List<UserPost> selectListByUserId(@NonNull Long userId) {
        return lambdaQuery().eq(UserPost::getUserId, userId)
                .orderByDesc(BaseEntity::getCreateTime)
                .list();
    }

    @Override
    public List<UserPost> selectListByPostIds(List<Long> postIds) {
        if (CollUtil.isEmpty(postIds)) {
            return new ArrayList<>();
        }
        return lambdaQuery().in(UserPost::getPostId, postIds).list();
    }

    @Override
    public Boolean deleteByUserId(Long userId) {
        List<Long> ids = new ArrayList<>();
        ids.add(userId);
        return deleteBatchByUserIds(ids);
    }

    @Override
    public Boolean deleteBatchByUserIds(List<Long> userIds) {
        if (CollUtil.isEmpty(userIds)) {
            return false;
        }
        QueryWrapper<UserPost> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("user_id", userIds);
        return remove(queryWrapper);
    }

    @Override
    public Boolean deleteByPostId(Long postId) {
        List<Long> ids = new ArrayList<>();
        ids.add(postId);
        return deleteBatchByPostIds(ids);
    }

    @Override
    public Boolean deleteBatchByPostIds(List<Long> postIds) {
        if (CollUtil.isEmpty(postIds)) {
            return false;
        }
        QueryWrapper<UserPost> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("post_id", postIds);
        return remove(queryWrapper);
    }

    @Override
    public Boolean userBindPost(List<Long> ids, Long userId) {
        if (CollUtil.isEmpty(ids)) {
            return false;
        }
        List<UserPost> list = ids.stream()
                .map(id -> UserPost.builder().postId(id).userId(userId).build())
                .collect(Collectors.toList());
        return saveBatch(list);
    }

}
