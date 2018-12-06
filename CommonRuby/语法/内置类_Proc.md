## Proc 
所谓 Proc，就是使块对象化的类

## 创建
```
hello1 = Proc.new do |name|
  puts "Hello, #{name}."
end
　
hello2 = proc do |name|
  puts "Hello, #{name}."
end
　
hello1.call("World")    #=> Hello, World.
hello2.call("Ruby")     #=> Hello, Ruby.

double = Proc.new do |*args|
  args.map{|i| i * 2 }    # 所有元素乘两倍
end
　
p double.call(1, 2, 3)    #=> [2, 3, 4]
p double[2, 3, 4]         #=> [4, 6, 8]

square = ->(n){ return n ** 2}
p square[5]    #=> 25
```