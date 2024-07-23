CREATE TABLE job_executions
(
    job_id         VARCHAR(255) PRIMARY KEY,
    execution_time TIMESTAMP
);


CREATE TABLE refresh_configs
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    data_type        VARCHAR(255) NOT NULL,
    refresh_url      VARCHAR(255) NOT NULL,
    status_url       VARCHAR(255) NOT NULL,
    post_params      TEXT,
    max_refresh_time INT          NOT NULL
);

-- 创建刷新配置表
CREATE TABLE refresh_configs (
                                 id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                 data_type VARCHAR(255) NOT NULL,
                                 refresh_url VARCHAR(255) NOT NULL,
                                 status_url VARCHAR(255) NOT NULL,
                                 post_params TEXT,
                                 max_refresh_time INT NOT NULL
);

-- 插入一些示例数据
INSERT INTO refresh_configs (data_type, refresh_url, status_url, post_params, max_refresh_time)
VALUES
    ('product', 'http://api.example.com/refresh/product', 'http://api.example.com/status/product', '{"key": "value"}', 300),
    ('user', 'http://api.example.com/refresh/user', 'http://api.example.com/status/user', '{"type": "full"}', 600),
    ('order', 'http://api.example.com/refresh/order', 'http://api.example.com/status/order', '{"last_days": 30}', 900);

-- 存储有关调度器实例的信息
CREATE TABLE QRTZ_SCHEDULER_STATE
(
    SCHED_NAME        VARCHAR(120) NOT NULL,
    INSTANCE_NAME     VARCHAR(200) NOT NULL,
    LAST_CHECKIN_TIME BIGINT(13) NOT NULL,
    CHECKIN_INTERVAL  BIGINT(13) NOT NULL,
    PRIMARY KEY (SCHED_NAME, INSTANCE_NAME)
);

-- 存储已触发的 Trigger 的信息和状态
CREATE TABLE QRTZ_FIRED_TRIGGERS
(
    SCHED_NAME        VARCHAR(120) NOT NULL,
    ENTRY_ID          VARCHAR(95)  NOT NULL,
    TRIGGER_NAME      VARCHAR(200) NOT NULL,
    TRIGGER_GROUP     VARCHAR(200) NOT NULL,
    INSTANCE_NAME     VARCHAR(200) NOT NULL,
    FIRED_TIME        BIGINT(13) NOT NULL,
    SCHED_TIME        BIGINT(13) NOT NULL,
    PRIORITY          INTEGER      NOT NULL,
    STATE             VARCHAR(16)  NOT NULL,
    JOB_NAME          VARCHAR(200) NULL,
    JOB_GROUP         VARCHAR(200) NULL,
    IS_NONCONCURRENT  VARCHAR(1) NULL,
    REQUESTS_RECOVERY VARCHAR(1) NULL,
    PRIMARY KEY (SCHED_NAME, ENTRY_ID)
);

-- 存储 JobDetail 信息
CREATE TABLE QRTZ_JOB_DETAILS
(
    SCHED_NAME        VARCHAR(120) NOT NULL,
    JOB_NAME          VARCHAR(200) NOT NULL,
    JOB_GROUP         VARCHAR(200) NOT NULL,
    DESCRIPTION       VARCHAR(250) NULL,
    JOB_CLASS_NAME    VARCHAR(250) NOT NULL,
    IS_DURABLE        VARCHAR(1)   NOT NULL,
    IS_NONCONCURRENT  VARCHAR(1)   NOT NULL,
    IS_UPDATE_DATA    VARCHAR(1)   NOT NULL,
    REQUESTS_RECOVERY VARCHAR(1)   NOT NULL,
    JOB_DATA          BLOB NULL,
    PRIMARY KEY (SCHED_NAME, JOB_NAME, JOB_GROUP)
);

