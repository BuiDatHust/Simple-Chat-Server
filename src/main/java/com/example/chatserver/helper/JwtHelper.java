package com.example.chatserver.helper;


import java.security.Key;
import java.util.Date;

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

  public String generateJwtToken(TokenType tokenType,String phoneNumber, Long time, Long expireTime) {
    if(tokenType==TokenType.ACCESS_TOKEN) {
      return Jwts.builder()
              .setIssuedAt(DateTimeHelper.convertUtcToDate(time))
              .setExpiration(DateTimeHelper.convertUtcToDate(expireTime))
              .setPayload(phoneNumber)
              .signWith(key(tokenType), SignatureAlgorithm.HS256)
              .compact();
    }
    if(tokenType==TokenType.REFRESH_TOKEN) {
      return Jwts.builder()
              .setIssuedAt(DateTimeHelper.convertUtcToDate(time))
              .setExpiration(DateTimeHelper.convertUtcToDate(expireTime))
              .setPayload(phoneNumber)
              .signWith(key(tokenType), SignatureAlgorithm.HS256)
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