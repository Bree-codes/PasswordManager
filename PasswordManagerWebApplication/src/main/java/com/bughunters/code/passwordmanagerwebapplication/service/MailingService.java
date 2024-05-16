package com.bughunters.code.passwordmanagerwebapplication.service;

import com.bughunters.code.passwordmanagerwebapplication.entity.User;
import com.bughunters.code.passwordmanagerwebapplication.exceptions.MailSendingException;
import freemarker.core.ParseException;
import freemarker.template.*;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MailingService {

    private final JavaMailSender javaMailSender;

    private final Configuration configuration;

    private final VerificationCodeManagementService verificationCodeManagementService;

    public void sendMails(User user){

        MimeMessage mailMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mailMessage,
                    MimeMessageHelper.MULTIPART_MODE_MIXED, StandardCharsets.UTF_8.name());


            Map<String, Integer> emailDetails = new HashMap<>();
            emailDetails.put("verificationCode", verificationCodeManagementService.generateVerificationCode(user));

            /*Configuring the email freemarker template.*/
            Template emailTemplate = configuration.getTemplate("verificationMail.ftl");

            String email = FreeMarkerTemplateUtils.processTemplateIntoString(emailTemplate, emailDetails);

            /*feeding data to the email object.*/
            helper.setSubject("VERIFY YOUR EMAIL");
            helper.setText(email, true);
            helper.setFrom("password manager");
            helper.setTo(user.getEmail());

            /*Send the email.*/
            javaMailSender.send(mailMessage);

        } catch (MessagingException e) {

            throw new MailSendingException("Error configuring MimeMessage."); /*handle this exception 3*/
        } catch (TemplateNotFoundException e) {

            throw new MailSendingException("Error Loading the email ftl template.");
        } catch (ParseException e) {

            throw new MailSendingException("Error converting mail template to string.");
        } catch (MalformedTemplateNameException e) {

            throw new RuntimeException(e);
        } catch (IOException e) {

            throw new MailSendingException("Error Sending the email.");
        } catch (TemplateException e) {
            throw new MailSendingException("Template error");
        }
    }
}
