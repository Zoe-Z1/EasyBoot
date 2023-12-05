package cn.easy.boot3.admin.userPost.entity;

import cn.easy.boot3.common.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
* @author zoe
* @date 2023/07/29
* @description 用户岗位关联 实体
*/
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@TableName("user_post")
@Schema(title = "UserPost对象", description = "用户岗位关联")
public class UserPost extends BaseEntity {

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(title = "用户ID")
    @TableField("user_id")
    private Long userId;

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(title = "岗位ID")
    @TableField("post_id")
    private Long postId;
}
