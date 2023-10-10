package com.AlbumService.Repository;

import com.AlbumService.Model.Album;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AlbumRepository extends MongoRepository<Album, ObjectId> {
    Album findAlbumByAlbumIdentification(String id); //TODO smarter solution to allow for duplicate album names

}
