package model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Service implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private int cost;
    private int upVotes;
    private int downVotes;

    @OneToOne
    private Colocation colocation;
    @OneToOne
    private User created_by;
    @OneToMany
    private List<User> userWhoVoted;

    public Service() {
    }

    public Service(Colocation colocation, User created_by, String title, String description, int cost) {
        this.colocation = colocation;
        this.created_by = created_by;
        this.title = title;
        this.description = description;
        this.cost = cost;
        this.upVotes = 0;
        this.downVotes = 0;
        this.userWhoVoted = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Colocation getColocation() {
        return colocation;
    }

    public void setColocation(Colocation colocation) {
        this.colocation = colocation;
    }

    public int getUpVotes() {
        return this.upVotes;
    }

    public void upVote() {
        this.upVotes++;
    }

    public int getDownVotes() {
        return this.downVotes;
    }

    public void downVote() {
        this.downVotes++;
    }

    public boolean isAccepted() {
        return this.upVotes > this.downVotes;
    }

    public List<User> getUserWhoVoted() {
        return userWhoVoted;
    }

    public void setUserWhoVoted(List<User> voted_for) {
        this.userWhoVoted = voted_for;
    }

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", cost=" + cost +
                ", colocation=" + colocation +
                ", created_by=" + created_by +
                '}';
    }
}
