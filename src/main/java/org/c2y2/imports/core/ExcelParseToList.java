package org.c2y2.imports.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.c2y2.imports.config.ImportFileColumn;
import org.c2y2.imports.config.ImportFileConfig;
import org.c2y2.imports.util.ImportExcelUtil;
import org.c2y2.imports.util.ImportFileConstants;
import org.c2y2.imports.util.LogFactory;


/**
 * 
 * @name ExcelParseToList
 * @description 
 * @author c2y2 lexiangtaotao1988@gmail.com http://www.c2y2.org
 * @date 2014年11月25日 上午11:24:35
 * @version：1.0.0
 */
public class ExcelParseToList<T> {
	public static org.apache.log4j.Logger logger =LogFactory.getLoger(ExcelParseToList.class); 
	/**
	 * 
	 * @name getListFromExcel
	 * @todo (用于将excel导入数据转换成相应实体list) 
	 * @conditions (这里描述这个方法适用条件 – 可选) 
	 * @step (这里描述这个方法业务步骤 – 可选) 
	 * @param importFileConfig
	 * @return
	 * @throws Exception  List<T> 
	 * @auth c2y2 2014年11月24日-下午11:34:50 
	 * @exception  
	 * @since  1.0.0
	 */
	public List<T> getListFromExcel(ImportFileConfig importFileConfig)throws Exception{
		if(importFileConfig.getFileType().equals(ImportFileConstants.FILE_TYPE_EXCEL2003)){
			return parseExcel2003(importFileConfig);
		}else if(importFileConfig.getFileType().equals(ImportFileConstants.FILE_TYPE_EXCEL2007)){
			return parseExcel2007(importFileConfig);
		}else{
			throw new Exception("导入的文件类型错误");
		}
	}
	
