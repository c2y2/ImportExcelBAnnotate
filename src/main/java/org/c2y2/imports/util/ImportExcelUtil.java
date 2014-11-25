package org.c2y2.imports.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.c2y2.imports.config.ImportFileColumn;
import org.c2y2.imports.config.ImportFileConfig;

/**
 * 
 * @name ImportExcelUtil
 * @description 批量导入工具类
 * @author c2y2 lexiangtaotao1988@gmail.com http://www.c2y2.org
 * @date 2014年11月25日 下午10:05:22
 * @version：1.0.0
 */
public class ImportExcelUtil {
	/**
	 * cell数据整数正则
	 */
	public static final String regexInt = "^\\-?[0-9]*\\.?0?$";
	/**
	 * cell数据双精度浮点数正则
	 */
	public static final String regexDouble = "^\\-?[0-9]*\\.[0-9]*$";
	
	
	/**
	 * 
	 * @name checkNumberType
	 * @todo (用于校验是否是整数类型，由于在poi中excel数值类型全部为double类型) 
	 * @conditions (这里描述这个方法适用条件 – 可选) 
	 * @step (这里描述这个方法业务步骤 – 可选) 
	 * @param cellValue
	 * @param valueType
	 * @return  boolean 
	 * @auth c2y2 2014年11月25日-上午11:01:34 
	 * @exception  
	 * @since  1.0.0
	 */
	public static boolean checkNumberType(double cellValue,String valueType){
		String intMatch = "^[0-9]+\\.0$";
		boolean result = false;
		if(valueType.equals(ImportFileConstants.VALUE_TYPE_INT)){
			result = String.valueOf(cellValue).matches(intMatch);
		}
		return result;
	}
	
	/**
	 * 
	 * @name getErrorFile
	 * @todo (生成错误文件) 
	 * @conditions (这里描述这个方法适用条件 – 可选) 
	 * @step (这里描述这个方法业务步骤 – 可选) 
	 * @param file
	 * @return  File 
	 * @auth c2y2 2014年11月25日-上午11:02:33 
	 * @exception  
	 * @since  1.0.0
	 */
	public static File getErrorFile(File file){
		String fileName = file.getName();
		String path = file.getPath();
		int lastFlag = path.lastIndexOf("\\");
		path = path.substring(0, lastFlag);
		String errorFilePath = path+File.separator+"error_"+fileName;
		File errorFile  = new File(errorFilePath);
		return errorFile;
	}
	
	/**
	 * 
	 * @name setErrorExcelHeader
	 * @todo (生成错误excel标题头) 
	 * @conditions (这里描述这个方法适用条件 – 可选) 
	 * @step (这里描述这个方法业务步骤 – 可选) 
	 * @param importFileConfig
	 * @param sourceSheet
	 * @param errorSheet  void 
	 * @auth c2y2 2014年11月25日-上午11:26:28 
	 * @exception  
	 * @since  1.0.0
	 */
	public static void setErrorExcelHeader(ImportFileConfig importFileConfig,Sheet sourceSheet,Sheet errorSheet){
		List<ImportFileColumn> importFileColumnList = importFileConfig.getColumnList();
		int colunmLength = importFileColumnList.size();
		int startRow = importFileConfig.getHeaderCount();
		Row headRow = sourceSheet.getRow(startRow-1);
		Row errorRowHead = errorSheet.createRow(startRow-1);
		for(int i=0;i<colunmLength;i++){
			Cell sourceHeadCell = headRow.getCell(i);
			Cell errorceHeadCell = errorRowHead.createCell(i);
			int cellType = sourceHeadCell.getCellType();
			switch (cellType) {
			case HSSFCell.CELL_TYPE_NUMERIC:
				errorceHeadCell.setCellValue(sourceHeadCell.getNumericCellValue());
				break;
			case HSSFCell.CELL_TYPE_BLANK:
				errorceHeadCell.setCellValue("");
				break;
			case HSSFCell.CELL_TYPE_BOOLEAN:
				errorceHeadCell.setCellValue(sourceHeadCell.getBooleanCellValue());
				break;
			case HSSFCell.CELL_TYPE_ERROR:
				errorceHeadCell.setCellValue(sourceHeadCell.getErrorCellValue());
				break;
			case HSSFCell.CELL_TYPE_FORMULA:
				errorceHeadCell.setCellValue(sourceHeadCell.getCellFormula());
				break;
			case HSSFCell.CELL_TYPE_STRING:
				errorceHeadCell.setCellValue(sourceHeadCell.getStringCellValue());
				break;
			default:
				break;
			}
		}
		errorRowHead.createCell(colunmLength).setCellValue("错误原因");
	}
	
