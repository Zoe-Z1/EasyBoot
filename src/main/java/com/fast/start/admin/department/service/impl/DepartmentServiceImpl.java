package com.fast.start.admin.department.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fast.start.admin.department.entity.*;
import com.fast.start.admin.department.mapper.DepartmentMapper;
import com.fast.start.admin.department.service.IDepartmentService;
import com.fast.start.common.base.BaseEntity;
import com.fast.start.exception.BusinessException;
import com.fast.start.utils.BeanUtil;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
* @author zoe
* @date 2023/07/29
* @description 部门 服务实现类
*/
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

    @Override
    public Department getRoot() {
        return lambdaQuery().eq(Department::getParentId, 0).one();
    }

    @Override
    public List<Department> selectListByParentId(@NonNull Long parentId) {
        return lambdaQuery().eq(Department::getParentId, parentId).list();
    }

    @Override
    public List<DepartmentTree> all() {
        DepartmentTreeQuery query = DepartmentTreeQuery.builder()
                .parentId(0L)
                .build();
        return treeList(query);
    }

    @Override
    public List<DepartmentTree> treeList(DepartmentTreeQuery query) {
        List<Department> list = lambdaQuery()
                .select(BaseEntity::getId,Department::getName,Department::getParentId,Department::getStatus, Department::getSort,BaseEntity::getCreateTime)
                .like(StrUtil.isNotEmpty(query.getName()), Department::getName, query.getName())
                .eq(Objects.nonNull(query.getStatus()), Department::getStatus, query.getStatus())
                .between(Objects.nonNull(query.getStartTime()) && Objects.nonNull(query.getEndTime()),
                        BaseEntity::getCreateTime, query.getStartTime(), query.getEndTime())
                .list();
        if (CollUtil.isEmpty(list)) {
            return new ArrayList<>();
        }
        // 转换list，直接去数据库查可以省略这一步
        List<DepartmentTree> treeList = list.stream().map(item -> BeanUtil.copyBean(item, DepartmentTree.class)).collect(Collectors.toList());
        Map<Long, List<DepartmentTree>> map = treeList.stream().collect(Collectors.groupingBy(Department::getParentId));
        List<DepartmentTree> empList = new ArrayList<>();
        treeList.forEach(item -> {
            List<DepartmentTree> children = map.get(item.getId());
            if (children == null) {
                children = empList;
            }
            // 根据sort升序排序，再根据createTime降序排序
            children.sort(Comparator.comparing(Department::getSort)
                    .thenComparing(BaseEntity::getCreateTime, Comparator.reverseOrder()));
            item.setChildren(children);
        });
        treeList.removeIf(res -> !res.getParentId().equals(query.getParentId()));
        return treeList;
    }

    @Override
    public IPage<Department> selectPage(DepartmentQuery query) {
        Page<Department> page = new Page<>(query.getPageNum(), query.getPageSize());
        return lambdaQuery()
                .like(StrUtil.isNotEmpty(query.getName()), Department::getName, query.getName())
                .eq(Objects.nonNull(query.getStatus()), Department::getStatus, query.getStatus())
                .eq(Objects.nonNull(query.getParentId()), Department::getParentId, query.getParentId())
                .between(Objects.nonNull(query.getStartTime()) && Objects.nonNull(query.getEndTime()),
                        BaseEntity::getCreateTime, query.getStartTime(), query.getEndTime())
                .orderByAsc(Department::getSort)
                .orderByDesc(BaseEntity::getCreateTime)
                .page(page);
    }

    @Override
    public Department detail(Long id) {
        return getById(id);
    }

    @Override
    public Boolean create(DepartmentCreateDTO dto) {
        if (dto.getParentId() == 0) {
            Department department = this.getRoot();
            if (department != null) {
                throw new BusinessException("已存在最上级部门");
            }
        } else {
            Department department = this.getById(dto.getParentId());
            if (department == null) {
                throw new BusinessException("上级部门不存在");
            }
        }
        Department department = lambdaQuery().eq(Department::getName, dto.getName())
                .eq(Department::getParentId, dto.getParentId())
                .one();
        if (Objects.nonNull(department)) {
            throw new BusinessException("当前部门下已存在相同名称的部门");
        }
        Department entity = BeanUtil.copyBean(dto, Department.class);
        return save(entity);
    }

    @Override
    public Boolean updateById(DepartmentUpdateDTO dto) {
        Department department = getById(dto.getId());
        if (department == null) {
            throw new BusinessException("当前部门不存在");
        }
        if (department.getParentId() == 0 && department.getParentId() != null && department.getParentId() != 0) {
            throw new BusinessException("最上级部门不可选择上级部门");
        }
        if (Objects.nonNull(dto.getParentId())) {
            if (dto.getParentId() == 0) {
                department = this.getRoot();
                if (department != null) {
                    throw new BusinessException("已存在最上级部门");
                }
            } else {
                department = this.getById(dto.getParentId());
                if (department == null) {
                    throw new BusinessException("上级部门不存在");
                }
            }
        }
        department = lambdaQuery().eq(Department::getName, dto.getName())
                .eq(Department::getParentId, dto.getParentId())
                .one();
        if (Objects.nonNull(department) && !department.getId().equals(dto.getId())) {
            throw new BusinessException("当前部门下已存在相同名称的部门");
        }
        Department entity = BeanUtil.copyBean(dto, Department.class);
        return updateById(entity);
    }

    @Override
    public Boolean deleteById(Long id) {
        List<Department> list = this.selectListByParentId(id);
        if (CollUtil.isNotEmpty(list)) {
            throw new BusinessException("部门存在子部门，不允许删除");
        }
        return removeById(id);
    }

}
