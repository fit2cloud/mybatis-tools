# 1. com.fit2cloud.tools.mybatis.SqlCriterionGeneratorPlugin


#### What's this
A mybatis generator plugin that generates a method to add sql formatted criterion to the [MBG](http://www.mybatis.org/generator/ "Mybatis Generator") generated criteria.

#### Why to use

Hanlde complex query conditons(e.g. including both 'and' and 'or') in a fluent way.

#### When to use
Consider some employees need to be filtered by name and other conditions, with the default criteria you may have to do this by tedious code.

```java
Criteria criteria1 = example.or();
criteria1.andCountryEqualTo("USA");
criteria1.andStateEqualTo("California");
criteria1.andCityEqualTo("LA");
criteria1.andMaleEqualTo("Female");

criteria1.andFirstNameLike("%Ava%");

Criteria criteria2 = example.or();
criteria2.andCountryEqualTo("USA");
criteria2.andStateEqualTo("California");
criteria2.andCityEqualTo("LA");
criteria2.andMaleEqualTo("Female");

criteria2.andLastNameLike("%Ava%");

mapper.selectByExamle(example);

/*the query will be 
select * from employee where (country = 'USA' and state = 'California' and city = 'LA' and male = 'Female'
and first_name like '%Ava%') or (country = 'USA' and state = 'California' and city = 'LA' and male = 'Female'
and last_name like '%Ava%')
*/
```

With this generator plugin a new method called andSqlCriterion will be generated in the Criteria class.

```java
public Criteria andSqlCriterion(String value) {
    addCriterion("(" + value + ")");
    return (Criteria) this;
}
```
Then above scenario can be implemented like this

```java
Criteria criteria = example.or();
criteria.andCountryEqualTo("USA");
criteria.andStateEqualTo("California");
criteria.andCityEqualTo("LA");
criteria.andMaleEqualTo("Female");

criteria.andSqlCriterion("first_name like '%Ava%' or last_name like '%Ava%'");

mapper.selectByExamle(example);

/*the query will be 
select * from employee where country = 'USA' and state = 'California' and city = 'LA' and male = 'Female'
and ( first_name like '%Ava%' or last_name like '%Ava%')
*/
```

##### How to use
Simply mvn package the project and add the plugin in your generatorConfig.xml(maybe some other file you named).
```xml
<plugin type="com.fit2cloud.tools.mybatis.SqlCriterionGeneratorPlugin" />
```
###### Reference
[Supplied Plugins by MBG](http://www.mybatis.org/generator/reference/plugins.html "Supplied Plugins")
