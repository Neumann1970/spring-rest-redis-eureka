/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.mmj.service2.domain.service;

import com.project.mmj.service2.domain.model.entity.Message;
import com.project.mmj.service2.domain.repository.MessageRepo;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Neumann
 */
@Service("messageService")
public class MessageService{
    
    protected MessageRepo messageRepo;
    
    @Autowired
    public MessageService(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }


    public void save(Message message)  {
         messageRepo.save(message);
    }

   
    public Optional<Message> findById(String messageId) {
        return messageRepo.findById(messageId);
           }

    public void deleteById(String id) {
        messageRepo.deleteById(id);
    }
    
    public Iterable<Message> findAll(){
        return messageRepo.findAll();
    }
}
