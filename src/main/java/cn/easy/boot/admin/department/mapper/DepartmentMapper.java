package cn.easy.boot.admin.department.mapper;

import cn.easy.boot.admin.department.entity.Department;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author zoe
* @date 2023/07/29
* @description 部门 Mapper接口
*/
@Mapper
public interface DepartmentMapper extends BaseMapper<Department> {

}
