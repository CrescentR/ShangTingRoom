package com.room.ShangTingRoom.web.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.room.ShangTingRoom.model.entity.*;
import com.room.ShangTingRoom.model.enums.ItemType;
import com.room.ShangTingRoom.web.admin.mapper.*;
import com.room.ShangTingRoom.web.admin.service.*;
import com.room.ShangTingRoom.web.admin.vo.attr.AttrValueVo;
import com.room.ShangTingRoom.web.admin.vo.graph.GraphVo;
import com.room.ShangTingRoom.web.admin.vo.room.RoomDetailVo;
import com.room.ShangTingRoom.web.admin.vo.room.RoomItemVo;
import com.room.ShangTingRoom.web.admin.vo.room.RoomQueryVo;
import com.room.ShangTingRoom.web.admin.vo.room.RoomSubmitVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author crescent
 * @description 针对表【room_info(房间信息表)】的数据库操作Service实现
 * @createDate 2023-07-24 15:48:00
 */
@Service
public class RoomInfoServiceImpl extends ServiceImpl<RoomInfoMapper, RoomInfo>
        implements RoomInfoService {
    private final GraphInfoService graphInfoService;
    private final RoomAttrValueService roomAttrValueService;
    private final RoomFacilityService roomFacilityService;
    private final RoomLabelService roomLabelService;
    private final RoomPaymentTypeService roomPaymentTypeService;
    private final RoomLeaseTermService roomLeaseTermService;
    private final RoomInfoMapper roomInfoMapper;
    private final ApartmentInfoMapper apartmentInfoMapper;
    private final GraphInfoMapper graphInfoMapper;
    private final AttrValueMapper attrValueMapper;
    private final FacilityInfoMapper facilityInfoMapper;
    private final LabelInfoMapper labelInfoMapper;
    private final PaymentTypeMapper paymentTypeMapper;
    private final LeaseTermMapper leaseTermMapper;

    @Autowired
    public RoomInfoServiceImpl(@Qualifier("graphInfoService") GraphInfoService graphInfoService,
                               @Qualifier("roomAttrValueService") RoomAttrValueService roomAttrValueService,
                               @Qualifier("roomFacilityService") RoomFacilityService roomFacilityService,
                               @Qualifier("roomLabelService") RoomLabelService roomLabelService,
                               @Qualifier("roomPaymentTypeService")RoomPaymentTypeService roomPaymentTypeService,
                               @Qualifier("roomLeaseTermService") RoomLeaseTermService roomLeaseTermService,
                               @Qualifier("roomInfoMapper")RoomInfoMapper roomInfoMapper,
                               @Qualifier("apartmentInfoMapper")ApartmentInfoMapper apartmentInfoMapper,
                               @Qualifier("graphInfoMapper")GraphInfoMapper graphInfoMapper,
                               @Qualifier("attrValueMapper")AttrValueMapper attrValueMapper,
                               @Qualifier("facilityInfoMapper")FacilityInfoMapper facilityInfoMapper,
                               @Qualifier("labelInfoMapper")LabelInfoMapper labelInfoMapper,
                               @Qualifier("paymentTypeMapper")PaymentTypeMapper paymentTypeMapper,
                               @Qualifier("leaseTermMapper")LeaseTermMapper leaseTermMapper) {
        this.graphInfoService = graphInfoService;
        this.roomAttrValueService = roomAttrValueService;
        this.roomFacilityService = roomFacilityService;
        this.roomLabelService = roomLabelService;
        this.roomPaymentTypeService = roomPaymentTypeService;
        this.roomLeaseTermService = roomLeaseTermService;
        this.roomInfoMapper=roomInfoMapper;
        this.apartmentInfoMapper=apartmentInfoMapper;
        this.graphInfoMapper=graphInfoMapper;
        this.attrValueMapper=attrValueMapper;
        this.facilityInfoMapper=facilityInfoMapper;
        this.labelInfoMapper=labelInfoMapper;
        this.paymentTypeMapper=paymentTypeMapper;
        this.leaseTermMapper=leaseTermMapper;
    }
    @Override
    public void saveOrUpdateRoom(RoomSubmitVo roomSubmitVo){
        boolean isUpdate = roomSubmitVo.getId() !=  null;
        super.saveOrUpdate(roomSubmitVo);
        if(isUpdate){
            //删除原有的graphInfoList
            LambdaQueryWrapper<GraphInfo> graphQueryWrapper=new LambdaQueryWrapper<>();
            graphQueryWrapper.eq(GraphInfo::getItemType, ItemType.ROOM);
            graphQueryWrapper.eq(GraphInfo::getItemId, roomSubmitVo.getId());
            graphInfoService.remove(graphQueryWrapper);
            //删除原来有的roomAttrValueList
            LambdaQueryWrapper<RoomAttrValue> attrQueryMapper =new LambdaQueryWrapper<>();
            attrQueryMapper.eq(RoomAttrValue::getRoomId, roomSubmitVo.getId());
            roomAttrValueService.remove(attrQueryMapper);
            //删除原有的配套信息
            LambdaQueryWrapper<RoomFacility>  facilityQueryWrapper=new LambdaQueryWrapper<>();
            facilityQueryWrapper.eq(RoomFacility::getRoomId, roomSubmitVo.getId());
            roomFacilityService.remove(facilityQueryWrapper);
            //删除标签信息
            LambdaQueryWrapper<RoomLabel> labelQueryWrapper= new LambdaQueryWrapper<>();
            labelQueryWrapper.eq(RoomLabel::getRoomId,roomSubmitVo.getId());
            roomLabelService.remove(labelQueryWrapper);
            //删除原有支付方式
            LambdaQueryWrapper<RoomPaymentType> paymentTypeQueryWrapper=new LambdaQueryWrapper<>();
            paymentTypeQueryWrapper.eq(RoomPaymentType::getRoomId,roomSubmitVo.getId());
            roomPaymentTypeService.remove(paymentTypeQueryWrapper);
            //删除原有续期信息
            LambdaQueryWrapper<RoomLeaseTerm> leaseTermQueryWrapper=new LambdaQueryWrapper<>();
            leaseTermQueryWrapper.eq(RoomLeaseTerm::getRoomId,roomSubmitVo.getId());
            roomLeaseTermService.remove(leaseTermQueryWrapper);

            //下面保存新的信息
            //保存新的图片
            List<GraphVo> graphVoList = roomSubmitVo.getGraphVoList();
            if (!CollectionUtils.isEmpty(graphVoList)) {
                ArrayList<GraphInfo> graphInfoList = new ArrayList<>();
                for (GraphVo graphVo : graphVoList) {
                    GraphInfo graphInfo = new GraphInfo();
                    graphInfo.setItemId(roomSubmitVo.getId());
                    graphInfo.setItemType(ItemType.ROOM);
                    graphInfo.setUrl(graphVo.getUrl());
                    graphInfoList.add(graphInfo);
                }
                graphInfoService.saveBatch(graphInfoList);
            }
            //2.保存新的roomAttrValueList
            List<Long> attrValueIds = roomSubmitVo.getAttrValueIds();
            if (!CollectionUtils.isEmpty(attrValueIds)) {
                List<RoomAttrValue> roomAttrValueList = new ArrayList<>();
                for (Long attrValueId : attrValueIds) {
                    RoomAttrValue roomAttrValue = RoomAttrValue.builder().roomId(roomSubmitVo.getId()).attrValueId(attrValueId).build();
                    roomAttrValueList.add(roomAttrValue);
                }
                roomAttrValueService.saveBatch(roomAttrValueList);
            }

            //3.保存新的facilityInfoList
            List<Long> facilityInfoIds = roomSubmitVo.getFacilityInfoIds();
            if (!CollectionUtils.isEmpty(facilityInfoIds)) {
                List<RoomFacility> roomFacilityList = new ArrayList<>();
                for (Long facilityInfoId : facilityInfoIds) {
                    RoomFacility roomFacility = RoomFacility.builder().roomId(roomSubmitVo.getId()).facilityId(facilityInfoId).build();
                    roomFacilityList.add(roomFacility);
                }
                roomFacilityService.saveBatch(roomFacilityList);
            }

            //4.保存新的labelInfoList
            List<Long> labelInfoIds = roomSubmitVo.getLabelInfoIds();
            if (!CollectionUtils.isEmpty(labelInfoIds)) {
                ArrayList<RoomLabel> roomLabelList = new ArrayList<>();
                for (Long labelInfoId : labelInfoIds) {
                    RoomLabel roomLabel = RoomLabel.builder().roomId(roomSubmitVo.getId()).labelId(labelInfoId).build();
                    roomLabelList.add(roomLabel);
                }
                roomLabelService.saveBatch(roomLabelList);
            }

            //5.保存新的paymentTypeList
            List<Long> paymentTypeIds = roomSubmitVo.getPaymentTypeIds();
            if (!CollectionUtils.isEmpty(paymentTypeIds)) {
                ArrayList<RoomPaymentType> roomPaymentTypeList = new ArrayList<>();
                for (Long paymentTypeId : paymentTypeIds) {
                    RoomPaymentType roomPaymentType = RoomPaymentType.builder().roomId(roomSubmitVo.getId()).paymentTypeId(paymentTypeId).build();
                    roomPaymentTypeList.add(roomPaymentType);
                }
                roomPaymentTypeService.saveBatch(roomPaymentTypeList);
            }

            //6.保存新的leaseTermList
            List<Long> leaseTermIds = roomSubmitVo.getLeaseTermIds();
            if (!CollectionUtils.isEmpty(leaseTermIds)) {
                ArrayList<RoomLeaseTerm> roomLeaseTerms = new ArrayList<>();
                for (Long leaseTermId : leaseTermIds) {
                    RoomLeaseTerm roomLeaseTerm = RoomLeaseTerm.builder().roomId(roomSubmitVo.getId()).leaseTermId(leaseTermId).build();
                    roomLeaseTerms.add(roomLeaseTerm);
                }
                roomLeaseTermService.saveBatch(roomLeaseTerms);
            }
        }
    }
    @Override
    public IPage<RoomItemVo> pageRoomItemByQuery(IPage<RoomItemVo> page, RoomQueryVo queryVo){
        return roomInfoMapper.pageRoomItemByQuery(page,queryVo);
    }
    @Override
    public RoomDetailVo getRoomDetailById(Long id){
        //查询房间基本信息
        RoomInfo roomInfo =roomInfoMapper.selectById(id);
        //查询所属公寓信息
        ApartmentInfo apartmentInfo =apartmentInfoMapper.selectById(roomInfo.getApartmentId());
        //查询图片信息列表
        List<GraphVo> graphVoList= graphInfoMapper.selectListByItemTypeAndId(ItemType.ROOM,id);
        //查询属性信息列表
        List<AttrValueVo> attrValueVoList =attrValueMapper.selectListByRoomId(id);
        //查询facilityInfoList
        List<FacilityInfo> facilityInfoList=facilityInfoMapper.selectListByRoomId(id);
        //查询标签信息列表
        List<LabelInfo> labelInfoList=labelInfoMapper.selectListByRoomId(id);
        //查询支付方式列表
        List<PaymentType> paymentTypeList=paymentTypeMapper.selectListByRoomId(id);
        //查询续期信息列表
        List<LeaseTerm> leaseTermList=leaseTermMapper.selectListByRoomId(id);

        RoomDetailVo adminRoomDetailVo=new RoomDetailVo();
        BeanUtils.copyProperties(roomInfo,adminRoomDetailVo);
        adminRoomDetailVo.setApartmentInfo(apartmentInfo);
        adminRoomDetailVo.setGraphVoList(graphVoList);
        adminRoomDetailVo.setAttrValueVoList(attrValueVoList);
        adminRoomDetailVo.setFacilityInfoList(facilityInfoList);
        adminRoomDetailVo.setLabelInfoList(labelInfoList);
        adminRoomDetailVo.setPaymentTypeList(paymentTypeList);
        adminRoomDetailVo.setLeaseTermList(leaseTermList);
        return adminRoomDetailVo;
    }

}




