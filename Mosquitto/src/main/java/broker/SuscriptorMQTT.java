package broker;

import java.util.Scanner;

import org.eclipse.paho.client.mqttv3.*;

public class SuscriptorMQTT {
	
	@SuppressWarnings("resource")
	public static void main(String[] args) throws MqttException {
		
		System.out.println("== INICIO Suscriptor == \n");
		
		MqttClient clienteMqtt = new MqttClient("tcp://localhost:1883", MqttClient.generateClientId());
		System.out.println("== ClienteMQTT creado == \n");
		
		clienteMqtt.connect();
        System.out.println("== ClienteMQTT conectado == \n"); 
        
        System.out.println("INGRESE topic:\t");
        String tema = new Scanner(System.in).nextLine();
        
        clienteMqtt.subscribe(tema);
        System.out.println("== ClienteMQTT suscripto a: " + tema + " == \n");
		
		clienteMqtt.setCallback(new MqttCallback() {
            public void connectionLost(Throwable throwable) {
            	System.out.println("== Conexión a MQTT perdida == \n");
            }

            public void messageArrived(String tema, MqttMessage mensaje) throws Exception {
                System.out.println("== Mensaje recibido:\t" + new String(mensaje.getPayload()) 
                				+ " == enviado mediante: " + tema + " ==\n");
            }

            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
            }
        });
	}	
}
