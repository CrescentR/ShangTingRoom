package com.room.ShangTingRoom.web.admin.controller.apartment;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.room.ShangTingRoom.common.result.Result;
import com.room.ShangTingRoom.model.entity.FeeKey;
import com.room.ShangTingRoom.model.entity.FeeValue;
import com.room.ShangTingRoom.web.admin.service.FeeKeyService;
import com.room.ShangTingRoom.web.admin.service.FeeValueService;
import com.room.ShangTingRoom.web.admin.vo.fee.FeeKeyVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "房间杂费管理")
@RestController
@RequestMapping("/admin/fee")
public class FeeController {
    private final FeeKeyService feeKeyService;
    private final FeeValueService feeValueService;
    @Autowired
    public FeeController(FeeKeyService feeKeyService,
                        FeeValueService feeValueService) {
        this.feeKeyService = feeKeyService;
        this.feeValueService = feeValueService;
    }
    @Operation(summary = "保存或更新杂费名称")
    @PostMapping("key/saveOrUpdate")
    public Result<Void> saveOrUpdateFeeKey(@RequestBody FeeKey feeKey) {
        feeKeyService.saveOrUpdate(feeKey);
        return Result.ok();
    }

    @Operation(summary = "保存或更新杂费值")
    @PostMapping("value/saveOrUpdate")
    public Result<Void> saveOrUpdateFeeValue(@RequestBody FeeValue feeValue) {
        feeValueService.saveOrUpdate(feeValue);
        return Result.ok();
    }


    @Operation(summary = "查询全部杂费名称和杂费值列表")
    @GetMapping("list")
    public Result<List<FeeKeyVo>> feeInfoList() {
        List<FeeKeyVo>list =feeKeyService.listFeeInfo();
        return Result.ok();
    }

    @Operation(summary = "根据id删除杂费名称")
    @DeleteMapping("key/deleteById")
    public Result<Void> deleteFeeKeyById(@RequestParam Long feeKeyId) {
        //删除杂费名称
        feeKeyService.removeById(feeKeyId);
        //删除杂费名称下的杂费值
        LambdaQueryWrapper<FeeValue> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FeeValue::getFeeKeyId,feeKeyId);
        feeValueService.remove(queryWrapper);
        return Result.ok();
    }

    @Operation(summary = "根据id删除杂费值")
    @DeleteMapping("value/deleteById")
    public Result<Void> deleteFeeValueById(@RequestParam Long id) {
        feeValueService.removeById(id);
        return Result.ok();
    }
}
