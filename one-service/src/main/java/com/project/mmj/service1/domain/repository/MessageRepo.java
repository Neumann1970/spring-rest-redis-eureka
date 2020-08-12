/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.mmj.service1.domain.repository;

import com.project.mmj.service1.domain.model.entity.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Neumann
 */
@Repository
public interface MessageRepo extends CrudRepository<Message,String> {
    
}
