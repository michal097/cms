package cms.demo;


import cms.demo.model.MailModel;
import cms.demo.model.Report;
import cms.demo.repos.MailRepo;
import cms.demo.service.WPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class Testing {

    private final WPService wpService;
    private final MailRepo mailRepo;

    @Autowired
    Testing(WPService wpService, MailRepo mailRepo) {
        this.wpService = wpService;
        this.mailRepo = mailRepo;
    }

    @GetMapping("get")
    public Report str() throws IOException {
        return wpService.prepareReport();
    }

    @GetMapping("mails")
    public List<String> mails(){
        return mailRepo.findAll().stream().map(MailModel::getMailAdress).collect(Collectors.toList());
    }
}
