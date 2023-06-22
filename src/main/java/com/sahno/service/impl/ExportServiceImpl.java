package com.sahno.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sahno.model.entity.business.Dashboard;
import com.sahno.model.entity.business.DashboardRes;
import com.sahno.repository.DashboardRepo;
import com.sahno.service.ExportService;
import lombok.SneakyThrows;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class ExportServiceImpl implements ExportService {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private DashboardRepo dashboardRepo;


    @SneakyThrows
    @Override
    public byte[] export(long id) {
        Dashboard dashboard = dashboardRepo.findById(id).get();
        List<DashboardRes> results = dashboard.getResults();

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(dashboard.getName());

        HSSFRow headers = sheet.createRow(0);

        fillHeaders(headers, dashboard);
        fillData(results, sheet);

        return getFile(workbook);
    }

    private static byte[] getFile(HSSFWorkbook workbook) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            workbook.write(bos);
        } finally {
            bos.close();
        }
        return bos.toByteArray();
    }

    private static void fillData(List<DashboardRes> results, HSSFSheet sheet) throws JsonProcessingException {
        int rowIndex = 1;
        for (; rowIndex <= results.size(); rowIndex++) {
            int columnIndex = 0;
            HSSFRow row = sheet.createRow(rowIndex);
            JsonNode jsonTree = OBJECT_MAPPER.readTree(results.get(columnIndex).getResult());

            for (Iterator<JsonNode> it = jsonTree.elements(); it.hasNext(); ) {
                JsonNode jsonNode = it.next();

                row.createCell(columnIndex).setCellValue(jsonNode.asText());
                columnIndex++;
            }
        }
    }

    @SneakyThrows
    private void fillHeaders(HSSFRow headers, Dashboard dashboard) {
        Optional<DashboardRes> dashboardResOpt = dashboard.getResults().stream().findFirst();
        if (dashboardResOpt.isPresent()) {
            DashboardRes res = dashboardResOpt.get();

            JsonNode jsonNode = OBJECT_MAPPER.readTree(res.getResult());

            int i = 0;
            for (Iterator<String> it = jsonNode.fieldNames(); it.hasNext(); i++) {
                String key = it.next();
                headers.createCell(i).setCellValue(key);
            }
        }
    }
}
