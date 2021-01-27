package cms.demo.mail;

import cms.demo.pdf.PdfService;
import cms.demo.service.WPService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MailController {

    private final MailService mailService;
    private final PdfService pdfService;
    private final WPService wpService;

    public MailController(MailService mailService, PdfService pdfService, WPService wpService){
        this.mailService=mailService;
        this.pdfService=pdfService;
        this.wpService=wpService;
    }

    @GetMapping("sendMail/{to}")
    public String sendMailTo(@PathVariable("to") String email) throws Exception {

        String body = "Raport w załączeniu";
        String subject = "Raport sprzedazy";
        pdfService.generatePdf(wpService.prepareReport());
        mailService.sendSimpleMail(email, subject,body);

        return "sprawdz maila";
    }
}
