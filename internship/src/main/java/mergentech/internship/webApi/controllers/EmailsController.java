package mergentech.internship.webApi.controllers;

import lombok.AllArgsConstructor;
import mergentech.internship.business.abstracts.EmailService;
import mergentech.internship.business.concretes.EmailManager;
import mergentech.internship.business.requests.SendEmailRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/emails")
public class EmailsController {

    EmailService emailService;

    @PostMapping("/sendEmail")
    public ResponseEntity<String> sendEmail(@RequestBody SendEmailRequest email,
                                            @RequestParam EmailManager.EmailType emailType) {
        String response = emailService.sendEmail(email, emailType);
        return ResponseEntity.ok(response);
    }

}
