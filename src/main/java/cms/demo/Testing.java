package cms.demo;


import cms.demo.model.Report;
import cms.demo.service.WPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class Testing {

    private final WPService wpService;

    @Autowired
    Testing(WPService wpService){
        this.wpService=wpService;
    }

    @GetMapping("get")
    public Report str() throws IOException {
        return wpService.prepareReport();
    }

}
