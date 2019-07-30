package kmihaly.mywebshop.controller;


import kmihaly.mywebshop.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendEmailController {

    @Autowired
    private EmailService emailService;

    @RequestMapping(value = "/SentMail", method = RequestMethod.GET)
    public void sendEMail(){
        try{
            emailService.sendMail("heymisi99@gmail.com","Test subj", "TestMassage");
        } catch (javax.mail.MessagingException e) {
            e.printStackTrace();
        }

    }
}
