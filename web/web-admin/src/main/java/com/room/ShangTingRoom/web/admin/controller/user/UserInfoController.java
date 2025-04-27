package com.room.ShangTingRoom.web.admin.controller.user;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.room.ShangTingRoom.common.result.Result;
import com.room.ShangTingRoom.model.entity.UserInfo;
import com.room.ShangTingRoom.model.enums.BaseStatus;
import com.room.ShangTingRoom.web.admin.service.UserInfoService;
import com.room.ShangTingRoom.web.admin.vo.user.UserInfoQueryVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户信息管理")
@RestController
@RequestMapping("/admin/user")
public class UserInfoController {
    private final UserInfoService userInfoService;
    @Autowired
    public UserInfoController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }
    @Operation(summary = "分页查询用户信息")
    @GetMapping("page")
    public Result<IPage<UserInfo>> pageUserInfo(@RequestParam long current, @RequestParam long size, UserInfoQueryVo queryVo) {
        IPage<UserInfo> page=new Page<>(current,size);
        LambdaQueryWrapper<UserInfo> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(queryVo.getPhone()!=null,UserInfo::getPhone,queryVo.getPhone());
        queryWrapper.eq(queryVo.getStatus()!=null,UserInfo::getStatus,queryVo.getStatus());
        IPage<UserInfo> list= userInfoService.page(page,queryWrapper);
        return Result.ok(list);
    }

    @Operation(summary = "根据用户id更新账号状态")
    @PostMapping("updateStatusById")
    public Result<Void> updateStatusById(@RequestParam Long id, @RequestParam BaseStatus status) {
        LambdaUpdateWrapper<UserInfo> updateWrapper=new LambdaUpdateWrapper<>();
        updateWrapper.eq(UserInfo::getId,id);
        updateWrapper.set(UserInfo::getStatus,status);
        userInfoService.update(updateWrapper);
        return Result.ok();
    }
}
