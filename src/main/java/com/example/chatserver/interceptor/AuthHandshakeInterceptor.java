package com.example.chatserver.interceptor;

import com.example.chatserver.constant.TokenParams;
import com.example.chatserver.helper.jwt.JwtHelper;
import com.example.chatserver.service.auth.enums.TokenType;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthHandshakeInterceptor implements HandshakeInterceptor {
    private final JwtHelper jwtHelper;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        // Extract token or credentials from request (e.g., headers or query parameters)
        String tokenHeader = request.getHeaders().getFirst("Authorization");
        log.info("validate token ws,header: {}", tokenHeader);
        if (Objects.isNull(tokenHeader) || !tokenHeader.startsWith("Bearer ")) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return false;
        }
        String token = tokenHeader.substring(7);
        boolean isValidated = this.jwtHelper.validateToken(TokenType.ACCESS_TOKEN, token);
        if (  !isValidated ) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return false;
        }
        // Add user details to attributes if necessary
        Claims claims = this.jwtHelper.getClaims(TokenType.ACCESS_TOKEN, token);
        attributes.put(TokenParams.userId, claims.get(TokenParams.userId));
        attributes.put(TokenParams.phoneNumber, claims.get(TokenParams.phoneNumber));
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
        // No-op
    }
}
