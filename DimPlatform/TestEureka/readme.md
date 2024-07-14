按以下步骤重新部署你的服务：

1. 停止并删除现有的容器和网络：

```bash
docker-compose down -v
```

2. 重新构建并启动服务：

```bash
docker-compose up --build
```

3. 等待几分钟，让服务完全启动并相互发现。

4. 检查日志以查看是否有任何错误：

```bash
docker-compose logs
```