package io.renren.modules.attend.dto.subject;

import lombok.Data;

import java.util.List;

/**
 * @ClassName SubjectDataDTO
 * @Description TODO
 * @Author Finder
 * @Date 2019/8/11 17:31
 **/
@Data
public class SubjectDataDTO {
        private String avatar;
        private Object birthday;
        private String come_from;
        private int company_id;
        private int create_time;
        private String department;
        private String description;
        private String email;
        private int end_time;
        private int entry_date;
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
        private List<PhotosDTO> photos;

    }

