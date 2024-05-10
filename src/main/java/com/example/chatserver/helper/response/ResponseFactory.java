package com.example.chatserver.helper.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

@Component
public class ResponseFactory {
    private static final Logger log = LoggerFactory.getLogger(ResponseFactory.class);
    @Autowired(required = false)
    private ErrorService errorService;
    @Autowired
    private MessageResponseConfig messageResponseConfig;

    public ResponseFactory() {
    }

    private String replaceParams(String message, Map<String, String> params) {
        Iterator var3;
        Map.Entry param;
        if (!CollectionUtils.isEmpty(params)) {
            for(var3 = params.entrySet().iterator(); var3.hasNext(); message = message.replaceAll("%%" + (String)param.getKey() + "%%", (String)param.getValue())) {
                param = (Map.Entry)var3.next();
            }
        }

        if (!CollectionUtils.isEmpty(this.messageResponseConfig.getParams())) {
            for(var3 = this.messageResponseConfig.getParams().entrySet().iterator(); var3.hasNext(); message = message.replaceAll("%%" + (String)param.getKey() + "%%", (String)param.getValue())) {
                param = (Map.Entry)var3.next();
            }
        }

        return message;
    }

    private ResponseStatus parseResponseStatus(String code, Map<String, String> params) {
        ResponseStatus responseStatus = new ResponseStatus(code, true);
        responseStatus.setMessage(this.replaceParams(responseStatus.getMessage(), params));
        String errorDetail = null;
        if (Objects.nonNull(this.errorService)) {
            errorDetail = this.errorService.getErrorDetail(code, LocaleContextHolder.getLocale().getLanguage());
        }

        if (Objects.nonNull(errorDetail)) {
            responseStatus.setDisplayMessage(this.replaceParams(errorDetail, params));
        } else {
            responseStatus.setDisplayMessage(responseStatus.getMessage());
        }

        log.debug(responseStatus.toString());
        responseStatus.setResponseTime(new Date());
        return responseStatus;
    }

    public <T> ResponseEntity<GeneralResponse<T>> success(T data) {
        GeneralResponse<T> responseObject = new GeneralResponse();
        responseObject.setData(data);
        return this.success(responseObject);
    }

    public <T> ResponseEntity<GeneralResponse<T>> success(GeneralResponse<T> responseObject) {
        ResponseStatus responseStatus = this.parseResponseStatus(ResponseStatusCodeEnum.SUCCESS.getCode(), (Map)null);
        responseObject.setStatus(responseStatus);
        return ResponseEntity.ok().body(responseObject);
    }

    public <T> ResponseEntity<GeneralResponse<T>> successWithHeader(MultiValueMap<String, String> header, T data) {
        GeneralResponse<T> responseObject = new GeneralResponse();
        responseObject.setData(data);
        ResponseStatus responseStatus = this.parseResponseStatus(ResponseStatusCodeEnum.SUCCESS.getCode(), (Map)null);
        responseObject.setStatus(responseStatus);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.addAll(header);
        return ((ResponseEntity.BodyBuilder)ResponseEntity.ok().headers(responseHeaders)).body(responseObject);
    }

    public <T> ResponseEntity<GeneralResponse<T>> fail(T data, ResponseStatusCode code) {
        GeneralResponse<T> responseObject = new GeneralResponse();
        responseObject.setData(data);
        return this.fail(responseObject, code, (Map)null);
    }

    public <T> ResponseEntity<GeneralResponse<T>> fail(ResponseStatusCode code) {
        GeneralResponse<T> responseObject = new GeneralResponse();
        return this.fail(responseObject, code, (Map)null);
    }

    public <T> ResponseEntity<GeneralResponse<T>> fail(GeneralResponse<T> responseObject, ResponseStatusCode code) {
        if (Objects.isNull(responseObject)) {
            responseObject = new GeneralResponse();
        }

        return this.fail(responseObject, code, (Map)null);
    }

    public <T> ResponseEntity<GeneralResponse<T>> fail(GeneralResponse<T> responseObject, ResponseStatusCode code, Map<String, String> params) {
        ResponseStatus responseStatus = this.parseResponseStatus(code.getCode(), params);
        if (Objects.isNull(responseObject)) {
            responseObject = new GeneralResponse();
        }

        responseObject.setStatus(responseStatus);
        return ResponseEntity.status(code.getHttpCode()).body(responseObject);
    }

    public <T> void httpServletResponseToClient(HttpServletResponse httpServletResponse, T data, ResponseStatusCode statusCode) throws IOException {
        this.httpServletResponseToClient(httpServletResponse, data, statusCode, (Map)null);
    }

    public <T> void httpServletResponseToClient(HttpServletResponse httpServletResponse, T data, ResponseStatusCode statusCode, Map<String, String> params) throws IOException {
        GeneralResponse<T> response = new GeneralResponse();
        response.setData(data);
        ResponseStatus responseStatus = this.parseResponseStatus(statusCode.getCode(), params);
        response.setStatus(responseStatus);
        this.writeToHttpServletResponse(httpServletResponse, response, statusCode);
    }

    public void writeToHttpServletResponse(HttpServletResponse httpServletResponse, Object response, ResponseStatusCode statusCode) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String responseString = mapper.writeValueAsString(response);
        httpServletResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());
        httpServletResponse.setStatus(statusCode.getHttpCode());
        httpServletResponse.setHeader("Content-Type", "application/json");
        httpServletResponse.getWriter().write(responseString);
        httpServletResponse.getWriter().flush();
        httpServletResponse.getWriter().close();
    }
}