	/**
	 * 
	 * @name parseExcel2003
	 * @todo (转换excel2003) 
	 * @conditions (这里描述这个方法适用条件 – 可选) 
	 * @step (这里描述这个方法业务步骤 – 可选) 
	 * @param importFileConfig
	 * @return
	 * @throws Exception  List<T> 
	 * @auth c2y2 2014年11月25日-上午12:37:08 
	 * @exception  
	 * @since  1.0.0
	 */
	public List<T> parseExcel2003(ImportFileConfig importFileConfig)throws Exception{
		File file = importFileConfig.getFile();
		importFileConfig.setErrorFile(ImportExcelUtil.getErrorFile(file));
		InputStream ins = new FileInputStream(file);
		POIFSFileSystem poiFsFileSystem = new POIFSFileSystem(ins);
		importFileConfig.setErrorFile(ImportExcelUtil.getErrorFile(file));
		File errorFile = importFileConfig.getErrorFile();
		OutputStream os = new FileOutputStream(errorFile);
		HSSFWorkbook workbook2003 = new HSSFWorkbook(poiFsFileSystem);
		Sheet sheet = workbook2003.getSheetAt(0);
		HSSFWorkbook errorWorkbook = new HSSFWorkbook();
		Sheet errorSheet = errorWorkbook.createSheet(sheet.getSheetName());
		ImportExcelUtil.setErrorExcelHeader(importFileConfig, sheet, errorSheet);
		List<T> tList = new ArrayList<T>();
		try {
			long start = System.currentTimeMillis();
			logger.debug("excel2003执行开始");
			parse(importFileConfig,sheet,errorSheet,tList);
			workbook2003.write(os);
			long end = System.currentTimeMillis();
			logger.debug("excel2003执行结束");
			logger.debug("执行耗时"+(end-start)/1000+"s");
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			ins.close();
			os.close();
		}
		return tList;
	} 
	
	
	/**
	 * 
	 * @name parseExcel2007
	 * @todo (转换excel2007) 
	 * @conditions (这里描述这个方法适用条件 – 可选) 
	 * @step (这里描述这个方法业务步骤 – 可选) 
	 * @param importFileConfig
	 * @return
	 * @throws Exception  List<T> 
	 * @auth c2y2 2014年11月25日-上午12:37:37 
	 * @exception  
	 * @since  1.0.0
	 */
	public List<T> parseExcel2007(ImportFileConfig importFileConfig)throws Exception{
		File file = importFileConfig.getFile();
		InputStream ins = new FileInputStream(file);
		importFileConfig.setErrorFile(ImportExcelUtil.getErrorFile(file));
		XSSFWorkbook workbook2007 = new XSSFWorkbook(ins);
		XSSFSheet sheet = workbook2007.getSheetAt(0);
		File errorFile = importFileConfig.getErrorFile();
		OutputStream os = new FileOutputStream(errorFile);
		XSSFWorkbook errorWorkbook2007 = new XSSFWorkbook();
		XSSFSheet errorSheet = errorWorkbook2007.createSheet(sheet.getSheetName());
		ImportExcelUtil.setErrorExcelHeader(importFileConfig, sheet, errorSheet);
		List<T> tList = new ArrayList<T>();
		try {
			long start = System.currentTimeMillis();
			logger.debug("excel2007执行开始");
			parse(importFileConfig,sheet,errorSheet,tList);
			errorWorkbook2007.write(os);
			long end = System.currentTimeMillis();
			logger.debug("excel2007执行结束");
			logger.debug("执行耗时"+(end-start)/1000+"s");
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			ins.close();
			os.close();
		}
		return tList;
	} 
	
	
	/**
	 * 
	 * @name parse
	 * @todo (核心转换函数) 
	 * @conditions (这里描述这个方法适用条件 – 可选) 
	 * @step (这里描述这个方法业务步骤 – 可选) 
	 * @param importFileConfig
	 * @param sheet
	 * @param tList
	 * @throws Exception  void 
	 * @auth c2y2 2014年11月25日-上午12:37:52 
	 * @exception  
	 * @since  1.0.0
	 */
	@SuppressWarnings("unchecked")
	public void parse(ImportFileConfig importFileConfig,Sheet sourceSheet,Sheet errorSheet,List<T> tList)throws Exception{
		int rowLength = sourceSheet.getPhysicalNumberOfRows();//获取总行数
		int startRow = importFileConfig.getHeaderCount();
		Class<?> clazz = importFileConfig.getClazz();
		List<ImportFileColumn> importFileColumnList = importFileConfig.getColumnList();
		for(int i=startRow;i<rowLength;i++){
			Row row = sourceSheet.getRow(i);
			boolean rowErrorFlag = false;//false表示不存在错误，true表示存在错误
			StringBuilder stringBuilder = new StringBuilder();
			T t =  (T)clazz.newInstance();
			int colunmsLength = importFileColumnList.size();
			for(int j=0;j<colunmsLength;j++){
				Cell cell = row.getCell(j);
				ImportFileColumn importFileColumn = importFileColumnList.get(j);
				int cellType = cell.getCellType();
				Boolean cellError = false;//false表示不存在错误，true表示存在错误
				boolean isNotNull = importFileColumn.getNotNull();//true 表示不能为空，false 表示可以为空
				if(ImportExcelUtil.validationCellEmpty(cell) && isNotNull){
					stringBuilder.append(importFileColumn.getTitle()).append("值不能为空;");
					cellError = true;
					continue;
				}
				Object value = null;
				String methodName = "set"+importFileColumn.getField();
				Class<?> fieldType = importFileColumn.getFieldType();
				Method method = clazz.getMethod(methodName,fieldType);
				switch (cellType) {
				case HSSFCell.CELL_TYPE_NUMERIC:
					value =  cell.getNumericCellValue();
					if(value!=null){
						 cellError = ImportExcelUtil.validation(importFileColumn, value, stringBuilder, cellError);
						if(fieldType == String.class && !cellError.booleanValue()){
							method.invoke(t, String.valueOf(value));
						}else if(fieldType==Integer.class && !cellError.booleanValue()){
							java.text.DecimalFormat df = new java.text.DecimalFormat("#0");
							value = String.valueOf(df.format(value));
							method.invoke(t, Integer.parseInt((String) value));
						}else if(fieldType==Double.class && !cellError.booleanValue()){
							method.invoke(t, (Double)value);
						}else if(fieldType==Float.class && !cellError.booleanValue()){
							method.invoke(t, Float.parseFloat(String.valueOf(value)));
						}else if(fieldType==Long.class && !cellError.booleanValue()){
							java.text.DecimalFormat df = new java.text.DecimalFormat("#0");
							method.invoke(t, Long.parseLong(String.valueOf(df.format(value))));
						}else if(fieldType==Date.class && !cellError.booleanValue()){
							if(DateUtil.isCellDateFormatted(cell)){
								method.invoke(t, cell.getDateCellValue());
							}
						}else if(fieldType==Boolean.class && !cellError.booleanValue()){
							java.text.DecimalFormat df = new java.text.DecimalFormat("#0");
							method.invoke(t, Boolean.parseBoolean(String.valueOf(df.format(value))));
						}else if(fieldType==char.class){
							java.text.DecimalFormat df = new java.text.DecimalFormat("#0");
							method.invoke(t, (char)Integer.parseInt((String.valueOf(df.format(value)))));
						}else if(fieldType==byte.class){
							method.invoke(t, (byte)value);
						}
					}
					break;
				case HSSFCell.CELL_TYPE_BOOLEAN://boolean 不可能转换为data类型
					value =  cell.getBooleanCellValue();
					if(value!=null){
						cellError = ImportExcelUtil.validation(importFileColumn, value, stringBuilder, cellError);
						if(fieldType == String.class && !cellError.booleanValue()){
							method.invoke(t, String.valueOf(value));
						}else if(fieldType==Integer.class && !cellError.booleanValue()){
							method.invoke(t, Integer.parseInt(String.valueOf(value)));
						}else if(fieldType==Double.class && !cellError.booleanValue()){
							method.invoke(t, Double.parseDouble(String.valueOf(value)));
						}else if(fieldType==Float.class && !cellError.booleanValue()){
							method.invoke(t, Float.parseFloat(String.valueOf(value)));
						}else if(fieldType==Boolean.class && !cellError.booleanValue()){
							method.invoke(t, value);
						}else if(fieldType==Long.class && !cellError.booleanValue()){
							method.invoke(t, Long.parseLong(String.valueOf(value)));
						}else if(fieldType==char.class){
							method.invoke(t, (char)Integer.parseInt(String.valueOf(value)));
						}else if(fieldType==byte.class){
							method.invoke(t, (byte)value);
						}
					}
					break;
				case HSSFCell.CELL_TYPE_ERROR://错误类型暂不处理
					break;
				case HSSFCell.CELL_TYPE_FORMULA://公式类型暂不处理
					break;
				case HSSFCell.CELL_TYPE_STRING:
					value =  cell.getStringCellValue();
					if(value!=null){
						cellError =  ImportExcelUtil.validation(importFileColumn, value, stringBuilder, cellError);
						if(fieldType == String.class && !cellError.booleanValue()){
							method.invoke(t, value.toString());
						}else if(fieldType==Integer.class && !cellError.booleanValue()){
							 if(value.toString().contains(".")){
								 String tempValue = value.toString().substring(0, value.toString().indexOf("."));
								 method.invoke(t, Integer.parseInt(tempValue.toString()));
							 }else{
								 method.invoke(t, Integer.parseInt(value.toString()));
							 }
						}else if(fieldType==Double.class && !cellError.booleanValue()){
							method.invoke(t, Double.parseDouble(value.toString()));
						}else if(fieldType==Date.class && !cellError.booleanValue()){
							SimpleDateFormat sdf = new SimpleDateFormat(importFileColumn.getDateFormat());
							sdf.parse(value.toString());
						}else if(fieldType==Float.class && !cellError.booleanValue()){
							method.invoke(t, Float.parseFloat(value.toString()));
						}else if(fieldType==Boolean.class && !cellError.booleanValue()){
							method.invoke(t, Boolean.parseBoolean(value.toString()));
						}else if(fieldType==Long.class && !cellError.booleanValue()){
							java.text.DecimalFormat df = new java.text.DecimalFormat("#0");
							method.invoke(t, Long.parseLong(df.format(value)));
						}else if(fieldType==char.class){
							method.invoke(t, (char)Integer.parseInt(value.toString()));
						}else if(fieldType==byte.class){
							method.invoke(t, (byte)value);
						}
					}
					break;
				default:
					break;
				}
				if(cellError){
					rowErrorFlag = cellError.booleanValue();
				}
			}
			if(t!=null && !rowErrorFlag){
				tList.add(t);
			}else{
				Row errorRow = errorSheet.createRow(i);
				ImportExcelUtil.importErrorRow(row, errorRow, stringBuilder.toString());
			}
		}
	}
}
