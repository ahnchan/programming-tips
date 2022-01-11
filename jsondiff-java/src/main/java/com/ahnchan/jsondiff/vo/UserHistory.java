package com.ahnchan.jsondiff.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class UserHistory extends Base {
    private Timestamp date;
    private Double height;
    private Double weight;
    private Double bmi;
}
