package com.todolistapp.util;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.todolistapp.Constans;
import com.todolistapp.dto.response.TodoExportResponse;
import com.todolistapp.models.entity.Item;

@Component
public class TodoExcelExporter {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<TodoExportResponse> listTodo;
    
    public TodoExcelExporter(List<TodoExportResponse> listTodo){
        this.listTodo = listTodo;
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine(){
        sheet = workbook.createSheet("Todo");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        createCell(row, 0, "No", style);
        createCell(row, 1, "Todo", style);
        createCell(row, 2, "Username", style);
        createCell(row, 3, "Items", style);
        createCell(row, 4, "Image", style);
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style){
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if(value instanceof Long){
            cell.setCellValue((Long) value);
        }else if(value instanceof Boolean){
            cell.setCellValue((Boolean) value);
        }else{
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLine(){
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        style.setWrapText(true);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        long no = 1;
        for( TodoExportResponse todo : listTodo){
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            String cekGetTodo = todo.getTodo() == null? "-" : todo.getTodo();
            String cekGetTodoImage = todo.getImage() == null? "-" : Constans.userDirectory + File.separator +todo.getImage().getImage() ;

            createCell(row, columnCount++, no++, style);
            createCell(row, columnCount++, cekGetTodo, style);
            createCell(row, columnCount++, todo.getUsername(), style);
            List<Item> items = todo.getItems();
            String listItem = "";
            if(!todo.getItems().isEmpty()){
                for(Item item : items){
                String cekGetItem = item.getItem() == null? "-" : item.getItem(); 
                String cekIsCek = item.isCek() == true? "Selesai" : "Belum Selesai";
                String cekGetItemImage= "";
                if(item.getImage() == null){
                    cekGetItemImage = "-";
                }else{
                    cekGetItemImage = Constans.userDirectory + File.separator +item.getImage().getImage();
                }
                listItem += cekGetItem + "|" +  cekIsCek + "|" + cekGetItemImage + "\n";
            }
            }else{
                listItem = "-";
            }
            
            createCell(row, columnCount++ , listItem, style);
            createCell(row, columnCount++, cekGetTodoImage, style);
         }

        
    }

    public void export(HttpServletResponse response) throws IOException {
        try{
            writeHeaderLine();
            writeDataLine();
            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
            String currentDateTime = dateFormatter.format(new Date());
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + currentDateTime +".xlsx\"");
    
            ServletOutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            workbook.close();
    
            outputStream.close();
        }catch(IOException ex){
            throw new RuntimeException(ex.getMessage());
        }
        
    }
}
