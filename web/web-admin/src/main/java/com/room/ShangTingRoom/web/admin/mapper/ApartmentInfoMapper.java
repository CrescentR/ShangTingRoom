package com.room.ShangTingRoom.web.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.room.ShangTingRoom.model.entity.ApartmentInfo;
import com.room.ShangTingRoom.web.admin.vo.apartment.ApartmentItemVo;
import com.room.ShangTingRoom.web.admin.vo.apartment.ApartmentQueryVo;

/**
* @author crescent
* @description 针对表【apartment_info(公寓信息表)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.room.ShangTingRoom.model.ApartmentInfo
*/
public interface ApartmentInfoMapper extends BaseMapper<ApartmentInfo> {
    IPage<ApartmentItemVo> pageApartmentItemByQuery(IPage<ApartmentItemVo> page, ApartmentQueryVo queryVo);
}




