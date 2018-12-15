package main;

import org.eclipse.paho.client.mqttv3.MqttException;

import reglasYSensor.Tipo;

public class Main {

	public static void main(String[] args) throws MqttException {
		
		SensorFisico sensorFisico = new SensorFisico(Tipo.Temperatura);
		
		sensorFisico.iniciar();
	}
}
