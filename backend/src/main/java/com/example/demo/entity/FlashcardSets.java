package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;


@Entity //This will let Java know that this is an entity that we are going to map to a database table.
@Table(name = "flashcardset") //This is for the actual name of the database table we are mapping to the class.
public class FlashcardSets {

    @Id //This will map the primary key.
    @GeneratedValue(strategy = GenerationType.IDENTITY) //This will auto increment your primary key
    @Column(name = "id") //This is mapping the primary key to the id column in the table.
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "userID", nullable = false)
    @JsonIgnore
    private Users users;

/*    @OneToMany(mappedBy="FlashcardSets",cascade= CascadeType.ALL,orphanRemoval = true)
    private Set<Flashcards> cards = new HashSet<>();*/

    public FlashcardSets() {
    }

    public FlashcardSets(Users users, String name) {
        this.users = users;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "FlashcardSets{" +
                "id=" + id +
                ", users=" + users +
                ", name='" + name + '\'' +
                '}';
    }
}
