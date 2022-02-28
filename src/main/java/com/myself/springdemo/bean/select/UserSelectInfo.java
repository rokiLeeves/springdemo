package com.myself.springdemo.bean.select;

import lombok.Data;

@Data
public class UserSelectInfo extends SelectInfo{
     public Integer id;
     public String name;
     public String address;
     public Integer age;
}
