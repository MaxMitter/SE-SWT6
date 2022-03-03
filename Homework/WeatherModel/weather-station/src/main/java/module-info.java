module weather.station {
    requires weather.model;
    requires temperature.sensor;
    requires org.slf4j;
    uses swt6.weathermodel.SensorProvider;
    exports swt6.weaterstation;
}