package com.room.ShangTingRoom.web.admin.controller.login;


import com.room.ShangTingRoom.common.result.Result;
import com.room.ShangTingRoom.web.admin.service.LoginService;
import com.room.ShangTingRoom.web.admin.vo.login.CaptchaVo;
import com.room.ShangTingRoom.web.admin.vo.login.LoginVo;
import com.room.ShangTingRoom.web.admin.vo.system.user.SystemUserInfoVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "后台管理系统登录管理")
@RestController
@RequestMapping("/admin")
public class LoginController {
    private final LoginService loginService;
    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }
    @Operation(summary = "获取图形验证码")
    @GetMapping("login/captcha")
    public Result<CaptchaVo> getCaptcha() {
        CaptchaVo captcha = loginService.getCaptcha();
        return Result.ok(captcha);
    }

    @Operation(summary = "登录")
    @PostMapping("login")
    public Result<String> login(@RequestBody LoginVo loginVo) {
        String token= loginService.login(loginVo);
        return Result.ok();
    }

    @Operation(summary = "获取登陆用户个人信息")
    @GetMapping("info")
    public Result<SystemUserInfoVo> info() {
        return Result.ok();
    }
}