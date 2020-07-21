package com.codeleven.config;

/**
 * 配置信息数据
 */
public class ConfigInfo {
    private static final ConfigInfo CONFIG_INFO = new ConfigInfo();
    private String yapiServerHost;
    private String swaggerExecutablePath;
    private String swaggerResultSavePath;
    private String swaggerScanPath;
    private String projectToken;
    private boolean enableDebug;

    private ConfigInfo() {
    }

    public static ConfigInfo getInstance() {
        return CONFIG_INFO;
    }

    public String getYapiServerHost() {
        return yapiServerHost;
    }

    public void setYapiServerHost(String yapiServerHost) {
        this.yapiServerHost = yapiServerHost;
    }

    public String getSwaggerExecutablePath() {
        return swaggerExecutablePath;
    }

    public void setSwaggerExecutablePath(String swaggerExecutablePath) {
        this.swaggerExecutablePath = swaggerExecutablePath;
    }

    public String getSwaggerResultSavePath() {
        return swaggerResultSavePath;
    }

    public void setSwaggerResultSavePath(String swaggerResultSavePath) {
        this.swaggerResultSavePath = swaggerResultSavePath;
    }

    public String getSwaggerScanPath() {
        return swaggerScanPath;
    }

    public void setSwaggerScanPath(String swaggerScanPath) {
        this.swaggerScanPath = swaggerScanPath;
    }

    public String getProjectToken() {
        return projectToken;
    }

    public void setProjectToken(String projectToken) {
        this.projectToken = projectToken;
    }

    public boolean isEnableDebug() {
        return enableDebug;
    }

    public void setEnableDebug(boolean enableDebug) {
        this.enableDebug = enableDebug;
    }
}
