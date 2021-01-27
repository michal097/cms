package cms.demo.pdf;

import cms.demo.model.Report;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.w3c.tidy.Tidy;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import static org.thymeleaf.templatemode.TemplateMode.HTML;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;


@Service
public class PdfService {

    private static final Charset CHARSET = StandardCharsets.UTF_8;


    public void generatePdf(Report data) throws Exception {

        String outputFile = "src/main/resources/pdf/cms_reports.pdf";
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(HTML);
        templateResolver.setCharacterEncoding(String.valueOf(CHARSET));

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.addTemplateResolver(templateResolver);


        Context context = new Context();
        context.setVariable("data", data);

        String renderedHtmlContent = templateEngine.process("template", context);
        String xHtml = convertToXhtml(renderedHtmlContent);

        ITextRenderer renderer = new ITextRenderer();

        String baseUrl = "src/main/resources/";
        renderer.setDocumentFromString(xHtml, baseUrl);
        renderer.layout();


        OutputStream outputStream = new FileOutputStream(outputFile);
        renderer.createPDF(outputStream);
        outputStream.close();
    }


    private String convertToXhtml(String html) {
        Tidy tidy = new Tidy();
        tidy.setXHTML(true);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(html.getBytes(CHARSET));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        tidy.parseDOM(inputStream, outputStream);
        return outputStream.toString();
    }

}
