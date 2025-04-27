package com.room.ShangTingRoom.web.admin.mapper;

import com.room.ShangTingRoom.model.entity.SystemUser;
import com.room.ShangTingRoom.web.admin.vo.system.user.SystemUserItemVo;
import com.room.ShangTingRoom.web.admin.vo.system.user.SystemUserQueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
* @author crescent
* @description 针对表【system_user(员工信息表)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.room.ShangTingRoom.model.SystemUser
*/
public interface SystemUserMapper extends BaseMapper<SystemUser> {
    IPage<SystemUserItemVo> pageSystemUserByQuery(IPage<SystemUser> page, SystemUserQueryVo queryVo);
}




