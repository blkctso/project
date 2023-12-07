package com.green.tonicbank;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class EmailController {
	@Autowired
	JavaMailSenderImpl mailSender;

	//�̸��� ����
	@PostMapping("/EmailAuth")
	@ResponseBody
	public ResponseEntity<String> emailAuth(String email) {
		System.out.println("���� ���� �̸��� �ּ� : " + email);
		
		if (!isValidEmail(email)) {
            return new ResponseEntity<>("INVALID_EMAIL", HttpStatus.BAD_REQUEST);
        }
		
		//������ ���� 111111 ~ 999999 (6�ڸ� ����)
		Random random = new Random();
		int checkNum = random.nextInt(888888)+111111;
		
		//�̸��� ���� ���
		String setFrom = "tonicbackmail@gmail.com"; //2�ܰ� ���� x, ���� �������� POP/IMAP ��� �������� POP/SMTP ��������� ����o
		String toMail = email;
		String title = "ȸ������ ���� �̸��� �Դϴ�.";
		String content = "���� �ڵ�� " + checkNum + " �Դϴ�." +
						 "<br>" +
						 "�ش� ���� �ڵ带 ���� �ڵ� Ȯ�ζ��� �����Ͽ� �ּ���.";
		
		try {
			MimeMessage message = mailSender.createMimeMessage(); //Spring���� �����ϴ� mail API
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setFrom(setFrom);
            helper.setTo(toMail);
            helper.setSubject(title);
            helper.setText(content, true);
            mailSender.send(message);
            System.out.println("�������� : " + checkNum);
            return new ResponseEntity<>(String.valueOf(checkNum), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("INVALID_EMAIL", HttpStatus.BAD_REQUEST);
		}
		
	}
	
	private boolean isValidEmail(String email) {
        // ������ �̸��� ��ȿ�� �˻�
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
	
}
