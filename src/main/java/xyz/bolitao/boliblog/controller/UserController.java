package xyz.bolitao.boliblog.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.bolitao.boliblog.entity.dbentity.MUser;
import xyz.bolitao.boliblog.service.UserService;
import xyz.bolitao.boliblog.util.Result;

/**
 * @author bolitao
 */
@RestController
@RequestMapping(value = "user")
@Api(tags = "user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "user api get test")
    @GetMapping(value = "test/{id}")
    @RequiresAuthentication
    public ResponseEntity<Result<MUser>> testGetOne(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(new Result<>("1", userService.getById(id)));
    }

    @ApiOperation(value = "save")
    @PostMapping(value = "/save")
    public ResponseEntity<Result<Boolean>> save(@Validated @RequestBody MUser user) {
        boolean dealResult = userService.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(new Result<>("1",dealResult));
    }
}
