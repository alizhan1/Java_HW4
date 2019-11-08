package space.harbour.java.hw3;

import javax.json.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import space.harbour.java.hw3.Jsonable;

public class Movie implements Jsonable {

    public String title, released;
    public Integer year, runtime;
    public List<String> genres;
    public List<Writer> writers;
    public List<Actor> actors;
    public Director director;
    public String plot;
    public List<String> languages;
    public List<String> countries;
    public String awards;
    public String poster;
    public List<Rating> ratings;

    public static class Rating {
        public String source;
        public String value;
        public Integer votes;

        public Rating(String source, String value, Integer votes) {
            this.source = source;
            this.value = value;
            this.votes = votes;
        }

        public Rating(String source, String value) {
            this.source = source;
            this.value = value;
            this.votes = null;
        }
    }

    public static class Director {
        String name;
        public Director(String name) {
            this.name = name;
        }
    }

    public static class Writer {
        String name;
        String type;
        public Writer(String name, String type) {
            this.name = name;
            this.type = type;
        }
    }

    public static class Actor {
        String name;
        String as;
        public Actor(String name, String as) {
            this.name = name;
            this.as = as;
        }
    }



    public Movie(){}

    public Movie(String title, Integer year, String released, Integer runtime, List<String> genres, Director director, List<Writer> writers, List<Actor> actors, String plot, List<String> languages, List<String> countries, String awards, String poster, List<Rating> ratings){
        this.title = title;
        this.year = year;
        this.released = released;
        this.runtime = runtime;
        this.genres = genres;
        this.director = director;
        this.writers = writers;
        this.actors = actors;
        this.plot = plot;
        this.languages = languages;
        this.countries = countries;
        this.awards = awards;
        this.poster = poster;
        this.ratings = ratings;
    }

    @Override
    public boolean writeJsonToFile(String filename) {
        JsonObject movie = Json.createObjectBuilder()
                .add("Title", title)
                .add("Year", year)
                .add("Released", released)
                .add("Runtime", runtime)
                .build();
        //System.out.println(movie.toString());
        try{
            FileOutputStream fo = new FileOutputStream(filename);
            fo.write(movie.toString().getBytes());
            System.out.println("Success");
        } catch (Exception e){
            return false;
        }

        return true;
    }

    @Override
    public Jsonable readFromJsonFile(String filename) {
        try {
            FileReader fr = new FileReader(filename);
            JsonReader jsonReader = Json.createReader(fr);
            JsonObject movieJson = jsonReader.readObject();

            JsonArray genres_json = movieJson.getJsonArray("Genres");
            List<String> genres = new ArrayList<String>();
            for(int i = 0; i < genres_json.size(); i++){
                genres.add(genres_json.getString(i));
            }

            JsonArray actors_json = movieJson.getJsonArray("Actors");
            List<Actor> actors = new ArrayList<Actor>();
            for(int i = 0; i < actors_json.size(); i++){
                actors.add((Actor)actors_json.getJsonObject(i));
            }

            JsonArray lang_json = movieJson.getJsonArray("Languages");
            List<String> lang = new ArrayList<String>();
            for(int i = 0; i < lang_json.size(); i++){
                lang.add(lang_json.getString(i));
            }

            JsonArray count_json = movieJson.getJsonArray("Countries");
            List<String> count = new ArrayList<String>();
            for(int i = 0; i < count_json.size(); i++){
                count.add(count_json.getString(i));
            }

            JsonArray ratings_json = movieJson.getJsonArray("Ratings");
            List<Rating> ratings = new ArrayList<Rating>();
            for(int i = 0; i < ratings_json.size(); i++){
                ratings.add((Rating)ratings_json.getJsonObject(i));
            }

            JsonArray writers_json = movieJson.getJsonArray("Writers");
            List<Writer> writers = new ArrayList<Writer>();
            for(int i = 0; i < writers_json.size(); i++){
                writers.add((Writer)writers_json.getJsonObject(i));
            }

            Movie movie = new Movie(
                    movieJson.getString("Title"),
                    movieJson.getInt("Year"),
                    movieJson.getString("Released"),
                    movieJson.getInt("Runtime"),
                    genres,
                    (Director) movieJson.getJsonObject("Director"),
                    writers,
                    actors,
                    movieJson.getString("Plot"),
                    lang,
                    count,
                    movieJson.getString("Awards"),
                    movieJson.getString("Poster"),
                    ratings
            );
            return movie;
        }
        catch (Exception e){
            return null;
        }
    }
    
    public static void main(String[] args){
        Movie movie2 = new Movie();
        movie2.readFromJsonFile("BladeRunner.json");
        System.out.println(movie2.title);
    }
}
