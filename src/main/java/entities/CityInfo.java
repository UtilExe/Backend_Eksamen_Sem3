
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
    
    @Column(name="city_name", length = 50)
    private String name; 
    // hm, return type of double ok?
    @Column(name="geocoordinates", length = 150)
    private String geocoordinates;
    @Column(name="municipality", length = 150)
    private String municipality; // kommune
    @Column(name="population")
    private int population;
    
    @OneToMany(mappedBy = "CityInfo", cascade = CascadeType.PERSIST)
    private List<Activity> activitys;
    
    public void addActivitys(Activity activityObj) {
        this.activitys.add(activityObj);
        if (activityObj != null) {
            activityObj.setCityInfo(this);
        }
    }

    public CityInfo() {
    }

    public CityInfo(String name, String geocoordinates, String municipality, int population) {
        this.name = name;
        this.geocoordinates = geocoordinates;
        this.municipality = municipality;
        this.population = population;
        this.activitys = new ArrayList();
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

    public String getGeocoordinates() {
        return geocoordinates;
    }

    public String getMunicipality() {
        return municipality;
    }

    public int getPopulation() {
        return population;
    }
        
    

}
