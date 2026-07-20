# OSD 数据字段说明

## 设备 OSD

`OsdController#deviceOsd` 通过 `POST /webhook/osd/device` 接收设备 OSD 数据。本文说明统一请求体中 `data` 对象的字段含义。

### 顶层字段

| 字段 | 名称 | 类型 | 约束/枚举 | 说明 |
| --- | --- | --- | --- | --- |
| `home_position_is_valid` | home 点有效性 | enum_int | `0`：航向和经纬度坐标都无效；`1`：航向和经纬度坐标都有效；`2`：航向有效、经纬度无效；`3`：经纬度有效、航向无效 | 表示机场经纬度、航向属性是否分别有效。机场未标定时，经纬度坐标无效，此时上报的 `latitude`、`longitude` 波动较大，不建议使用；航向不需要标定也可能有效。 |
| `heading` | 机场朝向角 | double | -180～180 ° | 机场朝向角。 |
| `rtcm_info` | 机场 RTK 标定源 | struct | 见“RTK 标定源” | 机场 RTK 标定信息。 |
| `wireless_link_topo` | 图传连接拓扑 | struct | 见“图传连接拓扑” | 图传设备的对频及连接拓扑。 |
| `air_conditioner` | 机场空调工作状态信息 | struct | 见“空调状态” | 机场空调当前工作状态。 |
| `air_transfer_enable` | 空中回传 | bool | `false`：关闭；`true`：开启 | 用户在指令飞行过程中拍摄的照片快速回传至云端；Dock 3 最新固件也支持快速回传航线任务中拍摄的照片。 |
| `silent_mode` | 机场静音模式 | enum_int | `0`：非静音模式；`1`：静音模式 | 开启后风扇转速降低、空调制冷性能下降，炎热天气下作业间隔变长；蜂鸣器关闭；机场待机状态的白色指示灯关闭，其他运行状态指示灯正常。 |
| `user_experience_improvement` | 用户体验改善计划 | enum_int | `0`：初始状态；`1`：拒绝加入；`2`：同意加入 | 用户体验改善计划的选择状态。 |
| `dongle_infos` | 4G Dongle 信息 | array&lt;struct&gt; | 见“4G Dongle 信息” | 机场关联的 4G Dongle 列表。 |
| `drone_battery_maintenance_info` | 飞行器电池保养信息 | struct | 见“飞行器电池保养信息” | 飞行器电池的保养、加热及详细状态。 |
| `maintain_status` | 保养信息 | struct | 见“机场保养信息” | 机场的历史保养信息。 |
| `position_state` | 搜星状态 | struct | 见“搜星状态” | 机场定位标定、收敛及搜星信息。 |
| `emergency_stop_state` | 紧急停止按钮状态 | enum_int | `0`：关闭；`1`：开启 | 紧急停止按钮当前状态。 |
| `drone_charge_state` | 飞行器充电状态 | struct | 见“飞行器充电状态” | 飞行器当前电量及充电状态。 |
| `backup_battery` | 机场备用电池信息 | struct | 见“机场备用电池信息” | 机场备用电池状态。 |
| `alarm_state` | 机场声光报警状态 | enum_int | `0`：关闭；`1`：开启 | 机场声光报警当前状态。 |
| `battery_store_mode` | 电池运行模式 | enum_int | `1`：计划模式；`2`：待命模式 | 计划模式适合规律作业，无任务时电量保持在 55%～60%，寿命较长；待命模式适合应急作业，无任务时电量保持在 90%～95%，寿命较短。 |
| `activation_time` | 机场激活时间 | int | 秒（Unix 时间戳） | 机场激活时间。 |
| `height` | 椭球高度 | double | m | 机场所在位置的椭球高度。 |
| `alternate_land_point` | 备降点 | struct | 见“备降点” | 机场配置的备降点信息。 |
| `compatible_status` | 固件一致性 | enum_int | `0`：不需要一致性升级；`1`：需要一致性升级 | 一致性升级表示飞行器某些模块的固件版本与系统匹配版本不一致，需要升级。例如更换了未升级的电池。普通升级则是将飞行器所有模块升级至指定固件版本。 |
| `acc_time` | 机场累计运行时长 | int | s | 机场累计运行时间。 |
| `first_power_on` | 首次上电时间 | int | ms | 机场首次上电时间。 |
| `storage` | 存储容量 | struct | 见“存储容量” | 机场存储容量信息。 |
| `working_current` | 工作电流 | float | mA | 机场当前工作电流。 |
| `working_voltage` | 工作电压 | int | mV | 机场当前工作电压。 |
| `humidity` | 舱内湿度 | float | 0～100 %RH，步长 0.1 | 机场舱内相对湿度。 |
| `temperature` | 舱内温度 | float | °C | 机场舱内温度。 |
| `environment_temperature` | 环境温度 | float | °C | 机场外部环境温度。 |
| `wind_speed` | 风速 | float | m/s | 机场环境风速。 |
| `rainfall` | 降雨量 | enum_int | `0`：无雨；`1`：小雨；`2`：中雨；`3`：大雨 | 机场检测到的降雨等级。 |
| `live_capacity` | 网关设备直播能力 | struct | 见“直播能力” | 网关可用的视频源及并发直播能力。 |
| `live_status` | 网关当前整体直播状态推送 | array&lt;struct&gt; | 见“直播状态” | 网关当前各路码流的直播状态。 |
| `wireless_link` | 图传链路 | struct | 见“图传链路” | SDR、4G 图传链路及信号质量。 |
| `media_file_detail` | 媒体文件上传细节 | struct | 见“媒体文件上传” | 媒体文件上传状态。 |
| `job_number` | 机场累计作业次数 | int | 次 | 机场累计完成的作业次数。 |
| `drone_in_dock` | 飞行器是否在舱 | enum_int | `0`：舱外；`1`：舱内 | 飞行器当前是否位于机场舱内。 |
| `network_state` | 网络状态 | struct | 见“网络状态” | 机场当前网络连接状态。 |
| `supplement_light_state` | 补光灯状态 | enum_int | `0`：关闭；`1`：打开 | 机场补光灯当前状态。 |
| `cover_state` | 舱盖状态 | enum_int | `0`：关闭；`1`：打开；`2`：半开；`3`：舱盖状态异常 | 机场舱盖当前状态。 |
| `sub_device` | 子设备状态 | struct | 见“子设备状态” | 机场停机坪上的飞行器状态。 |
| `flighttask_step_code` | 机场任务状态 | enum_int | `0`：作业准备中；`1`：飞行作业中；`2`：作业后状态恢复；`3`：自定义飞行区更新中；`4`：地形障碍物更新中；`5`：任务空闲；`255`：飞行器异常；`256`：未知状态 | 机场当前任务执行阶段。 |
| `mode_code` | 机场状态 | enum_int | `0`：空闲中；`1`：现场调试；`2`：远程调试；`3`：固件升级中；`4`：作业中；`5`：待标定 | 机场当前工作状态。 |
| `firmware_upgrade_status` | 固件升级状态 | enum_int | `0`：未升级；`1`：升级中 | 机场固件是否正在升级。 |
| `firmware_version` | 固件版本 | text | 最大长度 64 | 机场当前固件版本。 |
| `latitude` | 纬度 | double | -90～90，步长 0.01 | 网关设备的纬度。 |
| `longitude` | 经度 | double | -180～180，步长 0.01 | 网关设备的经度。 |
| `drc_state` | DRC 链路状态 | enum_int | `0`：未连接；`1`：连接中；`2`：已连接 | DRC 链路当前状态。 |
| `self_converge_coordinate` | 自收敛坐标 | struct | 见“自收敛坐标” | 机场通过自收敛标定得到的坐标。 |

### RTK 标定源

| 字段路径 | 名称 | 类型 | 约束/枚举 | 说明 |
| --- | --- | --- | --- | --- |
| `rtcm_info.mount_point` | 网络 RTK 挂载点信息 | text | — | 网络 RTK 挂载点。 |
| `rtcm_info.port` | 网络端口信息 | text | — | 网络 RTK 端口。 |
| `rtcm_info.host` | 网络 host 信息 | text | — | 网络 RTK 主机地址。 |
| `rtcm_info.rtcm_device_type` | 设备类型 | enum_int | `1`：机场 | RTCM 数据对应的设备类型。 |
| `rtcm_info.source_type` | 标定类型 | enum_int | `0`：未标定；`1`：自收敛标定；`2`：手动标定；`3`：网络 RTK 标定 | 机场采用的标定类型。 |

