package com.ahnchan.jsondiff.vo;

import com.ahnchan.jsondiff.ExcludeAudit;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class User extends Base {
    private String name;
    private Integer age;
    private Character gender;
    @ExcludeAudit
    private Double bmi;
    @ExcludeAudit
    private List<UserHistory> history;
}
