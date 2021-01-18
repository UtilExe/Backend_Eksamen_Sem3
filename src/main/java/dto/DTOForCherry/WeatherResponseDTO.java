
package dto.DTOForCherry;


public class WeatherResponseDTO {
       private String temperature;
    private String skyText;
    private String humidity;
    private String windText;
    
    

    public WeatherResponseDTO(String temperature, String skyText, String humidity, String windText) {
        this.temperature = temperature;
        this.skyText = skyText;
        this.humidity = humidity;
        this.windText = windText;
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
