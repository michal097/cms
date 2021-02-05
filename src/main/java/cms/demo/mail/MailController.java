package cms.demo.mail;

import cms.demo.model.MailModel;
import cms.demo.pdf.PdfService;
import cms.demo.repos.MailRepo;
import cms.demo.service.WPService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
public class MailController {

    private final MailService mailService;
    private final PdfService pdfService;
    private final WPService wpService;
    private final MailRepo mailRepo;

    public MailController(MailService mailService,
                          PdfService pdfService,
                          WPService wpService,
                          MailRepo mailRepo) {
        this.mailService = mailService;
        this.pdfService = pdfService;
        this.wpService = wpService;
        this.mailRepo = mailRepo;
    }

    @Value("${mail.body}")
    private  String body;
    @Value("${mail.subject}")
    private String subject;

    @GetMapping("sendMail/{to}")
    public String sendMailTo(@PathVariable("to") List<String> email) throws Exception {
        pdfService.generatePdf(wpService.prepareReport());
        for(String mail: email) {
            mailService.sendSimpleMail(mail, subject, body);
        }
        return "";
    }

    @GetMapping("addMeToCrone/{email}")
    public String addMeToCrone(@PathVariable List<String> email){
        for(String m : email) {
            if (mailRepo.findAll().stream().map(MailModel::getMailAdress)
                    .noneMatch(mail -> mail.equals(m))) {

                mailRepo.save(new MailModel(m));
            }
        }
        return "";
    }

    @GetMapping("removeFromCrone/{email}")
    public String removeMeFromCrone(@PathVariable String email){

        Optional<MailModel> mail = mailRepo.findAll()
                .stream()
                .filter(e->e.getMailAdress().equals(email))
                .findAny();

        mail.ifPresent(mailRepo::delete);

        return "";
    }


    @Async
    @Scheduled(cron = "0 49 18 * * *")
    public void setCron() throws Exception {

        pdfService.generatePdf(wpService.prepareReport());
        List<String> retrieveAllEmails = mailRepo.findAll()
                .stream()
                .map(MailModel::getMailAdress)
                .collect(Collectors.toList());

        for (String emails : retrieveAllEmails) {
            mailService.sendSimpleMail(emails, subject, body);
        }
    }
    @GetMapping("mails")
    public List<MailModel> mails(){
        return mailRepo.findAll();
    }
}
