package swt6.temperaturesensor;

import swt6.weathermodel.Sensor;
import swt6.weathermodel.SensorProvider;

public class TemperatureSensorProvider implements SensorProvider {
    @Override
    public Sensor createSensor(String unit, int interval) {
        return new TemperatureSensor(unit, interval);
    }
}
