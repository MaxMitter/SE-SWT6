module temperature.sensor {
    requires weather.model;
    requires org.slf4j;
    provides swt6.weathermodel.SensorProvider
            with swt6.temperaturesensor.TemperatureSensorProvider;
}