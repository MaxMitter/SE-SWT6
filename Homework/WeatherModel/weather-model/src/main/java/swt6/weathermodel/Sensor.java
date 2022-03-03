package swt6.weathermodel;

public interface Sensor {
    void start();
    void stop();

    boolean isRunning();
    void addWeatherStation(SensorListener listener);
    void removeWeatherStation(SensorListener listener);
}
