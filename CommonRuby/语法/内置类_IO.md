## IO类

1. 预定义常量 STDOUT

2. 全局变量$stdout

3. 输出文件
    ```
    # 命令后加上 > 文件名，就可以将程序执行时的输出结果保存到文件中
    ruby out.rb > log.txt
    ```

4. tty?检查标准输入是否为屏幕
    ```
    if $stdin.tty?
    print "Stdin is a TTY.\n"
    else
    print "Stdin is not a TTY.\n"
    end
    ```

5. 文件输入
    ```
    io = File.open(file, mode)
    io = open(file, mode)

    缺省模式为只读模式（"r"）
    各个模式如下：
    r   只读
    r+  读写
    w   只写, 不存在则创建，有则清空
    w+  读写，不存在则创建，有则清空
    a   追加，不存在则创建
    a+  读取模式追加，不存在则创建
    ```
6. 文件的常用方法
```
io.close        # 关闭
io.close?       # 是否关闭
data = File.read("foo.txt") # 可以一次性读取文件 file 的内容，在 Windows 中不能使用 File.read 方法读取像图像数据等二进制数据

```

7. 输入
```
# 省略 rs 时则用预定义变量 $/（默认值为 "\n"）
io.gets(rs)
io.each(rs)
io.each_line(rs)
io.readlines(rs)

# 1. gets用法
file = open("test.rb")
while line = file.gets
    line.chomp!
    puts line
end
p file.eof?

# 2. each_lines用法
file = open("test.rb")
file.each_line do |line| 
    puts line
end

# 3. each用法
file = open("test.rb")
file.each do |line| 
    puts line
end

# 4. readlines
file = open("test.rb")
p file.readlines

# 5. 读取行数
$stdin.each_line do |line|
  printf("%3d %s", $stdin.lineno, line)
end

# 6. io.each_char
open("test.rb").each_char do |ch| 
    puts ch
end

# 7. io.each_byte
open("test.rb").each_byte do |ch| 
    puts ch
end

# 8. io.getc 只读取 io 中的一个字符。根据文件编码的不同，有时一个字符会由多个字节组成，但这个方法只会读取一个字符，然后返回其字符串对象。数据全部读取完后再读取时会返回 nil
io = open("test.rb")
while ch = io.getc
    puts ch
end

# 9.ungetc 将参数 ch 指定的字符退回到 io 的输入缓冲中
# hello.txt 中的内容为“Hello, Ruby.\n”
File.open("hello.txt") do |io|
  p io.getc  #=> "H"
  io.ungetc(72)
  p io.gets  #=> "Hello, Ruby.\n"
end

# 10. io.getbyte

# 11. io.ungetbyte(byte)

# 12. io.read(size) 读取参数 size 指定的大小的数据。不指定大小时，则一次性读取全部数据并返回。
# hello.txt 中的内容为"Hello, Ruby.\n"
File.open("hello.txt") do |io|
  p io.read(5)  #=> "Hello"
  p io.read     #=> ",Ruby.\n"
end
```

8. 输出
```
# 1. io.puts(str1, str2,...)
$stdout.puts "foo", "bar", "baz"
open("test.rb", "a+").puts("this is")

# 2. io.putc(ch)
$stdout.putc(82)  # 82 是R 的ASCII 码
$stdout.putc("Ruby")
$stdout.putc("\n")
# 上面的结果是RR

# 3. io.print(str0, str1, …)
# 4. io.printf(fmt, arg0, arg1, …)
# 5. io.write(str)

# 6. io<<str 输出参数 str 指定的字符串。<< 会返回接收者本身，因此可以像下面这样写：
io << "foo" << "bar" << "baz"

```

9. 文件指针
```
# 1. io.pos
# hello.txt 中的内容为"Hello, Ruby.\n"
File.open("hello.txt") do |io|
  p io.read(5)    #=> "Hello"
  p io.pos        #=> 5
  io.pos = 0
  p io.gets       #=> "Hello, Ruby.\n"
end

# 2. io.seek(offset, whence)
whence 有三个值
IO::SEEK_SET	将文件指针移动到 offset 指定的位置
IO::SEEK_CUR	将 offset 视为相对于当前位置的偏移位置来移动文件指针
IO::SEEK_END	将 offset 指定为相对于文件末尾的偏移位置

# 3. io.rewind
将文件指针返回到文件的开头。lineno 方法返回的行编号为 0

# 4. o.truncate(size)
按照参数 size 指定的大小截断文件
io.truncate(0)         # 将文件大小置为0
io.truncate(io.pos)    # 删除当前文件指针以后的数据
```

10. 文本模式和二进制模式
```
File.open("foo.txt", "w") do |io|
  io.binmode
  io.write "Hello, world.\n"
end
这样就可以既不用转换换行符，又能得到与文件中一模一样的数据。
转换为二进制模式的 IO 对象无法再次转换为文本模式
```

11. 缓冲的状态
```
stdout.print "out1 "
$stderr.print "err1 "
$stdout.print "out2 "
$stdout.print "out3 "
$stderr.print "err2\n"
$stdout.print "out4\n"

# io.flush
强制输出缓冲中的数据

# o.sync
io.sync=(state)
通过 io.sync = true，程序写入缓冲时 flush 方法就会被自动调用
```

12. 与命令交互
O.popen(command, mode)

参数 mode 的使用方法与 File.open 方法是一样的，参数缺省时默认为 "r" 模式。

用 IO.popen 方法生成的 IO 对象的输入 / 输出，会关联启动后的命令 command 的标准输入 / 输出。也就是说，IO 对象的输出会作为命令的输入，命令的输出则会作为 IO 对象的输入

open("|command", mode)

将带有管道符号的命令传给 open 方法的效果与使用 IO.popen 方法是一样的。

```
# 打印当前目录
open ("|tree") do  |io|
    io.each_line do |line|
        puts line
    end
end
```

## open-uri库
基本使用
```
require "open-uri"

# 通过HTTP 读取数据
open("http://www.ruby-lang.org") do |io|
  puts io.read  # 将Ruby 的官方网页输出到控制台
end

# 通过FTP 读取数据
url = "ftp://www.ruby-lang.org/pub/ruby/2.0/ruby-2.0.0-p0.tar.gz"
open(url) do |io|
  open("ruby-2.0.0-p0.tar.gz", "w") do |f|  # 打开本地文件
    f.write(io.read)
  end
end
```

发送元信息（meta information）
```
require "open-uri"

options = {
  "Accept-Language" => "zh-cn, en;q=0.5",
}
open("http://www.ruby-lang.org", options){|io|
  puts io.read
}
```

## stringio库
StringIO 就是用于模拟 IO 对象的对象。通过 require 引用 stringio 库后，就可以使用 StringIO 对象了
```
require "stringio"

io = StringIO.new
io.puts("A")
io.puts("B")
io.puts("C")
io.rewind
p io.read  #=> "A\nB\nC\n"

```