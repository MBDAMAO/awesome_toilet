import paho.mqtt.client as mqtt
import time
import json
import random
from datetime import datetime

# MQTT 配置
MQTT_BROKER = "120.53.88.192"
MQTT_PORT = 1883
CLIENT_ID = f"python-mqtt-{random.randint(0, 1000)}"
DEVICE_ID = "1"
MQTT_TOPIC = "test/topic"  # 所有数据统一发送到此 topic
USERNAME = "damao"
PASSWORD = "1231h5867"

# 模拟环境数据（env）
def generate_env_data():
    return {
        "device": DEVICE_ID,
        "timestamp": datetime.utcnow().isoformat() + "Z",
        "type": "env",
        "data": {
            "temperature": round(random.uniform(18.0, 30.0), 2),
            "humidity": round(random.uniform(30.0, 70.0), 2),
            "ammonia": round(random.uniform(0.1, 5.0), 2),
            "pm2d5": round(random.uniform(5, 50), 1),
            "pm10": round(random.uniform(10, 100), 1),
            "pressure": round(random.uniform(950, 1050), 2),
            "h2s": round(random.uniform(0.1, 5.0), 2),
            "co2": round(random.uniform(350, 800), 1),
            "tvoc": round(random.uniform(0.1, 1.0), 2)
        }
    }

# 模拟人流数据（flow）
def generate_flow_data():
    return {
        "device": DEVICE_ID,
        "timestamp": datetime.utcnow().isoformat() + "Z",
        "type": "flow",
        "data": {
            "femaleVisitors": random.randint(0, 3),
            "maleVisitors": random.randint(0, 3)
        }
    }

# 模拟能耗数据（energy）
def generate_energy_data():
    return {
        "device": DEVICE_ID,
        "timestamp": datetime.utcnow().isoformat() + "Z",
        "type": "energy",
        "data": {
            "electricityUsage": round(random.uniform(0.01, 0.5), 2),  # kWh
            "waterUsage": round(random.uniform(0.5, 5.0), 2),          # 升
            "paperUsage": round(random.uniform(0.5, 5.0), 2)          # 升
        }
    }

# MQTT 回调函数
def on_connect(client, userdata, flags, rc):
    if rc == 0:
        print("✅ 成功连接到MQTT服务器")
    else:
        print(f"❌ 连接失败，返回码: {rc}")

def on_publish(client, userdata, mid):
    print(f"📤 消息已发布 (ID: {mid})")

def publish_data(client, data):
    payload = json.dumps(data)
    result = client.publish(MQTT_TOPIC, payload, qos=1)
    if result.rc == mqtt.MQTT_ERR_SUCCESS:
        print(f"✔️ 发送到 {MQTT_TOPIC}: {payload}")
    else:
        print(f"❌ 发送失败: {mqtt.error_string(result.rc)}")

def main():
    client = mqtt.Client(client_id=CLIENT_ID)
    client.username_pw_set(USERNAME, PASSWORD)  # 设置用户名和密码
    client.on_connect = on_connect
    client.on_publish = on_publish

    client.connect(MQTT_BROKER, MQTT_PORT, 60)
    client.loop_start()

    try:
        count = 0
        while True:
            if count % 2 == 0:
                publish_data(client, generate_env_data())

            if count % 6 == 0:
                publish_data(client, generate_flow_data())

            if count % 12 == 0:
                publish_data(client, generate_energy_data())

            time.sleep(5)
            count += 1

    except KeyboardInterrupt:
        print("⛔️ 模拟终止，断开连接")
        client.disconnect()
        client.loop_stop()

if __name__ == "__main__":
    main()
