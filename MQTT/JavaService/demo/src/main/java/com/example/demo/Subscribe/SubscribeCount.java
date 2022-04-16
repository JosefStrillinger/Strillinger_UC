package com.example.demo.Subscribe;

import java.io.IOException;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class SubscribeCount {
    private static Integer countPictures;
    public static void main(String[] args) throws IOException {
		String topic = "house/light";
		String broker = "tcp://localhost:2222";
		String clientId = "Subscribe Count";
		MemoryPersistence persistence = new MemoryPersistence();

		try {
			MqttClient client = new MqttClient(broker, clientId, persistence);
			MqttCallback callback = new MqttCallback() { //Wird aufgerufen, wenn z.B. eine Nachricht reinkommt

				public void connectionLost(Throwable cause) {
					System.out.println("Connection lost");

				}

				public void messageArrived(String topic, MqttMessage message) throws Exception {
					System.out.println(topic);
					String m = new String(message.getPayload());
					System.out.println(m);
                    if(message.toString().equals("foto_taken")) {
						countPictures++;
					}
				}

				public void deliveryComplete(IMqttDeliveryToken token) {
					System.out.println("Delivery Complete");

				}
			};

			MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(true);
			connOpts.setUserName("josef");
			connOpts.setPassword("Jstrillinger22".toCharArray());

			client.setCallback(callback);
			System.out.println("Connecting to broker: " + broker);
			client.connect(connOpts);
			client.subscribe(topic);
			System.out.println("Connected and listening....");
			System.in.read(); //Die Anwendung soll warten bis eine Taste gedrückt wird
			client.disconnect();
			System.out.println("Disconnected");
			System.exit(0);
		} catch (MqttException me) {
			me.printStackTrace();
		}

	}
}
