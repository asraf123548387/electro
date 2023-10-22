package com.shopping.electroshopping.service;

import com.shopping.electroshopping.dto.SalesReportDTO;
import com.shopping.electroshopping.model.order.Order;
import com.shopping.electroshopping.model.order.OrderItems;
import com.shopping.electroshopping.repository.OrderRepository;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.persistence.EntityNotFoundException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PdfGenerationService {
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private OrderRepository orderRepository;
    public byte[] generatePdf(Long id) throws Exception {
        // Create a Thymeleaf context and add any dynamic data needed in your template
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();

            // Create a Thymeleaf context and add the Order object as a variable
            Context context = new Context();
            context.setVariable("order", order);

            // Process the Thymeleaf template to generate HTML content
            String htmlContent = templateEngine.process("invoiceDownload", context);

            // Generate the PDF from the HTML content
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                ITextRenderer renderer = new ITextRenderer();
                renderer.setDocumentFromString(htmlContent);
                renderer.layout();
                renderer.createPDF(outputStream);

                return outputStream.toByteArray();
            }

        }
        else {
            // Handle the case where the order is not found (e.g., throw an exception or return an error response)
            throw new EntityNotFoundException("Order not found with ID: " + id);
        }
    }



    public byte[] generateSalesReportExcel(List<SalesReportDTO> salesData) throws IOException {
        // Create an instance of Workbook (e.g., Apache POI XSSFWorkbook for XLSX)
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Sales Report");

        // Create the header row
        XSSFRow headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Product");
        headerRow.createCell(1).setCellValue("Quantity Sold");
        headerRow.createCell(2).setCellValue("Total Sales");
        headerRow.createCell(3).setCellValue("Order Summary");

        // Fill in the data
        for (int i = 0; i < salesData.size(); i++) {
            SalesReportDTO sale = salesData.get(i);
            XSSFRow dataRow = sheet.createRow(i + 1);
            dataRow.createCell(0).setCellValue(sale.getProductName());
            dataRow.createCell(1).setCellValue(sale.getQuantitySold());
            dataRow.createCell(2).setCellValue(sale.getTotalSales());
            dataRow.createCell(3).setCellValue("Order ID: " + sale.getOrderId() + "\nOrder Date: " + sale.getOrderDate() + "\nOrder Total: " + sale.getOrderTotal());
        }

        // Create a ByteArrayOutputStream to store the Excel data
        ByteArrayOutputStream excelStream = new ByteArrayOutputStream();
        workbook.write(excelStream);
        workbook.close();

        return excelStream.toByteArray();
    }



    public List<SalesReportDTO> getSalesReportData() {

        List<SalesReportDTO> salesData = new ArrayList<>();
        List<Order> orders = orderRepository.findAll();

        for (Order order : orders) {
            for (OrderItems orderItem : order.getOrderItems()) {
                SalesReportDTO salesReport = new SalesReportDTO();

                // Add null check for product
                if (orderItem.getProduct() != null) {
                    salesReport.setProductName(orderItem.getProduct().getProductName());
                } else {
                    salesReport.setProductName("Unknown Product");
                }

                salesReport.setQuantitySold(orderItem.getQuantity());
                salesReport.setTotalSales(orderItem.getQuantity() * orderItem.getUnitPrice());


                salesReport.setOrderId(order.getOrderID());
                salesReport.setOrderDate(order.getOrderDate());
                salesReport.setOrderTotal(orderItem.getQuantity() * orderItem.getUnitPrice());



                salesData.add(salesReport);
            }
        }
        return salesData;
    }

    public byte[] generateSalesReportPdf(LocalDate startDate, LocalDate endDate) throws Exception {
        List<SalesReportDTO> salesData = new ArrayList<>();

        // Fetch orders within the specified date range
        List<Order> orders = orderRepository.findByOrderDateBetween(startDate, endDate);
        int totalQuantitySold = 0;
        double totalSales = 0.0;
        for (Order order : orders) {
            for (OrderItems orderItem : order.getOrderItems()) {
                SalesReportDTO salesReport = new SalesReportDTO();

                // Add null check for product
                if (orderItem.getProduct() != null) {
                    salesReport.setProductName(orderItem.getProduct().getProductName());
                } else {
                    salesReport.setProductName("Unknown Product");
                }

                salesReport.setQuantitySold(orderItem.getQuantity());
                salesReport.setTotalSales(orderItem.getQuantity() * orderItem.getUnitPrice());
                double itemTotalSales = orderItem.getQuantity() * orderItem.getUnitPrice();
                salesReport.setOrderId(order.getOrderID());
                salesReport.setOrderDate(order.getOrderDate());
                salesReport.setOrderTotal(orderItem.getQuantity() * orderItem.getUnitPrice());

                salesData.add(salesReport);
                totalQuantitySold += orderItem.getQuantity();
                totalSales += itemTotalSales;
            }
        }



        Context context = new Context();
        context.setVariable("salesData", salesData);
        context.setVariable("totalQuantitySold", totalQuantitySold);
        context.setVariable("totalSales", totalSales);
        // Fixed variable name
        String htmlContent = templateEngine.process("salesReport", context);

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(outputStream);

            return outputStream.toByteArray();
        }
    }

}
