package cn.easy.boot.admin.post.mapper;

import cn.easy.boot.admin.post.entity.Post;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author zoe
* @date 2023/07/29
* @description 岗位 Mapper接口
*/
@Mapper
public interface PostMapper extends BaseMapper<Post> {

}
