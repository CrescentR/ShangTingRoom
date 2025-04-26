package com.room.ShangTingRoom.web.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.room.ShangTingRoom.common.exception.LeaseException;
import com.room.ShangTingRoom.model.entity.*;
import com.room.ShangTingRoom.common.result.ResultCodeEnum;
import com.room.ShangTingRoom.model.enums.ItemType;
import com.room.ShangTingRoom.web.admin.mapper.*;
import com.room.ShangTingRoom.web.admin.service.*;
import com.room.ShangTingRoom.web.admin.vo.apartment.ApartmentDetailVo;
import com.room.ShangTingRoom.web.admin.vo.apartment.ApartmentItemVo;
import com.room.ShangTingRoom.web.admin.vo.apartment.ApartmentQueryVo;
import com.room.ShangTingRoom.web.admin.vo.apartment.ApartmentSubmitVo;
import com.room.ShangTingRoom.web.admin.vo.fee.FeeValueVo;
import com.room.ShangTingRoom.web.admin.vo.graph.GraphVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author crescent
 * @description 针对表【apartment_info(公寓信息表)】的数据库操作Service实现
 * @createDate 2023-07-24 15:48:00
 */
@Service
public class ApartmentInfoServiceImpl extends ServiceImpl<ApartmentInfoMapper, ApartmentInfo>
        implements ApartmentInfoService {

    private final GraphInfoService graphInfoService;
    private final ApartmentFeeValueService apartmentFeeValueService;
    private final ApartmentFacilityService apartmentFacilityService;
    private final ApartmentLabelService apartmentLabelService;
    private final ApartmentInfoMapper apartmentInfoMapper;
    private final LabelInfoMapper labelInfoMapper;
    private final GraphInfoMapper graphInfoMapper;
    private final FeeValueMapper feeValueMapper;
    private final FacilityInfoMapper facilityInfoMapper;
    private final RoomInfoMapper roomInfoMapper;
    @Autowired
    public ApartmentInfoServiceImpl(GraphInfoService graphInfoService,
                                    ApartmentFeeValueService apartmentFeeValueService,
                                    ApartmentFacilityService apartmentFacilityService,
                                    ApartmentLabelService apartmentLabelService,
                                    ApartmentInfoMapper apartmentInfoMapper,
                                    LabelInfoMapper labelInfoMapper,
                                    GraphInfoMapper graphInfoMapper,
                                    FeeValueMapper feeValueMapper,
                                    FacilityInfoMapper facilityInfoMapper,
                                    RoomInfoMapper roomInfoMapper) {
        this.graphInfoService = graphInfoService;
        this.apartmentFeeValueService = apartmentFeeValueService;
        this.apartmentFacilityService = apartmentFacilityService;
        this.apartmentLabelService = apartmentLabelService;
        this.apartmentInfoMapper = apartmentInfoMapper;
        this.labelInfoMapper = labelInfoMapper;
        this.graphInfoMapper = graphInfoMapper;
        this.feeValueMapper = feeValueMapper;
        this.facilityInfoMapper = facilityInfoMapper;
        this.roomInfoMapper= roomInfoMapper;
    }
    @Override
    public IPage<ApartmentItemVo> pageItem(IPage<ApartmentItemVo> page, ApartmentQueryVo queryVo) {

        return apartmentInfoMapper.pageApartmentItemByQuery(page, queryVo);
    }
    @Override
    public void saveOrUpdateApartment(ApartmentSubmitVo apartmentSubmitVo) {
        boolean isUpdate = apartmentSubmitVo.getId() != null;
        super.saveOrUpdate(apartmentSubmitVo);
        if (isUpdate) {
            //删除图片列表
            LambdaQueryWrapper<GraphInfo> graphQueryWrapper=new LambdaQueryWrapper<>();
            graphQueryWrapper.eq(GraphInfo::getItemType, ItemType.APARTMENT);
            graphQueryWrapper.eq(GraphInfo::getItemId, apartmentSubmitVo.getId());
            graphInfoService.remove(graphQueryWrapper);
            //删除配套设施
            LambdaQueryWrapper<ApartmentFacility> facilityQueryWrapper=new LambdaQueryWrapper<>();
            facilityQueryWrapper.eq(ApartmentFacility::getApartmentId, apartmentSubmitVo.getId());
            apartmentFacilityService.remove(facilityQueryWrapper);
            //删除标签
            LambdaQueryWrapper<ApartmentLabel> labelQueryWrapper=new LambdaQueryWrapper<>();
            labelQueryWrapper.eq(ApartmentLabel::getApartmentId, apartmentSubmitVo.getId());
            apartmentLabelService.remove(labelQueryWrapper);
            //删除杂费
            LambdaQueryWrapper<ApartmentFeeValue> feeValueQueryWrapper=new LambdaQueryWrapper<>();
            feeValueQueryWrapper.eq(ApartmentFeeValue::getApartmentId,apartmentSubmitVo.getId());
            apartmentFeeValueService.remove(feeValueQueryWrapper);
        }
        //插入图片列表
        List<GraphVo> graphVoList = apartmentSubmitVo.getGraphVoList();
        if(!CollectionUtils.isEmpty(graphVoList)){
            ArrayList<GraphInfo> graphInfoList = new ArrayList<>();
            for (GraphVo graphVo : graphVoList) {
                GraphInfo graphInfo = new GraphInfo();
                graphInfo.setItemId(apartmentSubmitVo.getId());
                graphInfo.setItemType(ItemType.APARTMENT);
                graphInfo.setName(graphVo.getName());
                graphInfo.setUrl(graphVo.getUrl());
                graphInfoList.add(graphInfo);
            }
            graphInfoService.saveBatch(graphInfoList);
        }
        //插入配套列表
        List<Long> facilityInfoIdList =apartmentSubmitVo.getFacilityInfoIds();
        if(!CollectionUtils.isEmpty(facilityInfoIdList)){
            ArrayList<ApartmentFacility> facilityList = new ArrayList<>();
            for (Long facilityInfoId : facilityInfoIdList) {
                ApartmentFacility apartmentFacility = new ApartmentFacility();
                apartmentFacility.setApartmentId(apartmentSubmitVo.getId());
                apartmentFacility.setFacilityId(facilityInfoId);
                facilityList.add(apartmentFacility);
            }
            apartmentFacilityService.saveBatch(facilityList);
        }
        //插入标签列表
        List<Long> labelIdsList=apartmentSubmitVo.getLabelIds();
        if(!CollectionUtils.isEmpty(labelIdsList)){
            ArrayList<ApartmentLabel> labelList = new ArrayList<>();
            for (Long labelId : labelIdsList) {
                ApartmentLabel apartmentLabel = new ApartmentLabel();
                apartmentLabel.setApartmentId(apartmentSubmitVo.getId());
                apartmentLabel.setLabelId(labelId);
                labelList.add(apartmentLabel);
            }
            apartmentLabelService.saveBatch(labelList);
        }
        //插入杂费列表
        List<Long> feeValueIdsList=apartmentSubmitVo.getFeeValueIds();
        if (!CollectionUtils.isEmpty(feeValueIdsList)){
            ArrayList<ApartmentFeeValue> feeValueList=new ArrayList<>();
            for(Long feeValueId : feeValueIdsList){
                ApartmentFeeValue apartmentFeeValue = new ApartmentFeeValue();
                apartmentFeeValue.setApartmentId(apartmentSubmitVo.getId());
                apartmentFeeValue.setFeeValueId(feeValueId);
                feeValueList.add(apartmentFeeValue);
            }
            apartmentFeeValueService.saveBatch(feeValueList);

        }
    }
    @Override
    public ApartmentDetailVo getApartmentDetailById(Long id){
        ApartmentInfo apartmentInfo = this.getById(id);
        if (apartmentInfo == null) {
            return null;
        }
        List<GraphVo> graphVoList = graphInfoMapper.selectListByItemTypeAndId(ItemType.APARTMENT,id);
        List<LabelInfo> labelInfoList = labelInfoMapper.selectListByApartmentId(id);
        List<FacilityInfo> facilityInfoList = facilityInfoMapper.selectListByApartmentId(id);
        List<FeeValueVo> feeValueVoList = feeValueMapper.selectListByApartmentId(id);
        ApartmentDetailVo adminApartmentDetailVo = new ApartmentDetailVo();
        BeanUtils.copyProperties(apartmentInfo, adminApartmentDetailVo);
        adminApartmentDetailVo.setGraphVoList(graphVoList);
        adminApartmentDetailVo.setLabelInfoList(labelInfoList);
        adminApartmentDetailVo.setFacilityInfoList(facilityInfoList);
        adminApartmentDetailVo.setFeeValueVoList(feeValueVoList);
        return adminApartmentDetailVo;
    }
    @Override
    public void removeApartmentById(Long id){
        LambdaQueryWrapper<RoomInfo> roomQueryWrapper= new LambdaQueryWrapper<>();
        roomQueryWrapper.eq(RoomInfo::getApartmentId,id);
        Long count = roomInfoMapper.selectCount(roomQueryWrapper);
        if (count > 0){
            throw new LeaseException(ResultCodeEnum.ADMIN_APARTMENT_DELETE_ERROR);
        }
        //删除GraphInfo图片
        LambdaQueryWrapper<GraphInfo> graphQueryWrapper= new LambdaQueryWrapper<>();
        graphQueryWrapper.eq(GraphInfo::getItemType,ItemType.APARTMENT);
        graphQueryWrapper.eq(GraphInfo::getItemId,id);
        graphInfoService.remove(graphQueryWrapper);
        //删除标签
        LambdaQueryWrapper<ApartmentLabel> labelQueryWrapper=new LambdaQueryWrapper<>();
        labelQueryWrapper.eq(ApartmentLabel::getApartmentId,id);
        apartmentLabelService.remove(labelQueryWrapper);
        //删除配套设施
        LambdaQueryWrapper<ApartmentFacility> facilityQueryWrapper=new LambdaQueryWrapper<>();
        facilityQueryWrapper.eq(ApartmentFacility::getApartmentId,id);
        apartmentFacilityService.remove(facilityQueryWrapper);
        //删除杂费
        LambdaQueryWrapper<ApartmentFeeValue> FeeQueryWrapper=new LambdaQueryWrapper<>();
        FeeQueryWrapper.eq(ApartmentFeeValue::getApartmentId,id);
        apartmentFeeValueService.remove(FeeQueryWrapper);
    }
}




