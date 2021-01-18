
package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "cityinfo")
public class CityInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="city_name")
    private String name; 
    // hm, return type of double ok?
    @Column(name="geocoordinates")
    private double geocoordinates;
    @Column(name="municipality")
    private String municipality; // kommune
    @Column(name="population")
    private int population;
    
    @OneToMany(mappedBy = "CityInfo", cascade = CascadeType.PERSIST)
    private List<Activity> activitys = new ArrayList<>();
    
    public void addActivitys(Activity activityObj) {
        this.activitys.add(activityObj);
        if (activityObj != null) {
            activityObj.setCityInfo(this);
        }
    }

    public CityInfo() {
    }

    public CityInfo(String name, double geocoordinates, String municipality, int population) {
        this.name = name;
        this.geocoordinates = geocoordinates;
        this.municipality = municipality;
        this.population = population;
    }
    
    

    public void setId(Long id) {
        this.id = id;
    }
    
        public Long getId() {
        return id;
    }

    public List<Activity> getActivitys() {
        return activitys;
    }

    public void setActivitys(List<Activity> activitys) {
        this.activitys = activitys;
    }

    public String getName() {
        return name;
    }

    public double getGeocoordinates() {
        return geocoordinates;
    }

    public String getMunicipality() {
        return municipality;
    }

    public int getPopulation() {
        return population;
    }
        
    

}
