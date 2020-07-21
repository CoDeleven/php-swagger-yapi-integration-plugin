package com.codeleven.config;

import com.codeleven.view.ViewYocentYApiServerConfiguration;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class YApiServerConfig implements Configurable {
    public static final String CONFIG_KEY_YAPI_SERVER_HOST = "key_yocent_yapi_server_host";
    public static final String CONFIG_KEY_SWAGGER_EXECUTABLE_PATH = "key_yocent_swagger_executable_path";
    public static final String CONFIG_KEY_SWAGGER_RESULT_SAVE_PATH = "key_yocent_swagger_result_save_path";
    public static final String CONFIG_KEY_SWAGGER_SCAN_PATH = "key_yocent_swagger_scan_path";
    public static final String CONFIG_KEY_YAPI_PROJECT_TOKEN = "key_yocent_yapi_project_token";
    public static final String CONFIG_KEY_YAPI_ENABLE_DEBUG = "key_yocent_swagger_enable_debug";
    private static ConfigInfoManager configInfoManager;
    private ViewYocentYApiServerConfiguration configuration;

    public YApiServerConfig(Project project) {
        this.configuration = new ViewYocentYApiServerConfiguration();
        configInfoManager = ConfigInfoManager.getInstance(project);
        reset();
    }

    @Override
    public String getDisplayName() {
        return "昱晟YApi设置";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        return configuration.getRootPanel();
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        // 返回帮助地址
        return "http://192.168.2.18:2017/ysphp/phpstorm-plugin/php-swagger-yapi-integration-plugin/blob/master/README.md";
    }

    /**
     * 每次打开设置栏时，触发
     */
    @Override
    public void reset() {
        this.updateUI();
    }

    @Override
    public void disposeUIResources() {
    }

    /**
     * 对配置进行修改时，会触发该方法
     *
     * @return
     */
    @Override
    public boolean isModified() {
        boolean projectTokenChanged = configInfoManager.isChanged(CONFIG_KEY_YAPI_PROJECT_TOKEN, this.configuration.getProjectToken());
        if (projectTokenChanged) return true;

        boolean serverHostChanged = configInfoManager.isChanged(CONFIG_KEY_YAPI_SERVER_HOST, this.configuration.getYapiServerHost());
        if (serverHostChanged) return true;

        boolean swaggerExecutablePathChanged = configInfoManager.isChanged(CONFIG_KEY_SWAGGER_EXECUTABLE_PATH,
                this.configuration.getSwaggerExecutorPath());
        if (swaggerExecutablePathChanged) return true;

        boolean swaggerResultSavePathChanged = configInfoManager.isChanged(CONFIG_KEY_SWAGGER_RESULT_SAVE_PATH,
                this.configuration.getSwaggerResultSavePath());
        if (swaggerResultSavePathChanged) return true;

        boolean swaggerScanPathChanged = configInfoManager.isChanged(CONFIG_KEY_SWAGGER_SCAN_PATH,
                this.configuration.getSwaggerScanPath());

        if (swaggerScanPathChanged) return true;

        boolean enableDebugChanged = configInfoManager.isChanged(CONFIG_KEY_YAPI_ENABLE_DEBUG, String.valueOf(this.configuration.isEnableDebug()));
        return enableDebugChanged;

    }

    /**
     * 当点击Apply按钮时，就触发应用
     */
    @Override
    public void apply() {
        // 去除斜杠
        String host = removeEndSlash(this.configuration.getYapiServerHost());
        configInfoManager.updateProperty(CONFIG_KEY_YAPI_SERVER_HOST, host);

        String executablePath = removeEndSlash(this.configuration.getSwaggerExecutorPath());
        configInfoManager.updateProperty(CONFIG_KEY_SWAGGER_EXECUTABLE_PATH, executablePath);

        String swaggerSavePath = addEndSlash(this.configuration.getSwaggerResultSavePath());
        configInfoManager.updateProperty(CONFIG_KEY_SWAGGER_RESULT_SAVE_PATH, swaggerSavePath);

        String swaggerScanPath = addEndSlash(this.configuration.getSwaggerScanPath());
        configInfoManager.updateProperty(CONFIG_KEY_SWAGGER_SCAN_PATH, swaggerScanPath);

        configInfoManager.updateProperty(CONFIG_KEY_YAPI_PROJECT_TOKEN, this.configuration.getProjectToken());
        configInfoManager.updateProperty(CONFIG_KEY_YAPI_ENABLE_DEBUG, this.configuration.isEnableDebug() + "");

        this.updateUI();
    }

    private String addEndSlash(String value) {
        if (!value.endsWith("/") && !value.endsWith("\\")) {
            value += "/";
        }
        return value;
    }

    private String removeEndSlash(String value) {
        if (value.endsWith("/") || value.endsWith("\\")) {
            value = value.substring(0, value.length() - 1);
        }
        return value;
    }

    /**
     * 更新UI界面
     */
    private void updateUI() {
        configuration.setYapiServerHost(configInfoManager.getProperty(CONFIG_KEY_YAPI_SERVER_HOST, ""));
        configuration.setProjectToken(configInfoManager.getProperty(CONFIG_KEY_YAPI_PROJECT_TOKEN, ""));
        configuration.setSwaggerExecutor(configInfoManager.getProperty(CONFIG_KEY_SWAGGER_EXECUTABLE_PATH, ""));
        configuration.setSwaggerResultSavePath(configInfoManager.getProperty(CONFIG_KEY_SWAGGER_RESULT_SAVE_PATH, ""));
        configuration.setSwaggerScanPath(configInfoManager.getProperty(CONFIG_KEY_SWAGGER_SCAN_PATH, ""));
        configuration.setEnableDebug(Boolean.parseBoolean(configInfoManager.getProperty(CONFIG_KEY_YAPI_ENABLE_DEBUG, "")));
    }

}
