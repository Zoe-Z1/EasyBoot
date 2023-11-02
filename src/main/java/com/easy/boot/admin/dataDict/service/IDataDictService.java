package com.easy.boot.admin.dataDict.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.easy.boot.admin.dataDict.entity.DataDict;
import com.easy.boot.admin.dataDict.entity.DataDictCreateDTO;
import com.easy.boot.admin.dataDict.entity.DataDictQuery;
import com.easy.boot.admin.dataDict.entity.DataDictUpdateDTO;
import com.easy.boot.common.excel.entity.ImportExcelError;

import java.util.List;

/**
* @author zoe
* @date 2023/08/01
* @description 数据字典 服务类
*/
public interface IDataDictService extends IService<DataDict> {

    /**
    * 查询数据字典
    * @param query
    * @return
    */
    IPage<DataDict> selectPage(DataDictQuery query);

    /**
     * 根据字典域ID集合获取字典
     * @param ids
     * @return
     */
    List<DataDict> selectListByDomainIds(List<Long> ids);

    /**
     * 获取数据字典详情
     * @param id
     * @return
     */
    DataDict detail(Long id);

    /**
     * 根据字典域ID和字典键获取字典
     * @param domainId
     * @param code
     * @return
     */
    DataDict getByDomainIdAndCode(Long domainId, String code);

    /**
     * 根据字典域ID获取字典列表
     * @param domainId
     * @return
     */
    List<DataDict> getByDomainId(Long domainId);

    /**
    * 创建数据字典
    * @param dto
    * @return
    */
    Boolean create(DataDictCreateDTO dto);

    /**
    * 编辑数据字典
    * @param dto
    * @return
    */
    Boolean updateById(DataDictUpdateDTO dto);

    /**
     * 删除数据字典
     * @param id
     * @return
     */
    Boolean deleteById(Long id);

    /**
     * 批量删除数据字典
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
    void importExcel(List<DataDict> list, List<DataDict> errorList, List<ImportExcelError> errors);
}
