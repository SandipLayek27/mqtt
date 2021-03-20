package com.sandiplayek.mqtt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sandiplayek.mqttlib.MQTTClientHolder;
import com.sandiplayek.mqttlib.ResponseMQTT;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MqttAndroidClient client;
    private String TAG = "MainActivity";
    private MQTTClientHolder mqttClientHolder;

    private EditText textMessage;
    private Button publishMessage, subscribe, unSubscribe, disconnect;

    public String MQTT_BROKER_URL = "";
    public String PUBLISH_TOPIC = "";
    public String CLIENT_ID = "";
    public String USERNAME = "";
    public String PASSWORD = "";
    public String offlineMSG = "";
    public int QOS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // GET README
        Readme.aboutMQTT();
        Readme.structureMQTT();

        // SET IDS
        textMessage = (EditText) findViewById(R.id.textMessage);
        publishMessage = (Button) findViewById(R.id.publishMessage);
        subscribe = (Button) findViewById(R.id.subscribe);
        unSubscribe = (Button) findViewById(R.id.unSubscribe);
        disconnect = (Button) findViewById(R.id.disconnect);

        subscribe.setOnClickListener(this::onClick);
        publishMessage.setOnClickListener(this::onClick);
        unSubscribe.setOnClickListener(this::onClick);
        disconnect.setOnClickListener(this::onClick);

        // CALL CONSTRUCTOR
        mqttClientHolder = new MQTTClientHolder(MainActivity.this, MQTT_BROKER_URL, PUBLISH_TOPIC, CLIENT_ID, USERNAME, PASSWORD, offlineMSG, QOS);

        // GET CLIENT
        client = mqttClientHolder.getMqttClient(new ResponseMQTT() {
            @Override
            public void getResponse(JSONObject response) {
                Log.d("MQTTResponse-CONNECT",response.toString());
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view == subscribe){
            doSubscribe();
        }else if(view == publishMessage){
            doPublished();
        }else if(view == unSubscribe){
            doUnsubscribe();
        }else if(view == disconnect){
            doDisconnect();
        }
    }

    private void doSubscribe() {
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
    }

    private void doPublished() {
        String msg = textMessage.getText().toString().trim();
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
    }

    private void doUnsubscribe() {
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
    }

    private void doDisconnect() {
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
    }
}