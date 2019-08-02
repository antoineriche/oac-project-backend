package com.gaminho.oacproject.web.service;

import com.gaminho.oacproject.dao.MCRepository;
import com.gaminho.oacproject.exception.mc.InvalidMCException;
import com.gaminho.oacproject.exception.mc.MCNotFoundException;
import com.gaminho.oacproject.exception.mc.NoMCException;
import com.gaminho.oacproject.model.MC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MCService {

    @Autowired
    private MCRepository mcRepository;

    public MC getMCWithId(long id) {
        Optional<MC> mc = mcRepository.findById(id);
        if (!mc.isPresent()) {
            throw new MCNotFoundException(id);
        }
        return mc.get();
    }

    public List<MC> getAllMCs(){
        List<MC> mcs = mcRepository.findAll();
        if(mcs.isEmpty()) {
            throw new NoMCException();
        } else {
            return mcs;
        }
    }

    @Transactional
    public MC saveMC(MC mcToSave){
        if(MC.isValid(mcToSave)) {
            mcToSave.setAddingDate(new Date());
            // TODO what to do if there's already an id ?
            return mcRepository.save(mcToSave);
        } else {
            throw new InvalidMCException();
        }
    }

    @Transactional
    public MC updateMC(long id, MC updatedMC){
        Optional<MC> oldMC = mcRepository.findById(id);

        if(oldMC.isPresent()) {
            MC mc = oldMC.get();
            mc.setDescription(updatedMC.getDescription());
            mc.setImageUrl(updatedMC.getImageUrl());
            mc.setName(updatedMC.getName());
            mc.setPunchline(updatedMC.getPunchline());
            mcRepository.flush();
            return mc;
        } else {
            throw new MCNotFoundException(id);
        }
    }

    @Transactional
    public void deleteMC(long id){
        Optional<MC> oldMC = mcRepository.findById(id);
        if(oldMC.isPresent()) {
            mcRepository.deleteById(id);
        } else {
            throw  new MCNotFoundException(id);
        }
    }

    @Transactional
    public void deleteAllMCs(){
        if(mcRepository.count() > 0) {
            mcRepository.deleteAll();
        } else {
            throw new NoMCException();
        }
    }
}
