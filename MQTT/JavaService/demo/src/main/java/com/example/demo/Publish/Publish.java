package com.example.demo.Publish;

import java.io.IOException;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;


public class Publish {
    public static void main(String[] args) {
		String topic = "house/light";
		String content = "Message from MqttPublishSample";
		int qos = 2;
		String broker = "tcp://localhost:2222";
		String clientId = "Publish";
		MemoryPersistence persistence = new MemoryPersistence();

		try {
			MqttClient client = new MqttClient(broker, clientId, persistence);
			MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(true);
			connOpts.setUserName("josef");
			connOpts.setPassword("Jstrillinger22".toCharArray());

			System.out.println("Connecting to broker: " + broker);
			client.connect(connOpts);
			System.out.println("Connected");

			System.out.println("Publishing message: " + content);
			MqttMessage message = new MqttMessage(content.getBytes());
			message.setQos(qos);
			client.publish(topic, message);

			client.disconnect();
			System.out.println("Disconnected");
			System.exit(0); // Sonst beendet das Programm nicht
		} catch (MqttException me) {
			me.printStackTrace();
		}

	}
}
