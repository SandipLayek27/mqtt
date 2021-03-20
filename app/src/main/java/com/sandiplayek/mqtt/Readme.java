package com.sandiplayek.mqtt;

public class Readme {

    public static String aboutMQTT(){
        String note = "";
        note = note+"MQTT PROTOCOL: Message Queuing Telemetry Transport"+"\n";
        note = note+"--------------------------------------------------"+"\n";
        note = note+"MQTT Service Provider: Mosquitto, Hive MQ, Cloud MQTT, Others... "+"\n";
        note = note+"Mosquitto => Offline"+"\n";
        note = note+"Others => Online Cloud, limited provider"+"\n";
        note = note+"Mosquitto => Software, which provides own server own machine"+"\n";
        note = note+"Mosquitto => Software, unlimited providers"+"\n";
        note = note+"Broker => Suppose I have a Raspberry pi device that is called broker, This is one chip device."+"\n";
        note = note+"One chip device means, Single chip provides a complete machine"+"\n";
        note = note+"And also I have 2 switch boards which have 4 switches each case."+"\n";
        note = note+"Switch boards are connected with Raspberry pi device."+"\n";
        note = note+"5 Key terms:--------------"+"\n";
        note = note+"1# Client Id"+"\n";
        note = note+"2# Topic"+"\n";
        note = note+"3# Subscribe"+"\n";
        note = note+"4# Publish"+"\n";
        note = note+"5# Payload"+"\n";
        note = note+"5# QOS"+"\n";
        note = note+"--------------------------"+"\n";
        note = note+"--BROKER---CLIENT---TOPIC-"+"\n";
        note = note+"Broker: Raspberry pi device, Present on cloud"+"\n";
        note = note+"Clients: Switch boards"+"\n";
        note = note+"Switch board 1 => Client id: 1"+"\n";
        note = note+"Switch board 2 => Client id: 2"+"\n";
        note = note+"Topic => can differentiate each switch boards"+"\n";
        note = note+"Using this topic data can be send and received from broker to client and vice versa"+"\n";
        note = note+"Topic may be same named on different clients"+"\n";
        note = note+"Topic name may be String format"+"\n";
        note = note+"Suppose, "+"\n";
        note = note+"Topic Name OF Switch board 1 => Living Room"+"\n";
        note = note+"Topic Name OF Switch board 2 => Bed Room"+"\n";
        note = note+"--------------------------"+"\n";
        note = note+"--SUBSCRIBE---------------"+"\n";
        note = note+"1st Case: Subscribe to Topic "+"\n";
        note = note+"That can be used for, to identify that where the broker can send data and which type of data send"+"\n";
        note = note+"--------------------------"+"\n";
        note = note+"--PUBLISH-----------------"+"\n";
        note = note+"2nd Case: Publish"+"\n";
        note = note+"Broker -----SEND DATA--------> Client"+"\n";
        note = note+"Client -----SEND DATA--------> Broker"+"\n";
        note = note+"Medium: Based on Topic "+"\n";
        note = note+"--------------------------"+"\n";
        note = note+"Now I want to turn on switch board 1(Client1) -> 2nd switch"+"\n";
        note = note+"Present State Off that means value: 0"+"\n";
        note = note+"I want to change it to value: 1"+"\n";
        note = note+"So, I give the command to broker from a mobile interface, that turn on 2nd switch of switch board 1"+"\n";
        note = note+"Broker publish(send) this value 1 for client1 using based on topic subscription"+"\n";
        note = note+"Then Client that case switch 2 of switch board 1 turned on"+"\n";
        note = note+"--------------------------"+"\n";
        note = note+"Now my switch board have a DOO Sensor"+"\n";
        note = note+"Which have 2 state"+"\n";
        note = note+"1st State: On(value: 1)"+"\n";
        note = note+"2nd State: Off(value: 0)"+"\n";
        note = note+"Now I turn off(value 0) 4th switch of 1st switch board(client 1)"+"\n";
        note = note+"Sensor publish data(send) to broker"+"\n";
        note = note+"Broker send notification to Mobile Interface."+"\n";
        note = note+"Mobile Interface detect that any one turn off 4th switch of 1st switch board"+"\n";
        note = note+"--------------------------"+"\n";
        note = note+"value is called PAYLOAD"+"\n";
        note = note+"--------------------------"+"\n";
        note = note+"CONCLUSION"+"\n";
        note = note+"--------------------------"+"\n";
        note = note+"1# Broker -----SUBSCRIBE---------> TOPIC"+"\n";
        note = note+"2# Mobile Interface ---SEND DATA(PAYLOAD)---> Broker ---PUBLISH(PAYLOAD)---> Client"+"\n";
        note = note+"3# Client ---PUBLISH(PAYLOAD)---> Broker ---NOTIFICATION---> Mobile Interface"+"\n";
        note = note+"--------------------------"+"\n";
        note = note+"--QOS(Quality Of Service)-"+"\n";
        note = note+"Types: 0,1,2"+"\n";
        note = note+"Type 0: Interface Order to Broker,"+"\n";
        note = note+"But broker don't give any back response that he doing his job properly or not,"+"\n";
        note = note+"That's called QOS:0"+"\n";
        note = note+""+"\n";
        note = note+"Type 1: Interface Order to Broker,"+"\n";
        note = note+"Broker give back response that he will do his job later"+"\n";
        note = note+"That's called QOS:0"+"\n";
        note = note+""+"\n";
        note = note+"Type 2: Interface Order to Broker,"+"\n";
        note = note+"Broker give back response that he will do his job later"+"\n";
        note = note+"Again ask by client or interface is it completed?"+"\n";
        note = note+"Broker response that YES"+"\n";
        note = note+"-----------------------------"+"\n";

        note = note+"Documented By: Sandip Layek"+"\n";
        note = note+"Dated:         05/03/2021"+"\n";
        note = note+"-----------------------------"+"\n";

        return note;
    }

    public static int structureMQTT(){
        int resource = R.drawable.mqtt;
        return resource;
    }
}
