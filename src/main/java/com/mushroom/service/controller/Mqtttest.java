package com.mushroom.service.controller;

import com.mushroom.service.service.SensorDataService;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import com.mushroom.service.model.SensorInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class Mqtttest implements MqttCallback{

    @Autowired
    SensorDataService sensorDataService;

    MqttClient client;
    public Mqtttest() {
    }

    public static void main(String[] args) {
        new Mqtttest().doDemo();
    }

    public void doDemo() {
        try {
            client = new MqttClient("tcp://182.163.112.207:1883", "Sending");
            client.connect();
            client.setCallback(this);
            client.subscribe("mushroom/+/+/sensor_data");
//            MqttMessage message = new MqttMessage();
//            message.setPayload("A single message from my computer fff"
//                    .getBytes());
//            client.publish("foo", message);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void connectionLost(Throwable cause) {
        // TODO Auto-generated method stub

    }

    @Override
    public void messageArrived(String topic, MqttMessage message)
            throws Exception {
        System.out.print(topic+" : ");
        String[] parts = topic.split("/");
        System.out.println(message);
        int user_id = Integer.parseInt(parts[1]);
        int device_id = Integer.parseInt(parts[2]);
        System.out.println("User : "+parts[1]);
        System.out.println("Device : "+parts[2]);
        String messageStr = message.toString();
        if(topic.equals("mushroom/"+parts[1]+"/"+parts[2]+"/sensor_data")){
            String[] sensors = messageStr.split(",");
            int humidity = Integer.parseInt(sensors[0]);
            int temp = Integer.parseInt(sensors[1]);
            int co2 = Integer.parseInt(sensors[2]);
            int light = Integer.parseInt(sensors[3]);
            System.out.println("Humidity : "+humidity);
            System.out.println("Temperature : "+temp);
            System.out.println("CO2 : "+co2);
            System.out.println("Light : "+light);
            String date = "2018.09.11";
            String time = "11:00:00";
            SensorInfo sensorinfo = new SensorInfo();
            sensorinfo.setDate(date);
            sensorinfo.setTime(time);
            sensorinfo.setUser(user_id);
            sensorinfo.setDevice(device_id);
            sensorinfo.setTemperature(temp);
            sensorinfo.setHumidity(humidity);
            sensorinfo.setCo2(co2);
            sensorinfo.setLight(light);

            System.out.println(sensorinfo.toString());

            sensorDataService.addSensorData(sensorinfo);
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // TODO Auto-generated method stub

    }

}
