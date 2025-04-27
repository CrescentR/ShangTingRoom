package com.room.ShangTingRoom.web.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.room.ShangTingRoom.model.entity.SystemPost;
import com.room.ShangTingRoom.model.entity.SystemUser;
import com.room.ShangTingRoom.web.admin.mapper.SystemPostMapper;
import com.room.ShangTingRoom.web.admin.mapper.SystemUserMapper;
import com.room.ShangTingRoom.web.admin.service.SystemUserService;
import com.room.ShangTingRoom.web.admin.vo.system.user.SystemUserItemVo;
import com.room.ShangTingRoom.web.admin.vo.system.user.SystemUserQueryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author crescent
 * @description 针对表【system_user(员工信息表)】的数据库操作Service实现
 * @createDate 2023-07-24 15:48:00
 */
@Service
public class SystemUserServiceImpl extends ServiceImpl<SystemUserMapper, SystemUser>
        implements SystemUserService {
    private final SystemUserMapper systemUserMapper;
    private final SystemPostMapper systemPostMapper;

    @Autowired
    public SystemUserServiceImpl(SystemUserMapper systemUserMapper, SystemPostMapper systemPostMapper) {
        this.systemUserMapper = systemUserMapper;
        this.systemPostMapper = systemPostMapper;
    }
    @Override
    public IPage<SystemUserItemVo> pageSystemUserByQuery(IPage<SystemUser> page, SystemUserQueryVo queryVo) {
        return systemUserMapper.pageSystemUserByQuery(page, queryVo);
    }
    @Override
    public SystemUserItemVo getSystemUserById(Long id){
        SystemUser systemUser  =systemUserMapper.selectById(id);
        SystemPost systemPost= systemPostMapper.selectById(systemUser.getPostId());
        SystemUserItemVo systemUserItemVo = new SystemUserItemVo();
        BeanUtils.copyProperties(systemPost, systemUserItemVo);
        systemUserItemVo.setPostName(systemUserItemVo.getPostName());
        return systemUserItemVo;
    }
}




