# 选择jdk版本
FROM openjdk:8-jdk
MAINTAINER "王玉星 <309597117@qq.com>"

# 复制jar包
ADD ./target/*.jar app.jar
# 暴露端口
EXPOSE 8080
# 启动环境变量
ENV PARAMS="--server.port=8080 --spring.profiles.active=prod --spring.cloud.nacos.discovery.server-addr=csc-nacos.csc:8848 --spring.cloud.nacos.config.server-addr=csc-nacos.csc:8848 --spring.cloud.nacos.config.namespace=2000a05a-5a88-42a3-8323-bb2f8c7d9090 --spring.profiles.active=prod --spring.cloud.nacos.config.file-extension=yml"
# 调整容器时区
RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone
# 容器启动命令
ENTRYPOINT ["/bin/sh","-c","java -Dfile.encoding=utf8 -Xms20m -Xmx100m -Djava.security.egd=file:/dev/./urandom -jar app.jar ${PARAMS}"]