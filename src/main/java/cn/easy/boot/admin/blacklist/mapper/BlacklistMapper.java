package cn.easy.boot.admin.blacklist.mapper;

import cn.easy.boot.admin.blacklist.entity.Blacklist;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author zoe
* @date 2023/08/01
* @description 黑名单 Mapper接口
*/
@Mapper
public interface BlacklistMapper extends BaseMapper<Blacklist> {

}
