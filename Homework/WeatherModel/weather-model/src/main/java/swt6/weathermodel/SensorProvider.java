package swt6.weathermodel;

public interface SensorProvider {
    Sensor createSensor(String unit, int interval);
}
