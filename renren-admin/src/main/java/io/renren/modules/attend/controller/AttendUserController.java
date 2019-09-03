package io.renren.modules.attend.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.attend.entity.AttendUserEntity;
import io.renren.modules.attend.service.AttendUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * @author Finder
 * @email zhangzhili92@163.com
 * @date 2019-07-31 14:14:11
 */
@RestController
@RequestMapping("attend/attenduser")
@Slf4j
public class AttendUserController {
    @Autowired
    private AttendUserService attendUserService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("attend:attenduser:list")
    public R list(@RequestParam Map<String, Object> params) {
        log.info("params:{}",params);
        PageUtils page = attendUserService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attendUserid}")
    @RequiresPermissions("attend:attenduser:info")
    public R info(@PathVariable("attendUserid") Long attendUserid) {
        AttendUserEntity attendUser = attendUserService.getById(attendUserid);

        return R.ok().put("attendUser", attendUser);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("attend:attenduser:save")
    public R save(@RequestBody AttendUserEntity attendUser) {
        attendUserService.save(attendUser);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("attend:attenduser:update")
    public R update(@RequestBody AttendUserEntity attendUser) {
        ValidatorUtils.validateEntity(attendUser);
        attendUserService.updateById(attendUser);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("attend:attenduser:delete")
    public R delete(@RequestBody Long[] attendUserids) {
        attendUserService.removeByIds(Arrays.asList(attendUserids));

        return R.ok();
    }

    /**
     * @Author Finder
     * @Description //TODO 签到
     * @Date 17:40 2019/8/1
     * @Param
     * @return
     */
    @RequestMapping("/sign")
    @RequiresPermissions("attend:attenduser:sign")
    public R sign(@RequestBody Long attendUserid){
        attendUserService.updateSignState(attendUserid);
        return R.ok();
    }
/**
 * @Author Finder
 * @Description //TODO 批量操作
 * @Date 17:54 2019/8/1
 * @Param 
 * @return 
 */
    @RequestMapping("/batchSign")
    @RequiresPermissions("attend:attenduser:batchSign")
    public R batchSign(@RequestBody Long [] newBatchAttendUserIds){
        log.info("newBatchAttendUserIds:{},Arrays.toString(newBatchAttendUserIds):{}",newBatchAttendUserIds.length,Arrays.toString(newBatchAttendUserIds));
        if(newBatchAttendUserIds.length==0){
            return R.error(1002,"请至少选择一条未签到记录");
        }
        attendUserService.batchUpdateSignState(Arrays.asList(newBatchAttendUserIds));
        return R.ok();
    }

    /**
     * @Author Finder
     * @Description //TODO 查询用户
     * @Date 23:07 2019/8/1
     * @Param 
     * @return 
     */
    @RequestMapping("/find")
    public R find(@RequestBody String attendUssname){
        List<AttendUserEntity> attendUserEntityList = attendUserService.queryByAttendUserName(attendUssname);
        if(attendUserEntityList.size()<=0){
            return R.error(1001,"查无记录");
        }
        PageUtils page = new PageUtils(attendUserEntityList,attendUserEntityList.size(),10,1);
        return R.ok().put("page",page);
    }

}