### 图传连接拓扑

| 字段路径 | 名称 | 类型 | 约束/枚举 | 说明 |
| --- | --- | --- | --- | --- |
| `wireless_link_topo.secret_code` | 加密编码 | array&lt;int&gt; | 固定 28 项 | 图传连接的加密编码。 |
| `wireless_link_topo.center_node` | 飞行器对频信息 | struct | — | 图传拓扑中心节点。 |
| `wireless_link_topo.center_node.sdr_id` | 扰码信息 | int | — | 飞行器的 SDR 扰码。 |
| `wireless_link_topo.center_node.sn` | 设备 SN | text | — | 飞行器设备序列号。 |
| `wireless_link_topo.leaf_nodes` | 机场或遥控器对频信息 | array&lt;struct&gt; | 数量不定 | 图传拓扑叶子节点。 |
| `wireless_link_topo.leaf_nodes[].sdr_id` | 扰码信息 | int | — | 机场或遥控器的 SDR 扰码。 |
| `wireless_link_topo.leaf_nodes[].sn` | 设备 SN | text | — | 机场或遥控器的设备序列号。 |
| `wireless_link_topo.leaf_nodes[].control_source_index` | 控制源序号 | int | 1～2，步长 1 | 控制源序号。 |

### 空调状态

| 字段路径 | 名称 | 类型 | 约束/枚举 | 说明 |
| --- | --- | --- | --- | --- |
| `air_conditioner.air_conditioner_state` | 机场空调状态 | enum_int | `0`：空闲模式；`1`：制冷模式；`2`：制热模式；`3`：除湿模式；`4`：制冷退出模式；`5`：制热退出模式；`6`：除湿退出模式；`7`：制冷准备模式；`8`：制热准备模式；`9`：除湿准备模式；`10`：风冷准备中；`11`：风冷中；`12`：风冷退出中；`13`：除雾准备中；`14`：除雾中；`15`：除雾退出中 | 空调仅存在一种工作模式。 |
| `air_conditioner.switch_time` | 剩余等待可切换时间 | int | s | 进行某个操作后，距离可切换至下一状态的剩余时间。空调状态按准备模式、工作模式、退出模式、空闲模式依次切换。 |

### 4G Dongle 信息

| 字段路径 | 名称 | 类型 | 约束/枚举 | 说明 |
| --- | --- | --- | --- | --- |
| `dongle_infos[].imei` | Dongle IMEI | text | — | Dongle 的唯一识别标志。 |
| `dongle_infos[].dongle_type` | Dongle 类型 | enum_int | `6`：旧 Dongle；`10`：支持 eSIM 的新 Dongle | Dongle 硬件类型。 |
| `dongle_infos[].eid` | Dongle EID | text | — | eSIM 的唯一识别标志，用于公众号查询套餐、购买服务。 |
| `dongle_infos[].esim_activate_state` | eSIM 激活状态 | enum_int | `0`：未知；`1`：未激活；`2`：已激活 | eSIM 需要激活后才能使用。 |
| `dongle_infos[].sim_card_state` | SIM 卡状态 | enum_int | `0`：未插入；`1`：已插入 | Dongle 中实体 SIM 卡的插入状态。 |
| `dongle_infos[].sim_slot` | SIM 卡槽使能状态 | enum_int | `0`：未知；`1`：实体 SIM 卡；`2`：eSIM | Dongle 当前使用的 SIM 卡槽。 |
| `dongle_infos[].esim_infos` | eSIM 信息 | array&lt;struct&gt; | 数量不定 | Dongle 中的 eSIM 列表。 |
| `dongle_infos[].esim_infos[].telecom_operator` | 支持的运营商 | enum_int | `0`：未知；`1`：移动；`2`：联通；`3`：电信 | eSIM 支持的运营商。 |
| `dongle_infos[].esim_infos[].enabled` | eSIM 使能状态 | bool | `false`：未使用；`true`：使用中 | 同一时刻只能有一个 eSIM 处于使能状态。 |
| `dongle_infos[].esim_infos[].iccid` | SIM ICCID | text | — | SIM 卡唯一识别标志，可用于套餐购买。 |
| `dongle_infos[].sim_info` | SIM 卡信息 | struct | — | 可插入 Dongle 的实体 SIM 卡信息。 |
| `dongle_infos[].sim_info.telecom_operator` | 支持的运营商 | enum_int | `0`：未知；`1`：移动；`2`：联通；`3`：电信 | 实体 SIM 卡支持的运营商。 |
| `dongle_infos[].sim_info.sim_type` | SIM 卡类型 | enum_int | `0`：未知；`1`：其他普通 SIM 卡；`2`：三网卡 | 实体 SIM 卡类型。 |
| `dongle_infos[].sim_info.iccid` | SIM ICCID | text | — | 实体 SIM 卡的唯一识别标志，可用于套餐购买。 |

### 飞行器电池保养信息

| 字段路径 | 名称 | 类型 | 约束/枚举 | 说明 |
| --- | --- | --- | --- | --- |
| `drone_battery_maintenance_info.maintenance_state` | 保养状态 | enum_int | `0`：无需保养；`1`：待保养；`2`：正在保养 | 飞行器电池当前保养状态。 |
| `drone_battery_maintenance_info.maintenance_time_left` | 电池保养剩余时间 | int | h | 电池保养剩余时间，向下取整。 |
| `drone_battery_maintenance_info.heat_state` | 电池加热保温状态 | enum_int | `0`：未开启加热或保温；`1`：加热中；`2`：保温中 | 飞行器在舱内关机时，由该字段上报机场连接飞行器的电池加热保温信息。 |
| `drone_battery_maintenance_info.batteries` | 电池详细信息 | array&lt;struct&gt; | 数量不定 | 飞行器在舱内关机时上报，基本数据与飞行器物模型中的电池信息一致。 |
| `drone_battery_maintenance_info.batteries[].capacity_percent` | 电池剩余电量 | int | 正常范围 0～100；异常值 32767 | 电池剩余电量百分比。设备端无法获取数据时上报 `32767`。 |
| `drone_battery_maintenance_info.batteries[].index` | 电池序号 | enum_int | `0`：左电池；`1`：右电池 | 电池安装位置。 |
| `drone_battery_maintenance_info.batteries[].voltage` | 电压 | int | 正常范围 0～28000 mV；异常值 32767 | 电池电压。设备端无法获取数据时上报 `32767`。 |
| `drone_battery_maintenance_info.batteries[].temperature` | 温度 | float | 正常范围 -40～150 °C；异常值 32767 | 电池温度，保留一位小数。设备端无法获取数据时上报 `32767`。 |

### 机场保养信息

| 字段路径 | 名称 | 类型 | 约束/枚举 | 说明 |
| --- | --- | --- | --- | --- |
| `maintain_status.maintain_status_array` | 保养信息数组 | array&lt;struct&gt; | 数量不定 | 机场保养记录列表。 |
| `maintain_status.maintain_status_array[].state` | 保养状态 | enum_int | `0`：无保养；`1`：有保养 | 当前是否存在保养信息。 |
| `maintain_status.maintain_status_array[].last_maintain_type` | 上一次保养类型 | enum_int | `0`：无保养；`17`：机场常规保养；`18`：机场深度保养 | 上一次执行的保养类型。 |
| `maintain_status.maintain_status_array[].last_maintain_time` | 上一次保养时间 | date | s | 上一次保养时间。 |
| `maintain_status.maintain_status_array[].last_maintain_work_sorties` | 上一次保养时作业架次 | int | 0～2147483647，步长 1 | 上一次保养时的累计作业架次。 |

### 搜星状态

