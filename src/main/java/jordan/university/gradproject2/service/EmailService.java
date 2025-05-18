//package jordan.university.gradproject2.service;
//
//import jakarta.mail.MessagingException;
//import jakarta.mail.internet.MimeMessage;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.thymeleaf.context.Context;
//import org.thymeleaf.spring6.SpringTemplateEngine;
//
//import java.util.Map;
//
//public class EmailService {
//
//    @Autowired
//    private JavaMailSender mailSender;
//
//    @Autowired
//    private SpringTemplateEngine templateEngine;
//
//    public void sendHtmlTemplateEmail(String to, String subject, String templateName, Map<String, Object> model)
//            throws MessagingException {
//
//        Context context = new Context();
//        context.setVariables(model);
//
//        String htmlContent = templateEngine.process(templateName, context);
//
//        MimeMessage message = mailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message, true);
//
//        helper.setTo(to);
//        helper.setSubject(subject);
//        helper.setText(htmlContent, true); // true = HTML
//
//        mailSender.send(message);
//    }
//
//    public void sendCustomEmail(String to, String subject, String body, boolean isHtml) throws MessagingException {
//        MimeMessage message = mailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message, true);
//
//        helper.setTo(to);
//        helper.setSubject(subject);
//        helper.setText(body, isHtml); // HTML support if needed
//
//        mailSender.send(message);
//    }
//
//    //    Map<String, Object> model = Map.of(
////            "activityId", activityForm.getId(),
////            "user", activityForm.getAssignedUserName(),
////            "status", activityForm.getStatus(),
////            "updatedBy", currentUserService.getCurrentUser().getUsername(),
////            "updatedDate", LocalDateTime.now().toString()
////    );
////
////        emailService.sendHtmlTemplateEmail(
////                activityForm.getAssignedUserEmail(),
////                "Activity Status Updated",
////                "activity-status-update", // template name without .html
////    model
////        );
//
//}
