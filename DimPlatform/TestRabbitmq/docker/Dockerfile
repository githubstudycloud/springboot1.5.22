FROM rabbitmq:3.7-management

# 启用RabbitMQ插件，包括tracing插件
RUN rabbitmq-plugins enable --offline rabbitmq_mqtt rabbitmq_federation rabbitmq_federation_management rabbitmq_tracing

# 复制自定义配置文件
COPY rabbitmq.conf /etc/rabbitmq/rabbitmq.conf

# 设置日志输出到标准输出，方便Docker收集日志
ENV RABBITMQ_LOGS=- RABBITMQ_SASL_LOGS=-

# 暴露端口
EXPOSE 5672 15672 1883 61613

# 设置健康检查
HEALTHCHECK --interval=30s --timeout=30s --retries=3 CMD rabbitmqctl node_health_check