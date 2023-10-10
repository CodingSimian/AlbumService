package com.AlbumService.Model;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class Artist {

    ObjectId artist_id;

    String name;

    public ObjectId getArtist_id() {
        return artist_id;
    }

    public void setArtist_id(ObjectId artist_id) {
        this.artist_id = artist_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
