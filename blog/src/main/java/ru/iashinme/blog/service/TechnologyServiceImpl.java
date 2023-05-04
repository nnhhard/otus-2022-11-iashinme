package ru.iashinme.blog.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iashinme.blog.dto.TechnologyDto;
import ru.iashinme.blog.exception.ValidateException;
import ru.iashinme.blog.mapper.TechnologyMapper;
import ru.iashinme.blog.repository.TechnologyRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TechnologyServiceImpl implements TechnologyService {

    private final TechnologyRepository technologyRepository;
    private final TechnologyMapper technologyMapper;

    @Override
    @Transactional(readOnly = true)
    public boolean existsTechnology() {
        return technologyRepository.existsBy();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TechnologyDto> findAll() {
        return technologyMapper.entityToDto(technologyRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public TechnologyDto findById(Long id) {

        return technologyRepository.findById(id)
                .map(technologyMapper :: entityToDto)
                .orElseThrow(() -> new ValidateException("technology not found."));
    }

    @Override
    @Transactional
    public TechnologyDto save(TechnologyDto technology) {
        if(technology.getId() != null) {
            throw new ValidateException("You cannot set the id when creating!");
        }
        validate(technology);
        return technologyMapper.entityToDto(technologyRepository.save(technology.toEntity()));
    }

    @Override
    @Transactional
    public TechnologyDto edit(TechnologyDto technology) {
        validate(technology);

        if(technologyRepository.findById(technology.getId()).isEmpty()){
            throw new ValidateException("Technology with id = " + technology.getId() + " not found!");
        }

        return technologyMapper.entityToDto(technologyRepository.save(technology.toEntity()));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        technologyRepository.deleteById(id);
    }

    private void validate(TechnologyDto technology) {
        if (StringUtils.isBlank(technology.getName())) {
            throw new ValidateException("Technology name is null or empty!");
        }
    }
}