| 字段路径 | 名称 | 类型 | 约束/枚举 | 说明 |
| --- | --- | --- | --- | --- |
| `position_state.is_calibration` | 是否标定 | enum_int | `0`：未标定；`1`：已标定 | 机场位置是否已标定。 |
| `position_state.is_fixed` | 是否收敛 | enum_int | `0`：未开始；`1`：收敛中；`2`：收敛成功；`3`：收敛失败 | RTK 收敛状态。 |
| `position_state.quality` | 搜星档位 | enum_int | `1`：1 档；`2`：2 档；`3`：3 档；`4`：4 档；`5`：5 档；`10`：RTK fixed | 当前搜星质量档位。 |
| `position_state.gps_number` | GPS 搜星数量 | int | — | 当前搜索到的 GPS 卫星数。 |
| `position_state.rtk_number` | RTK 搜星数量 | int | — | 当前搜索到的 RTK 卫星数。 |

### 飞行器充电状态

| 字段路径 | 名称 | 类型 | 约束/枚举 | 说明 |
| --- | --- | --- | --- | --- |
| `drone_charge_state.capacity_percent` | 电量百分比 | int | 0～100 | 飞行器电池电量百分比。 |
| `drone_charge_state.state` | 充电状态 | enum_int | `0`：空闲；`1`：充电中 | 飞行器当前充电状态。 |

### 机场备用电池信息

| 字段路径 | 名称 | 类型 | 约束/枚举 | 说明 |
| --- | --- | --- | --- | --- |
| `backup_battery.switch` | 备用电池开关 | enum_int | `0`：关闭；`1`：开启 | 备用电池开关状态。 |
| `backup_battery.voltage` | 备用电池电压 | int | 0～30000 mV，步长 1 | 备用电池关闭时电压为 `0`。 |
| `backup_battery.temperature` | 备用电池温度 | float | °C，步长 0.1 | 备用电池温度，保留一位小数。 |

### 备降点

| 字段路径 | 名称 | 类型 | 约束/枚举 | 说明 |
| --- | --- | --- | --- | --- |
| `alternate_land_point.longitude` | 经度 | float | — | 备降点经度。 |
| `alternate_land_point.latitude` | 纬度 | float | — | 备降点纬度。 |
| `alternate_land_point.safe_land_height` | 安全高度（备降转移高） | float | — | 飞行器前往备降点时使用的安全高度。 |
| `alternate_land_point.is_configured` | 是否设置备降点 | enum_int | `0`：未设置；`1`：已设置 | 备降点是否已配置。 |
| `alternate_land_point.height` | 椭球高度 | float | — | 备降点椭球高度。 |

### 存储容量

| 字段路径 | 名称 | 类型 | 约束/枚举 | 说明 |
| --- | --- | --- | --- | --- |
| `storage.total` | 总容量 | int | KB | 存储总容量。 |
| `storage.used` | 已使用容量 | int | KB | 已使用的存储容量。 |

### 直播能力

| 字段路径 | 名称 | 类型 | 约束/枚举 | 说明 |
| --- | --- | --- | --- | --- |
| `live_capacity.available_video_number` | 可选择推流的码流数量 | int | — | 网关可选择推流的码流总数。 |
| `live_capacity.coexist_video_number_max` | 可同时推流的最大码流数量 | int | — | 网关允许同时推流的最大码流数。 |
| `live_capacity.device_list` | 可选择的视频设备源 | array&lt;struct&gt; | 数量不定 | 设备层级的视频源列表，例如飞行器。 |
| `live_capacity.device_list[].sn` | 视频源设备序列号 | text | — | 飞行器等视频源设备的 SN。 |
| `live_capacity.device_list[].available_video_number` | 设备可选择推流的码流数 | int | — | 该 SN 设备可选择推流的码流数量。 |
| `live_capacity.device_list[].coexist_video_number_max` | 设备可同时推流的码流数 | int | — | 该 SN 设备允许同时推流的最大码流数量。 |
| `live_capacity.device_list[].camera_list` | 相机列表 | array&lt;struct&gt; | 数量不定 | 该设备上的相机列表。 |
| `live_capacity.device_list[].camera_list[].camera_index` | 相机索引 | text | `{type-subtype-gimbalindex}` | 相机索引。 |
| `live_capacity.device_list[].camera_list[].available_video_number` | 相机可选择推流的码流数 | int | — | 该相机可选择推流的码流数量。 |
| `live_capacity.device_list[].camera_list[].coexist_video_number_max` | 相机可同时推流的码流数 | int | — | 该相机允许同时推流的最大码流数量。 |
| `live_capacity.device_list[].camera_list[].video_list` | 可选择的码流列表 | array&lt;struct&gt; | 数量不定 | 该相机可选择的视频码流列表。 |
| `live_capacity.device_list[].camera_list[].video_list[].video_index` | 码流索引 | text | — | 该相机级别的视频源码流索引。 |
| `live_capacity.device_list[].camera_list[].video_list[].video_type` | 码流类型 | text | — | 该相机级别的视频源码流类型。 |
| `live_capacity.device_list[].camera_list[].video_list[].switchable_video_types` | 支持切换的镜头类型 | array&lt;text&gt; | 数量不定 | 该视频流支持切换的视频镜头类型。 |

### 直播状态

| 字段路径 | 名称 | 类型 | 约束/枚举 | 说明 |
| --- | --- | --- | --- | --- |
| `live_status[].video_id` | 直播码流标识符 | text | `{sn}/{camera_index}/{video_index}` | 某一路推流视频的唯一标识。`camera_index` 使用 `{type-subtype-gimbalindex}` 格式。 |
| `live_status[].video_type` | 视频类型 | text | 最大长度 24 | 视频镜头类型，例如 `normal`、`wide`、`zoom`、`infrared`。 |
| `live_status[].video_quality` | 直播码流质量 | enum_int | `0`：自适应；`1`：流畅；`2`：标清；`3`：高清；`4`：超清 | 当前直播码流质量。 |
| `live_status[].status` | 直播状态 | enum_int | `0`：未直播；`1`：在直播 | 当前码流是否正在直播。 |
| `live_status[].error_status` | 错误码 | int | 长度 6 | 直播错误码。 |

### 图传链路

| 字段路径 | 名称 | 类型 | 约束/枚举 | 说明 |
| --- | --- | --- | --- | --- |
| `wireless_link.dongle_number` | 飞行器上 Dongle 数量 | int | — | 飞行器上安装的 Dongle 数量。 |
| `wireless_link.4g_link_state` | 4G 链路连接状态 | enum_int | `0`：断开；`1`：连接 | 4G 图传链路是否已连接。 |
| `wireless_link.sdr_link_state` | SDR 链路连接状态 | enum_int | `0`：断开；`1`：连接 | SDR 图传链路是否已连接。 |
| `wireless_link.link_workmode` | 机场图传链路模式 | enum_int | `0`：SDR 模式；`1`：4G 融合模式 | 机场当前使用的图传链路模式。 |
| `wireless_link.sdr_quality` | SDR 信号质量 | int | 0～5，步长 1 | SDR 链路信号质量。 |
| `wireless_link.4g_quality` | 总体 4G 信号质量 | int | 0～5，步长 1 | 4G 链路总体信号质量。 |
| `wireless_link.4g_uav_quality` | 天端 4G 信号质量 | int | 0～5，步长 1 | 飞行器端与 4G 服务器之间的信号质量。 |
| `wireless_link.4g_gnd_quality` | 地端 4G 信号质量 | int | 0～5，步长 1 | 地面端（如遥控器、DJI Dock）与 4G 服务器之间的信号质量。 |
| `wireless_link.sdr_freq_band` | SDR 频段 | float | — | SDR 当前使用的频段。 |
| `wireless_link.4g_freq_band` | 4G 频段 | float | — | 4G 当前使用的频段。 |

### 媒体文件上传

| 字段路径 | 名称 | 类型 | 约束/枚举 | 说明 |
| --- | --- | --- | --- | --- |
| `media_file_detail.remain_upload` | 待上传数量 | int | — | 当前仍待上传的媒体文件数量。 |

### 网络状态

| 字段路径 | 名称 | 类型 | 约束/枚举 | 说明 |
| --- | --- | --- | --- | --- |
| `network_state.type` | 网络类型 | enum_int | `1`：4G；`2`：以太网 | 机场当前使用的网络类型。 |
| `network_state.quality` | 网络质量 | enum_int | `0`：无信号；`1`：差；`2`：较差；`3`：一般；`4`：较好；`5`：好 | 当前网络信号质量。 |
| `network_state.rate` | 网络速率 | float | KB/s | 当前网络传输速率。 |

