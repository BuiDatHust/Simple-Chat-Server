package com.example.chatserver.helper.jwt;


import java.security.Key;
import java.util.HashMap;
import java.util.Map;

import com.example.chatserver.helper.datetime.DateTimeHelper;
import com.example.chatserver.service.auth.enums.TokenType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
@Slf4j
public class JwtHelper {
  @Value("${app.security.jwt.access-token.secret}")
  private String jwtAccessTokenSecret;

  @Value("${app.security.jwt.access-token.expire-time}")
  private int jwtAccessTokenExpirationMs;

  @Value("${app.security.jwt.refresh-token.secret}")
  private String jwtRefreshTokenSecret;

  @Value("${app.security.jwt.refresh-token.expire-time}")
  private int jwtRefreshTokenExpirationMs;

  public String generateJwtToken(TokenGenrationData tokenGenrationData) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("phoneNumber", tokenGenrationData.getPhoneNumber());
    claims.put("tokenType", tokenGenrationData.getTokenType());
    claims.put("deviceName", tokenGenrationData.getDeviceName());
    claims.put("userId", tokenGenrationData.getUserId());
    if(tokenGenrationData.getTokenType()==TokenType.ACCESS_TOKEN) {
      return Jwts.builder()
              .setIssuedAt(DateTimeHelper.convertUtcToDate(tokenGenrationData.getTime()))
              .setExpiration(DateTimeHelper.convertUtcToDate(tokenGenrationData.getExpireTime()))
              .setClaims(claims)
              .signWith(key(tokenGenrationData.getTokenType()), SignatureAlgorithm.HS256)
              .compact();
    }
    if(tokenGenrationData.getTokenType()==TokenType.REFRESH_TOKEN) {
      return Jwts.builder()
              .setIssuedAt(DateTimeHelper.convertUtcToDate(tokenGenrationData.getTime()))
              .setExpiration(DateTimeHelper.convertUtcToDate(tokenGenrationData.getExpireTime()))
              .setClaims(claims)
              .signWith(key(tokenGenrationData.getTokenType()), SignatureAlgorithm.HS256)
              .compact();
    }
    return "";
  }
  
  private Key key(TokenType tokenType) {
    if(tokenType==TokenType.ACCESS_TOKEN){
      return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtAccessTokenSecret));
    }
    if(tokenType==TokenType.REFRESH_TOKEN) {
      return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtRefreshTokenSecret));
    }

    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(""));
  }

  public boolean validateToken(TokenType tokenType, String authToken) {
    try {
      Jwts.parserBuilder().setSigningKey(key(tokenType)).build().parse(authToken);
      return true;
    } catch (MalformedJwtException e) {
      log.error("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      log.error("JWT token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      log.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      log.error("JWT claims string is empty: {}", e.getMessage());
    }

    return false;
  }

  public Long getExpireTime(TokenType tokenType, Long time) {
    if(tokenType==TokenType.ACCESS_TOKEN) {
      return time + jwtAccessTokenExpirationMs;
    }
    return time + jwtRefreshTokenExpirationMs;
  }
}