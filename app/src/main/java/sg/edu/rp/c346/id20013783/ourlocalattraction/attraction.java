package sg.edu.rp.c346.id20013783.ourlocalattraction;

import java.io.Serializable;

public class attraction implements Serializable{
    private int id;
    private String title;
    private String description;
    private String location;
    private int stars;

    public attraction(String title, String description, String location ,int stars) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.stars = stars;
    }

    public attraction(int id, String title, String description, String location, int stars) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.stars = stars;
    }
    public int getId(){return id;}

    public attraction setID(int id){
        this.id=id;
        return this;
    }
    public String getTitle() {
        return title;
    }

    public attraction setTitle(String title) {
        this.title = title;
        return this;
    }
    public String getDescription() {
        return description;
    }

    public attraction setDescription(String description) {
        this.description = description;
        return this;
    }
    public String getLocation() {
        return location;
    }

    public attraction setLocation(String location) {
        this.location = location;
        return this;
    }
    public int getStars() {
        return stars;
    }

    public attraction setStars(int stars) {
        this.stars = stars;
        return this;
    }
    @Override
    public String toString() {
        String starsString = "";
        if (stars == 5){
            starsString = "* * * * *";
        } else if (stars == 4){
            starsString = "* * * *";
        } else if(stars ==3){
            starsString = "* * *";
        }else if(stars ==2){
            starsString = "* *";
        }
        else{
            starsString = "*";
        }


        return starsString;

    }

}

