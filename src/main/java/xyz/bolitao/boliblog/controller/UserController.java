package xyz.bolitao.boliblog.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public ResponseEntity<Result<MUser>> testGetOne(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(new Result<>(userService.getById(id)));
    }
}
