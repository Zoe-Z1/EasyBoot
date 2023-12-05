package cn.easy.boot3.admin.home.entity;

import cn.easy.boot3.common.Jackson.ToStringListSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * @author zoe
 * @date 2023/7/21
 * @description
 */
@Schema(title = "接口处理时长视图实体")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class HomeHandlerTimeVO {

    @Schema(title = "请求接口地址集合")
    private List<String> urls;

    @JsonSerialize(using = ToStringListSerializer.class)
    @Schema(title = "请求次数集合")
    private List<Long> times;

}
