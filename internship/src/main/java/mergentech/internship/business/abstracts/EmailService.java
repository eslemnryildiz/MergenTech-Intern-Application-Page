package mergentech.internship.business.abstracts;

import mergentech.internship.business.concretes.EmailManager;
import mergentech.internship.business.requests.SendEmailRequest;
import mergentech.internship.business.responses.*;

import java.util.List;

public interface EmailService {

    String sendEmail(SendEmailRequest request, EmailManager.EmailType emailType);

}
