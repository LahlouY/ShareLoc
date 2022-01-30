package model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;


@Entity
public class Colocation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToOne
    private User admin;
    @OneToMany
    private List<User> members;
    @OneToMany
    private List<Message> messages;

    public Colocation(){

    }

    public Colocation(String name, User admin){
        this.name = name;
        this.admin = admin;
        this.members = new ArrayList<>();
        members.add(admin);
        this.messages = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public void addMember(User user){
        this.members.add(user);
    }

    public void addMessage(Message message){
        this.messages.add(message);
    }

    public List<Message> getMessages(){
        return this.messages;
    }

    @Override
    public String toString() {
        return "Colocation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", admin=" + admin +
                ", members=" + members +
                '}';
    }
}
