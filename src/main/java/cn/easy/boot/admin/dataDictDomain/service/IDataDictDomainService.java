package cn.easy.boot.admin.dataDictDomain.service;

import cn.easy.boot.admin.dataDict.entity.DataDict;
import cn.easy.boot.admin.dataDictDomain.entity.DataDictDomain;
import cn.easy.boot.admin.dataDictDomain.entity.DataDictDomainQuery;
import cn.easy.boot.admin.dataDictDomain.entity.DataDictDomainUpdateDTO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.easy.boot.admin.dataDictDomain.entity.DataDictDomainCreateDTO;
import cn.easy.boot.common.excel.entity.ImportExcelError;

import java.util.List;
import java.util.Map;

/**
* @author zoe
* @date 2023/08/01
* @description 数据字典域 服务类
*/
public interface IDataDictDomainService extends IService<DataDictDomain> {

    /**
    * 查询数据字典域
    * @param query
    * @return
    */
    IPage<DataDictDomain> selectPage(DataDictDomainQuery query);

    /**
     * 获取全部数据字典，以字典域编码为key
     * @return
     */
    Map<String, List<DataDict>> selectAll();

    /**
     * 获取数据字典域详情
     * @param id
     * @return
     */
    DataDictDomain detail(Long id);

    /**
     * 根据根据字典域编码获取字典域详情
     * @param code
     * @return
     */
    DataDictDomain getByCode(String code);

    /**
     * 根据根据字典域编码获取未禁用的字典域详情
     * @param code
     * @return
     */
    DataDictDomain getNotDisabledByCode(String code);

    /**
     * 根据根据字典域编码获取字典域列表
     * @param codes
     * @return
     */
    List<DataDictDomain> selectListByCodes(List<String> codes);

    /**
     * 根据字典域编码获取字典列表
     * @param code
     * @return
     */
    List<DataDict> selectListByDomainCode(String code);

    /**
    * 创建数据字典域
    * @param dto
    * @return
    */
    Boolean create(DataDictDomainCreateDTO dto);

    /**
    * 编辑数据字典域
    * @param dto
    * @return
    */
    Boolean updateById(DataDictDomainUpdateDTO dto);

    /**
     * 删除数据字典域
     * @param id
     * @return
     */
    Boolean deleteById(Long id);

    /**
     * 批量删除数据字典域
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
    void importExcel(List<DataDictDomain> list, List<DataDictDomain> errorList, List<ImportExcelError> errors);
}
