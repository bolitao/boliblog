package xyz.bolitao.boliblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.bolitao.boliblog.entity.dbentity.MUser;
import xyz.bolitao.boliblog.mapper.MUserMapper;
import xyz.bolitao.boliblog.service.UserService;

@Service
public class UserServiceImpl extends ServiceImpl<MUserMapper, MUser> implements UserService {

}
