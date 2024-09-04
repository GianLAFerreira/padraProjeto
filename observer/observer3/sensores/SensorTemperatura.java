package observer3.sensores;

import observer3.estacao.Observer;

import java.util.ArrayList;
import java.util.List;

public class SensorTemperatura implements Sensor {

	@Override
	public double getMedicao() {
		return random.nextInt(20)+20;
	}
	
}
