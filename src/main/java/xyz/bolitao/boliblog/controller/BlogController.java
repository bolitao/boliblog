package xyz.bolitao.boliblog.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.bolitao.boliblog.entity.dbentity.MBlog;
import xyz.bolitao.boliblog.service.BlogService;
import xyz.bolitao.boliblog.util.Result;
import xyz.bolitao.boliblog.util.ShiroUtil;

import java.util.Date;

/**
 * @author bolitao
 */
@RestController
@Api(tags = "blogs")
@RequestMapping(value = "blogs")
public class BlogController {
    private final BlogService blogService;
    private static final Logger logger = LoggerFactory.getLogger(BlogController.class);

    @Autowired
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @ApiOperation(value = "list")
    @GetMapping()
    public ResponseEntity<Result<IPage<MBlog>>> list(@RequestParam(defaultValue = "1") Integer currentPage) {
        Page<MBlog> page = new Page<>(currentPage, 5);
        IPage<MBlog> pageableData = blogService.page(page,
                Wrappers.lambdaQuery(MBlog.class).orderByDesc(MBlog::getCreated));
        return ResponseEntity.ok(new Result<>("1", pageableData));
    }

    @GetMapping("{blogId}")
    @ApiOperation(value = "get spec blog")
    public ResponseEntity<Result<MBlog>> getSpecBlog(@PathVariable Long blogId) {
        MBlog blog = blogService.getById(blogId);
        Assert.notNull(blog, "不存在这篇博文");
        return ResponseEntity.ok(new Result<>("1", blog));
    }

    @RequiresAuthentication
    @PostMapping()
    @ApiOperation(value = "new/edit blog artiche")
    public ResponseEntity<Result<String>> newOrEditBlogArticle(@Validated @RequestBody MBlog blog) {
        MBlog temp = null;
        if (blog.getId() != null) { // 如果传入 blog 带 id 则为修改
            temp = blogService.getById(blog.getId());
            // 只能编辑自己的文章
            logger.info("{}", ShiroUtil.getAccountProfile().getId());
            Assert.isTrue(temp.getUserId().equals(ShiroUtil.getAccountProfile().getId()), "没有权限编辑");
        } else {
            temp = new MBlog();
            temp.setUserId(ShiroUtil.getAccountProfile().getId());
            temp.setCreated(new Date());
            temp.setStatus(new Byte("1"));
        }
        BeanUtil.copyProperties(blog, temp, "id", "userId", "created", "status");
        blogService.saveOrUpdate(temp);
        return ResponseEntity.ok(new Result<>("1", "成功"));


//        blog.setUserId(ShiroUtil.getAccountProfile().getId());
//        blog.setStatus(new Byte("1"));
//        blog.setCreated(new Date());
//        blogService.save(blog);
//        return ResponseEntity.ok(new Result<>("1", "成功"));
    }

//    @RequiresAuthentication
//    @PutMapping()
//    @ApiOperation(value = "edit sped blog artiche")
//    public ResponseEntity<Result<String>> editSpecBlogArticle(@RequestBody MBlog updateBean) {
//        MBlog oldBlog = blogService.getById(updateBean.getId());
//        MBlog newBlog = new MBlog();
//        // 根据 blog id 查询作者，与当前用户比对，判断是否有编辑权限
//        Assert.isTrue(oldBlog.getUserId().equals(ShiroUtil.getAccountProfile().getId()), "无编辑权限");
//        BeanUtils.copyProperties(oldBlog, newBlog);
//        BeanUtils.copyProperties(updateBean, newBlog, "id", "userId", "created", "status");
//        blogService.updateById(newBlog);
//        return ResponseEntity.ok(new Result<>("1", "博文修改成功"));
//    }
}
