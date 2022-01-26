package model;

import java.awt.*;
import java.util.Date;

public class AchievedService {
    private User from;
    private User to;
    private Date date;
    private Image picture;
    private boolean validate;

    public AchievedService(User from, User to, Date date, Image picture, boolean validate) {
        super();
        this.from = from;
        this.to = to;
        this.date = date;
        this.picture = picture;
        this.validate = validate;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public User getTo() {
        return to;
    }

    public void setTo(User to) {
        this.to = to;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Image getPicture() {
        return picture;
    }

    public void setPicture(Image picture) {
        this.picture = picture;
    }

    public boolean isValidate() {
        return validate;
    }

    public void setValidate(boolean validate) {
        this.validate = validate;
    }


}
