package com._22evil.test;

import com._22evil.util.FileUtil;
import com._22evil.util.HttpCallback;
import com._22evil.util.HttpUtil;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BasicTest {

    @Test
    public void test() throws Exception{
        String path = "C:\\Users\\Administrator\\Desktop\\最新屏蔽字\\invalidname.txt";
        LineNumberReader reader = new LineNumberReader(new FileReader(path));
        String line;

        while ((line=reader.readLine()) != null) {
            Pattern.compile(line);
        }
        reader.close();
    }

    @Test
    public void test2() {
        String url = "http://192.168.0.192:28080/wydpay/HaimaAndroidNotifyServlet?sign=64634ae98f4693a737ebb034babd04b2&body=弹弹岛2&notify_time=2019-08-01 12:04:58&subject=钻石&total_fee=6.00&user_param=1875895-33560482-1564632195319&appid=c25f6f427bd5bbfbdad291c72b6a1a43&out_trade_no=1875895-33560482-1564632195319&trade_status=1";
        HttpUtil.get(url, new HttpCallback() {
            @Override
            public void fail(String errorMsg) {
                System.out.println(errorMsg);
            }

            @Override
            public void success(int code, String data) {
                System.out.println(data);
            }
        });
    }

    @Test
    public void test3() throws Exception{
        class Payments {
            String orderId;
            int status;
            long create;
            long update;
        }

        class RechargeLog {
            String orderId;
            long create;
        }
        // 1. 获取全部payments订单
        Map<String, Payments> paymentsMap = new HashMap<>(2048*4);
        String path = "C:\\Users\\Administrator\\Desktop\\8-1\\payments.csv";
        LineNumberReader reader = new LineNumberReader(new FileReader(path));
        String line;
        String[] datas;
        while ((line = reader.readLine()) != null) {
            datas = line.split(",");
            Payments payments = new Payments();
            payments.orderId = datas[2];
            payments.status = Integer.valueOf(datas[15]);
            payments.create = Long.valueOf(datas[25].trim());
            payments.update = Long.valueOf(datas[27].trim());
            paymentsMap.put(payments.orderId, payments);
        }
        reader.close();
        System.out.println("payments size: " + paymentsMap.size());

        // 2. 获取rechagelog 记录
        Map<String, RechargeLog> rechargeLogMap = new HashMap<>(2048*4);
        path = "C:\\Users\\Administrator\\Desktop\\8-1\\add\\apps";
        String reg = ".*(2019-0[6-7]-[0-9]{2}\\|[0-9]{2}:[0-9]{2}:[0-9]{2}).*playerId:([0-9]+)\torderNum:([0-9\\-]+)\t.*";
        Pattern groupPtn = Pattern.compile(reg);
        List<File> all = FileUtil.getFileList(path);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd|hh:mm:ss");
        Matcher matcher;
        for (File file :all) {
            reader = new LineNumberReader(new FileReader(file));
            while ((line = reader.readLine()) != null) {
                matcher = groupPtn.matcher(line);
                if (matcher.find()) {
                    RechargeLog log = new RechargeLog();
                    log.create = sdf.parse(matcher.group(1)).getTime();
                    log.orderId = matcher.group(3);
                    rechargeLogMap.put(log.orderId, log);
                }
            }
            reader.close();
        }
        System.out.println("rechargelog size:" + rechargeLogMap.size());

        // 3. 分析数据
        Set<String> keys = rechargeLogMap.keySet();
        for (String key :keys) {
            if (!paymentsMap.containsKey(key)) {
                System.out.println(key);
                System.out.println(new Date(rechargeLogMap.get(key).create));
            }
        }
    }

    @Test
    public void test4() throws Exception{
        List<Pattern> ptns = new ArrayList<>(46000);
        List<String> names = new ArrayList<>();
        String path = "C:\\Users\\Administrator\\Desktop\\最新屏蔽字\\invalidname.txt";
        LineNumberReader reader = new LineNumberReader(new FileReader(path));
        String line;

        while ((line=reader.readLine()) != null) {
            line = line.replace(".*", "");
            Pattern pattern = Pattern.compile(line);
            ptns.add(pattern);
            names.add(line);
        }
        reader.close();

        // 1. 检测玩家姓名
        path = "C:\\Users\\Administrator\\Desktop\\8-5\\name\\name";
        List<File> files = FileUtil.getFileList(path);
        System.out.println("---------");
        for (File file : files) {
            reader = new LineNumberReader(new FileReader(file));
            while ((line=reader.readLine()) != null) {
                String[] lines = line.split("\t");
//                for (Pattern ptn : ptns) {
//                    String[] lines = line.split("\t");
//                    if (ptn.matcher(lines[1]).matches()) {
//                        System.out.println(file.getPath() + " " + line + "          " + ptn);
//                        break;
//                    }
//                }
                if (lines.length < 2) {
                    continue;
                }
                String check = lines[1];
                for (String s : names) {
                    if (s.equals(check)) {
                        System.out.println(file.getPath() + " " + line );
                        break;
                    }
                }
            }
            reader.close();
        }
        // 2. 检测公会姓名
        path = "C:\\Users\\Administrator\\Desktop\\8-5\\guild\\guild";
        files = FileUtil.getFileList(path);
        System.out.println("---------");
        for (File file : files) {
            if (true) {
                break;
            }
            reader = new LineNumberReader(new FileReader(file));
            while ((line=reader.readLine()) != null) {
                String[] lines = line.split("\t");
//                for (Pattern ptn : ptns) {
//                    String[] lines = line.split("\t");
//                    if (ptn.matcher(lines[1]).matches()) {
//                        System.out.println(file.getPath() + " " + line + "          " + ptn);
//                        break;
//                    }
//                }
                if (lines.length < 2) {
                    continue;
                }
                for (String s : names) {
                    if (s.equals(lines[1])) {
                        System.out.println(file.getPath() + " " + line );
                    }
                }
            }
            reader.close();
        }
        System.out.println("---检测完成--");
    }

    @Test
    public void test5() throws Exception{
        int i = 1137297010;
        System.out.println(Float.intBitsToFloat(i));
    }


    @Test
    public void test6() {
        Integer a = new Integer(6);
        ttt(a);
        System.out.println(a);
    }

    private void ttt(Integer a) {
        a = new Integer(a+1);
        System.out.println(a);
    }

    @Test
    public void test7() {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 1; i < 10; i++) {
            map.put(i, i);
        }
        Set<Integer> keys = map.keySet();
        Set<Integer> rm = new HashSet<>();
        for (int i : keys) {
            if (i < 5) {
                System.out.println(map.get(i));
                rm.add(i);
            }
        }
        for (int i : rm) {
            map.remove(i);
        }
    }
}