### 子设备状态

| 字段路径 | 名称 | 类型 | 约束/枚举 | 说明 |
| --- | --- | --- | --- | --- |
| `sub_device.device_sn` | 子设备序列号 | text | — | 机场停机坪上子设备的 SN。 |
| `sub_device.device_model_key` | 子设备枚举值 | text | `{domain-type-subtype}` | 子设备型号标识。 |
| `sub_device.device_online_status` | 飞行器开机状态 | enum_int | `0`：关机；`1`：开机 | 机场停机坪上的飞行器开机状态。 |
| `sub_device.device_paired` | 飞行器是否与机场对频 | enum_int | `0`：未对频；`1`：已对频 | 机场停机坪上的飞行器是否已与机场对频。 |

### 自收敛坐标

| 字段路径 | 名称 | 类型 | 约束/枚举 | 说明 |
| --- | --- | --- | --- | --- |
| `self_converge_coordinate.latitude` | 纬度 | double | -90～90，步长 0.01 | 自收敛标定得到的纬度。 |
| `self_converge_coordinate.longitude` | 经度 | double | -180～180，步长 0.01 | 自收敛标定得到的经度。 |
| `self_converge_coordinate.height` | 椭球高度 | double | m | 自收敛标定得到的椭球高度。 |

## 子设备 OSD

`OsdController#subDeviceOsd` 通过 `POST /webhook/osd/sub-device` 接收飞行器 OSD 数据。本文以下内容说明统一请求体中 `data` 对象的字段含义。

### 顶层字段

| 字段 | 名称 | 类型 | 约束/枚举 | 说明 |
| --- | --- | --- | --- | --- |
| `best_link_gateway` | 飞行器图传连接质量最好的网关 SN | text | — | 蛙跳任务场景下，飞行器与两个机场对频后，上报当前连接质量最好的机场 SN。 |
| `wireless_link_topo` | 图传连接拓扑 | struct | 见“子设备图传连接拓扑” | 飞行器当前图传连接拓扑。 |
| `cameras` | 飞行器相机信息 | array&lt;struct&gt; | 见“飞行器相机信息” | 飞行器负载相机列表。 |
| `flysafe_database_version` | 飞行安全数据库版本 | text | 最大长度 64 | 当前飞行安全数据库版本。 |
| `offline_map_enable` | 离线地图开关 | bool | `0`：关闭；`1`：开启 | 关闭离线地图后，不再自动同步离线地图。 |
| `dongle_infos` | 4G Dongle 信息 | array&lt;struct&gt; | 见“子设备 4G Dongle 信息” | 飞行器关联的 4G Dongle 列表。 |
| `current_rth_mode` | 返航高度模式当前值 | enum_int | `0`：智能高度；`1`：设定高度 | 大疆机场当前实际使用的返航高度模式。 |
| `rth_mode` | 返航高度模式设置值 | enum_int | `0`：智能高度；`1`：设定高度 | 智能返航模式下，飞行器自动规划最佳返航高度。大疆机场当前不支持设置返航高度模式，只能选择“设定高度”模式；当环境或光线不满足视觉系统要求时，飞行器使用设定高度直线返航。 |
| `obstacle_avoidance` | 飞行器避障状态 | struct | 见“飞行器避障状态” | 飞行器各方向避障功能状态。 |
| `is_near_area_limit` | 是否接近限飞区 | enum_int | `0`：未达到限飞区；`1`：接近限飞区 | 飞行器是否接近限飞区域。 |
| `is_near_height_limit` | 是否接近设定的限制高度 | enum_int | `0`：未达到设定的限制高度；`1`：接近设定的限制高度 | 飞行器是否接近限高。 |
| `height_limit` | 飞行器限高 | int | 20～1500 m，步长 1 | 飞行器当前限高设置。 |
| `night_lights_state` | 飞行器夜航灯状态 | enum_int | `0`：关闭；`1`：打开 | 飞行器夜航灯当前状态。 |
| `activation_time` | 飞行器激活时间 | int | 秒（Unix 时间戳） | 飞行器激活时间。 |
| `maintain_status` | 保养信息 | struct | 见“飞行器保养信息” | 飞行器历史保养信息。 |
| `total_flight_sorties` | 飞行器累计飞行总架次 | int | 0～2147483647，步长 1 | 飞行器累计飞行架次。 |
| `type_subtype_gimbalindex` | 负载编号 | struct | 见“负载与云台信息” | 与相机字段 `payload_index` 保持一致。 |
| `track_id` | 航迹 ID | text | 最大长度 64 | 当前飞行航迹标识。 |
| `position_state` | 搜星状态 | struct | 见“飞行器搜星状态” | 飞行器定位收敛及搜星信息。 |
| `storage` | 存储容量 | struct | 见“飞行器存储容量” | 容量单位为 KB。 |
| `battery` | 飞行器电池信息 | struct | 见“飞行器电池信息” | 飞行器总电量及各电池详细信息。 |
| `total_flight_distance` | 飞行器累计飞行总里程 | float | m | 飞行器累计飞行距离。 |
| `total_flight_time` | 飞行器累计飞行航时 | float | s | 飞行器累计飞行时间。 |
| `serious_low_battery_warning_threshold` | 严重低电量告警 | int | — | 用户设置的电池严重低电量告警百分比。 |
| `low_battery_warning_threshold` | 低电量告警 | int | — | 用户设置的电池低电量告警百分比。 |
| `control_source` | 当前控制源 | text | — | 可以是设备或浏览器。设备使用 `A`/`B` 表示 A 控、B 控，浏览器使用自行生成的 UUID。 |
| `wind_direction` | 当前风向 | enum_int | `1`：正北；`2`：东北；`3`：东；`4`：东南；`5`：南；`6`：西南；`7`：西；`8`：西北 | 飞行器估算的当前风向。 |
| `wind_speed` | 风速 | float | 0.1 m/s | 根据飞行器姿态估算，存在一定误差，仅供参考，不能作为气象数据使用。 |
| `home_distance` | 距离 Home 点的距离 | float | — | 飞行器当前位置与 Home 点的距离。 |
| `home_latitude` | Home 点纬度 | double | — | Home 点纬度。 |
| `home_longitude` | Home 点经度 | double | — | Home 点经度。 |
| `attitude_head` | 偏航轴角度 | int | — | 偏航轴与真北方向（经线）的夹角；0 到 6 点钟方向为正值，6 到 12 点钟方向为负值。 |
| `attitude_roll` | 横滚轴角度 | float | — | 飞行器横滚轴角度。 |
| `attitude_pitch` | 俯仰轴角度 | float | — | 飞行器俯仰轴角度。 |
| `elevation` | 相对起飞点高度 | float | — | 飞行器相对起飞点的高度。 |
| `height` | 绝对高度 | double | — | 相对地球椭球面的高度，计算方式为相对起飞点高度加起飞点椭球高。 |
| `latitude` | 当前所在位置纬度 | double | -1.4E-45～3.4028235E38，步长 0.1 | 飞行器当前位置纬度。 |
| `longitude` | 当前所在位置经度 | double | -1.4E-45～3.4028235E38，步长 0.1 | 飞行器当前位置经度。 |
| `vertical_speed` | 垂直速度 | float | m/s | 飞行器垂直方向速度。 |
| `horizontal_speed` | 水平速度 | float | — | 飞行器水平方向速度。 |
| `firmware_upgrade_status` | 固件升级状态 | enum_int | `0`：未升级；`1`：升级中 | 飞行器固件是否正在升级。 |
| `compatible_status` | 固件一致性 | enum_int | `0`：不需要一致性升级；`1`：需要一致性升级 | 一致性升级表示飞行器某些模块的固件版本与系统匹配版本不一致，需要升级，例如更换了未升级的电池。普通升级则是将飞行器所有模块升级至指定固件版本。 |
| `firmware_version` | 固件版本 | text | 最大长度 64 | 飞行器当前固件版本。 |
| `gear` | 档位 | enum_int | `0`：A；`1`：P；`2`：NAV；`3`：FPV；`4`：FARM；`5`：S；`6`：F；`7`：M；`8`：G；`9`：T | 飞行器当前档位。 |
| `mode_code_reason` | 飞行器进入当前状态的原因 | enum_int | `0`：无意义；`1`：电池电量不足（返航、降落）；`2`：电池电压不足（返航、降落）；`3`：电压严重过低（返航、降落）；`4`：遥控器按键请求（起飞、返航、降落）；`5`：App 请求（起飞、返航、降落）；`6`：遥控信号丢失（返航、降落、悬停）；`7`：导航、SDK 等外部设备触发（起飞、返航、降落）；`8`：进入机场限飞区（降落）；`9`：已触发返航但距离 Home 点太近（降落）；`10`：已触发返航但距离 Home 点太远（降落）；`11`：执行航点任务时请求（起飞）；`12`：返航阶段到达 Home 点上方后请求（降落）；`13`：距地面 0.7 m 时继续下降（二段降落限低，降落）；`14`：App、SDK 等设备强制突破限低保护（降落）；`15`：周围有航班经过（返航、降落）；`16`：高度控制失败（返航、降落）；`17`：智能低电量返航后进入（降落）；`18`：AP 控制飞行模式（手动飞行）；`19`：硬件异常（返航、降落）；`20`：防触地保护结束（降落）；`21`：返航取消（悬停）；`22`：返航时遇到障碍物（降落）；`23`：机场场景下大风触发（返航） | 飞行器进入当前状态的触发原因及对应动作。 |
| `commander_flight_height` | 指点飞行高度 | float | 2～3000 m，步长 0.1 | 相对机场起飞点的高度，即相对高 ALT。 |
| `commander_flight_mode` | 指点飞行模式设置值 | enum_int | `0`：智能高度飞行；`1`：设定高度飞行 | 指点飞行模式设置值。 |
| `commander_mode_lost_action` | 指点飞行失控动作 | enum_int | `0`：继续执行指点飞行任务；`1`：退出指点飞行任务并执行普通失控行为 | 指点飞行时失控后的处理方式。 |
| `camera_watermark_settings` | 相机水印设置 | struct | 见“相机水印设置” | 配置照片和录像文件的水印，目前不支持直播画面水印。 |
| `mode_code` | 飞行器状态 | enum_int | `0`：待机；`1`：起飞准备；`2`：起飞准备完毕；`3`：手动飞行；`4`：自动起飞；`5`：航线飞行；`6`：全景拍照；`7`：智能跟随；`8`：ADS-B 躲避；`9`：自动返航；`10`：自动降落；`11`：强制降落；`12`：三桨叶降落；`13`：升级中；`14`：未连接；`15`：APAS；`16`：虚拟摇杆状态；`17`：指令飞行；`18`：空中 RTK 收敛模式；`19`：机场选址中；`20`：POI 环绕；`21`：进离场航线飞行过程中 | “机场选址中”表示飞行器在空中悬停，以便为机场选址并检查 RTK 信号质量。 |
| `distance_limit_status` | 飞行器限远状态 | struct | 见“飞行器限远状态” | 飞行器限远设置及接近限远状态。 |
| `rth_altitude` | 返航高度 | int | 20～500 m | 飞行器返航高度。 |
| `psdk_ui_resource` | PSDK UI 资源包 | array&lt;struct&gt; | 见“PSDK UI 资源包” | PSDK 负载设备的 UI 资源。 |
| `psdk_widget_values` | PSDK 负载设备属性值 | array&lt;struct&gt; | 见“PSDK 负载设备属性值” | PSDK 负载信息、喊话器状态及控件值。 |
| `remaining_power_for_return_home` | 返航预留电量 | int | 0～100 | 飞行器为返航预留的电量百分比。 |

