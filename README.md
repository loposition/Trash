# 什么是trash
trash是一个规则解释器
是booleano 在JAVA下的替代品
目前都是根据我们的业务定制的
- 目前支持的符号 ()、 == 、 >= 、<= 、< 、> 以及在 一些特殊的关键字 included 、in 、and 、not等等
- 目前支持pojo的值嵌套 沿用booleano 的风格用:分割 例如 item:name

示例
  item:name >=1 


## 如何使用trash
trash 没有任何依赖 只要是java8.0 以上的版本请放心引入

### TrashExecutor
 这是trash的总执行入口 目前可以传入map 或者 一个普通的实现了getter setter 的普通对象
 
### @TrashObject 
 对于非map的普通对象 trash 默认使用类名(首字母小写)作为值得名字。相对普通的值对象定制化名字请在类上使用此注解
### @TrashField
 对于非map的普通对象，嵌套默认使用变量名，想要定制化可以在变量上添加TrashField注解 支持数组，来保证多对一关系。
### VarContextHolder
 对于长期不变量, 可以通过 VarContextHolder 的register 方法 并通过 '#' 加注册名获取
 > "#hello" in item:name
### 字典服务
 trash 本身内部不支持分词服务 可以通过外置的字典 只需要实现 textBelongsDict 接口 用$符号获取即可。可以返回命中词库以及命中的单词
 > "$违规词" in item:name     
 
 
 

