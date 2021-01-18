package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "activity")
public class Activity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name="user_name")
    private User user;
    
    @ManyToOne
    @JoinColumn(name="city_id")
    private CityInfo cityInfo;
    
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="weatherinfo_id")
    private WeatherInfo weatherInfo;
    
    @Column(name="exercise_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date exerciseDate;
    @Column(name="exercise_type", nullable = false)
    private String exerciseType;
    @Column(name="time_of_day", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date timeOfDay; // hm
    @Column(name="duration", nullable = false)
    private double duration;
    @Column(name="distance", nullable = false)
    private double distance;
    @Column(name="comment", nullable = true)
    private String comment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Activity() {
    }
    
    public Activity(Date exerciseDate, String exerciseType, Date timeOfDay, double duration, double distance, String comment) {
        this.exerciseDate = new Date();
        this.exerciseType = exerciseType;
        this.timeOfDay = new Date(); // hm(?). maybe a private method that handles the calculation(?).
        this.duration = duration;
        this.distance = distance;
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public CityInfo getCityInfo() {
        return cityInfo;
    }

    public WeatherInfo getWeatherInfo() {
        return weatherInfo;
    }

    public Date getExerciseDate() {
        return exerciseDate;
    }

    public String getExerciseType() {
        return exerciseType;
    }

    public Date getTimeOfDay() {
        return timeOfDay;
    }

    public double getDuration() {
        return duration;
    }

    public double getDistance() {
        return distance;
    }

    public String getComment() {
        return comment;
    }
    
    
    
    
}
