### 使用指南

1. jar包所在位置 /target/controllerData-1.0.0.jar
2. 通过maven命令把jar包制作成依赖，将在pom中引入jar包

```shell
mvn install:install-file -DgroupId=com.base -DartifactId=controllerData -Dversion=1.0 -Dpackaging=jar -Dfile=controllerData-1.0.0.jar
```

```java
<dependency>
	<groupId>com.base</groupId>
	<artifactId>controllerData</artifactId>
	<version>1.0.0</version>
</dependency>
```

### 使用jar包的方便之处

1. 如果接收前台大量参数无须封装entity，直接接收入库
2. 从数据库查询数据也同样无须封装entity，直接查询返回前台

### 代码中使用流程

```json
name:desk
type:1
amount:5
model_id:96e79218965eb72c92a549dd5a330112
domain_prefix:clone
//前台以form-data格式传递数据
```

```java
//继承BaseController可以获取所有前台form-data类型参数
public class DemoController extends BaseController {
    @RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity save() {
		PageData pd = this.getPageData();
		String name = pd.getString("name");
        int type = pd.getInt("type");
		return res;
	}
}
```

```java
<configuration>
	<typeAliases>
		<!--配置mybatis的返回值,这里添加生成实体类 -->
		<typeAlias type="com.base.controllerData.PageData" alias="pd"/>
	</typeAliases>
</configuration>
```

```java
<!--mapper 入参、出参都为 PageData对象-->
<select id="queryByName" parameterType="pd" resultType="pd">
		SELECT * 
		FROM desktop_pool
		WHERE name = #{name};
</select>
```

```java
//service 中以PageData 格式接收
public PageData queryById(PageData pd) {
	return (PageData) dao.findForObject("DeskMapper.queryById", pd);
}
```

