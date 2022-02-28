package com.myself.springdemo.bean.select;

import lombok.Data;

@Data
public class SelectInfo {
    public Integer page;
    public Integer limit;
    public String orderFiled;
    public Boolean sortive;
}
