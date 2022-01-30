package model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Score implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int score;
    @OneToOne
    private Colocation colocation;

    public Score(){}

    public Score(Colocation colocation, int score) {
        this.colocation = colocation;
        this.score = score;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Colocation getColocation() {
        return colocation;
    }

    public void setColocation(Colocation colocation) {
        this.colocation = colocation;
    }

    public void addToScore(int cost){
        this.score += cost;
    }

    public void decreaseScore(int cost){
        this.score -= cost;
    }

    @Override
    public String toString() {
        return "Score{" +
                "id=" + id +
                ", score=" + score +
                ", colocation=" + colocation +
                '}';
    }
}
