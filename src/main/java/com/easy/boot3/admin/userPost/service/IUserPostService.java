package com.easy.boot3.admin.userPost.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.easy.boot3.admin.userPost.entity.UserPost;

import java.util.List;

/**
* @author zoe
* @date 2023/07/29
* @description 用户岗位关联 服务类
*/
public interface IUserPostService extends IService<UserPost> {

    /**
     * 查询用户绑定的所有岗位ID
     * @param userId
     * @return
     */
    List<Long> selectIdsByUserId(Long userId);

    /**
    * 查询用户岗位关联
    * @param userId
    * @return
    */
    List<UserPost> selectListByUserId(Long userId);


    /**
     * 根据岗位ID集合查询用户岗位关联
     * @param postIds
     * @return
     */
    List<UserPost> selectListByPostIds(List<Long> postIds);

    /**
     * 根据用户ID删除用户岗位关联
     * @param userId
     * @return
     */
    Boolean deleteByUserId(Long userId);

    /**
     * 根据用户ID集合删除用户岗位关联
     * @param userIds
     * @return
     */
    Boolean deleteBatchByUserIds(List<Long> userIds);

    /**
     * 根据岗位ID删除用户岗位关联
     * @param postId
     * @return
     */
    Boolean deleteByPostId(Long postId);

    /**
     * 根据岗位ID集合删除用户岗位关联
     * @param postIds
     * @return
     */
    Boolean deleteBatchByPostIds(List<Long> postIds);

    /**
     * 用户绑定岗位
     * @param ids
     * @param userId
     * @return
     */
    Boolean userBindPost(List<Long> ids, Long userId);

}
