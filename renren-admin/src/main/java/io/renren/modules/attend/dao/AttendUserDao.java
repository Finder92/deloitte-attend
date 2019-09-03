package io.renren.modules.attend.dao;

import io.renren.modules.attend.entity.AttendUserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 
 * 
 * @author Finder
 * @email zhangzhili92@163.com
 * @date 2019-07-31 14:14:11
 */
@Mapper
public interface AttendUserDao extends BaseMapper<AttendUserEntity> {

    boolean updateSignState(Long attendUserid);

    boolean batchUpdateSignState(List<Long> userId);

    List<AttendUserEntity> queryByAttendUserName(String attendUserName);
}
