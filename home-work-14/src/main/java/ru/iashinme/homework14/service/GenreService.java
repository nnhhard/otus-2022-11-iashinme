package ru.iashinme.homework14.service;

import ru.iashinme.homework14.model.mongo.Genre;
import ru.iashinme.homework14.model.h2.GenreSQL;

public interface GenreService {

    GenreSQL convert(Genre genre);

    GenreSQL findByMongoId(String id);
}
