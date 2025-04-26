package com.room.ShangTingRoom.web.admin.service.impl;

import com.room.ShangTingRoom.model.entity.FeeKey;
import com.room.ShangTingRoom.web.admin.mapper.FeeKeyMapper;
import com.room.ShangTingRoom.web.admin.service.FeeKeyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.room.ShangTingRoom.web.admin.vo.fee.FeeKeyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author crescent
* @description 针对表【fee_key(杂项费用名称表)】的数据库操作Service实现
* @createDate 2023-07-24 15:48:00
*/
@Service
public class FeeKeyServiceImpl extends ServiceImpl<FeeKeyMapper, FeeKey>
    implements FeeKeyService{
    private final FeeKeyMapper feeKeyMapper;
    @Autowired
    public FeeKeyServiceImpl(FeeKeyMapper feeKeyMapper) {
        this.feeKeyMapper = feeKeyMapper;
    }
    @Override
    public List<FeeKeyVo> listFeeInfo(){
        return feeKeyMapper.listFeeInfo();
    }

}




