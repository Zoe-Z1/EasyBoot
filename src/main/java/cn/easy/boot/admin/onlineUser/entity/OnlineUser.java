package cn.easy.boot.admin.onlineUser.entity;

import cn.easy.boot.common.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
* @author zoe
* @date 2023/08/02
* @description 在线用户 实体
*/
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@TableName("online_user")
@ApiModel("在线用户")
public class OnlineUser extends BaseEntity {

    @ApiModelProperty("登录用户账号")
    @TableField("username")
    private String username;

    @ApiModelProperty("ip地址")
    @TableField("ip")
    private String ip;

    @ApiModelProperty("登录浏览器")
    @TableField("browser")
    private String browser;

    @ApiModelProperty("操作系统")
    @TableField("os")
    private String os;

    @ApiModelProperty("浏览器引擎")
    @TableField("engine")
    private String engine;

    @ApiModelProperty("省")
    @TableField("pro")
    private String pro;

    @ApiModelProperty("省份编码")
    @TableField("pro_code")
    private String proCode;

    @ApiModelProperty("市")
    @TableField("city")
    private String city;

    @ApiModelProperty("市编码")
    @TableField("city_code")
    private String cityCode;

    @ApiModelProperty("地址")
    @TableField("addr")
    private String addr;

    @ApiModelProperty("登录token")
    @TableField("token")
    private String token;

}
