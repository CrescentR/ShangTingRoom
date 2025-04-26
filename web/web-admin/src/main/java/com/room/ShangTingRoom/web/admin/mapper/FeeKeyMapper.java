package com.room.ShangTingRoom.web.admin.mapper;

import com.room.ShangTingRoom.model.entity.FeeKey;
import com.room.ShangTingRoom.web.admin.vo.fee.FeeKeyVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author crescent
* @description 针对表【fee_key(杂项费用名称表)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.room.ShangTingRoom.model.FeeKey
*/
public interface FeeKeyMapper extends BaseMapper<FeeKey> {
    List<FeeKeyVo> listFeeInfo();
}




