package com.codeleven.config;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.project.Project;

import static com.codeleven.config.YApiServerConfig.*;

public class ConfigInfoManager {
    private static ConfigInfoManager instance;
    private ConfigInfo configInfo;
    private PropertiesComponent propertiesComponent;

    private ConfigInfoManager(Project project) {
        this.propertiesComponent = PropertiesComponent.getInstance(project);
        this.configInfo = ConfigInfo.getInstance();

        this.updateConfigInfo();
    }

    public static ConfigInfoManager getInstance(Project project) {
        if (instance == null) {
            instance = new ConfigInfoManager(project);
        }
        return instance;
    }

    public void updateConfigInfo() {
        this.configInfo.setProjectToken(propertiesComponent.getValue(CONFIG_KEY_YAPI_PROJECT_TOKEN, ""));
        this.configInfo.setSwaggerExecutablePath(propertiesComponent.getValue(CONFIG_KEY_SWAGGER_EXECUTABLE_PATH, ""));
        this.configInfo.setSwaggerScanPath(propertiesComponent.getValue(CONFIG_KEY_SWAGGER_SCAN_PATH, ""));
        this.configInfo.setYapiServerHost(propertiesComponent.getValue(CONFIG_KEY_YAPI_SERVER_HOST, ""));
        this.configInfo.setSwaggerResultSavePath(propertiesComponent.getValue(CONFIG_KEY_SWAGGER_RESULT_SAVE_PATH, ""));
        this.configInfo.setEnableDebug(Boolean.valueOf(propertiesComponent.getValue(CONFIG_KEY_YAPI_ENABLE_DEBUG, "")));
    }

    public void updateProperty(String propertyKey, String propertyValue) {
        propertiesComponent.setValue(propertyKey, propertyValue);
        this.updateConfigInfo();
    }

    public boolean isChanged(String propertyKey, String yourValue) {
        String persistenceValue = propertiesComponent.getValue(propertyKey, "");
        return !persistenceValue.equals(yourValue);
    }

    public String getProperty(String propertyKey, String defaultValue) {
        return propertiesComponent.getValue(propertyKey, defaultValue);
    }

    public ConfigInfo getConfigInfo() {
        return configInfo;
    }
}
