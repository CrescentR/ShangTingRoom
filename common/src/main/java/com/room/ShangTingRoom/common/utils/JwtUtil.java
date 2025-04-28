package com.room.ShangTingRoom.common.utils;

import com.room.ShangTingRoom.common.exception.LeaseException;
import com.room.ShangTingRoom.common.result.ResultCodeEnum;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtUtil {
    private final static long tokenExpiration = 60 * 60 * 1000L;
    private final static SecretKey tokenSignKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    public static String createToken(Long uerId,String username){
        String token = Jwts.builder()
                .setSubject("USER_INFO")
                .claim("userId",uerId)
                .claim("username",username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .signWith(tokenSignKey)
                .compact();
        return token;
    }
    public static Claims parseToken(String token){
        if(token==null){
            throw new LeaseException(ResultCodeEnum.ADMIN_LOGIN_AUTH);
        }
        try{
            JwtParser jwtparser=Jwts.parserBuilder().setSigningKey(tokenSignKey).build();
            return jwtparser.parseClaimsJws(token).getBody();
        }catch (ExpiredJwtException e){
            throw new LeaseException(ResultCodeEnum.TOKEN_EXPIRED);
        }catch (JwtException e){
            throw new LeaseException(ResultCodeEnum.TOKEN_INVALID);
        }
    }
}
