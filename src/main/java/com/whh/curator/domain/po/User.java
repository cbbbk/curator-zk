package com.whh.curator.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(autoResultMap = true, value = "users")
public class User {
    @TableId(type = IdType.AUTO)
    private Integer userid;
    private String username;
    private String userpwd;
    private String truename;
    private Integer classid;
}
