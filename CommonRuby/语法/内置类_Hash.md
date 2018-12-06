## 散列
## Hash的创建
```
# 1. 使用{}
h1 = {"a"=>"b", "c"=>"d"}
p h1["a"]    #=> "b"

h2 = {a: "b", c: "d"}
p h2    #=> {:a=>"b", :c=>"d"}

# 2. 使用 Hash.new
h1 = Hash.new
h2 = Hash.new("")
p h1["not_key"]    #=> nil
p h2["not_key"]    #=> ""
```

## 可以充当key的类型
1. 字符串 String
2. 数值 Numberic
3. 符号 Symbol
4. 日期 Date

## 常见用法
```
# 1. 获取与赋值，[]下标或者store或者fetch方法
h = Hash.new
h["R"] = "Ruby"
p h["R"]    #=> "Ruby"

h = Hash.new
h.store("R", "Ruby")
p h.fetch("R")    #=> "Ruby"
p h.fetch("R", "NULL")  # =>NULL 第二个参数代表为空时默认值

# 2. 全部keys, values, 转arr
h = {"a"=>"b", "c"=>"d"}
p h.keys    #=> ["a", "c"]
p h.values  #=> ["b", "d"]
p h.to_a    #=> [["a", "b"], ["c", "d"]]

# 3. 是否存在key value
h = {"a" => "b", "c" => "d"}
p h.key?("a")       #=> true
p h.has_key?("a")   #=> true
p h.include?("z")   #=> false
p h.member?("z")    #=> false
h = {"a"=>"b", "c"=>"d"}
p h.value?("b")     #=> true
p h.has_value?("z") #=> false

# 4. 查找大小
h = {"a"=>"b", "c"=>"d"}
p h.length    #=> 2
p h.size      #=> 2
p h.empty?    #=> false

#5. 删除
h = {"R"=>"Ruby"}
p h["R"]    #=> "Ruby"
h.delete("R")
p h["R"]    #=> nil

h = {"R"=>"Ruby", "P"=>"Perl"}
p h.delete_if{|key, value| key == "L"}  #=> {"R"=>"Ruby", "P"=>"Perl"}
p h.reject!{|key, value| key == "L"}    #=> nil

#6. 初始化
h = {"a"=>"b", "c"=>"d"}
h.clear
p h.size    #=> 0
```

## Hash的默认值
```
h = Hash.new(1)
h["a"] = 10
p h["a"]    #=> 10
p h["x"]    #=> 1
p h["y"]    #=> 1       #不存在则返回1

# 或者使用块
h = Hash.new do |hash, key|
  hash[key] = key.upcase
end
h["a"] = "b"
p h["a"]    #=> "b"
p h["x"]    #=> "X"
p h["y"]    #=> "Y"
```