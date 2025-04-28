package com.room.ShangTingRoom.web.admin.service;

import com.room.ShangTingRoom.web.admin.vo.login.CaptchaVo;
import com.room.ShangTingRoom.web.admin.vo.login.LoginVo;

public interface LoginService {
    CaptchaVo getCaptcha();
    String login(LoginVo loginVo);
}
