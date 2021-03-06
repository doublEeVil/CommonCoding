# 1. 文本内容替换
#    例如：file_content_replace("data.txt", /RUBY/, "ruby")       
def file_content_replace(filepath, search, replace)
    text = File.read(filepath)
    new_content = text.gsub(search, replace)
    File.open(filepath, "w") {|file| file.puts new_content}
end

# ------------------------------------------------------------------------------------------------------------
# 2. 与命令行交互获取输出
def exe_console_test 
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
end

# ------------------------------------------------------------------------------------------------------------
# 3. 图片格式转换
# jpg与png格式相互转换

# ------------------------------------------------------------------------------------------------------------
# 4. 压缩某个目录文件，zip
# win下各种问题，暂时放弃


# ------------------------------------------------------------------------------------------------------------
# 5. 解压缩某个压缩文件, zip格式
# 例如：unzip("test.zip")
require 'zip/zip' 
def unzip(zipfilepath, to = '.')
    Zip::ZipFile.open(zipfilepath) do |zip_file|
        zip_file.each do |entry|
            begin
                entry.extract(File.join(to, entry::name))
            rescue => ex
                puts ex.message
            end
        end 
    end
end 

# ------------------------------------------------------------------------------------------------------------
# 6. 二维码生成
# 例如 qr_code("this si qqq", "test.png")
require 'rubygems'
require 'rqrcode'
require 'rqrcode_png'

def qr_code(input, output) 
    qr = RQRCode::QRCode.new(input, :size => 4, :level => :h)
    qr.to_img.resize(400, 400).save(output)
    Base64.encode64(qr.to_img.resize(400,400).to_s)
end

# ------------------------------------------------------------------------------------------------------------
# 7. 目录文本查找
# text_pattern用//格式，例如  search_text_in_dictionary(".", /ruby/)
# eg. search_text_in_dictionary("G:/我的坚果云/PersonData", /ruby/);
# eg. search_text_in_dictionary(".", /ruby/);
def search_text_in_dictionary(path = ".", text_pattern)
    puts "seach start ... "
    Dir.glob(path + "/**/*").each do |file| 
        line_cnt = 1
        # if FileTest.file?(file) && (File.extname(file) == ".txt" || 
        #                         File.extname(file) == ".log" || 
        #                         File.extname(file) == ".xml" ||
        #                         File.extname(file) == ".ini" ||
        #                         File.extname(file) == ".properties" ||
        #                         File.extname(file) == ".rb")
        if FileTest.file?(file) && FileTest.readable?(file)
            begin
                open(file).each_line do |line| 
                begin
                    puts "#{file}:#{line_cnt}:#{line}" if line =~ text_pattern
                rescue => ex
                end
                line_cnt += 1
            end
            rescue Exception => e 
            end
        end
    end
    puts "search end !!!"
end

# ------------------------------------------------------------------------------------------------------------
# 8. 启动一个本地http服务器
# 直接使用命令行也可以
def http_file_server_start
    # 以下两者都可以启动一个文件服务器
    `ruby -run -e httpd -- -p 5000`
    # `ruby -rwebrick -e'WEBrick::HTTPServer.new(:Port=>8000,:DocumentRoot=>Dir.pwd).start'`
end

# ------------------------------------------------------------------------------------------------------------
# 9. 开启一个本地服务器
# 单线程版本
require 'socket'
def tcp_server(port)
    server = TCPServer.open(port)
    loop {
    	client = server.accept
    	client.puts(Time.now.ctime)
    	client.puts "Bye..."
    	client.close
    }
end

# ------------------------------------------------------------------------------------------------------------
# 10. 开启一个本地服务器
# 多线程版本
require 'socket'
def tcp_server_m(port)
    server = TCPServer.open(port)
    loop {
        Thread.start(server.accept) do |client|
            client.puts(Time.now.ctime)
            client.puts "Bye..."
            client.close
        end
    }
end

# ------------------------------------------------------------------------------------------------------------
# 11. 开启一个本地客户端
def tcp_client(host, port)
    s = TCPSocket.open(host, port)
    req = "GET /index.html HTTP/1.0\r\n\r\n"
    s.print(req) # 发送请求
    resp = s.read
    puts resp
end

# ------------------------------------------------------------------------------------------------------------
# 12. 开启一个HTTP本地客户端
require 'net/http'  
def http_client(host, path)
    http = Net::HTTP.new(host)
    headers, body = http.get(path)
    if headers.code == "200"
    	p headers
    	print headers, body
    else 
    	puts headers.code
    end
end







