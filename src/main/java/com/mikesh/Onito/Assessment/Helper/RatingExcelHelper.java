package com.mikesh.Onito.Assessment.Helper;

import com.mikesh.Onito.Assessment.Entities.Ratings;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RatingExcelHelper {
    public static List<Ratings> convertExcelToList (InputStream inputStream){
        List<Ratings> ratingList = new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);
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
                Ratings rating = new Ratings();
                while (cells.hasNext()){
                    Cell cell = cells.next();
                    switch(cellid){
                        case 0 -> rating.setTconst(cell.getStringCellValue());
                        case 1 -> rating.setAverageRating(cell.getNumericCellValue());
                        case 2 -> rating.setNumVotes((int)cell.getNumericCellValue());
                    }
                    cellid++;
                }
                ratingList.add(rating);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return ratingList;
    }
}
