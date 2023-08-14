package com.easy.boot.admin.userPost.mapper;

import com.easy.boot.admin.userPost.entity.UserPost;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author zoe
* @date 2023/07/29
* @description 用户岗位关联 Mapper接口
*/
@Mapper
public interface UserPostMapper extends BaseMapper<UserPost> {

}
