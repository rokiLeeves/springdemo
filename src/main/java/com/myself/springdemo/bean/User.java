package com.myself.springdemo.bean;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * @author 45319
 */
@Data
@NoArgsConstructor
@TableName("user")
//@KeySequence(value = "SEQ_MYSQL_LONG_KEY", dbType =DbType.MYSQL)
public class User implements Serializable {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String name;
    private Integer age;
    @TableField(value = "email",fill = FieldFill.INSERT)
    private String email;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime creationDate;
    @TableField(fill=FieldFill.UPDATE)
    private LocalDateTime updateDate;

    @Version
    @TableField(fill = FieldFill.INSERT)
    private Integer version;


}