### 子设备图传连接拓扑

| 字段路径 | 名称 | 类型 | 约束/枚举 | 说明 |
| --- | --- | --- | --- | --- |
| `wireless_link_topo.secret_code` | 加密编码 | array&lt;int&gt; | 固定 28 项 | 图传连接的加密编码。 |
| `wireless_link_topo.center_node` | 飞行器对频信息 | struct | — | 图传拓扑中心节点。 |
| `wireless_link_topo.center_node.sdr_id` | 扰码信息 | int | — | 飞行器的 SDR 扰码。 |
| `wireless_link_topo.center_node.sn` | 设备 SN | text | — | 飞行器设备序列号。 |
| `wireless_link_topo.leaf_nodes` | 当前连接的机场或遥控器对频信息 | array&lt;struct&gt; | 数量不定 | 当前与飞行器连接的图传拓扑叶子节点。 |
| `wireless_link_topo.leaf_nodes[].sdr_id` | 扰码信息 | int | — | 机场或遥控器的 SDR 扰码。 |
| `wireless_link_topo.leaf_nodes[].sn` | 设备 SN | text | — | 机场或遥控器的设备序列号。 |
| `wireless_link_topo.leaf_nodes[].control_source_index` | 控制源序号 | int | 1～2，步长 1 | 控制源序号。 |


### 飞行器相机信息

