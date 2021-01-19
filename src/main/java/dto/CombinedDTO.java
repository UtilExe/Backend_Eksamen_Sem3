
package dto;

public class CombinedDTO {
    
    private CityDTO cityDTO[];
    private WeatherDTO weatherDTO;

    public CombinedDTO(CityDTO[] cityDTO, WeatherDTO weatherDTO) {
        this.cityDTO = cityDTO;
        this.weatherDTO = weatherDTO;
    }
    
    public boolean isEmpty() {
        if(utils.Helper.responseEqualsNull(this.cityDTO) && utils.Helper.responseEqualsNull(this.weatherDTO)) {
            return true;
        } else {
            return false;
        }
    }

    public void getCityDTO() {
        for (CityDTO cityDTO1 : cityDTO) {
            System.out.println("Combined" + cityDTO1);
        }
    }
 
}
