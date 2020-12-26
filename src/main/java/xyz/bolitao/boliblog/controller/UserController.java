package xyz.bolitao.boliblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.bolitao.boliblog.entity.dbentity.MUser;
import xyz.bolitao.boliblog.service.UserService;

/**
 * @author bolitao
 */
@RestController
@RequestMapping(value = "user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "test/{id}")
    public MUser testGetOne(@PathVariable Long id) {
        return userService.getById(id);
    }
}
