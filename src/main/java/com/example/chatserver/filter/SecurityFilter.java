package com.example.chatserver.filter;

import com.example.chatserver.constant.TokenParams;
import com.example.chatserver.helper.jwt.JwtHelper;
import com.example.chatserver.helper.response.GeneralResponse;
import com.example.chatserver.helper.response.ResponseStatus;
import com.example.chatserver.helper.response.ResponseStatusCodeEnum;
import com.example.chatserver.service.auth.enums.TokenType;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
@Order(5)
public class SecurityFilter extends OncePerRequestFilter {
    private final JwtHelper jwtHelper;
    private List<String> excludeUrlPatterns = new ArrayList<>(List.of(
            "/chat-server/v1/auth/register-by-phone",
            "/chat-server/v1/auth/login",
            "/chat-server/v1/otp/register-verification",
            "/chat-server/v1/otp/resend",
            "/chat-server/v1/otp/login-verification"
    ));

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        filterChain.doFilter(request,response);

        String authorization = requestWrapper.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            sendError(responseWrapper, ResponseStatusCodeEnum.FORBIDDEN_TOKEN_INVALID.getCode());
            return;
        }

        authorization = authorization.substring(7);
        try {
            boolean isValidate = jwtHelper.validateToken(TokenType.ACCESS_TOKEN, authorization);
            if(!isValidate) {
                sendError(responseWrapper, ResponseStatusCodeEnum.FORBIDDEN_TOKEN_INVALID.getCode());
            }
            Claims claims = jwtHelper.getClaims(TokenType.ACCESS_TOKEN, authorization);
            if(Objects.isNull(claims)) {
                sendError(responseWrapper, ResponseStatusCodeEnum.FORBIDDEN_TOKEN_INVALID.getCode());
            }

            requestWrapper.setAttribute(TokenParams.phoneNumber, claims.get(TokenParams.phoneNumber, String.class));
            requestWrapper.setAttribute(TokenParams.userId, claims.get(TokenParams.userId, Long.class));
            requestWrapper.setAttribute(TokenParams.tokenType, claims.get(TokenParams.tokenType, String.class));
            requestWrapper.setAttribute(TokenParams.deviceName, claims.get(TokenParams.deviceName, String.class));
//                filterChain.doFilter(requestWrapper, responseWrapper);
//                responseWrapper.copyBodyToResponse();
        } catch (ExpiredJwtException e) {
            log.info("Exception token expire");
            sendError(responseWrapper, ResponseStatusCodeEnum.FORBIDDEN_TOKEN_INVALID.getCode());
        } catch (Exception e) {
            log.info("Unknown exception in validating token", e);
            sendError(responseWrapper, ResponseStatusCodeEnum.FORBIDDEN_TOKEN_INVALID.getCode());
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return excludeUrlPatterns.contains(request.getServletPath());
    }

    private void sendError(ContentCachingResponseWrapper responseWrapper, String errorCode) {
        try {
            responseWrapper.setStatus(HttpStatus.FORBIDDEN.value());
            responseWrapper.setContentType("application/json; charset=utf-8");
            GeneralResponse<Object> responseObject = new GeneralResponse<>();
            ResponseStatus responseStatus = new ResponseStatus(errorCode, true);
            responseObject.setStatus(responseStatus);
            new ObjectMapper()
                    .setAnnotationIntrospector(new JacksonAnnotationIntrospector())
                    .registerModule(new JavaTimeModule())
                    .setDateFormat(new StdDateFormat())
                    .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                    .setTimeZone(Calendar.getInstance().getTimeZone())
                    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                    .writeValue(responseWrapper.getWriter(), responseObject);
            responseWrapper.copyBodyToResponse();
        } catch (IOException e) {
            log.error("io exception in writing response", e);
        }
    }
}
