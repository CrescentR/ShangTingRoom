package com.room.ShangTingRoom.web.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.room.ShangTingRoom.model.entity.ApartmentInfo;
import com.room.ShangTingRoom.web.admin.vo.apartment.ApartmentItemVo;
import com.room.ShangTingRoom.web.admin.vo.apartment.ApartmentQueryVo;
import com.room.ShangTingRoom.web.admin.vo.apartment.ApartmentSubmitVo;

/**
* @author crescent
* @description 针对表【apartment_info(公寓信息表)】的数据库操作Service
* @createDate 2023-07-24 15:48:00
*/
public interface ApartmentInfoService extends IService<ApartmentInfo> {
    void saveOrUpdateApartment(ApartmentSubmitVo apartmentSubmitVo);
    IPage<ApartmentItemVo> pageItem(IPage<ApartmentItemVo> page, ApartmentQueryVo queryVo);
}
