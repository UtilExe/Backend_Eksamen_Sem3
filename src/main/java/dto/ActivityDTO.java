package dto;

import entities.Activity;
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
    private Date timeOfDay;
    private String exerciseType;

    public ActivityDTO(Activity activityO) {
        this.id = activityO.getId();
        this.comment = activityO.getComment();
        this.distance = activityO.getDistance();
        this.duration = activityO.getDuration();
        this.exerciseDate = activityO.getExerciseDate();
        this.timeOfDay = activityO.getTimeOfDay();
        this.exerciseType = activityO.getExerciseType();
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

    public Date getTimeOfDay() {
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
