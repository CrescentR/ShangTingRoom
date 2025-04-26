package com.room.ShangTingRoom.web.admin.mapper;

import com.room.ShangTingRoom.model.entity.FacilityInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author crescent
* @description 针对表【facility_info(配套信息表)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.room.ShangTingRoom.model.FacilityInfo
*/
public interface FacilityInfoMapper extends BaseMapper<FacilityInfo> {
    List<FacilityInfo> selectListByApartmentId(Long id);
}




