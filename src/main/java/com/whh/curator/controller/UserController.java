package com.whh.curator.controller;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whh.curator.domain.po.PageResult;
import com.whh.curator.domain.po.User;
import com.whh.curator.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping("list")
    public PageResult<User> list(Integer pageSize, Integer currentPage) {
        Page<User> page = Page.of(currentPage, pageSize);
        userService.page(page.addOrder(new OrderItem("username", true)));
        return new PageResult<>(page.getTotal(), page.getRecords());
    }
}