| 字段路径 | 名称 | 类型 | 约束/枚举 | 说明 |
| --- | --- | --- | --- | --- |
| `cameras[].remain_photo_num` | 剩余拍照张数 | int | — | 剩余可拍摄照片张数。 |
| `cameras[].remain_record_duration` | 剩余录像时间 | int | s | 剩余可录像时间。 |
| `cameras[].record_time` | 视频录制时长 | int | s | 当前视频录制时长。 |
| `cameras[].payload_index` | 负载编号 | text | `{type-subtype-gimbalindex}` | 相机枚举值，不是标准的 `device_model_key`；支持的取值可参考对应产品能力。 |
| `cameras[].camera_mode` | 相机模式 | enum_int | `0`：拍照；`1`：录像；`2`：智能低光；`3`：全景拍照 | 相机当前工作模式。 |
| `cameras[].photo_state` | 拍照状态 | enum_int | `0`：空闲；`1`：拍照中 | 相机当前拍照状态。 |
| `cameras[].screen_split_enable` | 分屏是否使能 | bool | `0`：关闭；`1`：开启 | 相机分屏功能是否启用。 |
| `cameras[].recording_state` | 录像状态 | enum_int | `0`：空闲；`1`：录像中 | 相机当前录像状态。 |
| `cameras[].zoom_factor` | 变焦倍数 | float | — | 当前变焦倍数。 |
| `cameras[].ir_zoom_factor` | 红外变焦倍数 | float | — | 当前红外镜头变焦倍数。 |
| `cameras[].liveview_world_region` | 视场角在 LiveView 中的区域 | struct | — | 变焦相机的视场角相对于广角或红外相机视场角在 LiveView 中的区域，坐标原点为镜头左上角。 |
| `cameras[].liveview_world_region.left` | 左上角 x 轴起始点 | float | — | 区域左上角的 x 坐标。 |
| `cameras[].liveview_world_region.top` | 左上角 y 轴起始点 | float | — | 区域左上角的 y 坐标。 |
| `cameras[].liveview_world_region.right` | 右下角 x 轴起始点 | float | — | 区域右下角的 x 坐标。 |
| `cameras[].liveview_world_region.bottom` | 右下角 y 轴起始点 | float | — | 区域右下角的 y 坐标。 |
| `cameras[].photo_storage_settings` | 照片存储设置集合 | array&lt;enum_string&gt; | `current`、`vision`、`ir` | 照片存储镜头类型集合。 |
| `cameras[].video_storage_settings` | 视频存储设置集合 | array&lt;enum_string&gt; | `current`、`vision`、`ir` | 视频存储镜头类型集合。 |
| `cameras[].wide_exposure_mode` | 广角镜头曝光模式 | enum_int | `1`：自动；`2`：快门优先曝光；`3`：光圈优先曝光；`4`：手动曝光 | 当前配置的广角镜头曝光模式。 |
| `cameras[].wide_iso` | 广角镜头感光度 | enum_int | `0`：Auto；`1`：Auto (High Sense)；`2`：50；`3`：100；`4`：200；`5`：400；`6`：800；`7`：1600；`8`：3200；`9`：6400；`10`：12800；`11`：25600；`255`：FIXED | 当前配置的广角镜头感光度。 |
| `cameras[].wide_shutter_speed` | 广角镜头快门速度 | enum_int | 见“相机快门速度枚举” | 当前配置的广角镜头快门速度。 |
| `cameras[].wide_exposure_value` | 广角镜头曝光值 | enum_int | 见“相机曝光值枚举” | 当前配置的广角镜头曝光值。 |
| `cameras[].zoom_exposure_mode` | 变焦镜头曝光模式 | enum_int | `1`：自动；`2`：快门优先曝光；`3`：光圈优先曝光；`4`：手动曝光 | 当前配置的变焦镜头曝光模式。 |
| `cameras[].zoom_iso` | 变焦镜头感光度 | enum_int | `0`：Auto；`1`：Auto (High Sense)；`2`：50；`3`：100；`4`：200；`5`：400；`6`：800；`7`：1600；`8`：3200；`9`：6400；`10`：12800；`11`：25600；`255`：FIXED | 当前配置的变焦镜头感光度。 |
| `cameras[].zoom_shutter_speed` | 变焦镜头快门速度 | enum_int | 见“相机快门速度枚举” | 当前配置的变焦镜头快门速度。 |
| `cameras[].zoom_exposure_value` | 变焦镜头曝光值 | enum_int | 见“相机曝光值枚举” | 当前配置的变焦镜头曝光值。 |
| `cameras[].zoom_focus_mode` | 变焦镜头对焦模式 | enum_int | `0`：MF；`1`：AFS；`2`：AFC | 当前变焦镜头对焦模式。 |
| `cameras[].zoom_focus_value` | 变焦镜头对焦值 | int | — | 当前变焦镜头对焦值。 |
| `cameras[].zoom_max_focus_value` | 变焦镜头最大对焦值 | int | — | 变焦镜头支持的最大对焦值。 |
| `cameras[].zoom_min_focus_value` | 变焦镜头最小对焦值 | int | — | 变焦镜头支持的最小对焦值。 |
| `cameras[].zoom_calibrate_farthest_focus_value` | 变焦镜头标定的最远对焦值 | int | — | 最清晰的最远位置对焦值。 |
| `cameras[].zoom_calibrate_nearest_focus_value` | 变焦镜头标定的最近对焦值 | int | — | 最清晰的最近位置对焦值。 |
| `cameras[].zoom_focus_state` | 变焦镜头对焦状态 | enum_int | `0`：空闲；`1`：对焦中；`2`：对焦成功；`3`：对焦失败 | 变焦镜头当前对焦状态。 |
| `cameras[].ir_metering_mode` | 红外测温模式 | enum_int | `0`：关闭测温；`1`：点测温；`2`：区域测温 | 红外相机当前测温模式。 |
| `cameras[].ir_metering_point` | 红外测温点 | struct | — | 红外点测温信息。 |
| `cameras[].ir_metering_point.x` | 测温点坐标 x | double | 0～1 | 以镜头左上角为坐标原点，水平方向为 x 轴。 |
| `cameras[].ir_metering_point.y` | 测温点坐标 y | double | 0～1 | 以镜头左上角为坐标原点，竖直方向为 y 轴。 |
| `cameras[].ir_metering_point.temperature` | 测温点温度 | double | — | 测温点的温度。 |
| `cameras[].ir_metering_area` | 红外测温区域 | struct | — | 红外区域测温信息。 |
| `cameras[].ir_metering_area.x` | 测温区域左上角坐标 x | double | 0～1 | 以镜头左上角为坐标原点，水平方向为 x 轴。 |
| `cameras[].ir_metering_area.y` | 测温区域左上角坐标 y | double | 0～1 | 以镜头左上角为坐标原点，竖直方向为 y 轴。 |
| `cameras[].ir_metering_area.width` | 测温区域宽度 | double | 0～1 | 测温区域宽度。 |
| `cameras[].ir_metering_area.height` | 测温区域高度 | double | 0～1 | 测温区域高度。 |
| `cameras[].ir_metering_area.aver_temperature` | 测温区域平均温度 | double | — | 测温区域的平均温度。 |
| `cameras[].ir_metering_area.min_temperature_point` | 测温区域最低温度点 | struct | — | 测温区域最低温度点信息。 |
| `cameras[].ir_metering_area.min_temperature_point.x` | 最低温度点坐标 x | double | 0～1 | 以镜头左上角为坐标原点，水平方向为 x 轴。 |
| `cameras[].ir_metering_area.min_temperature_point.y` | 最低温度点坐标 y | double | 0～1 | 以镜头左上角为坐标原点，竖直方向为 y 轴。 |
| `cameras[].ir_metering_area.min_temperature_point.temperature` | 最低温度点温度 | double | — | 测温区域最低温度点的温度。 |
| `cameras[].ir_metering_area.max_temperature_point` | 测温区域最高温度点 | struct | — | 测温区域最高温度点信息。 |
| `cameras[].ir_metering_area.max_temperature_point.x` | 最高温度点坐标 x | double | 0～1 | 以镜头左上角为坐标原点，水平方向为 x 轴。 |
| `cameras[].ir_metering_area.max_temperature_point.y` | 最高温度点坐标 y | double | 0～1 | 以镜头左上角为坐标原点，竖直方向为 y 轴。 |
| `cameras[].ir_metering_area.max_temperature_point.temperature` | 最高温度点温度 | double | — | 测温区域最高温度点的温度。 |

### 相机快门速度枚举

`cameras[].wide_shutter_speed` 与 `cameras[].zoom_shutter_speed` 共用以下枚举：

| 值 | 快门速度 | 值 | 快门速度 |
| --- | --- | --- | --- |
| `0` | 1/8000 s | `1` | 1/6400 s |
| `2` | 1/6000 s | `3` | 1/5000 s |
| `4` | 1/4000 s | `5` | 1/3200 s |
| `6` | 1/3000 s | `7` | 1/2500 s |
| `8` | 1/2000 s | `9` | 1/1600 s |
| `10` | 1/1500 s | `11` | 1/1250 s |
| `12` | 1/1000 s | `13` | 1/800 s |
| `14` | 1/725 s | `15` | 1/640 s |
| `16` | 1/500 s | `17` | 1/400 s |
| `18` | 1/350 s | `19` | 1/320 s |
| `20` | 1/250 s | `21` | 1/240 s |
| `22` | 1/200 s | `23` | 1/180 s |
| `24` | 1/160 s | `25` | 1/125 s |
| `26` | 1/120 s | `27` | 1/100 s |
| `28` | 1/90 s | `29` | 1/80 s |
| `30` | 1/60 s | `31` | 1/50 s |
| `32` | 1/40 s | `33` | 1/30 s |
| `34` | 1/25 s | `35` | 1/20 s |
| `36` | 1/15 s | `37` | 1/12.5 s |
| `38` | 1/10 s | `39` | 1/8 s |
| `40` | 1/6.25 s | `41` | 1/5 s |
| `42` | 1/4 s | `43` | 1/3 s |
| `44` | 1/2.5 s | `45` | 1/2 s |
| `46` | 1/1.67 s | `47` | 1/1.25 s |
| `48` | 1.0 s | `49` | 1.3 s |
| `50` | 1.6 s | `51` | 2.0 s |
| `52` | 2.5 s | `53` | 3.0 s |
| `54` | 3.2 s | `55` | 4.0 s |
| `56` | 5.0 s | `57` | 6.0 s |
| `58` | 7.0 s | `59` | 8.0 s |
| `65534` | Auto | — | — |

### 相机曝光值枚举

`cameras[].wide_exposure_value` 与 `cameras[].zoom_exposure_value` 共用以下枚举：

| 值 | 曝光值 | 值 | 曝光值 |
| --- | --- | --- | --- |
| `1` | -5.0 EV | `2` | -4.7 EV |
| `3` | -4.3 EV | `4` | -4.0 EV |
| `5` | -3.7 EV | `6` | -3.3 EV |
| `7` | -3.0 EV | `8` | -2.7 EV |
| `9` | -2.3 EV | `10` | -2.0 EV |
| `11` | -1.7 EV | `12` | -1.3 EV |
| `13` | -1.0 EV | `14` | -0.7 EV |
| `15` | -0.3 EV | `16` | 0 EV |
| `17` | 0.3 EV | `18` | 0.7 EV |
| `19` | 1.0 EV | `20` | 1.3 EV |
| `21` | 1.7 EV | `22` | 2.0 EV |
| `23` | 2.3 EV | `24` | 2.7 EV |
| `25` | 3.0 EV | `26` | 3.3 EV |
| `27` | 3.7 EV | `28` | 4.0 EV |
| `29` | 4.3 EV | `30` | 4.7 EV |
| `31` | 5.0 EV | `255` | FIXED |

