package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.MemberService;
import com.itheima.PackageService;
import com.itheima.ReportService;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/report")
public class ReportController {
    @Reference
    private MemberService memberService;

    @Reference
    private PackageService packageService;

    @Reference
    private ReportService reportService;

    @GetMapping("/getMemberReport")
    public Result getMemberReport() {
        Map<String, Object> map = memberService.getMemberReport();
        return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS, map);
    }

    @GetMapping("/getPackageReport")
    public Result getPackageReport() {
        List<Map<String, Object>> list = packageService.getPackageReport();
        Map<String, Object> map = new HashMap<>();
        List<String> packageNames = new ArrayList<>();
        map.put("packageCount", list);
        for (Map<String, Object> map1 : list) {
            String name = (String) map1.get("name");
            packageNames.add(name);
        }
        map.put("packageNames", packageNames);
        return new Result(true, MessageConstant.GET_SETMEAL_LIST_SUCCESS, map);
    }

    @GetMapping("/getBusinessReportData")
    public Result getBusinessReportData() {
        Map<String, Object> map = null;
        try {
            map = reportService.getBusinessReportData();
            return new Result(true, MessageConstant.GET_BUSINESS_REPORT_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }
    }

    @RequestMapping("/exportBusinessReport")
    public Result exportBusinessReport(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> reportData = reportService.getBusinessReportData();
            String reportDate = (String) reportData.get("reportDate");
            Integer todayNewMember = (Integer) reportData.get("todayNewMember");
            Integer totalMember = (Integer) reportData.get("totalMember");
            Integer thisWeekNewMember = (Integer) reportData.get("thisWeekNewMember");
            Integer thisMonthNewMember = (Integer) reportData.get("thisMonthNewMember");
            Integer todayOrderNumber = (Integer) reportData.get("todayOrderNumber");
            Integer thisWeekOrderNumber = (Integer) reportData.get("thisWeekOrderNumber");
            Integer thisMonthOrderNumber = (Integer) reportData.get("thisMonthOrderNumber");
            Integer todayVisitsNumber = (Integer) reportData.get("todayVisitsNumber");
            Integer thisWeekVisitsNumber = (Integer) reportData.get("thisWeekVisitsNumber");
            Integer thisMonthVisitsNumber = (Integer) reportData.get("thisMonthVisitsNumber");
            List<Map> hotPackage = (List<Map>) reportData.get("hotPackage");
            //获得Excel模板文件绝对路径
            String temlateRealPath = request.getSession().getServletContext().getRealPath("template") + File.separator + "report_template.xlsx";
            //读取模板文件,创建excel表格对象
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(new File(temlateRealPath)));
            XSSFSheet sheet = workbook.getSheetAt(0);
            XSSFRow row = sheet.getRow(2);
            row.getCell(5).setCellValue(reportDate);
            row = sheet.getRow(4);
            row.getCell(5).setCellValue(todayNewMember);//新增会员数（本日）
            row.getCell(7).setCellValue(totalMember);//总会员数

            row = sheet.getRow(5);
            row.getCell(5).setCellValue(thisWeekNewMember);//本周新增会员数
            row.getCell(7).setCellValue(thisMonthNewMember);//本月新增会员数

            row = sheet.getRow(7);
            row.getCell(5).setCellValue(todayOrderNumber);//今日预约数
            row.getCell(7).setCellValue(todayVisitsNumber);//今日到诊数

            row = sheet.getRow(8);
            row.getCell(5).setCellValue(thisWeekOrderNumber);//本周预约数
            row.getCell(7).setCellValue(thisWeekVisitsNumber);//本周到诊数

            row = sheet.getRow(9);
            row.getCell(5).setCellValue(thisMonthOrderNumber);//本月预约数
            row.getCell(7).setCellValue(thisMonthVisitsNumber);//本月到诊数
            int rowNum = 12;
            for (Map map : hotPackage) {//热门套餐
                String name = (String) map.get("name");
                Long package_count = (Long) map.get("package_count");
                BigDecimal proportion = (BigDecimal) map.get("proportion");
                row = sheet.getRow(rowNum++);
                row.getCell(4).setCellValue(name);//套餐名称
                //todo debug
                row.getCell(5).setCellValue(package_count);//预约数量
                row.getCell(6).setCellValue(proportion.doubleValue());//占比
            }
            //通过输出流进行文件下载
            ServletOutputStream out = response.getOutputStream();
            String filename = "数据报表.xlsx";
            filename = URLEncoder.encode(filename, "UTF-8");
            //这个可以在tomcat中看到
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("content-Disposition", "attachment;filename=" + filename);
            workbook.write(out);
            out.flush();
            out.close();
            workbook.close();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_BUSINESS_REPORT_FAIL, null);
        }
    }
}
