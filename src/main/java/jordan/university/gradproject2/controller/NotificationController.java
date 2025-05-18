//package jordan.university.gradproject2.controller;
//
//import jakarta.mail.MessagingException;
//import jordan.university.gradproject2.request.EmailRequest;
//import jordan.university.gradproject2.service.EmailService;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import static jordan.university.gradproject2.controller.NotificationController.NOTIFICATION_URL;
//
//@RestController
//@RequestMapping(NOTIFICATION_URL)
//public class NotificationController {
//    public final static String NOTIFICATION_URL = "/api/notification";
//    private final EmailService emailService;
//
//    public NotificationController(EmailService emailService) {
//        this.emailService = emailService;
//    }
//
//    @PostMapping("/email")
//    public String sendCustomEmail(@RequestBody EmailRequest request) {
//        try {
//            emailService.sendCustomEmail(request.getTo(), request.getSubject(), request.getBody(), request.isHtml());
//            return "Email sent successfully.";
//        } catch (MessagingException e) {
//            return "Failed to send email: " + e.getMessage();
//        }
//    }
//}