### 子设备 4G Dongle 信息

| 字段路径 | 名称 | 类型 | 约束/枚举 | 说明 |
| --- | --- | --- | --- | --- |
| `dongle_infos[].imei` | Dongle IMEI | text | — | Dongle 的唯一识别标志。 |
| `dongle_infos[].dongle_type` | Dongle 类型 | enum_int | `6`：旧 Dongle；`10`：支持 eSIM 的新 Dongle | Dongle 硬件类型。 |
| `dongle_infos[].eid` | Dongle EID | text | — | eSIM 的唯一识别标志，用于公众号查询套餐、购买服务。 |
| `dongle_infos[].esim_activate_state` | eSIM 激活状态 | enum_int | `0`：未知；`1`：未激活；`2`：已激活 | eSIM 需要激活后才能使用。 |
| `dongle_infos[].sim_card_state` | SIM 卡状态 | enum_int | `0`：未插入；`1`：已插入 | Dongle 中实体 SIM 卡的插入状态。 |
| `dongle_infos[].sim_slot` | SIM 卡槽使能状态 | enum_int | `0`：未知；`1`：实体 SIM 卡；`2`：eSIM | Dongle 当前使用的 SIM 卡槽。 |
| `dongle_infos[].esim_infos` | eSIM 信息 | array&lt;struct&gt; | 数量不定 | Dongle 中的 eSIM 列表。 |
| `dongle_infos[].esim_infos[].telecom_operator` | 支持的运营商 | enum_int | `0`：未知；`1`：移动；`2`：联通；`3`：电信 | eSIM 支持的运营商。 |
| `dongle_infos[].esim_infos[].enabled` | eSIM 使能状态 | bool | `false`：未使用；`true`：使用中 | 同一时刻只能有一个 eSIM 处于使能状态。 |
| `dongle_infos[].esim_infos[].iccid` | SIM ICCID | text | — | SIM 卡唯一识别标志，可用于套餐购买。 |
| `dongle_infos[].sim_info` | SIM 卡信息 | struct | — | 可插入 Dongle 的实体 SIM 卡信息。 |
| `dongle_infos[].sim_info.telecom_operator` | 支持的运营商 | enum_int | `0`：未知；`1`：移动；`2`：联通；`3`：电信 | 实体 SIM 卡支持的运营商。 |
| `dongle_infos[].sim_info.sim_type` | SIM 卡类型 | enum_int | `0`：未知；`1`：其他普通 SIM 卡；`2`：三网卡 | 实体 SIM 卡类型。 |
| `dongle_infos[].sim_info.iccid` | SIM ICCID | text | — | 实体 SIM 卡的唯一识别标志，可用于套餐购买。 |

### 飞行器避障状态

| 字段路径 | 名称 | 类型 | 约束/枚举 | 说明 |
| --- | --- | --- | --- | --- |
| `obstacle_avoidance.horizon` | 水平避障状态 | enum_int | `0`：关闭；`1`：开启 | 水平方向避障功能状态。 |
| `obstacle_avoidance.upside` | 上视避障状态 | enum_int | `0`：关闭；`1`：开启 | 上视避障功能状态。 |
| `obstacle_avoidance.downside` | 下视避障状态 | enum_int | `0`：关闭；`1`：开启 | 下视避障功能状态。 |

### 飞行器保养信息

| 字段路径 | 名称 | 类型 | 约束/枚举 | 说明 |
| --- | --- | --- | --- | --- |
| `maintain_status.maintain_status_array` | 保养信息数组 | array&lt;struct&gt; | 数量不定 | 飞行器保养记录列表。 |
| `maintain_status.maintain_status_array[].state` | 保养状态 | enum_int | `0`：无保养；`1`：有保养 | 当前是否存在保养信息。 |
| `maintain_status.maintain_status_array[].last_maintain_type` | 上一次保养类型 | enum_int | `1`：飞行器基础保养；`2`：飞行器常规保养；`3`：飞行器深度保养 | 上一次执行的保养类型。 |
| `maintain_status.maintain_status_array[].last_maintain_time` | 上一次保养时间 | date | s | 上一次保养时间。 |
| `maintain_status.maintain_status_array[].last_maintain_flight_time` | 上一次保养时飞行航时 | int | h | 上一次保养时的累计飞行航时。 |
| `maintain_status.maintain_status_array[].last_maintain_flight_sorties` | 上一次保养时飞行架次 | int | 0～2147483647，步长 1 | 上一次保养时的累计飞行架次。 |

### 负载与云台信息

| 字段路径 | 名称 | 类型 | 约束/枚举 | 说明 |
| --- | --- | --- | --- | --- |
| `type_subtype_gimbalindex.gimbal_pitch` | 云台俯仰轴角度 | double | -180～180 °，步长 0.1 | 云台俯仰轴角度。 |
| `type_subtype_gimbalindex.gimbal_roll` | 云台横滚轴角度 | double | -180～180 °，步长 0.1 | 云台横滚轴角度。 |
| `type_subtype_gimbalindex.gimbal_yaw` | 云台偏航轴角度 | double | -180～180 °，步长 0.1 | 云台偏航轴角度。 |
| `type_subtype_gimbalindex.measure_target_longitude` | 激光测距目标经度 | double | -180～180 ° | 激光测距目标经度。 |
| `type_subtype_gimbalindex.measure_target_latitude` | 激光测距目标纬度 | double | -90～90 ° | 激光测距目标纬度。 |
| `type_subtype_gimbalindex.measure_target_altitude` | 激光测距目标海拔 | double | m | 激光测距目标海拔。 |
| `type_subtype_gimbalindex.measure_target_distance` | 激光测距距离 | double | m | 飞行器与测距目标之间的距离。 |
| `type_subtype_gimbalindex.measure_target_error_state` | 激光测距状态 | enum_int | `0`：NORMAL；`1`：TOO_CLOSE；`2`：TOO_FAR；`3`：NO_SIGNAL | 激光测距当前状态。 |
| `type_subtype_gimbalindex.payload_index` | 负载索引 | text | `{type-subtype-gimbalindex}` | 负载索引。 |
| `type_subtype_gimbalindex.zoom_factor` | 变焦倍数 | double | — | 负载相机当前变焦倍数。 |
| `type_subtype_gimbalindex.thermal_current_palette_style` | 调色盘样式 | enum_int | `0`：白热；`1`：黑热；`2`：描红；`3`：医疗；`5`：彩虹 1；`6`：铁红；`8`：北极；`11`：熔岩；`12`：热铁；`13`：彩虹 2 | 红外相机提供多种调色样式，可根据场景选择以便更清晰地查看目标。 |
| `type_subtype_gimbalindex.thermal_supported_palette_styles` | 设备支持的调色盘样式集合 | array&lt;enum_int&gt; | 数量不定 | 不同设备支持的调色盘样式可能不同。 |
| `type_subtype_gimbalindex.thermal_gain_mode` | 增益模式 | enum_int | `0`：自动；`1`：低增益，测温范围 0～500 °C；`2`：高增益，测温范围 -20～150 °C | 低增益提供更大的测温范围，高增益具有更高的测温精度。 |
| `type_subtype_gimbalindex.thermal_isotherm_state` | 是否开启等温线 | enum_int | `0`：关闭；`1`：开启 | 等温线用于突出用户关注的温度区间。 |
| `type_subtype_gimbalindex.thermal_isotherm_upper_limit` | 测温区间上限 | int | °C | 仅启用等温线功能后有效。 |
| `type_subtype_gimbalindex.thermal_isotherm_lower_limit` | 测温区间下限 | int | °C | 仅启用等温线功能后有效。 |
| `type_subtype_gimbalindex.thermal_global_temperature_min` | 全局画面最低温度 | float | °C | 全局画面中测得的最低温度。 |
| `type_subtype_gimbalindex.thermal_global_temperature_max` | 全局画面最高温度 | float | °C | 全局画面中测得的最高温度。 |

