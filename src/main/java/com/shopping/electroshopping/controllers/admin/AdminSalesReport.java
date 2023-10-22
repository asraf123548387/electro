package com.shopping.electroshopping.controllers.admin;

import com.shopping.electroshopping.dto.SalesReportDTO;
import com.shopping.electroshopping.service.PdfGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminSalesReport {

    @Autowired
    private PdfGenerationService pdfGenerationService;

    @GetMapping("/salesReportPdf")
    public void downloadSalesReport(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            HttpServletResponse response) throws IOException {
        try {
            byte[] pdfBytes = pdfGenerationService.generateSalesReportPdf(startDate, endDate);

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=salesReport.pdf");

            response.getOutputStream().write(pdfBytes);
            response.getOutputStream().flush();
        } catch (Exception e) {
            // Handle any exceptions
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }





    @GetMapping("/salesReportExcel")
    public void downloadSalesReportExcel(HttpServletResponse response) throws IOException {
        try {


            List<SalesReportDTO> salesData = pdfGenerationService.getSalesReportData();

            byte[] excelBytes =pdfGenerationService.generateSalesReportExcel(salesData);


            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=salesReport.xlsx");
            // Write the PDF bytes to the response output stream
            response.getOutputStream().write(excelBytes);
            response.getOutputStream().flush();

        } catch (Exception e) {
            // Handle any exceptions
            e.printStackTrace();
            // Redirect to an error page or provide an error response
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
