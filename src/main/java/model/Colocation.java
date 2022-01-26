package model;


import javax.persistence.*;

@Entity
@Table(name = "colocation")
public class Colocation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int colocId;
    private String nameColocation;
    private String userAdmin;

    public Colocation(String nameColocation, String userAdmin) {
        super();
        this.nameColocation = nameColocation;
        this.userAdmin = userAdmin;
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

    public String getUserAdmin() {
        return userAdmin;
    }

    public void setUserAdmin(String userAdmin) {
        this.userAdmin = userAdmin;
    }


}
