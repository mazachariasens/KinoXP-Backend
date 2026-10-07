package org.example.kinoxpbackend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity

public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String category;
    private int ageLimit;
    private int duration;

    public Movie (){
    }

    public Movie(String title, String category, int ageLimit, int duration){
        this.title = title;
        this.category = category;
        this.ageLimit = ageLimit;
        this.duration = duration;
    }

    public long getId(){
        return id;
    }
    public String getTitle(){
        return title;
    }
    public String getCategory(){
        return category;
    }
    public int getAgeLimit(){
        return ageLimit;
    }
    public int getDuration(){
        return duration;
    }

    public void setTitle(String title){
        this.title = title;
    }
    public void setCategory(String category){
        this.category = category;
    }
    public void setAgeLimit(int ageLimit){
        this.ageLimit = ageLimit;
    }
    public void setDuration(int duration){
        this.duration = duration;
    }

}
