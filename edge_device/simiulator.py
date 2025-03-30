import requests
import time
import random
import uuid
import logging
from datetime import datetime
from typing import Dict, Any

# 基础配置
BASE_URL = "http://your-api-domain.com/api"
ENV_ENDPOINT = f"{BASE_URL}/south/data/env"
TRAFFIC_ENDPOINT = f"{BASE_URL}/south/data/traffic"
REGISTER_ENDPOINT = f"{BASE_URL}/south/device/register"  # 需要实现设备注册接口

# 初始化日志
logging.basicConfig(
    level=logging.INFO,
    format='%(asctime)s - %(name)s - %(levelname)s - %(message)s'
)
logger = logging.getLogger("IoTDevice")


class IoTDevice:
    def __init__(self):
        self.device_id = None
        self.location = None  # 设备安装位置
        self.last_people_count = 0
        self.is_registered = False

    def register_device(self):
        """设备注册逻辑（需要后端实现对应接口）"""
        try:
            payload = {
                "deviceSn": str(uuid.uuid4()),
                "deviceType": "TOILET_SENSOR_V2",
                "installLocation": "3F_WEST",
                "installTime": datetime.now().isoformat()
            }
            response = requests.post(REGISTER_ENDPOINT, json=payload)
            if response.status_code == 200:
                data = response.json()
                self.device_id = data['deviceId']
                self.location = data['installLocation']
                self.is_registered = True
                logger.info(f"Device registered successfully. ID: {self.device_id}")
            else:
                logger.error(f"Registration failed: {response.text}")
        except Exception as e:
            logger.error(f"Registration error: {str(e)}")

    def generate_env_data(self) -> Dict[str, Any]:
        """生成环境监测数据"""
        return {
            "deviceId": self.device_id,
            "temp": round(random.uniform(18.0, 30.0), 1),  # 温度(℃)
            "humidity": random.randint(40, 80),  # 湿度(%)
            "nh3": round(random.uniform(0, 10.0), 2),  # 氨气浓度(ppm)
            "h2s": round(random.uniform(0, 5.0), 2),  # 硫化氢浓度(ppm)
            "pm25": random.randint(0, 100),  # PM2.5
            "battery": random.randint(20, 100),  # 电池电量(%)
            "timestamp": datetime.now().isoformat()
        }

    def generate_traffic_data(self) -> Dict[str, Any]:
        """生成人流量数据"""
        # 模拟递增计数，偶尔重置
        self.last_people_count += random.randint(0, 2)
        if random.random() < 0.1:
            self.last_people_count = 0

        return {
            "deviceId": self.device_id,
            "peopleCount": self.last_people_count,
            "doorStatus": random.choice(["OPEN", "CLOSED"]),
            "emergencyAlert": random.random() < 0.02,  # 2%概率触发紧急报警
            "timestamp": datetime.now().isoformat()
        }

    def send_data(self, endpoint: str, data: dict):
        """发送数据到指定端点"""
        try:
            response = requests.post(
                url=endpoint,
                json=data,
                timeout=5
            )
            if not response.ok:
                logger.warning(f"Send data failed: {response.status_code} - {response.text}")
            else:
                logger.debug(f"Data sent successfully: {data}")
        except Exception as e:
            logger.error(f"Connection error: {str(e)}")


def main():
    # 初始化设备
    device = IoTDevice()

    # 设备注册（需要先实现注册接口）
    # device.register_device()

    # 如果暂时不需要注册，使用模拟ID
    device.device_id = "DEV_0001"
    device.location = "3F_WEST_TOILET"

    # 主循环
    while True:
        try:
            # 每5秒发送环境数据
            env_data = device.generate_env_data()
            device.send_data(ENV_ENDPOINT, env_data)

            # 每60秒发送人流量数据
            if int(time.time()) % 60 == 0:
                traffic_data = device.generate_traffic_data()
                device.send_data(TRAFFIC_ENDPOINT, traffic_data)

            time.sleep(5)

        except KeyboardInterrupt:
            logger.info("Device simulator stopped")
            break


if __name__ == "__main__":
    main()