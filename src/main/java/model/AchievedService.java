package model;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Entity
public class AchievedService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Service service;
    @OneToOne
    private User from;
    @OneToMany
    private List<User> to;
    private Timestamp date;
    @OneToOne
    private Image picture;
    private boolean validated;

    public AchievedService() {
    }

    public AchievedService(Service service, User from, List<User> to, Timestamp date, Image picture, boolean validated) {
        this.service = service;
        this.from = from;
        this.to = to;
        this.date = date;
        this.picture = picture;
        this.validated = validated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public List<User> getTo() {
        return to;
    }

    public void setTo(List<User> to) {
        this.to = to;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Image getPicture() {
        return picture;
    }

    public void setPicture(Image picture) {
        this.picture = picture;
    }

    public boolean isValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }

    @Override
    public String toString() {
        return "AchievedService{" +
                "id=" + id +
                ", service=" + service +
                ", from=" + from +
                ", to=" + to +
                ", date=" + date +
                ", picture=" + picture +
                ", validated=" + validated +
                '}';
    }
}
