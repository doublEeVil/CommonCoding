# 1. 文本内容替换
#    例如：file_content_replace("data.txt", /RUBY/, "ruby")       
def file_content_replace(filepath, search, replace)
    text = File.read(filepath)
    new_content = text.gsub(search, replace)
    File.open(filepath, "w") {|file| file.puts new_content}
end

# 2. 与命令行交互获取输出
# 总体来说有4种方法
# 2.1 使用``，但用stderr输出的不可使用，得到的是nil
puts `tree` #打印当前目录， `tree`的内容也是字符串
# 2.2 使用oepn命令，和上面类似
open("|tree") do {|line| puts line}
# 2.3 使用IO.popen, 同上
IO.popen("tree")
# 2.4 强化版，可以获得java输出
require "open3"
Open3.popen3("java -version") do |stdin, stdout, stderr|
    @content = stderr.read
    puts @content
end

