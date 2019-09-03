package io.renren.modules.attend.dto.video;

import lombok.Data;

import java.util.List;

/**
 * @ClassName ScreenDTO
 * @Description TODO
 * @Author Finder
 * @Date 2019/7/30 18:16
 **/
@Data
public class ScreenDTO {
    private Object network_switcher_status;
    private int camera_status;
    private String box_token;
    private Object description;
    private boolean allow_all_subjects;
    private String network_switcher;
    private String camera_name;
    private int box_heartbeat;
    private boolean allow_visitor;
    private String screen_token;
    private Object network_switcher_token;
    private String box_status;
    private int is_select;
    private String camera_position;
    private int network_switcher_drive;
    private int type;
    private int id;
    private String camera_address;
    private String box_address;
    private List<?> allowed_subject_ids;

}
