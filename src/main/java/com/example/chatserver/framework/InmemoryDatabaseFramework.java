package com.example.chatserver.framework;

public interface InmemoryDatabaseFramework {
    void setKey(String key, String value);
    String getKey(String key);
}
