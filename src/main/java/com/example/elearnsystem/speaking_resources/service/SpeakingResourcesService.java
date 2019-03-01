package com.example.elearnsystem.speaking_resources.service;

import com.example.elearnsystem.speaking_resources.domain.SpeakingResource;
import com.example.elearnsystem.speaking_resources.domain.TemporaryResource;
import com.example.elearnsystem.speaking_resources.domain.dto.SpeakingResourceDTO;
import com.example.elearnsystem.speaking_resources.repository.SpeakingResourcesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public class SpeakingResourcesService implements ISpeakingResourcesService{

    @Autowired
    private SpeakingResourcesRepository speakingResourcesRepository;
    @Override
    public void saveOne(SpeakingResource speakingResource) {
        speakingResourcesRepository.save(speakingResource);
    }

    @Override
    public void saveAll(Iterable<SpeakingResource> speakingResources) {
        speakingResourcesRepository.saveAll(speakingResources);
    }

    @Override
    public Page<SpeakingResource> findAll(String resourcesCategory, Pageable pageable) {
        Page<SpeakingResource> speakingResource = speakingResourcesRepository.findAll(pageable);
        return null;
    }

    @Override
    public Page<TemporaryResource> findAllTemporary(String resourcesCategory, Pageable pageable) {
        return null;
    }

    @Override
    public void update(Long id, SpeakingResource speakingResource) {

    }

    @Override
    public void updateTemporary(Long id, TemporaryResource temporaryResource) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void deleteAll(Long[] ids) {

    }
}
