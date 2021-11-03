# MQTT
MQTT Connection Protocol.

## Developed
[![Sandip](https://avatars1.githubusercontent.com/u/31722942?v=4&u=18643bfaaba26114584d27693e9891db26bcb582&s=39) Sandip](https://github.com/SandipLayek27)  
# ★ Gradle Dependency
Add Gradle dependency in the build.gradle file of your application module (app in the most cases) :
First Tab:

```sh
allprojects{
    repositories{
        jcenter()
        maven {
            url 'https://jitpack.io'
        }
    }
}
```

AND

```sh
dependencies {
  implementation 'com.github.SandipLayek27:mqtt:1.0'
  // ALSO ADD
  implementation fileTree(dir: '../mqttlib/libs', include: '*.jar')
}
```

# ★ Add to manifest.xml
```
<uses-permission android:name="android.permission.WAKE_LOCK" />
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.READ_PHONE_STATE" />
```

# ★ Aout MQTT
![alt text](https://raw.githubusercontent.com/SandipLayek27/mqtt/master/app/src/main/res/drawable/mqtt.png)

# ★ Features are
1. Connection established MQTT Protocol
2. Subscribe Topic.
3. Published Message - Hex Format
4. Un-Subscribe Topic.
5. Disconnect MQTT Protocol

★ Useful variables
```
private MqttAndroidClient client;
private MQTTClientHolder mqttClientHolder;
public String MQTT_BROKER_URL = "tcp:/XXX.XXX.XXX.XXX:XXXX"; [tcp://ip:port]
public String PUBLISH_TOPIC = "";
public String CLIENT_ID = "";
public String USERNAME = "";
public String PASSWORD = "";
public String offlineMSG = "";
public int QOS = 1;
```
★ Readme In Details
```
Readme.aboutMQTT();
Readme.structureMQTT();
```

★ Call Constructor
```
mqttClientHolder = new MQTTClientHolder(MainActivity.this, MQTT_BROKER_URL, PUBLISH_TOPIC, CLIENT_ID, USERNAME, PASSWORD, offlineMSG, QOS);
```

★ 1. Established Connection
```
client = mqttClientHolder.getMqttClient(new ResponseMQTT() {
    @Override
    public void getResponse(JSONObject response) {
        Log.d("MQTTResponse-CONNECT",response.toString());
    }
});
```

★ 2. Subscribe Topic
```
try {
    mqttClientHolder.subscribe(client, new ResponseMQTT() {
        @Override
        public void getResponse(JSONObject response) {
            Log.d("MQTTResponse-SUBSCRIBE",response.toString());
        }
    });
} catch (MqttException e) {
    e.printStackTrace();
}
```

★ 3. Published Message
```
String msg = "Your Message";
if (!msg.isEmpty()) {
    try {
        mqttClientHolder.publishMessage(client, msg, new ResponseMQTT() {
            @Override
            public void getResponse(JSONObject response) {
                Log.d("MQTTResponse-PUBLISHED",response.toString());
            }
        });
    } catch (MqttException e) {
        e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
    }
}
```

★ 4. Un-Subscribe Topic
```
try {
    mqttClientHolder.unSubscribe(client, new ResponseMQTT() {
        @Override
        public void getResponse(JSONObject response) {
            Log.d("MQTTResponse-UNSUBS",response.toString());
        }
    });
} catch (MqttException e) {
    e.printStackTrace();
}
```

★ 5. Disconnect MQTT Protocol
```
try {
    mqttClientHolder.disconnect(client, new ResponseMQTT() {
        @Override
        public void getResponse(JSONObject response) {
            Log.d("MQTTResponse-DISCONN",response.toString());
        }
    });
} catch (MqttException e) {
    e.printStackTrace();
}
```