-- 存储 Trigger 信息
CREATE TABLE QRTZ_TRIGGERS
(
    SCHED_NAME     VARCHAR(120) NOT NULL,
    TRIGGER_NAME   VARCHAR(200) NOT NULL,
    TRIGGER_GROUP  VARCHAR(200) NOT NULL,
    JOB_NAME       VARCHAR(200) NOT NULL,
    JOB_GROUP      VARCHAR(200) NOT NULL,
    DESCRIPTION    VARCHAR(250) NULL,
    NEXT_FIRE_TIME BIGINT(13) NULL,
    PREV_FIRE_TIME BIGINT(13) NULL,
    PRIORITY       INTEGER NULL,
    TRIGGER_STATE  VARCHAR(16)  NOT NULL,
    TRIGGER_TYPE   VARCHAR(8)   NOT NULL,
    START_TIME     BIGINT(13) NOT NULL,
    END_TIME       BIGINT(13) NULL,
    CALENDAR_NAME  VARCHAR(200) NULL,
    MISFIRE_INSTR  SMALLINT(2) NULL,
    JOB_DATA       BLOB NULL,
    PRIMARY KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    FOREIGN KEY (SCHED_NAME, JOB_NAME, JOB_GROUP)
        REFERENCES QRTZ_JOB_DETAILS (SCHED_NAME, JOB_NAME, JOB_GROUP)
);

-- 存储 Cron Trigger 的信息
CREATE TABLE QRTZ_CRON_TRIGGERS
(
    SCHED_NAME      VARCHAR(120) NOT NULL,
    TRIGGER_NAME    VARCHAR(200) NOT NULL,
    TRIGGER_GROUP   VARCHAR(200) NOT NULL,
    CRON_EXPRESSION VARCHAR(200) NOT NULL,
    TIME_ZONE_ID    VARCHAR(80),
    PRIMARY KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    FOREIGN KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
        REFERENCES QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);

-- 存储简单的 Trigger，包括重复次数、间隔等
CREATE TABLE QRTZ_SIMPLE_TRIGGERS
(
    SCHED_NAME      VARCHAR(120) NOT NULL,
    TRIGGER_NAME    VARCHAR(200) NOT NULL,
    TRIGGER_GROUP   VARCHAR(200) NOT NULL,
    REPEAT_COUNT    BIGINT(7) NOT NULL,
    REPEAT_INTERVAL BIGINT(12) NOT NULL,
    TIMES_TRIGGERED BIGINT(10) NOT NULL,
    PRIMARY KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    FOREIGN KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
        REFERENCES QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);

-- 存储 Trigger 的日历信息
CREATE TABLE QRTZ_CALENDARS
(
    SCHED_NAME    VARCHAR(120) NOT NULL,
    CALENDAR_NAME VARCHAR(200) NOT NULL,
    CALENDAR      BLOB         NOT NULL,
    PRIMARY KEY (SCHED_NAME, CALENDAR_NAME)
);

-- 存储已暂停的 Trigger 组的信息
CREATE TABLE QRTZ_PAUSED_TRIGGER_GRPS
(
    SCHED_NAME    VARCHAR(120) NOT NULL,
    TRIGGER_GROUP VARCHAR(200) NOT NULL,
    PRIMARY KEY (SCHED_NAME, TRIGGER_GROUP)
);

-- 存储 Blob 类型的 Trigger
CREATE TABLE QRTZ_BLOB_TRIGGERS
(
    SCHED_NAME    VARCHAR(120) NOT NULL,
    TRIGGER_NAME  VARCHAR(200) NOT NULL,
    TRIGGER_GROUP VARCHAR(200) NOT NULL,
    BLOB_DATA     BLOB NULL,
    PRIMARY KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    FOREIGN KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
        REFERENCES QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);

-- 存储锁信息
CREATE TABLE QRTZ_LOCKS
(
    SCHED_NAME VARCHAR(120) NOT NULL,
    LOCK_NAME  VARCHAR(40)  NOT NULL,
    PRIMARY KEY (SCHED_NAME, LOCK_NAME)
);