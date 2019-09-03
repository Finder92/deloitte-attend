package io.renren.modules.attend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.attend.entity.AttendUserEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author Finder
 * @email zhangzhili92@163.com
 * @date 2019-07-31 14:14:11
 */
public interface AttendUserService extends IService<AttendUserEntity> {

    PageUtils queryPage(Map<String, Object> params);

    boolean updateSignState(Long attendUserid);

    boolean batchUpdateSignState(List<Long> userId);

    List<AttendUserEntity> queryByAttendUserName(String attendUserName);
}

