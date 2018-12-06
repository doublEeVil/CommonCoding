## File Dir类
File类
```
# 1. 变更文件名
File.rename("before.txt", "after.txt")
# 还可以将文件移动到已存在的目录下，目录不存在则程序会产生错误。File.rename 方法无法跨文件系统或者驱动器移动文件
File.rename("data.txt", "backup/data.txt")

# 2. 复制文件
def copy(from, to)
  File.open(from) do |input|
    File.open(to, "w") do |output|
      output.write(input.read)
    end
  end
end

# 3. fileutils库拷贝和复制
require "fileutils"
FileUtils.cp("data.txt", "backup/data.txt")
FileUtils.mv("data.txt", "backup/data.txt")

# 4. 删除文件
File.delete(file)
File.unlink(file)
```

## Dir类操作
```
# 1. 当前目录
Dir.pwd

# 2. 变更当前目录
Dir.chdir(dir)

# 3. 读取目录
p Dir.pwd
dir = Dir.open(".")
while name = dir.read
    p name
end
dir.close

dir = Dir.open("/usr/bin")
dir.each do |name|
  p name
end
dir.close

# 4. 遍历文件夹
def traverse(path)
    if File.directory?(path)
        dir = Dir.open(path)
        while name = dir.read
            next if name == "."
            next if name == ".."
            traverse(path + "/" + name)
        end
        dir.close
    else
        puts path
    end
end

# 5. Dir.glob
Dir.glob("*")       # 获取目录所有文件名
Dir.glob(".*")      # 获取当前目录中所有的隐藏文件名
Dir.glob(["*.html", "*.htm"]) # 获取当前目录中扩展名为 .html 或者 .htm 的文件名
Dir.glob(%w(*.html *.htm)) #同上
Dir.glob(["*/*.html", "*/*.htm"]) #获取子目录下扩展名为 .html 或者 .htm 的文件名。
Dir.glob("**/*")    # 获取当前目录及其子目录中所有的文件名，递归查找目录。
Dir.glob("foo/**/*.html") #获取目录 foo 及其子目录中所有扩展名为 .html 的文件名，递归查找目录。

# 6. 创建目录
Dir.mkdir("temp")

# 7. 删除目录
Dir.rmdir(path)
```

## 文件/目录的属性
File.stat(path)

File.stat 方法返回的是 File::Stat 类的实例,属性如下
```
dev	        文件系统的编号
ino	i-node  编号
mode	    文件的属性
nlink	    链接数
uid	        文件所有者的用户 ID
gid	        文件所属组的组 ID
rdev	    文件系统的驱动器种类
size	    文件大小
blksize	    文件系统的块大小
blocks	    文件占用的块数量
atime	    文件的最后访问时间
mtime	    文件的最后修改时间
ctime	    文件状态的最后更改时间
```

## 其他方法
```
File.ctime(path)
File.mtime(path)
File.atime(path)
这三个方法的执行结果与实例方法 File::Stat#ctime、File::Stat#mtime、File::Stat#atime 是一样的

File.utime(atime, mtime, path)

改变文件属性中的最后访问时间 atime、最后修改时间 mtime

File.chmod(mode, path)

修改文件 path 的访问权限（permission）。mode 的值为整数值，表示新的访问权限值。同时还能指定多个路径。
```

## 文件权限具体值

位1 | 位2 | 位3 | 位4 | 位5 | 位6 | 位7 | 位8 | 位9
--|--|--|--
读 | 写 | 执行| 所属组读|所属组写|所属组执行|其他用户读|其他用户写|其他用户执行

最大值就是777

## FileTest模块
```
exist?(path)    path 若存在则返回 true

file?(path)     path 若是文件则返回 true

directory?(path)    path 若是目录则返回 true

owned?(path)    path 的所有者与执行用户一样则返回 true

grpowned?(path) path 的所属组与执行用户的所属组一样则返回 true

readable?(path) path 可读取则返回 true

writable?(path) path 可写则返回 true

executable?(path)   path 可执行则返回 true

size(path)      返回 path 的大小

size?(path)     path 的大小比 0 大则返回 true ，大小为 0 或者文件不存在则返回 nil

zero?(path)     path 的大小为 0 则返回 true

```

## 其他常用用法
```
# 1. basename
p File.basename("/usr/local/bin/ruby")    #=> "ruby"
p File.basename("src/ruby/file.c", ".c")  #=> "file"
p File.basename("file.c")                 #=> "file"

# 2. dirname
p File.dirname("/usr/local/bin/ruby")    #=> "/usr/local/bin"
p File.dirname("ruby")                   #=> "."
p File.dirname("/")                      #=> "/"

# 3. extname
p File.extname("helloruby.rb")            #=> ".rb"
p File.extname("ruby-2.0.0-p0.tar.gz")    #=> ".gz"
p File.extname("img/foo.png")             #=> ".png"
p File.extname("/usr/local/bin/ruby")     #=> ""
p File.extname("~/.zshrc")                #=> ""
p File.extname("/etc/init.d/ssh")         #=> ""

# 4. File.split(path)
p File.split("/usr/local/bin/ruby")
                        #=> ["/usr/local/bin", "ruby"]
p File.split("ruby")    #=> [".", "ruby"]
p File.split("/")       #=> ["/", ""]
　
dir, base = File.split("/usr/local/bin/ruby")
p dir                   #=> "/usr/local/bin"
p base                  #=> "ruby"

# 5. File.join
File.join(name1[, name2, …])

# 6. File.expand_path(path[, default_dir]) 将相对路径 path 转换为绝对路径
p Dir.pwd                            #=> "/usr/local"
p File.expand_path("bin")            #=> "/usr/local/bin"
p File.expand_path("../bin")         #=> "/usr/bin"
p File.expand_path("bin", "/usr")    #=> "/usr/bin"
p File.expand_path("../etc", "/usr") #=> "/etc"
```

## find库

## tempfile库
在处理大量数据的程序中，有时候会将一部分正在处理的数据写入到临时文件。这些文件一般在程序执行完毕后就不再需要，因此必须删除，但为了能够确实删除文件，就必须记住每个临时文件的名称。此外，有时候程序还会同时处理多个文件，或者同时执行多个程序，考虑到这些情况，临时文件还不能使用相同的名称，而这就形成了一个非常麻烦的问题。
```
Tempfile.new(basename[, tempdir])
tempfile.close(real)
tempfile.open
tempfile.path
```

## FileUtils库
```
FileUtils.cp(from, to)      # 从 from 拷贝到 to
FileUtils.cp_r(from, to)    # 会进行递归拷贝
FileUtils.mv(from, to)
FileUtils.rm(path)
FileUtils.rm_f(path)
FileUtils.rm_r(path)
FileUtils.rm_rf(path)
FileUtils.compare(from, to)
FileUtils.install(from, to[, option]) 把文件从 from 拷贝到 to。如果 to 已经存在，且与 from 内容一致，则不会拷贝

FileUtils.mkdir_p(path) #按顺序逐个创建上层目录
```

