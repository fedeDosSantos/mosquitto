package main;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.paho.client.mqttv3.MqttException;

import actuadores.Actuador;
import broker.PublicadorMQTT;
import dispositivos.Inteligente;
import estadoDispositivo.Estado;
import models.SensorModel;
import reglasYSensor.Regla;
import reglasYSensor.ReglaObserver;
import reglasYSensor.Sensor;
import reglasYSensor.Tipo;

public class SistemaDeGestion {

	public SistemaDeGestion(Tipo tipo, float medicion) throws MqttException {
		
		Sensor sensor = new SensorModel().buscarSensorPorTipo(tipo);
		List<ReglaObserver> reglas = sensor.getObservadores();
		
		Set<Actuador> actuadores = new HashSet<>();
		for (ReglaObserver regla : reglas) {
			actuadores.addAll(((Regla) regla).getActuadores());
		}
		
		Set<Inteligente> inteligentes = new HashSet<>();
		for(Actuador actuador : actuadores) {
			inteligentes.addAll(actuador.getDispositivos());
		}
		
		Map<Inteligente, Estado> estadosPorInteligente = new HashMap<>();
		
		for (Inteligente inteligente : inteligentes) {
			estadosPorInteligente.put(inteligente, inteligente.getEstado());
		}
		
		sensor.medir(medicion);
		
		for(Inteligente inteligente : inteligentes) {
			Estado estadoPrevioASensor = estadosPorInteligente.get(inteligente);
			Estado estadoTrasSensor = inteligente.getEstado();
			
			if(estadoPrevioASensor != estadoTrasSensor) {
				String topic = inteligente.getCaracteristicas().getNombreGenerico() + "/estado";
				PublicadorMQTT publicadorMQTT = new PublicadorMQTT(topic);
				publicadorMQTT.publicar("== " 
						+ inteligente.getCaracteristicas().getNombreGenerico() 
						+ " " + inteligente.getCaracteristicas().getEquipoConcreto()
						+ " cambio su estado de " + estadoPrevioASensor.getNombre()
						+ " a " + estadoTrasSensor.getNombre());
			}
		}
	}

}
