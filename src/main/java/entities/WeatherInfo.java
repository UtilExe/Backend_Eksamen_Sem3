
package entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "weatherinfo")
public class WeatherInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name="temperature")
    private String temperature;
    @Column(name="sky_text") // himmelstatus
    private String skyText;
    @Column(name="humidity") // fugtighed
    private String humidity;
    @Column(name="windText")
    private String windText;
    
    @OneToOne(mappedBy = "WeatherInfo")
    private Activity activity;

    public WeatherInfo(String temperature, String skyText, String humidity, String windText, Activity activity) {
        this.temperature = temperature;
        this.skyText = skyText;
        this.humidity = humidity;
        this.windText = windText;
        this.activity = activity;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WeatherInfo() {
    }

    
}
