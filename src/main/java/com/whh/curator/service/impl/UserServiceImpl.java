package com.whh.curator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whh.curator.domain.po.User;
import com.whh.curator.mapper.UserMapper;
import com.whh.curator.service.IUserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
}
