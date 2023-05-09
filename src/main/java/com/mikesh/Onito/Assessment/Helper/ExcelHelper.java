package com.mikesh.Onito.Assessment.Helper;

import com.mikesh.Onito.Assessment.Entities.Movies;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelHelper {

    public static boolean checkExcelFormat(MultipartFile file) {

        String contentType = file.getContentType();

        if (contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
            return true;
        } else {
            return false;
        }

    }

    public static List<Movies> convertExcelToList (InputStream inputStream){
        List<Movies> moviesList = new ArrayList<>();

        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

            XSSFSheet sheet = workbook.getSheet("movies");

            int rownumber = 0;

            Iterator<Row> rowIterator = sheet.iterator();

            while (rowIterator.hasNext()){
                Row row = rowIterator.next();
                if(rownumber ==0){
                    rownumber++;
                    continue;
                }
                Iterator<Cell> cells = row.iterator();
                int cellid = 0;
                Movies movies1 = new Movies();
                while (cells.hasNext()){
                    Cell cell = cells.next();
                    switch(cellid){
                        case 0 -> movies1.setTconst(cell.getStringCellValue());
                        case 1 -> movies1.setTitleType(cell.getStringCellValue());
                        case 2 -> movies1.setPrimaryTitle(cell.getStringCellValue());
                        case 3 -> movies1.setRuntimeMinutes((int) cell.getNumericCellValue());
                        case 4 -> movies1.setGenres(cell.getStringCellValue());
                    }
                    cellid++;
                }
                moviesList.add(movies1);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return moviesList;
    }
}
