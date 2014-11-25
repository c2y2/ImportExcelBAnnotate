package org.c2y2.imports.config;

import java.io.File;
import java.util.List;

import org.c2y2.imports.util.ImportFileConstants;

/**
 * 
 * @name ImportFileConfig
 * @description 批量导入配置
 * @author c2y2 lexiangtaotao1988@gmail.com http://www.c2y2.org
 * @date 2014年11月24日 下午10:17:56
 * @version：1.0.0
 */
public class ImportFileConfig {
	private String id;//表id
	private String fileType;//文件类型
	private String insertSql;//插入语句
	private String validator;//验证存储过程
	private String seperator;//用于存放txt文件的分隔符
	private Integer headerCount;//用于设置excel表头
	private Class<?> clazz;//要转换成的对象
	private File file;//文件
	private File errorFile;//错误文件
	private List<ImportFileColumn> columnList;//列配置
	
	
	
	public File getErrorFile() {
		return errorFile;
	}
	public void setErrorFile(File errorFile) {
		this.errorFile = errorFile;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		String fileName = file.getName();
		String SUFFIX = fileName.substring(fileName.lastIndexOf(".")+1,fileName.length());
		System.out.println(SUFFIX);
		if(SUFFIX.equals(ImportFileConstants.EXCEL2003_SUFFIX)){
			this.fileType = ImportFileConstants.FILE_TYPE_EXCEL2003;
		}else{
			this.fileType = ImportFileConstants.FILE_TYPE_EXCEL2007;
		}
		this.file = file;
	}
	public Class<?> getClazz() {
		return clazz;
	}
	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}
	public Integer getHeaderCount() {
		return headerCount;
	}
	public void setHeaderCount(Integer headerCount) {
		this.headerCount = headerCount;
	}
	public ImportFileConfig() {
		super();
	}
	public String getSeperator() {
		return seperator;
	}
	public void setSeperator(String seperator) {
		this.seperator = seperator;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFileType() {
		return fileType;
	}
	public String getInsertSql() {
		return insertSql;
	}
	public void setInsertSql(String insertSql) {
		this.insertSql = insertSql;
	}
	public String getValidator() {
		return validator;
	}
	public void setValidator(String validator) {
		this.validator = validator;
	}
	public List<ImportFileColumn> getColumnList() {
		return columnList;
	}
	public void setColumnList(List<ImportFileColumn> columnList) {
		this.columnList = columnList;
	}
}
