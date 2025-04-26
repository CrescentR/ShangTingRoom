package com.room.ShangTingRoom.web.admin.mapper;

import com.room.ShangTingRoom.model.entity.AttrKey;
import com.room.ShangTingRoom.web.admin.vo.attr.AttrKeyVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author crescent
* @description 针对表【attr_key(房间基本属性表)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.room.ShangTingRoom.model.AttrKey
*/
public interface AttrKeyMapper extends BaseMapper<AttrKey> {
    List<AttrKeyVo> listAttrInfo();
}




