基于注解的批量导入（excel到database）
以实现：
    注解生成配置（包括table配置，以及colunm配置）
    动态sql生成，错误文件生成，
使用方法
导入前准备：
1，将要需要导入的excel内容对应的excel进行注解说明
如：

```java
@ImportExcel(tableName ="user")
public class User {
	/**
	 * 姓名
	 */
	@ImportColumnConfig(title="姓名",columnName="user_name",regexTitle="",isNotNull=true)
	private String username;
	/**
	 * 性别
	 */
	@ImportColumnConfig(title="性别",columnName="sex",regexTitle="",isNotNull=true)
	private Integer sex;
	/**
	 * 年龄
	 */
	@ImportColumnConfig(title="年龄",columnName="age",regexTitle="",isNotNull=true)
	private Integer age;
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
}
```
2，然后调用AnnotateConfigHandler类中的handler类生成相关配置
如：
```java
ImportFileConfig importFileConfig = AnnotateConfigHandler.handler(User.class);
```
3，根据模板组织数据，然后进行批量导入
如：
```java
public class ExcelTest {
	public static void main(String[] args) {
		ImportFileConfig importFileConfig = AnnotateConfigHandler.handler(User.class);
		importFileConfig.setFile(new File("D:/Jfinal/Annotate/resource/user.xls"));
		ExcelParseToList<User> excelParseToList = new ExcelParseToList<User>();
		try {
			List<User> userLIst = excelParseToList.getListFromExcel(importFileConfig);
			for (User user : userLIst) {
				System.out.println(user.getUsername()+"=="+user.getAge()+"=="+user.getSex());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
```
todo待续。。。。