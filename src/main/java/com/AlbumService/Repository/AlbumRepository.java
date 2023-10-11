package com.AlbumService.Repository;

import com.AlbumService.Model.Album;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AlbumRepository extends MongoRepository<Album, ObjectId> {
    Album findAlbumByAlbumIdentification(String id); //TODO smarter solution to allow for duplicate album names

    Album findAlbumByAlbumIdentificationAndArtistName(String id, String name);

    List<Album> findAlbumsByArtistName(String name);

}
