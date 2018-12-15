package main;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.paho.client.mqttv3.MqttException;

import broker.PublicadorMQTT;
import reglasYSensor.Tipo;

public class SensorFisico {
	private float medicion;
	private Tipo tipo;
	private PublicadorMQTT publicador;

	public SensorFisico(Tipo tipo) throws MqttException {
		this.tipo = tipo;
		publicador  = new PublicadorMQTT(tipo.name());
	}
	
	public void iniciar() {
		Timer temporizador = new Timer();
		TimerTask tareaARepetir = new TimerTask() {
			@Override
			public void run() {
				medicion = new Random().nextFloat()*50;
				System.out.println("Medicion : " + medicion);
				try {
					publicador.publicar(String.valueOf(medicion));
					new SistemaDeGestion(tipo, medicion);
				} catch (MqttException e) {
					e.printStackTrace();
				}
			}
		};
		temporizador.scheduleAtFixedRate(tareaARepetir, 1000L, 1000L * 10L);
	}
}
