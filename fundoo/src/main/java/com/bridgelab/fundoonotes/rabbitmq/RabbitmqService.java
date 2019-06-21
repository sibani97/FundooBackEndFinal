package com.bridgelab.fundoonotes.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.bridgelabz.fundoonotes.elasticsearch.ElasticsearchServiceImpl;
import com.bridgelabz.fundoonotes.notes.model.Notes;
import com.bridgelabz.fundoonotes.user.model.Email;
import com.bridgelabz.fundoonotes.util.NoteContainer;
//import com.bridgelabz.fundoonotes.util.UserToken;

public class RabbitmqService {

@Autowired	
private AmqpTemplate amqplTemplate; 

@Autowired
private JavaMailSender javaMailSender;

//@Autowired
//private UserToken userToken;

@Autowired
private ElasticsearchServiceImpl elasticImpl;

@Value("{$exchange}")
private String exchange;

@Value("{$routingKey}")
private String routingKey;

private String elasticroutingKey="routingKey";

public void send(Email email)
{
	SimpleMailMessage mailMessage=new SimpleMailMessage();
	mailMessage.setTo(email.getTo());
//	mailMessage.setFrom(email.getFrom());
	mailMessage.setText(email.getBody());
	mailMessage.setSubject(email.getSubject());
	System.out.println("mail sending");
	javaMailSender.send(mailMessage);
	System.out.println("mail successfully sending");
	
}
//public void sendNote(NoteContainer noteContainer) {
//	 amqpTemplate.convertAndSend(exchange,elasticRountingKey, noteContainer);
//}
public void sendNote(NoteContainer notecontainer)
{
	amqplTemplate.convertAndSend(exchange,elasticroutingKey,notecontainer);
	}

@RabbitListener(queues = "elasticQueue")
public void operation(NoteContainer notecontainer) {
	System.out.println("operation");
	Notes note=notecontainer.getNote();
	switch(notecontainer.getNoteOperation()) {
	
	case CREATE:
		elasticImpl.create(note);
		break;
		
	case UPDATE :
		elasticImpl.updateNote(note);
		break;

	case DELETE :
		elasticImpl.deleteNote(note.getNoteId());
		break;
	}
}


}
