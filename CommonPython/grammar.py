# -*- coding: utf-8 -*- 

"""
1. 条件语句
"""
# 第一种写法
file_name = "abc.html"
if file_name == "ab.html":
    print "ab.html"
else:
    print "not ab.html"

#第二种写法
if file_name == "ab.html":
    print "ab.html"
elif file_name == "bc.html":
    print "not ab.html"
else:
    print "not all"

"""
2. 文件输入输出
"""
# 第一种读文件写法
f = open("test.py")
line = f.readline()
while line:
    print line,
    line = f.readline()
f.close()

# 第二种读文件写法
for line in open("test.py"):
    print line

# 写入文件
f = open("a.txt", "w")
print >> f, "this is test" # py2写法
f.write("abc")
f.close()

"""
3. 读取用户输入
import sys
"""
import sys

sys.stdout.write("enter name: ")
name = sys.stdin.readline()

#py2，可以 name = raw_input("nter name: ")

"""
4. 字符串
"""
