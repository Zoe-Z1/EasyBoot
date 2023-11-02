package com.easy.boot.admin.post.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.easy.boot.admin.post.entity.Post;
import com.easy.boot.admin.post.entity.PostCreateDTO;
import com.easy.boot.admin.post.entity.PostQuery;
import com.easy.boot.admin.post.entity.PostUpdateDTO;
import com.easy.boot.common.excel.entity.ImportExcelError;

import java.util.List;

/**
* @author zoe
* @date 2023/07/29
* @description 岗位 服务类
*/
public interface IPostService extends IService<Post> {

    /**
     * 获取岗位列表
     * @return
     */
    List<Post> selectAll();

    /**
    * 查询岗位
    * @param query
    * @return
    */
    IPage<Post> selectPage(PostQuery query);

    /**
     * 获取岗位详情
     * @param id
     * @return
     */
    Post detail(Long id);

    /**
    * 创建岗位
    * @param dto
    * @return
    */
    Boolean create(PostCreateDTO dto);

    /**
    * 编辑岗位
    * @param dto
    * @return
    */
    Boolean updateById(PostUpdateDTO dto);

    /**
     * 删除岗位
     * @param id
     * @return
     */
    Boolean deleteById(Long id);

    /**
     * 批量删除岗位
     * @param ids
     * @return
     */
    Boolean deleteBatchByIds(List<Long> ids);

    /**
     * 导入Excel
     * @param list 要导入的数据集合
     * @param errorList 导入错误的数据集合
     * @param errors 错误标注集合
     */
    void importExcel(List<Post> list, List<Post> errorList, List<ImportExcelError> errors);

    /**
     * 根据用户ID获取未被禁用的岗位ID集合
     * @param userId
     * @return
     */
    List<Long> selectNotDisabledIdsByUserId(Long userId);
    
    /**
     * 根据岗位ID集合获取未被禁用的岗位集合
     * @param postIds
     * @return
     */
    List<Post> selectNotDisabledListInPostIds(List<Long> postIds);

    /**
     * 根据岗位ID集合获取未被禁用的岗位ID集合
     * @param postIds
     * @return
     */
    List<Long> selectNotDisabledIdsInPostIds(List<Long> postIds);

}
