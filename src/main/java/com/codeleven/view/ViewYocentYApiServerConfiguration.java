package com.codeleven.view;

import javax.swing.*;

public class ViewYocentYApiServerConfiguration {
    private JPanel rootPanel;
    private JTextField yapiServerHost;
    private JTextField swaggerExecutor;
    private JTextField scanPath;
    private JTextField projectToken;
    private JTextField swaggerResultSavePath;
    private JCheckBox enableDebugCheckbox;

    public boolean isEnableDebug() {
        return enableDebugCheckbox.isSelected();
    }

    public void setEnableDebug(boolean enable) {
        enableDebugCheckbox.setSelected(enable);
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    public void setRootPanel(JPanel rootPanel) {
        this.rootPanel = rootPanel;
    }

    public void setSwaggerExecutor(String executorPath) {
        swaggerExecutor.setText(executorPath);
    }

    public String getYapiServerHost() {
        return this.yapiServerHost.getText();
    }

    public void setYapiServerHost(String host) {
        yapiServerHost.setText(host);
    }

    public String getSwaggerExecutorPath() {
        return this.swaggerExecutor.getText();
    }

    public String getSwaggerScanPath() {
        return this.scanPath.getText();
    }

    public void setSwaggerScanPath(String scanPath) {
        this.scanPath.setText(scanPath);
    }

    public String getSwaggerResultSavePath() {
        return this.swaggerResultSavePath.getText();
    }

    public void setSwaggerResultSavePath(String path) {
        swaggerResultSavePath.setText(path);
    }

    public String getProjectToken() {
        return this.projectToken.getText();
    }

    public void setProjectToken(String token) {
        projectToken.setText(token);
    }
}
