package com.project.mmj.service1.resources;


import com.project.mmj.service1.domain.model.entity.Message;
import com.project.mmj.service1.domain.service.MessageService;
import com.project.mmj.service1.domain.valueobject.MessageSigned;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Collection;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Neumann
 */
@RestController
@RequestMapping("/v1/service1")
public class Service1Controller {

   
    protected static final Logger logger = Logger.getLogger(Service1Controller.class.getName());
    
    protected MessageService messageService;

    @Autowired
    public Service1Controller(MessageService messageService){
        this.messageService = messageService;
    }
  
    
    @GetMapping("/")
    public ResponseEntity<MessageSigned> create() throws NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, InvalidKeyException{
       byte[] bytes = getBytes();
       String id = new String(Base64.getEncoder().encode(bytes));
      
       Message msg = new Message(id,"For sign service2");
       messageService.save(msg);
        
       ResponseEntity<MessageSigned> responseEntity = new RestTemplate().postForEntity("http://localhost:2229/v1/service2", msg, MessageSigned.class);
       MessageSigned ms = new MessageSigned(responseEntity.getBody().getId(),
                                             responseEntity.getBody().getValue(),
                                             responseEntity.getBody().getPublicKey(),
                                             "SHA256withECDSA"+" and resul for sign correct :"+String.valueOf(verifySign(responseEntity)).toUpperCase()); 
       
       
       messageService.deleteById(id);
        
       return ms != null ? new ResponseEntity<>(ms, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
   
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Message> findById(@PathVariable("id") String id) {
        logger.info(String.format("one-service findById() invoked:{} for {} ", messageService.getClass().getName(), id));
        
        Message message;
        try {
            message = messageService.findById(id).get();
        } catch (Exception ex) {
            logger.log(Level.WARNING, "Exception raised findById REST Call {0}", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return message != null ? new ResponseEntity<>(message, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @GetMapping("/all")
    public ResponseEntity<Collection<Message>> getAll() {
        Collection<Message> messages;
        try {
            messages = (Collection<Message>) messageService.findAll();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Exception raised findByName REST Call", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return messages.size() > 0 ? new ResponseEntity<>(messages, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") String id){
        messageService.deleteById(id);
    }
    
    private boolean verifySign(ResponseEntity<MessageSigned> responseEntity) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException{
        
        Signature ecdsaVerify = Signature.getInstance(responseEntity.getBody().getAlgorithm());
        KeyFactory kf = KeyFactory.getInstance("EC");

        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(responseEntity.getBody().getPublicKey()));

        KeyFactory keyFactory = KeyFactory.getInstance("EC");
        PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

        ecdsaVerify.initVerify(publicKey);
        
        byte[] decoded = Base64.getDecoder().decode(responseEntity.getBody().getId());
        
        ecdsaVerify.update(decoded);
        boolean result = ecdsaVerify.verify(Base64.getDecoder().decode(responseEntity.getBody().getValue()));

        return result;
    }
    
     protected byte[] getBytes(){
        byte[] b = new byte[200*1024];
        try {
            SecureRandom.getInstanceStrong().nextBytes(b);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Service1Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
         return b;
     }
    
    
    
     protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

}
