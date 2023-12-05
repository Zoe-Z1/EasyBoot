package cn.easy.boot3.admin.dataDict.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import jakarta.validation.constraints.NotNull;

/**
* @author zoe
* @date 2023/08/01
* @description 数据字典 DTO
*/
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "DataDict对象", description = "数据字典")
public class DataDictUpdateDTO extends DataDictCreateDTO {

    @NotNull(message = "字典ID不能为空")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "字典ID")
    private Long id;

}