### 飞行器搜星状态

| 字段路径 | 名称 | 类型 | 约束/枚举 | 说明 |
| --- | --- | --- | --- | --- |
| `position_state.is_fixed` | 是否收敛 | enum_int | `0`：未开始；`1`：收敛中；`2`：收敛成功；`3`：收敛失败 | RTK 收敛状态。 |
| `position_state.quality` | 搜星档位 | enum_int | `1`：1 档；`2`：2 档；`3`：3 档；`4`：4 档；`5`：5 档；`10`：RTK fixed | 当前搜星质量档位。 |
| `position_state.gps_number` | GPS 搜星数量 | int | — | 当前搜索到的 GPS 卫星数。 |
| `position_state.rtk_number` | RTK 搜星数量 | int | — | 当前搜索到的 RTK 卫星数。 |

### 飞行器存储容量

| 字段路径 | 名称 | 类型 | 约束/枚举 | 说明 |
| --- | --- | --- | --- | --- |
| `storage.total` | 总容量 | int | KB | 存储总容量。 |
| `storage.used` | 已使用容量 | int | KB | 已使用的存储容量。 |

### 飞行器电池信息

| 字段路径 | 名称 | 类型 | 约束/枚举 | 说明 |
| --- | --- | --- | --- | --- |
| `battery.capacity_percent` | 电池总剩余电量 | int | 0～100 | 飞行器电池总剩余电量百分比。 |
| `battery.remain_flight_time` | 剩余飞行时间 | int | s | 按当前电量估算的剩余飞行时间。 |
| `battery.return_home_power` | 返航所需电量百分比 | int | 0～100 | 飞行器返航所需电量。 |
| `battery.landing_power` | 强制降落电量百分比 | int | 0～100 | 触发强制降落的电量。 |
| `battery.batteries` | 电池详细信息 | array&lt;struct&gt; | 数量不定 | 各块电池的详细信息。 |
| `battery.batteries[].capacity_percent` | 电池剩余电量 | int | 0～100 | 单块电池的剩余电量百分比。 |
| `battery.batteries[].index` | 电池序号 | int | 最小值 0 | 电池序号。 |
| `battery.batteries[].sn` | 电池序列号 | text | — | 电池 SN。 |
| `battery.batteries[].type` | 电池类型 | enum_int | 设备定义 | 电池类型。 |
| `battery.batteries[].sub_type` | 电池子类型 | enum_int | 设备定义 | 电池子类型。 |
| `battery.batteries[].firmware_version` | 固件版本 | text | — | 电池固件版本。 |
| `battery.batteries[].loop_times` | 电池循环次数 | int | — | 电池累计循环次数。 |
| `battery.batteries[].voltage` | 电压 | int | mV | 电池电压。 |
| `battery.batteries[].temperature` | 温度 | float | °C | 电池温度，保留一位小数。 |
| `battery.batteries[].high_voltage_storage_days` | 高电压存储天数 | int | 日 | 电池处于高电压状态的存储天数。 |

### 相机水印设置

| 字段路径 | 名称 | 类型 | 约束/枚举 | 说明 |
| --- | --- | --- | --- | --- |
| `camera_watermark_settings.global_enable` | 水印显示全局开关 | enum_int | `0`：关闭；`1`：开启 | 必须开启全局开关才能显示水印。 |
| `camera_watermark_settings.drone_type_enable` | 机型显示开关 | enum_int | `0`：关闭；`1`：开启 | 是否在水印中显示机型。 |
| `camera_watermark_settings.drone_sn_enable` | 飞行器序列号显示开关 | enum_int | `0`：关闭；`1`：开启 | 是否在水印中显示飞行器 SN。 |
| `camera_watermark_settings.datetime_enable` | 日期时间显示开关 | enum_int | `0`：关闭；`1`：开启 | 时区默认为当地时区。 |
| `camera_watermark_settings.gps_enable` | 经纬度和海拔显示开关 | enum_int | `0`：关闭；`1`：开启 | 是否在水印中显示经纬度和海拔。 |
| `camera_watermark_settings.user_custom_string_enable` | 自定义文案显示开关 | enum_int | `0`：关闭；`1`：开启 | 是否显示自定义水印文案。 |
| `camera_watermark_settings.user_custom_string` | 自定义文案内容 | text | 最多 250 字节 | 自定义水印文案。 |
| `camera_watermark_settings.layout` | 水印在画面中的位置 | enum_int | `0`：左上；`1`：左下；`2`：右上；`3`：右下 | 水印布局位置。 |

### 飞行器限远状态

| 字段路径 | 名称 | 类型 | 约束/枚举 | 说明 |
| --- | --- | --- | --- | --- |
| `distance_limit_status.state` | 是否开启限远 | enum_int | `0`：未设置；`1`：已设置 | 飞行器是否设置限远。 |
| `distance_limit_status.distance_limit` | 限远距离 | int | 15～8000 m，步长 1 | 飞行器限远距离。 |
| `distance_limit_status.is_near_distance_limit` | 是否接近设定的限制距离 | enum_int | `0`：未达到设定的限制距离；`1`：接近设定的限制距离 | 飞行器是否接近限远边界。 |

### PSDK UI 资源包

| 字段路径 | 名称 | 类型 | 约束/枚举 | 说明 |
| --- | --- | --- | --- | --- |
| `psdk_ui_resource[].psdk_index` | PSDK 负载设备索引 | int | 最小值 0 | PSDK 负载设备索引。 |
| `psdk_ui_resource[].psdk_ready` | PSDK 就绪状态 | enum_int | `0`：未就绪；`1`：已就绪 | PSDK 负载设备是否就绪。 |
| `psdk_ui_resource[].object_key` | OSS 对象 | text | — | PSDK UI 资源对应的对象存储 Key。 |

### PSDK 负载设备属性值

| 字段路径 | 名称 | 类型 | 约束/枚举 | 说明 |
| --- | --- | --- | --- | --- |
| `psdk_widget_values[].psdk_index` | PSDK 负载设备索引 | int | 最小值 0 | PSDK 负载设备索引。 |
| `psdk_widget_values[].psdk_name` | 设备名称 | text | — | PSDK 负载设备名称。 |
| `psdk_widget_values[].psdk_sn` | 设备序号 | text | — | PSDK 负载设备序列号。 |
| `psdk_widget_values[].psdk_version` | 设备固件版本 | text | — | PSDK 负载设备固件版本。 |
| `psdk_widget_values[].psdk_lib_version` | PSDK lib 版本 | text | — | PSDK 库版本。 |
| `psdk_widget_values[].speaker` | 喊话器状态 | struct | — | PSDK 喊话器状态。 |
| `psdk_widget_values[].speaker.work_mode` | 喊话器工作模式 | enum_int | `0`：TTS 负载模式；`1`：录音喊话 | 喊话器当前工作模式。 |
| `psdk_widget_values[].speaker.play_mode` | 喊话器播放模式 | enum_int | `0`：单次播放；`1`：循环播放（单曲） | 喊话器当前播放模式。 |
| `psdk_widget_values[].speaker.play_volume` | 喊话器音量 | int | 0～100，步长 1 | 喊话器播放音量。 |
| `psdk_widget_values[].speaker.system_state` | 喊话器状态 | enum_int | `0`：空闲中；`1`：传输中（机场到飞行器）；`2`：播放中；`3`：异常中；`4`：TTS 文本转换中；`99`：下载中（机场从云端下载） | 喊话器当前系统状态。 |
| `psdk_widget_values[].speaker.play_file_name` | 最近一次播放的文件名称 | text | 最大长度 128 B | 喊话器最近一次播放的文件名称。 |
| `psdk_widget_values[].speaker.play_file_md5` | 最近一次播放文件的 MD5 校验和 | text | — | 喊话器最近一次播放文件的 MD5。 |
| `psdk_widget_values[].values` | PSDK 控件值列表 | array&lt;struct&gt; | 数量不定 | PSDK 负载设备的控件值列表。 |
| `psdk_widget_values[].values[].index` | 控件编号 | int | 最小值 0，步长 1 | 控件编号。 |
| `psdk_widget_values[].values[].value` | 控件值 | int | 设备定义 | 控件当前值。 |
