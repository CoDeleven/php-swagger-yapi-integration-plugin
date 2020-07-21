# PHP-Swagger-YApi-Integration-Plugin
 本插件主要用来生成基于PHP注解的API接口文档并将接口文档上传至YAPI服务器。
 
## 依赖
 
 *  插件的使用需要安装`zircote/swagger-php` （注意使用全局的）： 
 ```bash
composer global require zircote/swagger-php 2.0.*
 ```

 * YApi服务器
 
 * 最低版本：`phpstorm 2016.2` 
 
## 安装
1. 打开你的PhpStorm
2. `ctrl + alt + s` 唤出项目配置窗口
3. 选择`plugins`,点击齿轮。如下图所示：
![安装插件面板](https://raw.githubusercontent.com/CoDeleven/php-swagger-yapi-integration-plugin/master/pic/plugin-install1.png)
4. 选择下载的plugin文件，点击确定后安装完毕，重启后生效
![安装插件面板](https://raw.githubusercontent.com/CoDeleven/php-swagger-yapi-integration-plugin/master/pic/plugin-install2.png)

## 配置

### 配置PHP解释器
 使用`ctrl + alt + s` 唤出项目配置窗口，如下所示：
 
 ![设置面板](https://raw.githubusercontent.com/CoDeleven/php-swagger-yapi-integration-plugin/master/settings-php.png)

### 配置YAPIServer信息
 使用`ctrl + alt + s` 唤出项目配置窗口，如下所示：
 
 ![设置面板](https://raw.githubusercontent.com/CoDeleven/php-swagger-yapi-integration-plugin/master/pic/setting.png)
 
 

1. YApi服务器地址： 不多哔哔，一般情况下是 `http://192.168.2.18:3000/` （直接复制使用即可）
2. Swagger可执行文件： 我们前面全局安装的 `zircote/swagger-php` 的可执行文件路径，比如我的就是
```text
C:\Users\Administrator\AppData\Roaming\Composer\vendor\zircote\swagger-php\bin\swagger
```
3. 待扫描的PHP文件路径：指定 `swagger-php` 要扫描哪个路径上的PHP文件，注意是文件夹路径，最后保留斜杠`/`。推荐扫描项目根目录下的application文件夹。
4. 保存swagger结果文件的路径： 指定 `swagger-php` 扫描后生成的API文档保存路径，注意是文件夹路径，最后保留斜杠`/`。推荐保存在项目根目录下。
5. 项目Token： 指定我们的API文档要保存到哪个项目中（这里的Token来自YAPI的 X项目-设置-Token配置-工具标识）
![ProjectToken获取](https://raw.githubusercontent.com/CoDeleven/php-swagger-yapi-integration-plugin/master/pic/project%20token.png)

## 使用
点击右上角的API标签，如下所示：
![运行Yocent-YAPI](https://raw.githubusercontent.com/CoDeleven/php-swagger-yapi-integration-plugin/master/pic/yocent-yapi-run.png)


# 功能清单

* [x] 接口文档的生成及上传
* [ ] 快捷生成Swagger注解（Swagger注解很长，手动一个个敲有点恶心，寻求快速生成的方法） 
* [ ] 实现多类型文档的生成
* [ ] 实现前端文档的生成

# 感谢
下面排名不分先后：
   1. czw
