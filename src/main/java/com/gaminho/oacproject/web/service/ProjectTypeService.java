package com.gaminho.oacproject.web.service;

import com.gaminho.oacproject.dao.ProjectTypeRepository;
import com.gaminho.oacproject.error.exception.project.InvalidTypeException;
import com.gaminho.oacproject.error.exception.project.NoTypeException;
import com.gaminho.oacproject.error.exception.project.TypeNotFoundException;
import com.gaminho.oacproject.model.ProjectType;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProjectTypeService {

    @Autowired
    private ProjectTypeRepository typeRepository;

    public ProjectType getProjectTypeWithId(long id) {
        Optional<ProjectType> type = typeRepository.findById(id);
        if (!type.isPresent()) {
            throw new TypeNotFoundException(id);
        }
        return type.get();
    }

    public List<ProjectType> getAllProjectTypes(){
        List<ProjectType> types = typeRepository.findAll();
        if(types.isEmpty()) {
            throw new NoTypeException();
        } else {
            return types;
        }
    }

    @Transactional
    public ProjectType saveProjectType(ProjectType typeToSave) throws InvalidTypeException {
        if(ProjectType.isValid(typeToSave)) {
            return typeRepository.save(typeToSave);
        } else {
            throw new InvalidTypeException();
        }
    }

    @Transactional
    public ProjectType updateProjectType(long id, ProjectType updateType){
        Optional<ProjectType> oldType = typeRepository.findById(id);

        if(oldType.isPresent()) {
            ProjectType type = oldType.get();
            type.setLabel(updateType.getLabel());
            typeRepository.flush();
            return type;
        } else {
            throw new TypeNotFoundException(id);
        }
    }

    @Transactional
    public void deleteProjectType(long id){
        Optional<ProjectType> oldType = typeRepository.findById(id);
        if(oldType.isPresent()) {
            typeRepository.deleteById(id);
        } else {
            throw new TypeNotFoundException(id);
        }
    }

    @Transactional
    public void deleteAllTypes(){
        if( typeRepository.count() > 0) {
            typeRepository.deleteAll();
        } else {
            throw new NoTypeException();
        }
    }
}
