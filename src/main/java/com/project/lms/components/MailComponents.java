package com.project.lms.components;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class MailComponents {

	private final JavaMailSender javaMailSender;
	
	public void sendMailTest() {
		
		SimpleMailMessage msg = new SimpleMailMessage();
		
		msg.setTo("choiyl930@naver.com");
		msg.setSubject("Signin");
		msg.setText("Nice to Meet you!");
		
		javaMailSender.send(msg);
		
	}
	
	public boolean sendMail(String mail, String subject, String text) {
		
		boolean result = false;
		
		MimeMessagePreparator msg = new MimeMessagePreparator() {
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				mimeMessageHelper.setTo(mail);
				mimeMessageHelper.setSubject(subject);
				mimeMessageHelper.setText(text, true);
			}
		};
		
		try {
			javaMailSender.send(msg);
			result = true;
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return result;
	}
	
}
