package com.AlbumService.Model;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection="albums")
public class Album {

    @Id
    ObjectId _id;

    String name;

    String albumIdentification;

    public String getAlbumIdentification() {
        return albumIdentification;
    }

    public void setAlbumIdentification(String albumIdentification) {
        this.albumIdentification = albumIdentification;
    }
//TODO add so that the writer for each song is represented in the album

    List<String> songs;

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getSongs() {
        return songs;
    }

    public void setSongs(List<String> songs) {
        this.songs = songs;
    }
}