package ru.iashinme.blog.service;

import ru.iashinme.blog.dto.TechnologyDto;

import java.util.List;

public interface TechnologyService {

    Long count();

    List<TechnologyDto> findAll();

    TechnologyDto findById(Long id);

    TechnologyDto save(TechnologyDto technology);

    TechnologyDto edit(TechnologyDto technology);

    void deleteById(Long id);
}