package com.whh.curator.controller;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whh.curator.domain.po.PageResult;
import com.whh.curator.domain.po.User;
import com.whh.curator.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping("/list")
    public PageResult<User> list(Integer pageSize, Integer currentPage) {
        Page<User> page = Page.of(currentPage, pageSize);
        userService.page(page.addOrder(new OrderItem("username", true)));
        return new PageResult<>(page.getTotal(), page.getRecords());
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable String id){
        return userService.getById(id);
    }

    @PostMapping
    public void save(@RequestBody User user){
        userService.save(user);
    }

    @PutMapping
    public void updateById(@RequestBody User user){
        userService.updateById(user);
    }

    @DeleteMapping("/{id}")
    public void removeById(@PathVariable Integer id){
        userService.removeById(id);
    }
}
