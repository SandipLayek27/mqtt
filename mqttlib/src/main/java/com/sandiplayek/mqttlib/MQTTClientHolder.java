package com.sandiplayek.mqttlib;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class MQTTClientHolder {

    private static final String TAG = "MQTTClientHolder";
    private MqttAndroidClient mqttAndroidClient;

    String MQTT_BROKER_URL,PUBLISH_TOPIC,CLIENT_ID,USERNAME,PASSWORD,offlineMSG;
    int QOS = 0;
    Context context;



    public MQTTClientHolder(Context context, String MQTT_BROKER_URL,String PUBLISH_TOPIC,String CLIENT_ID,String USERNAME,String PASSWORD, String offlineMSG, int QOS) {
        this.context = context;
        this.MQTT_BROKER_URL = MQTT_BROKER_URL;
        this.PUBLISH_TOPIC = PUBLISH_TOPIC;
        this.CLIENT_ID = CLIENT_ID;
        this.USERNAME = USERNAME;
        this.PASSWORD = PASSWORD;
        this.offlineMSG = offlineMSG;
        this.QOS = QOS;
    }

    public MqttAndroidClient getMqttClient(ResponseMQTT responseMQTT) {
        JSONObject jsonObject = new JSONObject();
        mqttAndroidClient = new MqttAndroidClient(context, MQTT_BROKER_URL, CLIENT_ID);
        try {
            IMqttToken token = mqttAndroidClient.connect(getMqttConnectionOption());
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    mqttAndroidClient.setBufferOpts(getDisconnectedBufferOptions());

                    try{
                        jsonObject.put("responseCode", "200");
                        jsonObject.put("responseMessage", "Client Connect Successfully");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    responseMQTT.getResponse(jsonObject);
                    Log.d(TAG, "Success");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    try{
                        jsonObject.put("responseCode", "204");
                        jsonObject.put("responseMessage", exception.toString());
                        responseMQTT.getResponse(jsonObject);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
        return mqttAndroidClient;
    }

    @NonNull
    private DisconnectedBufferOptions getDisconnectedBufferOptions() {
        DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
        disconnectedBufferOptions.setBufferEnabled(true);
        disconnectedBufferOptions.setBufferSize(100);
        disconnectedBufferOptions.setPersistBuffer(false);
        disconnectedBufferOptions.setDeleteOldestMessages(false);
        return disconnectedBufferOptions;
    }

    @NonNull
    private MqttConnectOptions getMqttConnectionOption() {
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setCleanSession(false);
        /*
        On connection, a client sets the “clean session” flag.If the clean session is set to false,
        means when the client disconnects, any subscriptions it has will remain and any subsequent QoS 1 or 2 messages
        will be stored until it connects again in the future.
        If the clean session is true, then all subscriptions will be removed from the client when it disconnects.
        */
        mqttConnectOptions.setAutomaticReconnect(true);
        /* Sets whether the client will automatically attempt to reconnect to the server
        if the connection is lost.It will initially wait 1 second before it attempts to reconnect,
        for every failed to reconnect attempt,
        the delay will double until it is at 2 minutes at which point the delay will stay at 2 minutes.
        */
        mqttConnectOptions.setWill(PUBLISH_TOPIC, offlineMSG.getBytes(), 1, true);
        /*
        When a client connects to a broker,
        it may inform the broker that it has a will.
        This is a message that it wishes the broker to send when the client disconnects unexpectedly.
        The will message has a topic, QoS and retains status just the same as any other message.
        */
        mqttConnectOptions.setUserName(USERNAME);
        mqttConnectOptions.setPassword(PASSWORD.toCharArray());
        return mqttConnectOptions;
    }

    public void subscribe(@NonNull MqttAndroidClient client, ResponseMQTT responseMQTT) throws MqttException {
        JSONObject jsonObject = new JSONObject();
        IMqttToken token = client.subscribe(PUBLISH_TOPIC, QOS);
        token.setActionCallback(new IMqttActionListener() {
            @Override
            public void onSuccess(IMqttToken iMqttToken) {
                try{
                    jsonObject.put("responseCode", "200");
                    jsonObject.put("responseMessage", PUBLISH_TOPIC+" Subscription Successfully");
                }catch (Exception e){
                    e.printStackTrace();
                }
                responseMQTT.getResponse(jsonObject);
                Log.d(TAG, "Subscribe Successfully " + PUBLISH_TOPIC);
            }

            @Override
            public void onFailure(IMqttToken iMqttToken, Throwable throwable) {
                try{
                    jsonObject.put("responseCode", "204");
                    jsonObject.put("responseMessage", PUBLISH_TOPIC+" Subscription Failed");
                }catch (Exception e){
                    e.printStackTrace();
                }
                responseMQTT.getResponse(jsonObject);
                Log.e(TAG, "Subscribe Failed " + PUBLISH_TOPIC);

            }
        });
    }

    public void publishMessage(@NonNull MqttAndroidClient client, @NonNull String msg, ResponseMQTT responseMQTT) throws MqttException, UnsupportedEncodingException {
        JSONObject jsonObject = new JSONObject();
        try{
            byte[] encodedPayload = new byte[0];
            encodedPayload = msg.getBytes("UTF-8");
            MqttMessage message = new MqttMessage(encodedPayload);
            message.setId(320);
            message.setRetained(true);
            message.setQos(QOS);
            client.publish(PUBLISH_TOPIC, message);

            jsonObject.put("responseCode", "200");
            jsonObject.put("responseMessage", "Published Successfully");
            responseMQTT.getResponse(jsonObject);
        }catch (Exception e){
            try {
                jsonObject.put("responseCode", "204");
                jsonObject.put("responseMessage", e.toString());
                responseMQTT.getResponse(jsonObject);
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            }
        }

    }

    public void unSubscribe(@NonNull MqttAndroidClient client, ResponseMQTT responseMQTT) throws MqttException {
        JSONObject jsonObject = new JSONObject();
        IMqttToken token = client.unsubscribe(PUBLISH_TOPIC);

        token.setActionCallback(new IMqttActionListener() {
            @Override
            public void onSuccess(IMqttToken iMqttToken) {
                try{
                    jsonObject.put("responseCode", "200");
                    jsonObject.put("responseMessage", PUBLISH_TOPIC+" UnSubscribe Successfully");
                }catch (Exception e){
                    e.printStackTrace();
                }
                responseMQTT.getResponse(jsonObject);
                Log.d(TAG, "UnSubscribe Successfully " + PUBLISH_TOPIC);
            }

            @Override
            public void onFailure(IMqttToken iMqttToken, Throwable throwable) {
                try{
                    jsonObject.put("responseCode", "204");
                    jsonObject.put("responseMessage", PUBLISH_TOPIC+" UnSubscribe Failed");
                }catch (Exception e){
                    e.printStackTrace();
                }
                responseMQTT.getResponse(jsonObject);
                Log.e(TAG, "UnSubscribe Failed " + PUBLISH_TOPIC);
            }
        });
    }

    public void disconnect(@NonNull MqttAndroidClient client, ResponseMQTT responseMQTT) throws MqttException {
        JSONObject jsonObject = new JSONObject();
        IMqttToken mqttToken = client.disconnect();
        mqttToken.setActionCallback(new IMqttActionListener() {
            @Override
            public void onSuccess(IMqttToken iMqttToken) {
                try{
                    jsonObject.put("responseCode", "200");
                    jsonObject.put("responseMessage", PUBLISH_TOPIC+" Successfully disconnected");
                }catch (Exception e){
                    e.printStackTrace();
                }
                responseMQTT.getResponse(jsonObject);
                Log.d(TAG, "Successfully disconnected");
            }

            @Override
            public void onFailure(IMqttToken iMqttToken, Throwable throwable) {
                try{
                    jsonObject.put("responseCode", "200");
                    jsonObject.put("responseMessage", "Failed to disconnected " + throwable.toString());
                }catch (Exception e){
                    e.printStackTrace();
                }
                responseMQTT.getResponse(jsonObject);
                Log.d(TAG, "Failed to disconnected " + throwable.toString());
            }
        });
    }

}