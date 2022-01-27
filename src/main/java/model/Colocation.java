package model;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;


@Entity
@Table(name = "colocation")
public class Colocation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int colocId;
    private String nameColocation;
    private int userAdmin;

    @ElementCollection // 1
    @CollectionTable(name = "colocation_members", joinColumns = @JoinColumn(name = "id")) // 2
    @Column(name = "members") // 3
    private List<Integer> membersId;

    public Colocation(String nameColocation, int userAdmin) {
        super();
        this.nameColocation = nameColocation;
        this.userAdmin = userAdmin;
        membersId = new ArrayList<>();
    }

    public Colocation() {

    }

    public int getColocId() {
        return colocId;
    }

    public void setColocId(int colocId) {
        this.colocId = colocId;
    }

    public String getNameColocation() {
        return nameColocation;
    }

    public void setNameColocation(String nameColocation) {
        this.nameColocation = nameColocation;
    }

    public int getUserAdmin() {
        return userAdmin;
    }

    public void setUserAdmin(int userAdmin) {
        this.userAdmin = userAdmin;
    }

    public List<Integer> getMembersId() {
        return membersId;
    }

    public void setMembersId(List<Integer> membersId) {
        this.membersId = membersId;
    }
}
