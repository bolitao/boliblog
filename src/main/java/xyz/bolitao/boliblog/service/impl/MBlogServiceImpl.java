package xyz.bolitao.boliblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.bolitao.boliblog.entity.dbentity.MBlog;
import xyz.bolitao.boliblog.mapper.MBlogMapper;
import xyz.bolitao.boliblog.service.MBlogService;

@Service
public class MBlogServiceImpl extends ServiceImpl<MBlogMapper, MBlog> implements MBlogService {

}
