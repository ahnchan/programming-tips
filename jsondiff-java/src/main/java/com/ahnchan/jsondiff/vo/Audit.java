package com.ahnchan.jsondiff.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Audit extends Base {
    private String key;
    private Object before;
    private Object after;
}
