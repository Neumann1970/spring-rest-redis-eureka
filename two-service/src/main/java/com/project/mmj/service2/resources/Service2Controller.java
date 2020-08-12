package com.project.mmj.service2.resources;


import com.project.mmj.service2.domain.model.entity.Message;
import com.project.mmj.service2.domain.service.MessageService;
import com.project.mmj.service2.domain.valueobject.MessageSigned;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.ECGenParameterSpec;
import java.util.Base64;
import java.util.logging.Logger;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Neumann
 */
@RestController
@RequestMapping("/v1/service2")
public class Service2Controller {

   
    protected static final Logger logger = Logger.getLogger(Service2Controller.class.getName());
    
    protected MessageService messageService;

    @Autowired
    public Service2Controller(MessageService messageService){
        this.messageService = messageService;
    }
    
    
    @PostMapping
    public ResponseEntity<MessageSigned> post(@RequestBody Message message) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, InvalidKeyException, SignatureException{
        Message forCheck = messageService.findById(message.getId()).get();
        String al = "SHA256withECDSA";
        byte[] decoded = Base64.getDecoder().decode(forCheck.getId());
        
        ImmutablePair<String, String> pair = generateKeysAndSigninig(decoded);
        forCheck.setId(message.getId());
        forCheck.setValue(pair.getValue());
        messageService.save(forCheck);

        Message newMessage = messageService.findById(message.getId()).get();
        MessageSigned ms = new MessageSigned(newMessage.getId(),newMessage.getValue(),pair.getKey(),al);
        
        return forCheck != null ? new ResponseEntity<>(ms, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    public ImmutablePair<String, String> generateKeysAndSigninig(byte[] decoded) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, SignatureException{
        
         //KEYS FOR SIGNING
        ECGenParameterSpec ecSpec = new ECGenParameterSpec("secp256k1");
        KeyPairGenerator g = KeyPairGenerator.getInstance("EC");
        g.initialize(ecSpec, new SecureRandom());
        KeyPair keypair = g.generateKeyPair();
        PublicKey publicKey = keypair.getPublic();
        PrivateKey privateKey = keypair.getPrivate();

        Signature ecdsaSign = Signature.getInstance("SHA256withECDSA");
        ecdsaSign.initSign(privateKey);
        ecdsaSign.update(decoded);
        byte[] signature = ecdsaSign.sign();

        return new ImmutablePair<>(
                Base64.getEncoder().encodeToString(publicKey.getEncoded()),
                Base64.getEncoder().encodeToString(signature));
    }
}
