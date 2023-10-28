package com.easy.boot.admin.menu.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easy.boot.admin.menu.entity.*;
import com.easy.boot.admin.menu.mapper.MenuMapper;
import com.easy.boot.admin.menu.service.IMenuService;
import com.easy.boot.common.base.BaseEntity;
import com.easy.boot.exception.BusinessException;
import com.easy.boot.utils.BeanUtil;
import com.easy.boot.utils.JsonUtil;
import lombok.NonNull;
import lombok.var;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
* @author zoe
* @date 2023/07/30
* @description 菜单 服务实现类
*/
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Override
    public Menu getRoot() {
        return lambdaQuery().eq(Menu::getParentId, 0).one();
    }

    @Override
    public List<Menu> selectListByParentId(@NonNull Long parentId) {
        return lambdaQuery().eq(Menu::getParentId, parentId).list();
    }

    @Override
    public List<String> selectPermissionsByIds(List<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return new ArrayList<>();
        }
        var list = lambdaQuery()
                .select(BaseEntity::getId, Menu::getPermission)
                .in(BaseEntity::getId, ids)
                .list();
        return list.stream().map(Menu::getPermission).distinct().collect(Collectors.toList());
    }

    @Override
    public List<MenuTree> all() {
        MenuTreeQuery query = MenuTreeQuery.builder()
                .parentId(0L)
                .build();
        return treeList(query);
    }

    @Override
    public List<Menu> selectMenuAll() {
        return lambdaQuery()
                .eq(Menu::getStatus, 1)
                .list();
    }

    @Override
    public List<MenuTree> treeList(MenuTreeQuery query) {
        List<Menu> list = lambdaQuery()
                .eq(query.getStatus() != null, Menu::getStatus, query.getStatus())
                .list();
        if (CollUtil.isEmpty(list)) {
            return new ArrayList<>();
        }
        // 转换list，直接去数据库查可以省略这一步
        List<MenuTree> treeList = list.stream().map(item -> BeanUtil.copyBean(item, MenuTree.class)).collect(Collectors.toList());
        Map<Long, List<MenuTree>> map = treeList.stream().collect(Collectors.groupingBy(Menu::getParentId));
        treeList.forEach(item -> {
            List<MenuTree> children = map.get(item.getId());
            if (children == null) {
                children = new ArrayList<>();
            }
            // 根据sort升序排序，再根据createTime降序排序
            children.sort(Comparator.comparing(MenuTree::getSort)
                    .thenComparing(BaseEntity::getCreateTime, Comparator.reverseOrder()));
            item.setChildren(children);
        });
        if (query.getParentId() != null) {
            treeList.removeIf(res -> !res.getParentId().equals(query.getParentId()));
        }
        return treeList;
    }

    @Override
    public IPage<Menu> selectPage(MenuQuery query) {
        Page<Menu> page = new Page<>(query.getPageNum(), query.getPageSize());
        return lambdaQuery()
                .and(StrUtil.isNotEmpty(query.getKeyword()), keywordQuery -> {
                    keywordQuery.like(Menu::getLabel, query.getKeyword()).or()
                            .like(Menu::getPermission, query.getKeyword());
                })
                .eq(Objects.nonNull(query.getParentId()), Menu::getParentId, query.getParentId())
                .eq(Objects.nonNull(query.getType()), Menu::getType, query.getType())
                .eq(Objects.nonNull(query.getStatus()), Menu::getStatus, query.getStatus())
                .eq(Objects.nonNull(query.getShowStatus()), Menu::getShowStatus, query.getShowStatus())
                .like(StrUtil.isNotEmpty(query.getLabel()) , Menu::getLabel, query.getLabel())
                .like(StrUtil.isNotEmpty(query.getPermission()) , Menu::getPermission, query.getPermission())
                .between(Objects.nonNull(query.getStartTime()) && Objects.nonNull(query.getEndTime()),
                        BaseEntity::getCreateTime, query.getStartTime(), query.getEndTime())
                .orderByAsc(Menu::getSort)
                .orderByDesc(BaseEntity::getCreateTime)
                .page(page);
    }

    @Override
    public List<MenuLazyVO> selectList(MenuTreeLazyQuery query) {
        List<Menu> list = lambdaQuery()
                .and(StrUtil.isNotEmpty(query.getKeyword()), keywordQuery -> {
                    keywordQuery.like(Menu::getLabel, query.getKeyword()).or()
                            .like(Menu::getPermission, query.getKeyword());
                })
                .eq(Objects.nonNull(query.getParentId()), Menu::getParentId, query.getParentId())
                .eq(Objects.nonNull(query.getType()), Menu::getType, query.getType())
                .eq(Objects.nonNull(query.getStatus()), Menu::getStatus, query.getStatus())
                .eq(Objects.nonNull(query.getShowStatus()), Menu::getShowStatus, query.getShowStatus())
                .like(StrUtil.isNotEmpty(query.getLabel()) , Menu::getLabel, query.getLabel())
                .like(StrUtil.isNotEmpty(query.getPermission()) , Menu::getPermission, query.getPermission())
                .between(Objects.nonNull(query.getStartTime()) && Objects.nonNull(query.getEndTime()),
                        BaseEntity::getCreateTime, query.getStartTime(), query.getEndTime())
                .orderByAsc(Menu::getSort)
                .orderByDesc(BaseEntity::getCreateTime)
                .list();
        if (CollUtil.isEmpty(list)) {
            return new ArrayList<>();
        }
        List<MenuLazyVO> voList = JsonUtil.copyList(list, MenuLazyVO.class);
        List<Long> ids = voList.stream().map(BaseEntity::getId).collect(Collectors.toList());
        List<Menu> menus = lambdaQuery().select(Menu::getParentId).in(Menu::getParentId, ids).list();
        Set<Long> parentIds = menus.stream().map(Menu::getParentId).collect(Collectors.toSet());
        voList.forEach(item -> {
            item.setIsLeaf(!parentIds.contains(item.getId()));
        });
        return voList;
    }

    @Override
    public Menu detail(Long id) {
        return getById(id);
    }

    @Override
    public Boolean create(MenuCreateDTO dto) {
        if (dto.getParentId() == 0) {
            Menu menu = this.getRoot();
            if (menu != null) {
                throw new BusinessException("已存在最上级菜单");
            }
        } else {
            Menu menu = this.getById(dto.getParentId());
            if (menu == null) {
                throw new BusinessException("上级菜单不存在");
            }
        }
        Menu entity = BeanUtil.copyBean(dto, Menu.class);
        validateMenu(entity);
        return save(entity);
    }

    /**
     * 校验菜单
     * @param menu
     */
    private void validateMenu(Menu menu) {
        if (menu.getType() == 1) {
            // 校验目录
            if (StrUtil.isEmpty(menu.getLabel())) {
                throw new BusinessException("菜单名称不能为空");
            }
            if (StrUtil.isEmpty(menu.getPath())) {
                throw new BusinessException("路由地址不能为空");
            }
        } else if (menu.getType() == 2) {
            // 校验菜单
            if (StrUtil.isEmpty(menu.getLabel())) {
                throw new BusinessException("菜单名称不能为空");
            }
            if (StrUtil.isEmpty(menu.getPath())) {
                throw new BusinessException("路由地址不能为空");
            }
            if (menu.getIsLink() != null && menu.getIsLink() == 2) {
                if (StrUtil.isEmpty(menu.getComponent())) {
                    throw new BusinessException("组件路径不能为空");
                }
                if (StrUtil.isEmpty(menu.getPermission())) {
                    throw new BusinessException("权限字符不能为空");
                }
                if (menu.getCache() == 1 && StrUtil.isEmpty(menu.getName())) {
                    throw new BusinessException("路由名称不能为空");
                }
            }
        } else if (menu.getType() == 3) {
            // 校验接口
            if (StrUtil.isEmpty(menu.getLabel())) {
                throw new BusinessException("菜单名称不能为空");
            }
            if (StrUtil.isEmpty(menu.getPermission())) {
                throw new BusinessException("权限字符不能为空");
            }
        }
    }

    @Override
    public Boolean updateById(MenuUpdateDTO dto) {
        Menu menu = getById(dto.getId());
        if (menu == null) {
            throw new BusinessException("当前菜单不存在");
        }
        if (menu.getParentId() == 0 && menu.getParentId() != null && menu.getParentId() != 0) {
            throw new BusinessException("最上级菜单不可选择上级菜单");
        }
        if (Objects.nonNull(dto.getParentId())) {
            if (dto.getParentId() == 0) {
                menu = this.getRoot();
                if (menu != null && !menu.getId().equals(dto.getId())) {
                    throw new BusinessException("已存在最上级菜单");
                }
            } else {
                menu = this.getById(dto.getParentId());
                if (menu == null) {
                    throw new BusinessException("上级菜单不存在");
                }
            }
        }
        Menu entity = BeanUtil.copyBean(dto, Menu.class);
        validateMenu(entity);
        return updateById(entity);
    }

    @Override
    public Boolean deleteById(Long id) {
        List<Menu> list = this.selectListByParentId(id);
        if (CollUtil.isNotEmpty(list)) {
            throw new BusinessException("存在子菜单，不允许删除");
        }
        return removeById(id);
    }

}
