package com.room.ShangTingRoom.web.admin.custom.interceptor;

import com.room.ShangTingRoom.common.exception.LeaseException;
import com.room.ShangTingRoom.common.result.ResultCodeEnum;
import com.room.ShangTingRoom.common.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {
        // 排除登录和获取验证码等不需要鉴权的路径
        String uri = request.getRequestURI();
        if (uri.contains("/admin/login")) {
            return true;
        }

        // 获取并验证token
        String token = request.getHeader("access-token");
        if (token == null || token.isEmpty()) {
            throw new LeaseException(ResultCodeEnum.ADMIN_LOGIN_AUTH);
        }

        // 解析token并将用户信息存入请求属性
        Claims claims = JwtUtil.parseToken(token);
        request.setAttribute("userId", claims.get("userId"));
        request.setAttribute("username", claims.get("username"));

        return true;
    }
}
