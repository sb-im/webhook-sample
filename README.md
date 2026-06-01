# Webhook 示例服务

## 功能

- 接收设备事件回调，并把关键字段打印到日志。
- 接收设备和子设备 OSD 数据回调。
- 提供 MinIO/S3 上传配置，返回临时 STS 凭证和对象前缀。
- 提供航线文件下载地址，返回 `sample.kmz` 的 MD5 指纹和预签名 URL。
- 日志写入 `logs/webhook-sample.log`，并按日期和大小滚动。

## 部署环境

- JDK 25
- GraalVM Community 25.0.2（用于原生编译）
- Gradle Wrapper（使用仓库内置 `./gradlew`）
- MinIO 或兼容 S3 的对象存储服务

## 启动

```bash
./gradlew bootRun
```

服务默认监听端口：

```text
8181
```

## 原生编译

```bash
./gradlew clean nativeCompile --refresh-dependencies --no-daemon
./build/native/nativeCompile/webhook-sample
```

## 通用格式

大部分 `POST` 接口使用统一请求格式：

```json
{
  "timestamp": 1710000000000,
  "retry": 0,
  "message": "previous failure reason",
  "device_sn": "xxx",
  "data": {}
}
```

字段说明：

| 字段 | 类型 | 是否必填 | 说明 |
| --- | --- | --- | --- |
| `timestamp` | number | 是 | 服务端发送 webhook 时的毫秒时间戳 |
| `retry` | number | 是 | 当前重试次数，首次请求为 `0` |
| `message` | string | 否 | 仅重试时可能出现，表示上一次 webhook 请求失败原因 |
| `device_sn` | string | 是 | 触发该 webhook 的设备 SN |
| `data` | object | 是 | 业务数据，不同 webhook path 结构不同 |

重试规则：

最多发送 3 次，`retry` 取值为 `0`、`1`、`2`，即首次请求 1 次加重试 2 次。如果第一次失败，第二次请求会带上一次失败原因：

```json
{
  "timestamp": 1710000000000,
  "retry": 1,
  "message": "previous webhook request failed",
  "device_sn": "xxx",
  "data": {}
}
```

成功响应：

```json
{
  "code": 0,
  "data": null,
  "message": "success"
}
```

## 事件回调接口

| 方法 | 路径 | 功能 |
| --- | --- | --- |
| POST | `/webhook/events/cover-open` | 舱盖打开进度回调，打印 `event_id`、`result`、`percent`、`result_message` |
| POST | `/webhook/events/cover-close` | 舱盖关闭进度回调，打印 `event_id`、`result`、`percent`、`result_message` |
| POST | `/webhook/events/charge-open` | 充电打开进度回调，打印 `event_id`、`result`、`percent`、`result_message` |
| POST | `/webhook/events/charge-close` | 充电关闭进度回调，打印 `event_id`、`result`、`percent`、`result_message` |
| POST | `/webhook/events/drone-open` | 飞行器舱门打开进度回调，打印 `event_id`、`result`、`percent`、`result_message` |
| POST | `/webhook/events/drone-close` | 飞行器舱门关闭进度回调，打印 `event_id`、`result`、`percent`、`result_message` |
| POST | `/webhook/events/device/hms` | 设备 HMS 告警回调，打印 `device_sn` 和 `data.list[].message` |
| POST | `/webhook/events/sub-device/hms` | 子设备 HMS 告警回调，打印 `device_sn` 和 `data.list[].message` |
| POST | `/webhook/events/file-upload-callback` | 文件上传完成回调，打印 `event_id` 和 `file.name` |
| POST | `/webhook/events/flighttask-progress` | 航线任务进度回调，打印 `event_id` 和 `percent`；当 `result != 0` 时额外打印 `message` |

## 请求类接口

| 方法 | 路径 | 功能 |
| --- | --- | --- |
| POST | `/webhook/requests/storage-config-get` | 返回对象存储上传配置，包含 `bucket`、`endpoint`、`region`、`object_key_prefix` 和临时 `credentials` |
| GET | `/webhook/requests/flighttask-resource-get?event-id=xxx` | 返回航线文件 `sample.kmz` 的 `fingerprint` 和预签名下载 `url` |

## OSD 回调接口

| 方法 | 路径 | 功能 |
| --- | --- | --- |
| POST | `/webhook/osd/device` | 接收设备 OSD 数据，返回成功响应 |
| POST | `/webhook/osd/sub-device` | 接收子设备 OSD 数据，返回成功响应 |
