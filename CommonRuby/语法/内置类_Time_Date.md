## Time Date类
常见用法
```
# 1. 当前时间
p Time.new    #=> 2013-03-30 03:06:00 +0900
sleep 1       #=> 等待1 秒
p Time.now    #=> 2013-03-30 03:06:01 +0900

# 2. 时间具体信息
t = Time.now
p t        #=> 2013-03-30 03:07:13 +0900
p t.year   #=> 2013
p t.month  #=> 3
p t.day    #=> 30
具体参数有
year	年
month	月
day	日
hour	时
min	分
sec	秒
usec	秒以下的位数（以毫秒为单位）
to_i	从 1970 年 1 月 1 日到当前时间的秒数
to_i	从 1970 年 1 月 1 日到当前时间的秒数
wday	一周中的第几天（0 表示星期天）
mday	一个月中的第几天（与 day 方法一样）
yday	一年中的第几天（1 表示 1 月 1 日）
zone	时区（JST 等）

# 3. Time.mktime
t = Time.mktime(2013, 5, 30, 3, 11, 12)
p t    #=> 2013-05-30 03:11:12 +900

# 4. 比较
t1 = Time.now
sleep(10)    # 等10 秒
t2 = Time.now
p t1 < t2    #=> true
p t2 - t1    #=> 10.005073

# 5. +-秒数
t = Time.now
p t                    #=> 2013-03-30 03:11:44 +0900
t2 = t + 60 * 60 * 24  #=> 增加24 小时的秒数
p t2                   #=> 2013-03-31 03:11:44 +0900

# 6. 时间的格式
t.strftime(format)
t.to_s

# 7. 本地时间
t.utc
t.localtime

# 8. 从字符串中获取时间
require "time"
Time.parse(str)

# 9. 日期的获取
require "date"
d = Date.today
puts d    #=> 2013-03-30

d = Date.today
p d.year    # 年 => 2013
p d.month   # 月 => 3
p d.day     # 日 => 30
p d.wday    # 一周中的第几天（0 表示星期天）      => 6
p d.mday    # 一个月中的第几天（与 day 方法一样） => 30
p d.yday    # 一年中的第几天（1 表示 1 月 1 日）  => 89

# 10. 日期的运算
require "date"

d1 = Date.new(2013, 1, 1)
d2 = Date.new(2013, 1, 4)
puts d2 - d1    #=> 3/1 (3 天的意思)

d = Date.today
puts d          #=> 2013-03-30
puts d + 1      #=> 2013-03-31
puts d + 100    #=> 2013-07-08
puts d - 1      #=> 2013-03-29
puts d -100     #=> 2012-12-20

require "date"

## 用 << 运算符得到的是表示前一个月相同日期的 Date 对象
d = Date.today
puts d        #=> 2013-03-30
puts d >> 1   #=> 2013-04-30
puts d >> 100 #=> 2021-07-30
puts d << 1   #=> 2013-02-28
puts d << 100 #=> 2004-11-30
```