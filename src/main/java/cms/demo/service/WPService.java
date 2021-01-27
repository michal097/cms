package cms.demo.service;

import cms.demo.model.Report;
import lombok.Getter;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Service
public class WPService {


    private final String wpURL = "https://sklep.dan3k.pl/wp-json/wc/v3/reports/sales?consumer_key=ck_dc602a557a97e8f74f1d7a2567b7c4e897adf12f&consumer_secret=cs_aa56f79e25c7f8a1da2978947b41cb050cdffb17";

    public Report prepareReport() throws IOException {
        JSONArray json = new JSONArray(IOUtils.toString(new URL(wpURL), StandardCharsets.UTF_8));
        JSONObject jsonObject = json.getJSONObject(0);
        return Report.builder()
                .net_sales(Double.parseDouble((String)jsonObject.get("net_sales")))
                .total_sales(Double.parseDouble((String) jsonObject.get("total_sales")))
                .total_orders((Integer) jsonObject.get("total_orders"))
                .total_items((Integer) jsonObject.get("total_items"))
                .build();
    }
}
