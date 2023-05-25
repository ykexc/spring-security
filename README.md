# spring-security

### springboot3+springsecurity6+jwt解决方案，此demo包括:

- #### 基于jwt的登录和注册包含refreshToken实现

- #### Authentication和Authorization

- #### springsecurity的`AccessDeniedHandler`、`AuthenticationEntryPoint`、`AuthenticationFailureHandler`、`AuthenticationSuccessHandler`以及自定义异常枚举+全局异常的解决方案

- #### 基于`spring-boot-starter-validation`实现接口参数校验

- #### Dockerfile+docker-compose部署

### 项目部署

#### 1.上传Jar包、Dockerfile、docker-compose及sql文件在服务器上

2.创建一个文件夹,将docker-compose和Dockerfile移至此文件夹,在文件夹内创建文件夹init和data，将sql文件移动到init文件夹

```shell
mkdir app
~~~~~
cd app
mkdir init
mkdir data
mv *.sql init/
```

3.执行命令

```shell
docker-compose up
```

