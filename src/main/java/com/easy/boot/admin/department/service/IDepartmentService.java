package com.easy.boot.admin.department.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.easy.boot.admin.department.entity.*;
import lombok.NonNull;

import java.util.List;

/**
* @author zoe
* @date 2023/07/29
* @description 部门 服务类
*/
public interface IDepartmentService extends IService<Department> {

    /**
     * 获取最上级部门
     * @return
     */
    Department getRoot();

    /**
     * 根据父级部门ID查询子部门
     * @param parentId
     * @return
     */
    List<Department> selectListByParentId(@NonNull Long parentId);

    /**
     * 查询部门
     * @param query
     * @return
     */
    List<DepartmentLazyVO> selectList(DepartmentLazyQuery query);

    /**
     * 获取部门详情
     * @param id
     * @return
     */
    Department detail(Long id);

    /**
     * 获取当前部门的所有父级部门ID
     * @param id 部门ID
     * @return
     */
    List<Long> getParentIds(Long id);

    /**
    * 创建部门
    * @param dto
    * @return
    */
    Boolean create(DepartmentCreateDTO dto);

    /**
    * 编辑部门
    * @param dto
    * @return
    */
    Boolean updateById(DepartmentUpdateDTO dto);

    /**
     * 删除部门
     * @param id
     * @return
     */
    Boolean deleteById(Long id);

}
