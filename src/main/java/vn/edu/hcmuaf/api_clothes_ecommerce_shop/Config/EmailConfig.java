package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
@RequiredArgsConstructor
public class EmailConfig {
    private JavaMailSender javaMailSender;

    @Autowired
    public EmailConfig(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void send(String subject, String toEmail, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("OTP for " + subject);
        message.setText("Your OTP is: " + otp);
        javaMailSender.send(message);
    }

    public void sendNewPassword(String toEmail, String newPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Cung cấp lại mật khẩu mới");
        message.setText("Mật khẩu mới của bạn là: " + newPassword);
        javaMailSender.send(message);
    }

}
