
package dto;

import entities.Activity;
import java.util.Date;

public class ActivityDTO {
    
    private String comment;
    private double duration;
    private Date exerciseDate;
    private Date timeOfDay;
    private String exerciseType;

    public ActivityDTO(Activity activityO) {
        this.comment = activityO.getComment();
        this.duration = activityO.getDuration();
        this.exerciseDate = activityO.getExerciseDate();
        this.timeOfDay = activityO.getTimeOfDay(); // needs to be changed a bit to timeOfDay only.
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
    
    

}
