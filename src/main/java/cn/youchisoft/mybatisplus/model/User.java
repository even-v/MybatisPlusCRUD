package cn.youchisoft.mybatisplus.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 用户Model,用户实体类
 * 
 * @author jiangjingwei@yysoft.org.cn
 * @Package cn.youchisoft.mybatisplus.model
 * @version Nov 21, 2019
 */

/*** @Data 注解是LomBok的注解，使用该注解可以自动生成getter、setter、toString、HashCode等方法 ***/
@Data

/*** @TableName 注解是 MybatisPlus的表名注解 ***/
@TableName("user")
public class User implements Serializable {

	/**
	 * 主键
	 */
	private String id;

	/**
	 * 是否删除。1：已删除，0：未删除
	 */
	/*** @TableField 注解是 MybatisPlus的子段注解(非主键) ***/
	@TableField("deleted")
	private Boolean deleted;

	/**
	 * 创建人
	 */
	@TableField("create_user")
	private String createUser;

	/**
	 * 修改人
	 */
	@TableField("update_user")
	private String updateUser;

	/**
	 * 创建时间
	 */
	@TableField("create_time")
	private Date createTime;

	/**
	 * 最后修改时间
	 */
	@TableField("update_time")
	private Date updateTime;

	/**
	 * 是否锁住，1：已锁住，0：未锁住
	 */
	@TableField("locked")
	private Boolean locked;

	/**
	 * 登录名
	 */
	@TableField("username")
	private String username;

	/**
	 * 密码
	 */
	@TableField("password")
	private String password;

}