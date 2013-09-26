package com.enation.framework.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelUtil
{
  private Workbook wb;
  private POIFSFileSystem fs = null;

  public ExcelUtil() {
    this.wb = new HSSFWorkbook();
  }

  public void openModal(InputStream in) {
    try {
      this.fs = new POIFSFileSystem(in);
      this.wb = new HSSFWorkbook(this.fs);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void openModal(String modlePath)
  {
    InputStream In = null;
    FileInputStream File_in = null;
    try
    {
      this.fs = new POIFSFileSystem(new FileInputStream(modlePath));
      this.wb = new HSSFWorkbook(this.fs);
    } catch (IOException e) {
      System.out.println("------read ::" + modlePath + "-----ERROR!---");
      e.printStackTrace(System.err);
    }
  }

  public void writeToFile(String targetFile) {
    FileOutputStream fileOut = null;
    try {
      fileOut = new FileOutputStream(targetFile);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    try {
      this.wb.write(fileOut);
    } catch (IOException e1) {
      e1.printStackTrace();
    } finally {
      this.wb = null;
      try {
        fileOut.flush();
        fileOut.close();
      } catch (IOException e2) {
        e2.printStackTrace();
      }
    }
  }

  public void setCellMoney(int numRow, int numCol)
  {
    try
    {
      if (this.wb.getSheetAt(0) != null) {
        Sheet aSheet = this.wb.getSheetAt(0);
        DataFormat format = this.wb.createDataFormat();
        Row row = aSheet.getRow((short)numRow);
        Cell csCell = row.getCell((short)numCol);
        CellStyle style = this.wb.createCellStyle();
        style.setDataFormat(format.getFormat("0.00"));
        style.setBorderBottom((short)1);
        style.setBottomBorderColor((short)8);
        style.setBorderLeft((short)1);
        style.setLeftBorderColor((short)8);
        style.setBorderRight((short)1);
        style.setRightBorderColor((short)8);
        style.setBorderTop((short)1);
        style.setTopBorderColor((short)8);

        csCell.setCellStyle(style);
      }
    }
    catch (Exception e) {
      System.out.println("insertDataToExcel" + e);
    }
  }

  public void writeStringToCell(int numRow, int numCol, String strval) {
    try {
      strval = StringUtil.isEmpty(strval) ? "" : strval;
      if (this.wb.getSheetAt(0) != null) {
        Sheet aSheet = this.wb.getSheetAt(0);
        Row row = aSheet.getRow((short)numRow);
        if (row == null) {
          row = aSheet.createRow(numRow);
        }
        Cell csCell = row.getCell((short)numCol);
        if (csCell == null) {
          csCell = row.createCell(numCol);
        }
        csCell.setCellValue(strval);
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void insertStringToCell(int numRow, int numCol, String strval)
  {
    try
    {
      if (this.wb.getSheetAt(0) != null) {
        Sheet aSheet = this.wb.getSheetAt(0);
        Row row = aSheet.createRow((short)numRow);
        Cell csCell = row.createCell((short)numCol);

        csCell.setCellValue(strval);
      }
    }
    catch (Exception e)
    {
      System.out.println("insertDataToExcel" + e);
    }
  }

  public void writeFormula(int numRow, int numCol, String formula)
  {
    try {
      if (this.wb.getSheetAt(0) != null) {
        Sheet aSheet = this.wb.getSheetAt(0);
        Row row = aSheet.getRow((short)numRow);
        Cell csCell = row.getCell((short)numCol);
        csCell.setCellFormula(formula);
      }
    } catch (Exception e) {
      System.out.println("insertDataToExcel" + e);
    }
  }

  public void insertFormula(int numRow, int numCol, String formula) {
    try {
      if (this.wb.getSheetAt(0) != null) {
        Sheet aSheet = this.wb.getSheetAt(0);
        Row row = aSheet.createRow((short)numRow);
        Cell csCell = row.createCell((short)numCol);
        csCell.setCellFormula(formula);
      }
    } catch (Exception e) {
      System.out.println("insertDataToExcel" + e);
    }
  }

  public void writeNumToCell(int numRow, int numCol, Double num) {
    try {
      if (this.wb.getSheetAt(0) != null) {
        Sheet aSheet = this.wb.getSheetAt(0);
        Row row = aSheet.getRow((short)numRow);
        Cell csCell = row.getCell((short)numCol);
        csCell.setCellValue(num.doubleValue());
      }
    }
    catch (Exception e) {
      System.out.println("insertDataToExcel" + e);
    }
  }

  public void insertNumToCell(int numRow, int numCol, Double num)
  {
    try {
      if (this.wb.getSheetAt(0) != null) {
        Sheet aSheet = this.wb.getSheetAt(0);
        Row row = aSheet.createRow((short)numRow);
        Cell csCell = row.createCell((short)numCol);
        csCell.setCellValue(num.doubleValue());
      }
    }
    catch (Exception e) {
      System.out.println("insertDataToExcel" + e);
    }
  }

  public void replaceDataToCell(int numRow, int numCol, String temstr, String strval)
  {
    try
    {
      if (this.wb.getSheetAt(0) != null) {
        Sheet aSheet = this.wb.getSheetAt(0);
        Row row = aSheet.getRow((short)numRow);
        Cell csCell = row.getCell((short)numCol);
        String temp = "";
        temp = csCell.getStringCellValue();
        temp = temp.replaceAll(temstr, strval);
        csCell.setCellValue(temp);
      }
    }
    catch (Exception e) {
      System.out.println("insertDataToExcel" + e);
    }
  }

  public void insertDataToExcel(int numRow, Object[] object)
  {
    try
    {
      if (null != this.wb.getSheetAt(0)) {
        Sheet aSheet = this.wb.getSheetAt(0);
        Row row = aSheet.getRow((short)numRow);

        if (row == null) {
          row = aSheet.createRow((short)numRow);
        }

        for (int i = 0; i < object.length; i++) {
          Cell csCell = row.createCell((short)i);

          CellStyle style = this.wb.createCellStyle();
          style.setBorderBottom((short)1);
          style.setBottomBorderColor((short)8);
          style.setBorderLeft((short)1);
          style.setLeftBorderColor((short)8);
          style.setBorderRight((short)1);
          style.setRightBorderColor((short)8);
          style.setBorderTop((short)1);
          style.setTopBorderColor((short)8);

          csCell.setCellStyle(style);

          if (object[i] != null)
            csCell.setCellValue(object[i].toString());
          else {
            csCell.setCellValue("0");
          }
        }
      }
    }
    catch (Exception e)
    {
      System.out.println("insertDataToExcel" + e);
    }
  }

  public int getExcelLastCellNum()
  {
    int count = 0;
    if (null != this.wb.getSheetAt(0)) {
      Sheet aSheet = this.wb.getSheetAt(0);
      Row aRow = aSheet.getRow(0);
      count = aRow.getLastCellNum();
    }
    return count;
  }

  public String getOutputPath(String str) {
    String temp = "";
    if (str.lastIndexOf("/") != -1)
    {
      temp = str.substring(0, str.lastIndexOf("/"));
    } else if (str.lastIndexOf("\\") != -1)
    {
      temp = str.substring(0, str.lastIndexOf("\\"));
    } else if (str.lastIndexOf("\\\\") != -1)
    {
      temp = str.substring(0, str.lastIndexOf("\\\\"));
    }
    else {
      temp = str;
    }

    return temp;
  }

  public Row getRow(int sheetIndex, int rowIndex)
  {
    Sheet sheet = this.wb.getSheetAt(sheetIndex);
    return sheet.getRow(rowIndex);
  }

  public Sheet getSheet(int sheetIndex)
  {
    return this.wb.getSheetAt(sheetIndex);
  }

  public static Object getCellValue(Cell cell)
  {
    if (cell == null) return null;
    int celltype = cell.getCellType();

    if (celltype == 0) {
      return Double.valueOf(cell.getNumericCellValue());
    }
    if (celltype == 1) {
      String value = cell.getStringCellValue();
      if ("null".equals(value)) {
        value = "";
      }

      if (!StringUtil.isEmpty(value))
      {
        value = value.replaceAll(" ", "");
        value = value.replaceAll("  ", "");
      }

      return value;
    }

    if (celltype == 2) {
      String value = cell.getStringCellValue();
      if ("null".equals(value)) {
        value = "";
      }

      return value;
    }

    if (celltype == 3) {
      return "";
    }
    if (celltype == 5) {
      return "";
    }

    return "";
  }

  public static void main(String[] args)
  {
    int a = Double.valueOf("3.0").intValue();

    System.out.println(a);
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.framework.util.ExcelUtil
 * JD-Core Version:    0.6.1
 */