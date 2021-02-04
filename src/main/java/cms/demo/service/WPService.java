package cms.demo.service;

import cms.demo.model.Report;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

@Service
public class WPService {

    public JSONObject getReport(String url, int index) throws IOException {
        JSONArray json = new JSONArray(IOUtils.toString(new URL(url), StandardCharsets.UTF_8));
        return json.getJSONObject(index);
    }

    public Report prepareReport() throws IOException {

        String credentials = "?consumer_key=ck_dc602a557a97e8f74f1d7a2567b7c4e897adf12f&consumer_secret=cs_aa56f79e25c7f8a1da2978947b41cb050cdffb17";
        String prefix = "https://sklep.dan3k.pl/wp-json/wc/v3/reports";

        String sales = prefix + "/sales" + credentials;
        String orders = prefix + "/orders/totals" + credentials;
        String products = prefix + "/products/totals" + credentials;

        return Report.builder()
                .net_sales(Double.parseDouble((String) getReport(sales, 0).get("net_sales")))
                .total_sales(Double.parseDouble((String) getReport(sales, 0).get("total_sales")))
                .total_orders((Integer) getReport(sales, 0).get("total_orders"))
                .total_items((Integer) getReport(sales, 0).get("total_items"))
                .pending((Integer) getReport(orders, 0).get("total"))
                .processing((Integer) getReport(orders, 1).get("total"))
                .on_hold((Integer) getReport(orders, 2).get("total"))
                .completed((Integer) getReport(orders, 3).get("total"))
                .cancelled((Integer) getReport(orders, 4).get("total"))
                .refunded((Integer) getReport(orders, 5).get("total"))
                .failed((Integer) getReport(orders, 6).get("total"))
                .external((Integer) getReport(products, 0).get("total"))
                .grouped((Integer) getReport(products, 1).get("total"))
                .simple((Integer) getReport(products, 2).get("total"))
                .variable((Integer) getReport(products, 3).get("total"))
                .generationDate(LocalDate.now())
                .build();
    }
}
