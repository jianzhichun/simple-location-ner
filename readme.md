# simple-location-ner
## 简单的地区命名实体识别

### 大致流程

构造前缀树：
<br/>
1.src/main/java/resources/generateLocationDat.py：使用python将网上拉下的location.json转成自定义的格式location.dat
<br/>
2.src/main/java/slner/trietree：前缀树实现，使用了泛型 可扩展，最大正向匹配MaximumMatching方法
<br/>
实体识别：
<br/>
src/main/java/slner/core/SimpleLocationRecognizer.java：因为区域可能重名，所以前缀树每个node存的是Location list，又得识别出实际信息（比如：没有省份得通过城市得到省份），因此这里处理较复杂
<br/>
Testcase:
<br/>
就测了一个方法,见src/test/java/
<br/>
见SimpleLocationRecognizer.java 可测试 
