package io.renren.modules.attend.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author Finder
 * @email zhangzhili92@163.com
 * @date 2019-07-31 14:14:11
 */
@Data
@TableName("attend_user")
public class AttendUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long attendUserid;
	/**
	 * 0:用户 1陌生人
	 */
	private String attendUsertype;
	/**
	 * 与会人员姓名
	 */
	private String attendUssname;
	/**
	 * 签到状态1:已签到 0未签到
	 */
	private String attendSignstate;
	/**
	 * 签到时间
	 */
	private Date attendSigntime;
	/**
	 * 人脸识别记录的时间
	 */
	private Date attendCreatetime;

	/**
	 * 识别照片
	 */
	private String attendPictureUrl;

}
