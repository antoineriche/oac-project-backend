package com.gaminho.oacproject.web.service;

import com.gaminho.oacproject.dao.MCRepository;
import com.gaminho.oacproject.exception.MCException;
import com.gaminho.oacproject.exception.SongException;
import com.gaminho.oacproject.model.MC;
import com.gaminho.oacproject.model.Song;
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
            throw new MCException("No mc found for id " + id);
        }
        return mc.get();
    }

    public List<MC> getAllMCs(){
        List<MC> mcs = mcRepository.findAll();
        if(mcs.isEmpty()) {
            throw new MCException(MCException.NO_MC);
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
            throw new MCException(MCException.INVALID_MC);
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
            throw new MCException("MC with id " + id + " does not exist");
        }
    }

    @Transactional
    //FIXME try to parse msg as JSON but it's string
    public String deleteMC(long id){
        Optional<MC> oldMC = mcRepository.findById(id);
        if(oldMC.isPresent()) {
            mcRepository.deleteById(id);
            return "MC has been deleted";
        } else {
            return "MC with id " + id + " does not exist";
        }
    }

    @Transactional
    public String deleteAllMCs(){
        long count = mcRepository.count();
        if(count > 0) {
            mcRepository.deleteAll();
            return String.format("%d mcs have been deleted", count);
        } else {
            return "No mc in database";
        }
    }
}