	/**
	 * 
	 * @name validationCellEmpty
	 * @todo (校验单元格cell是否为空,返回true表示为空) 
	 * @conditions (这里描述这个方法适用条件 – 可选) 
	 * @step (这里描述这个方法业务步骤 – 可选) 
	 * @param cell
	 * @return  boolean 
	 * @auth c2y2 2014年11月25日-上午11:35:35 
	 * @exception  
	 * @since  1.0.0
	 */
	public static boolean validationCellEmpty(Cell cell){
		if(cell==null || cell.getCellType() == HSSFCell.CELL_TYPE_BLANK){
			return true;
		}else{
			int cellType = cell.getCellType();
			String value = "";
			switch (cellType) {
			case HSSFCell.CELL_TYPE_NUMERIC:
				value = String.valueOf(cell.getNumericCellValue());
				break;
			case HSSFCell.CELL_TYPE_BOOLEAN:
				value = String.valueOf(cell.getBooleanCellValue());
				break;
			case HSSFCell.CELL_TYPE_ERROR:
				value = String.valueOf(cell.getErrorCellValue());
				break;
			case HSSFCell.CELL_TYPE_FORMULA:
				value = String.valueOf(cell.getCellFormula());
				break;
			case HSSFCell.CELL_TYPE_STRING:
				value = String.valueOf(cell.getStringCellValue());
				break;
			default:
				break;
			}
			if(value==null || value.equals("")){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @name validation
	 * @todo (用于错误值校验) 
	 * @conditions (这里描述这个方法适用条件 – 可选) 
	 * @step (这里描述这个方法业务步骤 – 可选) 
	 * @param importFileColumn
	 * @param value
	 * @param stringBuilder
	 * @param cellError
	 * @return  boolean 
	 * @auth c2y2 2014年11月25日-下午3:19:55 
	 * @exception  
	 * @since  1.0.0
	 */
	public static boolean validation(ImportFileColumn importFileColumn,Object value,StringBuilder stringBuilder,Boolean cellError){
		if(value!=null){
			String valueString = String.valueOf(value);
			int colunmLength = valueString.length();
			int validationLength = importFileColumn.getMaxLength();
			Class<?> fieldType = importFileColumn.getFieldType();
			String regex = importFileColumn.getRegexExpress();
			String regexTitle = importFileColumn.getRegexTitle();
			String title = importFileColumn.getTitle();
			if(fieldType == String.class){
				if(!regex.equals("")){
					Pattern pattern = Pattern.compile(regex);
					Matcher matcher = pattern.matcher(valueString);
					if(!matcher.matches()){
						cellError = true;
						stringBuilder.append(title).append("值类型不合符要求");
						if(!regexTitle.equals("")){
							stringBuilder.append(title).append("正确格式如(").append(regexTitle).append(");");
						}
					}
					if(validationLength!=-1){
						if(colunmLength>validationLength){
							cellError=true;
							stringBuilder.append(title).append("长度不符合要求，请修改后重新导入;");
						}
					}
				}
			}else if(fieldType==Integer.class){
				Pattern intPattern = Pattern.compile(ImportExcelUtil.regexInt);
				if(!intPattern.matcher(valueString).matches()){
					cellError=true;
					stringBuilder.append(title).append("正确格式如(").append("1").append(");");
				}
				if(!regex.equals("")){
					Pattern pattern = Pattern.compile(regex);
					Matcher matcher = pattern.matcher(valueString);
					if(!matcher.matches()){
						stringBuilder.append(title).append("值类型不合符要求");
						if(!regexTitle.equals("")){
							cellError=true;
							stringBuilder.append(title).append("正确格式如(").append(regexTitle).append(");");
						}
					}
					if(validationLength!=-1){
						if(colunmLength>validationLength){
							cellError=true;
							stringBuilder.append(title).append("长度不符合要求，请修改后重新导入;");
						}
					}
				}
			}else if(fieldType==Double.class){
				Pattern doublePattern = Pattern.compile(ImportExcelUtil.regexDouble);
				if(!doublePattern.matcher(valueString).matches()){
					cellError=true;
					stringBuilder.append(title).append("正确格式如(").append("12.02").append(");");
				}
				if(!regex.equals("")){
					Pattern pattern = Pattern.compile(regex);
					Matcher matcher = pattern.matcher(valueString);
					if(!matcher.matches()){
						stringBuilder.append(title).append("值类型不合符要求");
						if(!regexTitle.equals("")){
							cellError=true;
							stringBuilder.append(title).append("正确格式如(").append(regexTitle).append(");");
						}
					}
					if(validationLength!=-1){
						if(colunmLength>validationLength){
							cellError=true;
							stringBuilder.append(title).append("长度不符合要求，请修改后重新导入;");
						}
					}
				}
			}else if(fieldType==Date.class){
				Pattern doublePattern = Pattern.compile(ImportExcelUtil.regexDouble);
				if(!doublePattern.matcher(valueString).matches()){
					cellError=true;
					stringBuilder.append(title).append("正确格式如(").append("2014-12-23").append(");");
				}
				if(!regex.equals("")){
					Pattern pattern = Pattern.compile(regex);
					Matcher matcher = pattern.matcher(valueString);
					if(!matcher.matches()){
						stringBuilder.append(title).append("值类型不合符要求");
						if(!regexTitle.equals("")){
							cellError=true;
							stringBuilder.append(title).append("正确格式如(").append(regexTitle).append(");");
						}
					}
					if(validationLength!=-1){
						if(colunmLength>validationLength){
							cellError=true;
							stringBuilder.append(title).append("长度不符合要求，请修改后重新导入;");
						}
					}
				}
			}else if(fieldType==Float.class){
				Pattern doublePattern = Pattern.compile(ImportExcelUtil.regexDouble);
				if(!doublePattern.matcher(valueString).matches()){
					cellError=true;
					stringBuilder.append(title).append("正确格式如(").append("12.02").append(");");
				}
				if(!regex.equals("")){
					Pattern pattern = Pattern.compile(regex);
					Matcher matcher = pattern.matcher(valueString);
					if(!matcher.matches()){
						stringBuilder.append(title).append("值类型不合符要求");
						if(!regexTitle.equals("")){
							cellError=true;
							stringBuilder.append(title).append("正确格式如(").append(regexTitle).append(");");
						}
					}
					if(validationLength!=-1){
						if(colunmLength>validationLength){
							cellError=true;
							stringBuilder.append(title).append("长度不符合要求，请修改后重新导入;");
						}
					}
				}
			}else if(fieldType==Long.class){
				Pattern intPattern = Pattern.compile(ImportExcelUtil.regexInt);
				if(!intPattern.matcher(valueString).matches()){
					cellError=true;
					stringBuilder.append(title).append("正确格式如(").append("1").append(");");
				}
				if(!regex.equals("")){
					Pattern pattern = Pattern.compile(regex);
					Matcher matcher = pattern.matcher(valueString);
					if(!matcher.matches()){
						stringBuilder.append(title).append("值类型不合符要求");
						if(!regexTitle.equals("")){
							cellError=true;
							stringBuilder.append(title).append("正确格式如(").append(regexTitle).append(");");
						}
					}
					if(validationLength!=-1){
						if(colunmLength>validationLength){
							cellError=true;
							stringBuilder.append(title).append("长度不符合要求，请修改后重新导入;");
						}
					}
				}
			}/*else if(fieldType==Boolean.class){//暂不校验
				
			}else if(fieldType==char.class){ 
				
			}else if(fieldType==byte.class){
				
			}*/
		return cellError.booleanValue();
		}
		return cellError.booleanValue();
	}
	
	/**
	 * 
	 * @name importErrorRow
	 * @todo (将存在错误数据的row到处到错误row) 
	 * @conditions (这里描述这个方法适用条件 – 可选) 
	 * @step (这里描述这个方法业务步骤 – 可选) 
	 * @param sourceRow
	 * @param errorRow
	 * @param errorMessage  void 
	 * @auth c2y2 2014年11月25日-下午3:26:28 
	 * @exception  
	 * @since  1.0.0
	 */
	public static void importErrorRow(Row sourceRow,Row errorRow,String errorMessage){
		int cellLength = sourceRow.getLastCellNum();
		for (int i = 0; i < cellLength; i++) {
			Cell sourceCell = sourceRow.getCell(i);
			Cell errorCell = errorRow.createCell(i);
			int cellType = sourceCell.getCellType();
			switch (cellType) {
			case HSSFCell.CELL_TYPE_NUMERIC:
				errorCell.setCellValue(sourceCell.getNumericCellValue());
				break;
			case HSSFCell.CELL_TYPE_BOOLEAN:
				errorCell.setCellValue(sourceCell.getBooleanCellValue());
				break;
			case HSSFCell.CELL_TYPE_ERROR:
				errorCell.setCellValue(sourceCell.getErrorCellValue());
				break;
			case HSSFCell.CELL_TYPE_FORMULA:
				errorCell.setCellValue(sourceCell.getCellFormula());
				break;
			case HSSFCell.CELL_TYPE_STRING:
				errorCell.setCellValue(sourceCell.getStringCellValue());
				break;
			case HSSFCell.CELL_TYPE_BLANK:
				errorCell.setCellValue("");
				break;
			default:
				break;
			}
		}
		errorRow.createCell(cellLength).setCellValue(errorMessage);
	}
	
	public static Object getCellValueByType(Cell cell,String type){
		Object value = null;
		int cellType = cell.getCellType();
		switch (type) {
		case ImportFileConstants.VALUE_TYPE_INT:{//数值型
			switch (cellType) {
				case HSSFCell.CELL_TYPE_NUMERIC:{//数值型
					if(HSSFDateUtil.isCellDateFormatted(cell)){
							java.sql.Date date = new java.sql.Date(cell.getDateCellValue().getTime());
							if(date!=null){
								try{
									value = Integer.parseInt(date.toString());
									return value;
								}catch(Exception e){}
							}
						}else{
							try{
								value = (int)cell.getNumericCellValue();
							}catch(Exception e){}
						}
					}
					break;
				case HSSFCell.CELL_TYPE_STRING:{//字符串型
					try{
							value = Integer.parseInt(cell.getStringCellValue());
						}catch(Exception e){}
					}
					break;
				case HSSFCell.CELL_TYPE_FORMULA:{//公式型
					try{
							value = Integer.parseInt(cell.getCellFormula());
						}catch(Exception e){}
					}
					break;
				case HSSFCell.CELL_TYPE_BLANK:{//空值型
					try{
							value = Integer.parseInt(cell.getStringCellValue());
						}catch(Exception e){}
					}
					break;
				default:
					break;
				}
			}
			break;
		case ImportFileConstants.VALUE_TYPE_STRING:{//字符串型
				switch (cellType) {
				case HSSFCell.CELL_TYPE_NUMERIC:{//数值型
					if(HSSFDateUtil.isCellDateFormatted(cell)){
							java.sql.Date date = new java.sql.Date(cell.getDateCellValue().getTime());
							if(date!=null){
								try{
									value =date.toString();
								}catch(Exception e){}
							}
						}else{
							try{
								java.text.DecimalFormat df = new java.text.DecimalFormat("#0");
								value = String.valueOf(df.format(cell.getNumericCellValue()));
							}catch(Exception e){}
						}
					}
					break;
				case HSSFCell.CELL_TYPE_STRING:{//字符串型
					try{
							value = cell.getStringCellValue();
						}catch(Exception e){}
					}
					break;
				case HSSFCell.CELL_TYPE_FORMULA:{//公式型
					try{
							value = cell.getCellFormula();
						}catch(Exception e){}
					}
					break;
				case HSSFCell.CELL_TYPE_BLANK:{//空值型
					try{
							value = cell.getStringCellValue();
						}catch(Exception e){}
					}
					break;
				case HSSFCell.CELL_TYPE_BOOLEAN:{//布尔型
					try{
						value = String.valueOf(cell.getBooleanCellValue());
						}catch(Exception e){}
					}
					break;
				case HSSFCell.CELL_TYPE_ERROR:{//错误型
					try{
							value = String.valueOf(cell.getErrorCellValue());
						}catch(Exception e){}
					}
					break;
				default:
					break;
				}
			}
			break;
		case ImportFileConstants.VALUE_TYPE_DOUBLE:{//公式型
			switch (cellType) {
					case HSSFCell.CELL_TYPE_NUMERIC:{//数值型
						if(HSSFDateUtil.isCellDateFormatted(cell)){
							java.sql.Date date = new java.sql.Date(cell.getDateCellValue().getTime());
							if(date!=null){
									try{
										value =Double.parseDouble(String.valueOf(date.getTime()));
									}catch(Exception e){}
								}
							}else{
								try{
									value = cell.getNumericCellValue();
								}catch(Exception e){}
							}
						}
						break;
					case HSSFCell.CELL_TYPE_STRING:{//字符串型
						try{
								value =Double.parseDouble(cell.getStringCellValue());
							}catch(Exception e){}
						}
						break;
					case HSSFCell.CELL_TYPE_FORMULA:{//公式型
						try{
								value = Double.parseDouble(cell.getCellFormula());
							}catch(Exception e){}
						}
						break;
					case HSSFCell.CELL_TYPE_BLANK:{//空值型
						try{
								value = Double.parseDouble(cell.getStringCellValue());
							}catch(Exception e){}
						}
						break;
					case HSSFCell.CELL_TYPE_BOOLEAN:{//布尔型
						try{
								value = Double.parseDouble(String.valueOf(cell.getBooleanCellValue()));
							}catch(Exception e){}
						}
						break;
					case HSSFCell.CELL_TYPE_ERROR:{//错误型
						try{
								value = Double.parseDouble(String.valueOf(cell.getErrorCellValue()));
							}catch(Exception e){}
						}
						break;
					default:
						break;
				}
			}
			break;
		case ImportFileConstants.VALUE_TYPE_DATE:{
			if(cell.getCellType()== HSSFCell.CELL_TYPE_NUMERIC){//数值型
				if(HSSFDateUtil.isCellDateFormatted(cell)){
					try{
						 value = new java.sql.Date(cell.getDateCellValue().getTime());
						}catch(Exception e){}
					}
				}else{
					try{
						try{
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							Date date = sdf.parse(cell.getStringCellValue());
							value = new java.sql.Date(date.getTime());
						}catch(Exception e){
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							Date date = sdf.parse(cell.getStringCellValue());
							value = new java.sql.Date(date.getTime());
						}
					}catch(Exception e){}
				}
			}
			break;
		default:
			break;
		}
		return value;
	}

	
}
