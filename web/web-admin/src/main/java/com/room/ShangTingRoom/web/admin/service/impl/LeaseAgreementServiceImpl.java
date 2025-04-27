package com.room.ShangTingRoom.web.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.room.ShangTingRoom.model.entity.*;
import com.room.ShangTingRoom.web.admin.mapper.*;
import com.room.ShangTingRoom.web.admin.service.LeaseAgreementService;
import com.room.ShangTingRoom.web.admin.vo.agreement.AgreementQueryVo;
import com.room.ShangTingRoom.web.admin.vo.agreement.AgreementVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author crescent
 * @description 针对表【lease_agreement(租约信息表)】的数据库操作Service实现
 * @createDate 2023-07-24 15:48:00
 */
@Service
public class LeaseAgreementServiceImpl extends ServiceImpl<LeaseAgreementMapper, LeaseAgreement>
        implements LeaseAgreementService {
    private final LeaseAgreementMapper leaseAgreementMapper;
    private final ApartmentInfoMapper apartmentInfoMapper;
    private final RoomInfoMapper roomInfoMapper;
    private final PaymentTypeMapper paymentTypeMapper;
    private final LeaseTermMapper leaseTermMapper;
    @Autowired
    public LeaseAgreementServiceImpl(LeaseAgreementMapper leaseAgreementMapper,
                                     ApartmentInfoMapper apartmentInfoMapper,
                                     RoomInfoMapper roomInfoMapper,
                                     PaymentTypeMapper paymentTypeMapper,
                                     LeaseTermMapper leaseTermMapper) {
        this.leaseAgreementMapper = leaseAgreementMapper;
        this.apartmentInfoMapper = apartmentInfoMapper;
        this.roomInfoMapper = roomInfoMapper;
        this.paymentTypeMapper = paymentTypeMapper;
        this.leaseTermMapper = leaseTermMapper;
    }
    @Override
    public IPage<AgreementVo> pageAgreementByQuery(IPage<AgreementVo> page, AgreementQueryVo queryVo) {
        return leaseAgreementMapper.pageAgreementByQuery(page, queryVo);
    }
    @Override
    public AgreementVo getAgreementById(Long id){
        LeaseAgreement leaseAgreement=leaseAgreementMapper.selectById(id);
        ApartmentInfo apartmentInfo=apartmentInfoMapper.selectById(leaseAgreement.getApartmentId());
        RoomInfo roomInfo=roomInfoMapper.selectById(leaseAgreement.getRoomId());
        PaymentType paymentType=paymentTypeMapper.selectById(leaseAgreement.getPaymentTypeId());
        LeaseTerm leaseTerm=leaseTermMapper.selectById(leaseAgreement.getLeaseTermId());
        AgreementVo adminAgreementVo=new AgreementVo();
        BeanUtils.copyProperties(leaseAgreement,adminAgreementVo);
        adminAgreementVo.setApartmentInfo(apartmentInfo);
        adminAgreementVo.setRoomInfo(roomInfo);
        adminAgreementVo.setPaymentType(paymentType);
        adminAgreementVo.setLeaseTerm(leaseTerm);
        return adminAgreementVo;
    }
}




