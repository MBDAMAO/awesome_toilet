package com.damao.common.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> implements Serializable {
    private Long total;
    private Long size;
    private Long page;
    private HashMap<String, Object> params;
    private List<T> records;

    public PageResult(List<T> records, long total) {
        this.records = records;
        this.total = total;
    }
}
