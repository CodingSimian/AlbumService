package com.AlbumService.Model;

import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class Song {
    //Since this application should really only present the song name, songs in the album in the right order, and the
    //album name this media object doesn't need all of the properties that are available in media service

    //Since different artists could have collaborated on different songs in the same album, and that album could be
    //published by two artist in of themselves, the artist names are needed.

    //All of the properties are decided by how data is presented from the mongodb that media service is using.
    @Id
    ObjectId song_id;

    String name;

    int album_id;

    int alb_order;


    List<Artist> artists;

    public ObjectId getSong_id() {
        return song_id;
    }

    public void setSong_id(ObjectId song_id) {
        this.song_id = song_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(int album_id) {
        this.album_id = album_id;
    }

    public int getAlb_order() {
        return alb_order;
    }

    public void setAlb_order(int alb_order) {
        this.alb_order = alb_order;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }
}
