package com.todolistapp.util;

// import java.io.IOException;
// import java.util.ArrayList;
// import java.util.List;

// import org.dhatim.fastexcel.reader.ReadableWorkbook;
// import org.dhatim.fastexcel.reader.Row;
// import org.dhatim.fastexcel.reader.Sheet;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
// import org.springframework.web.multipart.MultipartFile;

// import com.todolistapp.dto.request.TodoRequest;
// import com.todolistapp.models.entity.Item;
// import com.todolistapp.services.TodoService;

@Component
public class ExcelImporter {

    // @Autowired
    // private TodoService todoService;

    // public void importer(MultipartFile file, long id) throws IOException{
    // try(ReadableWorkbook wb = new ReadableWorkbook(file.getInputStream())){

    //     Sheet sheet = wb.getFirstSheet();
    //     List<Row> rows = sheet.read();
    //     rows.remove(0);
    //     rows.forEach(row -> {
    //         TodoRequest request = new TodoRequest();
    //         request.setTodo(row.getCell(1).getText());
    //         request.setUserId(id);
    //         String itemString = row.getCell(3).getText();

    //         List<Item> items = new ArrayList<>();
    //         String[] itemsSplit = itemString.split("\n");
    //         for (String i : itemsSplit) {
    //                 String[] itemSplit = i.split("\\|");
    //                 System.out.println(itemSplit);
    //                 Item item = new Item();
    //                 item.setItem(itemSplit[0]);
    //                 item.setCek(Boolean.parseBoolean(itemSplit[1]));
    //                 item.setImage(itemSplit[2]);
    //                 items.add(item);
    //         } 
    //         request.setItems(items);
    //         request.setImage(row.getCell(4).getText());
    //         todoService.createTodo(request);
    //     });
        
    //     }
    // }
    // public void importExcel(MultipartFile file, long id) throws IOException{
    //     importer(file, id);
    // }
}
