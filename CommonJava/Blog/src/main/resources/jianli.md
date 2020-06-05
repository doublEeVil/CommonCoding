## CentOS7 折腾记录

以下内容记录了一些折腾cent os7的一些记录。环境是采用vm虚拟机

## 1.设置IP
centos中查看IP可以用以前的命令`ifconfig`,也可以用最新的命令`ipconfig`。
在使用vm 构建虚拟机时，由于想虚拟机和局域网的其他环境相连，手动更改网络适配器（由默认的NAT模式改为桥接模式，并勾选复制物理网络连接状态）

接下来就是手动设置IP时间，首先是win主机的网络情况，cmd命令`ipconfig`
```
PS C:\Users\98437> ipconfig

Windows IP 配置


以太网适配器 以太网:

   连接特定的 DNS 后缀 . . . . . . . :
   本地链接 IPv6 地址. . . . . . . . : fe80::911d:586b:ace2:1a80%19
   IPv4 地址 . . . . . . . . . . . . : 192.168.3.40
   子网掩码  . . . . . . . . . . . . : 255.255.255.0
   默认网关. . . . . . . . . . . . . : 192.168.3.1

以太网适配器 VMware Network Adapter VMnet1:

   连接特定的 DNS 后缀 . . . . . . . :
   本地链接 IPv6 地址. . . . . . . . : fe80::a951:c80:373c:57%18
   IPv4 地址 . . . . . . . . . . . . : 192.168.159.1
   子网掩码  . . . . . . . . . . . . : 255.255.255.0
   默认网关. . . . . . . . . . . . . :

以太网适配器 VMware Network Adapter VMnet8:

   连接特定的 DNS 后缀 . . . . . . . :
   本地链接 IPv6 地址. . . . . . . . : fe80::adaa:ed4d:329e:683a%14
   IPv4 地址 . . . . . . . . . . . . : 192.168.2.1
   子网掩码  . . . . . . . . . . . . : 255.255.255.0
   默认网关. . . . . . . . . . . . . :
```

以此为基础，设置centos的IP,输入命令`sudo vi /etc/sysconfig/network-scripts/ifcfg-ens33` （ifcfg-ens33可能是ifcfg-ens32，也可能是ifcfg-ens34,根据实际情况确定）
```
TYPE=Ethernet
PROXY_METHOD=none
BROWSER_ONLY=no
BOOTPROTO=dhcp
DEFROUTE=yes
IPV4_FAILURE_FATAL=no
IPV6INIT=yes
IPV6_AUTOCONF=yes
IPV6_DEFROUTE=yes
IPV6_FAILURE_FATAL=no
IPV6_ADDR_GEN_MODE=stable-privacy
NAME=ens33
UUID=e9022d8b-d65c-491b-bfaf-6bdb586b215c
DEVICE=ens33
ONBOOT=no
```

我们把`BOOTPROTO=dhcp`改为`BOOTPROTO=static`,`ONBOOT=no`改为`ONBOOT=yes`,同时最下面增加几行
```
IPADDR=192.168.3.110
NETMASK=255.255.255.0
GATEWAY=192.168.3.1
DNS1=119.29.29.29
DNS2=8.8.8.8
```

保存，然后重启网络服务`systemctl restart network`即可

## 2. 安装mariaDB 
+ 默认情况下是已经安装了MariaDB,如果没有，输入命令`yum -y install mariadb mariadb-server`
+ 启动服务`systemctl start mariadb`
+ 设置开机启动 `systemctl enable mariadb`
+ 设置密码`mysqladmin -u root -password 'root'`,此时就可以登录了
+ 新建用户`create user 'zjs'@'%' identified by '123456';` 
+ 用上面的用户`zjs`发现本地登录不了，用`root`用户登录，删除mysql.user表下user=''的用户名，即删除用户名为空的账户。此时发现可以本地登录了
+ 用上面的远程登录，发现登录不了。有两种方法，
一个是关闭防火墙`systemctl stop firewalld.service `。不推荐，  
第二种是防火墙设置通过。` firewall-cmd --zone=public --add-port=3306/tcp --permanent`,并立即生效`firewall-cmd --reload`。如果想关闭端口的话，直接`firewall-cmd --zone=public --remove-port=3306/tcp --permanent`。
查询开放端口的命令`firewall-cmd --zone=public --list-ports`

## 3. 防火墙策略

+ 关闭防火墙`systemctl stop firewalld.service `, 开启防火墙`systemctl start firewalld.service `

+ 查看开放端口`firewall-cmd --zone=public --list-ports`

+ 开放单个端口`firewall-cmd --zone=public --add-port=3306/tcp --permanent`, 关闭单个端口`firewall-cmd --zone=public --remove-port=3306/tcp --permanent`

+ 使规则立即生效`firewall-cmd --reload`

+ 开放范围端口`firewall-cmd --zone=public --add-port=3306-4444/tcp --permanent` 

## 4. 安装Redis
1. 下载redis源代码，5.X版本的，最新的6.x版本编译失败
2. 解压后进入redis-xxx目录，直接执行make命令即可
3. 安装tcl ` wget http://downloads.sourceforge.net/tcl/tcl8.6.1-src.tar.gz`
4. 编译tcl,进入unix目录 `./configure;make; make install`
5. 启动redis 
   + 修改redis.conf `daemonize yes`
   + `./src/redis-server ./redis.conf` 即后台启动了。
6. 增加密码`requirepass abc`