# Oracle

sysdba   =>  admin account 

show user    =>  show current user

conn /  as  sysdba   =>  connect again

alter user .... identified by tiger account unlock;  => unlock user account

### SQL select statement

sql not case sensitive 

sql can be multiple line or single line

keyword can not  separate

sub sentence need a newline 

indent can make it readability

支持计算表达式，四则运算

select *   sal*1.1 from emp;

select  name ,sal,   sal*1.1, sal+comm from emp;

空值不能参与四则运算

显示列别名

select   ename first_name, sal  from emp；

多列显示一列

select name||job from emp;

连接操作符

select name|| ‘is a’ || job from emp;

批量删除表

select 'drop table' || tname||';' from tab;

去重处理,  压缩重复的行

select distinct number from emp;   

过滤数据

select * from emp where number=10;

select * from emp where sal>=1000;

字符串大小写敏感, 必须使用单引号

select * from emp where name='abc';

多条件： 与

select * from emp where name='abc' and job='dev';

日期： 格式敏感, 必须用正确的格式

select  *  from emp where date='2021-09-21';

边界： between ... and

select  *  from emp where sal between 1000 and 2000;

类型过滤 ： in  (...., ....)   同一个类型， 数组

select *  from emp where job in ('dev', "sales");

查询null

select *  from emp where company is null;

查询 not null 

select *  from emp where company is not null;

模糊匹配： like  wildcard => %  任意多个字符

select *  from table where name like ‘A%’;

模糊匹配：第一个字符任意，第二个字符是O

select *  from table where name like ‘_O%’;

模糊匹配：第一个字符任意，第二个字符是_, 需要转义, escape 声明转义符

select *  from table where name like ‘_ _%’ escape '  ';

查询某行：  子查询， 对结果集检索

select  *  from (select rownum rn, emp.*  from emp)  where rn=2;

#### 多表联接：

笛卡尔联接：不带条件

select * from table1, table2；

n个表相连，必须有n-1个条件

等值联接：

select name, age from table1, table2  where emp.number = dept.number;

附加条件：

select name, age from table1, table2  where emp.number = dept.number and dept.job="tom";

外键联接：联接条件以外的行并到结果集，要显示哪个表就把+ 放在哪个表

select  *  from table1, table2  where emp.number（+） = dept.number;

不等联接： 等级查询

select  ename, grade  from table1, table2  where  sal between low and high;

自联接查询： 

select  e.ename, m.name  from table1 e, table1 m  where  e.mgrno=m.number;



SQL：

笛卡尔连接：

select * from table1 cross join table2  ；

自然连接：

select * from table1, table2  natural join；

两张表有多个同名列：

select * from table1 natural join table2  using (dept_number)；

两张表没有同名列：

select * from table1 t1  join table2 t2 on (t1.number= t2.number) ；

外键连接：

right outer  右外连接

select * from table1 t1 right outer  join table2 t2 using (dept_number)；

left outer  左外连接

select * from table1 t1 left outer  join table2 t2 using (dept_number)；

full outer  全外连接

select * from table1 t1 full outer  join table2 t2 using (dept_number)；





drop table

update table set name ='tom' where name ="tommy";









