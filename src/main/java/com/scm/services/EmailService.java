package com.scm.services;

public interface EmailService {

    void sendEmail(String to, String subject, String boby);

    void sendEmailWithHtml();

    void sendEmailWithAttachment();

}
