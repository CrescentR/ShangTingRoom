package com.room.ShangTingRoom.web.admin.service.impl;

import com.room.ShangTingRoom.web.admin.service.LoginService;
import com.room.ShangTingRoom.web.admin.vo.login.CaptchaVo;
import com.room.ShangTingRoom.common.constant.RedisConstant;
import com.wf.captcha.SpecCaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {
    private final StringRedisTemplate redisTemplate;
    @Autowired
    public LoginServiceImpl(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    @Override
    public CaptchaVo getCaptcha() {
        // 生成验证码
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 4);
        specCaptcha.setCharType(SpecCaptcha.TYPE_ONLY_CHAR);

        String code=specCaptcha.text().toLowerCase();
        String key = RedisConstant.ADMIN_LOGIN_PREFIX+ UUID.randomUUID();
        String img=specCaptcha.toBase64();
        redisTemplate.opsForValue().set(key,code,RedisConstant.ADMIN_LOGIN_CAPTCHA_TTL_SEC, TimeUnit.SECONDS);
        return new CaptchaVo(img,key);
    }
}
