package com.room.ShangTingRoom.web.admin.controller.lease;


import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.room.ShangTingRoom.common.result.Result;
import com.room.ShangTingRoom.model.entity.LeaseAgreement;
import com.room.ShangTingRoom.model.enums.LeaseStatus;
import com.room.ShangTingRoom.web.admin.mapper.LeaseAgreementMapper;
import com.room.ShangTingRoom.web.admin.service.LeaseAgreementService;
import com.room.ShangTingRoom.web.admin.vo.agreement.AgreementQueryVo;
import com.room.ShangTingRoom.web.admin.vo.agreement.AgreementVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Tag(name = "租约管理")
@RestController
@RequestMapping("/admin/agreement")
public class LeaseAgreementController {
    private final LeaseAgreementService leaseAgreementService;
    private final LeaseAgreementMapper leaseAgreementMapper;

    @Autowired
    public LeaseAgreementController(LeaseAgreementService leaseAgreementService, LeaseAgreementMapper leaseAgreementMapper) {
        this.leaseAgreementService = leaseAgreementService;
        this.leaseAgreementMapper = leaseAgreementMapper;
    }
    @Operation(summary = "保存或修改租约信息")
    @PostMapping("saveOrUpdate")
    public Result<Void> saveOrUpdate(@RequestBody LeaseAgreement leaseAgreement) {
        leaseAgreementService.saveOrUpdate(leaseAgreement);
        return Result.ok();
    }

    @Operation(summary = "根据条件分页查询租约列表")
    @GetMapping("page")
    public Result<IPage<AgreementVo>> page(@RequestParam long current, @RequestParam long size, AgreementQueryVo queryVo) {
        IPage<AgreementVo> page =new Page<>(current,size);
        IPage<AgreementVo> list = leaseAgreementService.pageAgreementByQuery(page,queryVo);
        return Result.ok();
    }

    @Operation(summary = "根据id查询租约信息")
    @GetMapping(name = "getById")
    public Result<AgreementVo> getById(@RequestParam Long id) {
        AgreementVo apartment=leaseAgreementService.getAgreementById(id);
        return Result.ok(apartment);
    }

    @Operation(summary = "根据id删除租约信息")
    @DeleteMapping("removeById")
    public Result<Void> removeById(@RequestParam Long id) {
        leaseAgreementService.removeById(id);
        return Result.ok();
    }

    @Operation(summary = "根据id更新租约状态")
    @PostMapping("updateStatusById")
    public Result<Void> updateStatusById(@RequestParam Long id, @RequestParam LeaseStatus status) {
        LambdaUpdateWrapper<LeaseAgreement> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(LeaseAgreement::getId,id);
        updateWrapper.set(LeaseAgreement::getStatus,status);
        leaseAgreementService.update(updateWrapper);
        return Result.ok();
    }

}

