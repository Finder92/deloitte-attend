package io.renren.modules.attend.dto.history;

import lombok.Data;

/**
 * @ClassName HistoryRequestDto
 * @Description TODO
 * @Author Finder
 * @Date 2019/8/2 16:59
 **/
@Data
public class HistoryRequestDto {
    //开始时间时间戳（秒）
    private int start;
    //结束时间时间戳（秒）
    private int end;
    //	角色 {不传: 所有, 0: 员工(默认)， 1: 访客, 2: VIP}
    private int user_role;
    //按用户名模糊查询
    private String user_name;
    //	按门禁ID查询
    private int screen_id;
    //按用户ID精确查询
    private int subjectt_id;
    //第几页 默认 1
    private int page;
    //每页数量 默认 10，最大1000，大于1000会使用1000
    private int size;
}
