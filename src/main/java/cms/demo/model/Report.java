package cms.demo.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class Report {

    private LocalDate generationDate;

    //sales
    private double total_sales;
    private double net_sales;
    private int total_orders;
    private int total_items;
    //orders
    private int pending;
    private int processing;
    private int on_hold;
    private int completed;
    private int cancelled;
    private int refunded;
    private int failed;
    //products
    private int external;
    private int grouped;
    private int simple;
    private int variable;

}

