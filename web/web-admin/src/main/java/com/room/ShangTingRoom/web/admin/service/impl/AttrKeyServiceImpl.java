package com.room.ShangTingRoom.web.admin.service.impl;

import com.room.ShangTingRoom.model.entity.AttrKey;
import com.room.ShangTingRoom.web.admin.mapper.AttrKeyMapper;
import com.room.ShangTingRoom.web.admin.service.AttrKeyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.room.ShangTingRoom.web.admin.vo.attr.AttrKeyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author crescent
* @description 针对表【attr_key(房间基本属性表)】的数据库操作Service实现
* @createDate 2023-07-24 15:48:00
*/
@Service
public class AttrKeyServiceImpl extends ServiceImpl<AttrKeyMapper, AttrKey>
    implements AttrKeyService{
    private final AttrKeyMapper attrKeyMapper;
    @Autowired
    public AttrKeyServiceImpl(AttrKeyMapper attrKeyMapper) {
        this.attrKeyMapper = attrKeyMapper;
    }
    @Override
    public List<AttrKeyVo> listAttrInfo(){
        return attrKeyMapper.listAttrInfo();
    }
}




