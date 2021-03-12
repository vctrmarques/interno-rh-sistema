package com.rhlinkcon.service;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {
	@Autowired
	private JavaMailSender javaMailSender;

	public void sendMail(String to, String subject, String body) {

		try {
			SimpleMailMessage mail = new SimpleMailMessage();

			//mail.setFrom(from);
			mail.setTo(to);
			mail.setSubject(subject);
			mail.setText(body);

			javaMailSender.send(mail);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendMailHtml(String from, String to, String subject, String body) {
		try {
			MimeMessage mail = javaMailSender.createMimeMessage();

			MimeMessageHelper helper = new MimeMessageHelper(mail);
			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body, true);

			javaMailSender.send(mail);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
