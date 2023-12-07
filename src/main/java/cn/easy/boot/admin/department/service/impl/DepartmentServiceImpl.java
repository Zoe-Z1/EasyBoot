package cn.easy.boot.admin.department.service.impl;

import cn.easy.boot.admin.department.entity.*;
import cn.easy.boot.admin.department.mapper.DepartmentMapper;
import cn.easy.boot.admin.department.service.IDepartmentService;
import cn.easy.boot.common.base.BaseEntity;
import cn.easy.boot.exception.BusinessException;
import cn.easy.boot.utils.BeanUtil;
import cn.easy.boot.utils.JsonUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
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
        return lambdaQuery().eq(Department::getParentId, parentId)
                .orderByAsc(Department::getSort)
                .orderByDesc(BaseEntity::getCreateTime)
                .list();
    }

    @Override
    public List<DepartmentLazyVO> selectList(DepartmentLazyQuery query) {
        List<Department> list = lambdaQuery()
                .and(StrUtil.isNotEmpty(query.getKeyword()), keywordQuery -> {
                    keywordQuery.like(Department::getName, query.getKeyword());
                })
                .eq(query.getStatus() != null, Department::getStatus, query.getStatus())
                .eq(query.getParentId() != null, Department::getParentId, query.getParentId())
                .eq(query.getId() != null, Department::getId, query.getId())
                .orderByAsc(Department::getSort)
                .orderByDesc(BaseEntity::getCreateTime)
                .list();
        if (CollUtil.isEmpty(list)) {
            return new ArrayList<>();
        }
        List<DepartmentLazyVO> voList = JsonUtil.copyList(list, DepartmentLazyVO.class);
        List<Long> ids = voList.stream().map(BaseEntity::getId).collect(Collectors.toList());
        List<Department> departments = lambdaQuery().select(Department::getParentId).in(Department::getParentId, ids).list();
        Set<Long> parentIds = departments.stream().map(Department::getParentId).collect(Collectors.toSet());
        voList.forEach(item -> {
            item.setIsLeaf(!parentIds.contains(item.getId()));
        });
        return voList;
    }

    @Override
    public Department detail(Long id) {
        return getById(id);
    }

    @Override
    public List<Long> getParentIds(Long id) {
        List<Long> ids = new ArrayList<>();
        Department department = getById(id);
        if (department != null && !department.getParentId().equals(0L)) {
            ids = getParentIds(department.getParentId(), ids);
        }
        return ids;
    }

    /**
     * 递归获取所有父级
     * @param id
     * @param ids
     * @return
     */
    private List<Long> getParentIds(Long id, List<Long> ids) {
        ids.add(0, id);
        Department department = getById(id);
        if (department == null || department.getParentId().equals(0L)) {
            return ids;
        }
        return getParentIds(department.getParentId(), ids);
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
        if (dto.getParentId() == null) {
            throw new BusinessException("上级部门不能为空");
        }
        if (department.getParentId() != null && department.getParentId() == 0 && dto.getParentId() != 0) {
            throw new BusinessException("最上级部门不可选择上级部门");
        }
        if (dto.getParentId() == 0) {
            if (department.getParentId() != 0) {
                department = this.getRoot();
                if (department != null) {
                    throw new BusinessException("已存在最上级部门");
                }
            }
        } else {
            department = this.getById(dto.getParentId());
            if (department == null) {
                throw new BusinessException("上级部门不存在");
            }
        }
        department = lambdaQuery().eq(Department::getName, dto.getName())
                .eq(Department::getParentId, dto.getParentId())
                .one();
        if (Objects.nonNull(department) && !department.getId().equals(dto.getId())) {
            throw new BusinessException("当前部门下已存在相同名称的部门");
        }
        if (dto.getId().equals(dto.getParentId())) {
            throw new BusinessException("上级部门不能是自己");
        }
        if (isChild(dto.getId(), dto.getParentId())) {
            throw new BusinessException("上级部门不能是自己的子部门");
        }
        Department entity = BeanUtil.copyBean(dto, Department.class);
        return updateById(entity);
    }

    @Override
    public Boolean deleteById(Long id) {
        List<Department> list = this.selectListByParentId(id);
        if (CollUtil.isNotEmpty(list)) {
            throw new BusinessException("存在子部门，不允许删除");
        }
        return removeById(id);
    }

    @Override
    public Boolean isChild(Long id, Long parentId) {
        List<Long> ids = getParentIds(parentId);
        if (ids.isEmpty()) {
            return false;
        }
        return ids.contains(id);
    }
}
