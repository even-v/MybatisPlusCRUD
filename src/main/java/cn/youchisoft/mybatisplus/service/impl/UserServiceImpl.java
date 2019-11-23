package cn.youchisoft.mybatisplus.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.youchisoft.mybatisplus.mapper.UserMapper;
import cn.youchisoft.mybatisplus.model.User;
import cn.youchisoft.mybatisplus.service.UserService;

/**
 * UserServiceImpl实现类，需继承MybatisPlus的ServiceImpl类，继承UserService接口
 * 
 * @author jiangjingwei@yysoft.org.cn
 * @Package cn.youchisoft.mybatisplus.service.impl
 * @version Nov 21, 2019
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
