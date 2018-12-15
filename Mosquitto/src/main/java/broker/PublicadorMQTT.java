package broker;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;

public class PublicadorMQTT {

	private MqttClient clienteMqtt;
	private String tema;
	
	public PublicadorMQTT(String tema) throws MqttException {
        clienteMqtt = new MqttClient("tcp://localhost:1883", MqttClient.generateClientId());
        clienteMqtt.connect();
        System.out.println("== INICIO Publicador == \n");
		this.tema = tema;
		System.out.println("== El topic es : " + tema + " ==\n");
	}
	
	public void publicar(String dato) throws MqttPersistenceException, MqttException {
        MqttMessage message = new MqttMessage();
        message.setPayload(dato.getBytes());
        System.out.println("== dato recibido por publicador: " + message + " ==\n");
        clienteMqtt.publish(tema, message);
	}
	
/*	
    public static void main(String[] args) throws MqttException {
        Scanner scanner = new Scanner(System.in);
   

        


        while (true) {


            System.out.print("Ingresar mensaje:\t");
            String selection = scanner.nextLine();

            if (selection.equals("salir")) {
            	System.out.println("entre en el if");
                break;
            } else {
                MqttMessage message = new MqttMessage();
                message.setPayload(selection.getBytes());
                client.publish(topic, message);
            }
        }

        client.disconnect();

        System.out.println("== END PUBLISHER ==");

    }
*/
}
