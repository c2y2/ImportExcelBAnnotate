package org.c2y2.imports.core;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Random;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.c2y2.imports.config.ImportFileColumn;
import org.c2y2.imports.config.ImportFileConfig;
import org.c2y2.imports.util.ImportFileConstants;

/**
 * 
 * @name GenerateExcelTp
 * @description 模板生成类
 * @author c2y2 lexiangtaotao1988@gmail.com http://www.c2y2.org
 * @date 2014年11月25日 下午11:48:54
 * @version：1.0.0
 */
public class GenerateExcelTp {
	/**
	 * 
	 * @name generateExcelTp
	 * @todo (根据注解生成的配置，生成批量导入模板，配置必须存在) 
	 * @conditions (这里描述这个方法适用条件 – 可选) 
	 * @step (这里描述这个方法业务步骤 – 可选) 
	 * @param importFileConfig
	 * @return Workbook
	 * @throws Exception   
	 * @auth c2y2 2014年11月26日-上午12:11:26 
	 * @exception  
	 * @since  1.0.0
	 * @example
	 * workbook = GenerateExcelTp.generateExcelTp(importFileConfig);
	 * File tpFile = new File("e:/tpFile.xls");//注意生成的excel类型，请跟importFileConfig 中类型保持一致
	 * OutputStream os = new FileOutputStream(tpFile);
	 * workbook.write(os);
	 * os.flush();
	 */
	public static Workbook generateExcelTp(ImportFileConfig importFileConfig)throws Exception{
		if(importFileConfig==null ){
			throw new Exception("配置属性不全");
		}
		String fileType = importFileConfig.getFileType();
		Workbook workbook = null;
		if(fileType.equals(ImportFileConstants.FILE_TYPE_EXCEL2003)){
			workbook = new HSSFWorkbook();
		}else if(fileType.equals(ImportFileConstants.FILE_TYPE_EXCEL2007)){
			workbook = new XSSFWorkbook();
		}
		Sheet sheet = workbook.createSheet(importFileConfig.getSheetName());
		List<ImportFileColumn> importFileColumnList = importFileConfig.getColumnList();
		int colunmLength = importFileColumnList.size();
		Row headRow = sheet.createRow(0);
		Row expRow = sheet.createRow(1);
		for (int i = 0; i < colunmLength;i++) {
			ImportFileColumn importFileColumn = importFileColumnList.get(i);
			if(importFileColumn!=null){
				String title = importFileColumn.getTitle();
				Cell cell = headRow.createCell(i);
				cell.setCellValue(title);
				Class<?> fieldType = importFileColumn.getFieldType();
				if(fieldType == String.class){
					Cell expCell = expRow.createCell(i);
					expCell.setCellValue("所需要填写的内容");
				}else if(fieldType == Integer.class){
					Cell expCell = expRow.createCell(i);
					int cellContent = Random.class.newInstance().nextInt(100);
					expCell.setCellValue(cellContent);
				}
			}
		}
		return workbook;
	}
}
