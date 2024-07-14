FROM openjdk:8-jdk-alpine
# 设置工作目录
WORKDIR /app
# 创建共享目录
#RUN mkdir -p /shared
# 声明挂载点
VOLUME /tmp
## 设置时区为东八区
ENV TZ=Asia/Shanghai
#RUN apk add --no-cache tzdata && \
#    ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && \
#    echo $TZ > /etc/timezone
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && \
    echo $TZ > /etc/timezone
#ARG JAR_FILE=target/*.jar
# Adjust the path to the JAR file
#ARG JAR_FILE=target/TestEureka-1.0.0-SNAPSHOT.jar
#COPY ${JAR_FILE} app.jar
ADD TestEureka-1.0.0-SNAPSHOT.jar app.jar

# 暴露应用端口和调试端口
#EXPOSE 8080 5005
#ENTRYPOINT ["sh", "-c", "java -Djava.security.egd=file:/dev/./urandom -jar /app.jar"]
# 启动命令,开启远程调试 java8
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005", "-jar", "/app/app.jar"]
# 启动命令,开启远程调试 java9+
#ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005", "-jar", "/app/app.jar"]
