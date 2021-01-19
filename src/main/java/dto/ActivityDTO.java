package dto;

import entities.Activity;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;

public class ActivityDTO {

    private Long id;
    private String comment;
    private double duration;
    private double distance;
    private Date exerciseDate;
    private String timeOfDay;
    private String exerciseType;
    

    public ActivityDTO(Activity activityO) {
        this.comment = activityO.getComment();
        this.distance = activityO.getDistance();
        this.duration = activityO.getDuration();
        this.exerciseDate = new Date();
        this.timeOfDay = getCurrentTimeOfDay();
        this.exerciseType = activityO.getExerciseType();
    }

    private String getCurrentTimeOfDay() {
        Format f = new SimpleDateFormat("hh:mm");
        String currentTimeOfDay = f.format(new Date());
        return currentTimeOfDay; // currently is one hour behind as it returns in GMT, and we're in GMT +1
    }

    public ActivityDTO(String comment, double duration, double distance, String exerciseType) {
        this.comment = comment;
        this.duration = duration;
        this.distance = distance;
        this.exerciseDate = new Date();
        this.timeOfDay = getCurrentTimeOfDay();
        this.exerciseType = exerciseType;
    }
    
    

    public String getComment() {
        return comment;
    }

    public double getDuration() {
        return duration;
    }

    public Date getExerciseDate() {
        return exerciseDate;
    }

    public String getTimeOfDay() {
        return timeOfDay;
    }

    public String getExerciseType() {
        return exerciseType;
    }

    public Long getId() {
        return id;
    }

    public double getDistance() {
        return distance;
    }

}
