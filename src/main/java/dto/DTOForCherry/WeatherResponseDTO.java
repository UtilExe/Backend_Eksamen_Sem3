
package dto.DTOForCherry;

import entities.WeatherInfo;


public class WeatherResponseDTO {
    private String temperature;
    private String skyText;
    private String humidity;
    private String windText;
    
    public WeatherResponseDTO(String humidity, String skyText, String temperature, String windText) {
        this.temperature = temperature;
        this.skyText = skyText;
        this.humidity = humidity;
        this.windText = windText;
    }
    
    public WeatherResponseDTO(WeatherInfo weatherInfo) {
        this.temperature = weatherInfo.getTemperature();
        this.skyText = weatherInfo.getSkyText();
        this.humidity = weatherInfo.getHumidity();
        this.windText = weatherInfo.getWindText();
    }

    public String getTemperature() {
        return temperature;
    }

    public String getSkyText() {
        return skyText;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getWindText() {
        return windText;
    }
}
