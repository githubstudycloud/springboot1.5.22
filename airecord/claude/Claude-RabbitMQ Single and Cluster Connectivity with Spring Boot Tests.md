## Prompt:
undefined

## Response:
undefined

## Prompt:
快速开启一个可连接rabbitmq单机和集群的服务，同时要有测试类

## Response:
非常好,我理解您需要一个基于Spring Boot 1.5.22版本的服务,能够连接RabbitMQ的单机和集群环境,并且包含测试类。我会为您创建这样一个项目,并解释每个步骤。

首先,让我们创建一个基本的Spring Boot项目结构:

```
Copysrc
├── main
│   ├── java
│   │   └── com
│   │       └── example
│   │           └── rabbitmqdemo
│   │               ├── RabbitmqDemoApplication.java
│   │               ├── config
│   │               │   └── RabbitMQConfig.java
│   │               ├── service
│   │               │   └── MessageService.java
│   │               └── controller
│   │                   └── MessageController.java
│   └── resources
│       ├── application.properties
│       └── application-cluster.properties
└── test
    └── java
        └── com
            └── example
                └── rabbitmqdemo
                    ├── RabbitmqDemoApplicationTests.java
                    └── service
                        └── MessageServiceTests.java
```

现在,让我们逐个实现这些文件:

1. `pom.xml`:

```xml
Copy<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>1.5.22.RELEASE</version>
        <relativePath/>
    </parent>
    <groupId>com.example</groupId>
    <artifactId>rabbitmq-demo</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>rabbitmq-demo</name>
    <description>Demo project for Spring Boot and RabbitMQ</description>

    <properties>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-amqp</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
```

2. `RabbitmqDemoApplication.java`:

```java
Copypackage com.example.rabbitmqdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabbitmqDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqDemoApplication.class, args);
    }

}
```

3. `RabbitMQConfig.java`:

```java
Copypackage com.example.rabbitmqdemo.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue hello() {
        return new Queue("hello");
    }

}
```

4. `MessageService.java`:

```java
Copypackage com.example.rabbitmqdemo.service;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private final RabbitTemplate rabbitTemplate;
    private final Queue queue;

    @Autowired
    public MessageService(RabbitTemplate rabbitTemplate, Queue queue) {
        this.rabbitTemplate = rabbitTemplate;
        this.queue = queue;
    }

    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend(queue.getName(), message);
    }

    public String receiveMessage() {
        return (String) rabbitTemplate.receiveAndConvert(queue.getName());
    }
}
```

5. `MessageController.java`:

```java
Copypackage com.example.rabbitmqdemo.controller;

import com.example.rabbitmqdemo.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public void sendMessage(@RequestBody String message) {
        messageService.sendMessage(message);
    }

    @GetMapping
    public String receiveMessage() {
        return messageService.receiveMessage();
    }
}
```

6. `application.properties`:

```properties
Copyspring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest
```

7. `application-cluster.properties`:

```properties
Copyspring.rabbitmq.addresses=192.168.1.1:5672,192.168.1.2:5672,192.168.1.3:5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest
```

8. `RabbitmqDemoApplicationTests.java`:

```java
Copypackage com.example.rabbitmqdemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitmqDemoApplicationTests {

    @Test
    public void contextLoads() {
    }

}
```

9. `MessageServiceTests.java`:

```java
Copypackage com.example.rabbitmqdemo.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageServiceTests {

    @Autowired
    private MessageService messageService;

    @Test
    public void testSendAndReceiveMessage() {
        String message = "Hello, RabbitMQ!";
        messageService.sendMessage(message);
        String receivedMessage = messageService.receiveMessage();
        assertEquals(message, receivedMessage);
    }
}
```

这个项目现在可以连接到RabbitMQ的单机和集群环境。要切换到集群模式,只需要在运行时指定使用cluster配置文件:

```
Copyjava -jar your-application.jar --spring.profiles.active=cluster
```

这个项目包含了基本的消息发送和接收功能,以及相应的测试。你可以通过REST API来发送和接收消息,也可以运行测试来验证功能。

注意:在运行测试之前,请确保RabbitMQ服务器已经启动并且可以连接。

这个项目为您提供了一个良好的起点,您可以根据需要进一步扩展功能,例如添加更多的队列、交换机,或者实现更复杂的消息路由逻辑。

