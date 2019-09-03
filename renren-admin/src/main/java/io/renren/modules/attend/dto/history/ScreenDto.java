package io.renren.modules.attend.dto.history;

import lombok.Data;

/**
 * @ClassName ScreenDto
 * @Description TODO
 * @Author Finder
 * @Date 2019/7/29 13:21
 **/
@Data
public class ScreenDto {
    private boolean allow_all_subjects;
    private boolean allow_visitor;
    private String box_address;
    private int box_heartbeat;
    private String box_status;
    private String box_token;
    private String camera_address;
    private String camera_name;
    private String camera_position;
    private String camera_status;
    private Object description;
    private int id;
    private int is_select;
    private String network_switcher;
    private int network_switcher_drive;
    private Object network_switcher_status;
    private Object network_switcher_token;
    private String screen_token;
    private int type;

}
