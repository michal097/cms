package cms.demo.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Report {

    private double total_sales;
    private double net_sales;
    private int total_orders;
    private int total_items;

}

