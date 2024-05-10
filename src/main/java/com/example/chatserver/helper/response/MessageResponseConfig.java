package com.example.chatserver.helper.response;


import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(
        prefix = "message-response"
)
public class MessageResponseConfig {
    private Map<String, String> params;

    public MessageResponseConfig() {
    }

    public Map<String, String> getParams() {
        return this.params;
    }

    public void setParams(final Map<String, String> params) {
        this.params = params;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof MessageResponseConfig)) {
            return false;
        } else {
            MessageResponseConfig other = (MessageResponseConfig)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$params = this.getParams();
                Object other$params = other.getParams();
                if (this$params == null) {
                    if (other$params != null) {
                        return false;
                    }
                } else if (!this$params.equals(other$params)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof MessageResponseConfig;
    }

    public int hashCode() {
        int result = 1;
        Object $params = this.getParams();
        result = result * 59 + ($params == null ? 43 : $params.hashCode());
        return result;
    }

    public String toString() {
        return "MessageResponseConfig(params=" + this.getParams() + ")";
    }
}
