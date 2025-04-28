package com.room.ShangTingRoom.web.admin.service.impl;

import org.springframework.util.StringUtils;
import com.room.ShangTingRoom.common.constant.RedisConstant;
import com.room.ShangTingRoom.common.exception.LeaseException;
import com.room.ShangTingRoom.common.result.ResultCodeEnum;
import com.room.ShangTingRoom.common.utils.JwtUtil;
import com.room.ShangTingRoom.model.entity.SystemUser;
import com.room.ShangTingRoom.model.enums.BaseStatus;
import com.room.ShangTingRoom.web.admin.mapper.SystemUserMapper;
import com.room.ShangTingRoom.web.admin.service.LoginService;
import com.room.ShangTingRoom.web.admin.vo.login.CaptchaVo;
import com.room.ShangTingRoom.web.admin.vo.login.LoginVo;
import com.wf.captcha.SpecCaptcha;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;


import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {
    private final StringRedisTemplate redisTemplate;
    private final SystemUserMapper systemUserMapper;
    @Autowired
    public LoginServiceImpl(StringRedisTemplate redisTemplate,
                            SystemUserMapper systemUserMapper) {
        this.redisTemplate = redisTemplate;
        this.systemUserMapper = systemUserMapper;
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
    @Override
    public String login(LoginVo loginVo) {
        //1.判断是否输入了验证码
        if (!StringUtils.hasText(loginVo.getCaptchaCode())) {
            throw new LeaseException(ResultCodeEnum.ADMIN_CAPTCHA_CODE_NOT_FOUND);
        }

        //2.校验验证码
        String code = redisTemplate.opsForValue().get(loginVo.getCaptchaKey());
        if (code == null) {
            throw new LeaseException(ResultCodeEnum.ADMIN_CAPTCHA_CODE_EXPIRED);
        }

        if (!code.equals(loginVo.getCaptchaCode().toLowerCase())) {
            throw new LeaseException(ResultCodeEnum.ADMIN_CAPTCHA_CODE_ERROR);
        }

        //3.校验用户是否存在
        SystemUser systemUser = systemUserMapper.selectOneByUsername(loginVo.getUsername());

        if (systemUser == null) {
            throw new LeaseException(ResultCodeEnum.ADMIN_ACCOUNT_NOT_EXIST_ERROR);
        }

        //4.校验用户是否被禁
        if (systemUser.getStatus() == BaseStatus.DISABLE) {
            throw new LeaseException(ResultCodeEnum.ADMIN_ACCOUNT_DISABLED_ERROR);
        }

        //5.校验用户密码
        if (!systemUser.getPassword().equals(DigestUtils.md5Hex(loginVo.getPassword()))) {
            throw new LeaseException(ResultCodeEnum.ADMIN_ACCOUNT_ERROR);
        }

        //6.创建并返回TOKEN
        return JwtUtil.createToken(systemUser.getId(), systemUser.getUsername());
    }
}
