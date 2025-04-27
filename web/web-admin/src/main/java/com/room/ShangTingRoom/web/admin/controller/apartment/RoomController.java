package com.room.ShangTingRoom.web.admin.controller.apartment;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.room.ShangTingRoom.common.result.Result;
import com.room.ShangTingRoom.model.entity.RoomInfo;
import com.room.ShangTingRoom.model.enums.ReleaseStatus;
import com.room.ShangTingRoom.web.admin.service.RoomInfoService;
import com.room.ShangTingRoom.web.admin.vo.room.RoomDetailVo;
import com.room.ShangTingRoom.web.admin.vo.room.RoomItemVo;
import com.room.ShangTingRoom.web.admin.vo.room.RoomQueryVo;
import com.room.ShangTingRoom.web.admin.vo.room.RoomSubmitVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "房间信息管理")
@RestController
@RequestMapping("/admin/room")
public class RoomController {
    private final RoomInfoService roomInfoService;
    @Autowired
    public RoomController(RoomInfoService roomInfoService) {
        this.roomInfoService = roomInfoService;
    }
    @Operation(summary = "保存或更新房间信息")
    @PostMapping("saveOrUpdate")
    public Result<Void> saveOrUpdate(@RequestBody RoomSubmitVo roomSubmitVo) {
        roomInfoService.saveOrUpdateRoom(roomSubmitVo);
        return Result.ok();
    }

    @Operation(summary = "根据条件分页查询房间列表")
    @GetMapping("pageItem")
    public Result<IPage<RoomItemVo>> pageItem(@RequestParam long current, @RequestParam long size, RoomQueryVo queryVo) {
        IPage<RoomItemVo> page = new Page<>(current, size);
        IPage<RoomItemVo> result = roomInfoService.pageRoomItemByQuery(page, queryVo);
        return Result.ok(result);
    }

    @Operation(summary = "根据id获取房间详细信息")
    @GetMapping("getDetailById")
    public Result<RoomDetailVo> getDetailById(@RequestParam Long id) {
        RoomDetailVo roomInfo= roomInfoService.getRoomDetailById(id);
        return Result.ok(roomInfo);
    }

    @Operation(summary = "根据id删除房间信息")
    @DeleteMapping("removeById")
    public Result removeById(@RequestParam Long id) {
        roomInfoService.removeRoomById(id);
        return Result.ok();
    }

    @Operation(summary = "根据id修改房间发布状态")
    @PostMapping("updateReleaseStatusById")
    public Result updateReleaseStatusById(Long id, ReleaseStatus status) {
        LambdaUpdateWrapper<RoomInfo> updateWrapper=new LambdaUpdateWrapper<>();
        updateWrapper.eq(RoomInfo::getId, id)
                .set(RoomInfo::getIsRelease, status);
        roomInfoService.update(updateWrapper);
        return Result.ok();
    }

    @GetMapping("listBasicByApartmentId")
    @Operation(summary = "根据公寓id查询房间列表")
    public Result<List<RoomInfo>> listBasicByApartmentId(Long id) {
        LambdaQueryWrapper<RoomInfo> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(RoomInfo::getApartmentId, id);
        queryWrapper.eq(RoomInfo::getIsRelease, ReleaseStatus.RELEASED);
        List<RoomInfo> roomInfoList= roomInfoService.list(queryWrapper);
        return Result.ok(roomInfoList);
    }

}


















