package io.renren.modules.attend.dto.history;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


import java.util.List;

/**
 * @ClassName SubjectDto
 * @Description TODO
 * @Author Finder
 * @Date 2019/7/29 13:21
 **/
@Data
public class SubjectDto {
    private String avatar;
    private Object birthday;
    private String come_from;
    private int company_id;
    private int create_time;
    private String department;
    private String description;
    private String email;
    private int end_time;
    private Object entry_date;
    private int gender;
    private int id;
    private String interviewee;
    private String interviewee_pinyin;
    private String job_number;
    private String name;
    private boolean password_reseted;
    private String phone;
    private String pinyin;
    private int purpose;
    private String remark;
    private int start_time;
    private int subject_type;
    private String title;
    private boolean visit_notify;
    @JsonProperty("photos")
    private List<PhotosDto> photos;

}
