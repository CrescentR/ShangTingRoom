package com.room.ShangTingRoom.web.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.room.ShangTingRoom.model.entity.LeaseTerm;
import com.room.ShangTingRoom.web.admin.service.LeaseTermService;
import com.room.ShangTingRoom.web.admin.mapper.LeaseTermMapper;
import org.springframework.stereotype.Service;

/**
* @author crescent
* @description 针对表【lease_term(租期)】的数据库操作Service实现
* @createDate 2023-07-24 15:48:00
*/
@Service
public class LeaseTermServiceImpl extends ServiceImpl<LeaseTermMapper, LeaseTerm>
    implements LeaseTermService{

}




