package com._22evil.util;

import com._22evil.db.DbPoolConnection;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.bson.Document;

import java.io.*;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

/**
 * Created by shangguyi on 23/12/2017.
 * 简单数据库数据, 读写
 * 目前支持mysql, mongodb, excel
 * 所有数据格式为id + {自定义内容} + createTime
 */
public class DbUtil {
    private static Logger logger = LogManager.getLogger(DbUtil.class);
    /**
     * 写入数据库
     * 数据库表名为  _t[T], 其中[T]为类名
     * 字段名与类结构一致, 如果数据库缺少该字段,则添加
     * 以下为对应方法
     *          byte === int(3)
     *          boolean === boolean
     *          int  === int(13)
     *          double === double
     *          short === int
     *          float === double
     *          char === char
     *          long === int(16)
     *          String === char(255)
     *          对象 === 转JSON存 blob   暂未实现
     * @param data
     */
    public static void writeToMysql(List data) {
        if (data == null) {
            return;
        }
        if (data.isEmpty()) {
            return;
        }
        try {
            Class clazz = data.get(0).getClass();
            // 得到类结构, 与数据库表结构对比, 如果数据库不存在该字段, 则添加
            Field[] fields = clazz.getFields();
            logger.info(clazz.getSimpleName(), "存在如下字段");
            for (Field field : fields) {
                logger.info(field.getName() + " " + field.getType());
            }
            Connection conn = DbPoolConnection.getInstance().getConnection();
            Statement statement =  conn.createStatement();
            // 先检查表是否存在, 如果不存在则创建
            String sql = "SHOW TABLES";
            ResultSet resultSet = statement.executeQuery(sql);
            boolean createTable = true;
            while (resultSet.next()) {
                String tabName = resultSet.getString(1);
                if (tabName.equals("_t" + clazz.getSimpleName())) {
                    createTable = false;
                }
            }
            if (createTable) {
                // 创建表
                createTable(statement, clazz);
            }
            // 如果表存在,则检查字段是否全
            checkAlterTable(statement, clazz);
            // 写入数据, 一行一行插入,不用批量
            for (Object obj : data) {
                StringBuilder insertSql = new StringBuilder();
                insertSql.append("insert into _t" + clazz.getSimpleName());
                insertSql.append("(");
                for (Field field : fields) {
                    insertSql.append(field.getName());
                    insertSql.append(",");
                }
                insertSql.deleteCharAt(insertSql.length() - 1);
                insertSql.append(") value(");
                for (Field field : fields) {
                    insertSql.append("?");
                    insertSql.append(",");
                }
                insertSql.deleteCharAt(insertSql.length() - 1);
                insertSql.append(")");
                PreparedStatement psmt = conn.prepareStatement(insertSql.toString());
                int index = 1;
                for (Field field : fields) {
                    Object value = getObjectValue(field.get(obj));
                    psmt.setObject(index, value);
                    index++;
                }
                //psmt.execute();
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Object getObjectValue(Object obj) throws IllegalAccessException{
        if (obj == null) {
            return null;
        }
        switch (obj.getClass().toString()) {
            case "int":
            case "class java.lang.Integer":
            case "byte":
            case "class java.lang.Byte":
            case "short":
            case "class java.lang.Short":
            case "char":
            case "class java.lang.Character":
            case "double":
            case "class java.lang.Double":
            case "long":
            case "class java.lang.Long":
            case "boolean":
            case "class java.lang.Boolean":
            case "float":
            case "class java.lang.Float":
                return obj;
            case "class java.lang.String":
                return "'" + obj + "'";
            default:
                return obj;
        }
    }

    private static void checkAlterTable(Statement statement, Class clazz) throws Exception{
        // 得到当前类结构
        Field[] classFields = clazz.getFields();
        // 得到数据库结构
        String sql = "desc _t" + clazz.getSimpleName();
        ResultSet resultSet = statement.executeQuery(sql);
        Set<String> set = new HashSet<>();
        while (resultSet.next()) {
            String fieldName = resultSet.getString(1);
            set.add(fieldName);
        }
        for (Field field : classFields) {
            if (!set.contains(field.getName())) {
                // 不包含该字段,数据库添加字段
                sql = "ALTER TABLE " + "_t" + clazz.getSimpleName() + " add " + field.getName() + " " + getMysqlColumnType(field.getType());
                statement.execute(sql);
            }
        }
    }

    private static void createTable(Statement statement, Class clazz) throws Exception{
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE ");
        String tabName = "_t" + clazz.getSimpleName();
        sql.append(tabName);
        sql.append("(");
        Field[] fields = clazz.getFields();
        for (Field field : fields) {
            sql.append(field.getName());
            sql.append(" ");
            sql.append(getMysqlColumnType(field.getType()));
            sql.append( "," );
        }
        sql.deleteCharAt(sql.length() - 1);
        sql.append(")");
        statement.execute(sql.toString());
    }

    private static String getMysqlColumnType(Class clazz) {
        String type;
        switch (clazz.toString()) {
            case "int":
            case "class java.lang.Integer":
                type = "int(13)";
                break;
            case "byte":
            case "class java.lang.Byte":
                type = "int(4)";
                break;
            case "short":
            case "class java.lang.Short":
                type = "int(6)";
                break;
            case "char":
            case "class java.lang.Character":
                type = "char";
                break;
            case "double":
            case "class java.lang.Double":
                type = "double";
                break;
            case "long":
            case "class java.lang.Long":
                type = "bigint";
                break;
            case "boolean":
            case "class java.lang.Boolean":
                type = "bool";
                break;
            case "float":
            case "class java.lang.Float":
                type = "float";
                break;
            case "class java.lang.String":
                type = "varchar(255)";
                break;
            default: type = "blob";
        }
        return type;
    }

    /**
     * 从mysql 复原对象
     * @param clazz
     * @return
     */
    public static List readFromMysql(Class clazz) {
        List list = new ArrayList<>();
        try {
            Connection conn = DbPoolConnection.getInstance().getConnection();
            Statement statement =  conn.createStatement();
            String sql = "select * from _t" + clazz.getSimpleName();
            ResultSet resultSet = statement.executeQuery(sql);
            //ResultSetMetaData mataData = resultSet.getMetaData();
            Field[] fields = clazz.getFields();
            while (resultSet.next()) {
                Object obj = clazz.newInstance();
                for (Field field : fields) {
                    Object value = resultSet.getObject(field.getName());
                    value = getObjectValue(value);
                    field.set(obj, value);
                }
                list.add(obj);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 写入mongoDB, 集合名为类名小写
     * 直接将对象转JSON存
     * @param data
     * @param <T>
     */
    public static <T> void writeToMongo(List<T> data, String dbName) {
        if (data == null) {
            return;
        }
        if (data.isEmpty()) {
            return;
        }
        //链接到库
        try {
            MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
            MongoDatabase mongoDatabase = mongoClient.getDatabase(dbName);
            ///////如果需要密码
            //            ServerAddress serverAddress = new ServerAddress("localhost",27017);
            //            List<ServerAddress> addrs = new ArrayList<ServerAddress>();
            //            addrs.add(serverAddress);
            //
            //            //MongoCredential.createScramSha1Credential()三个参数分别为 用户名 数据库名称 密码
            //            MongoCredential credential = MongoCredential.createScramSha1Credential("username", "databaseName", "password".toCharArray());
            //            List<MongoCredential> credentials = new ArrayList<MongoCredential>();
            //            credentials.add(credential);
            //
            //            //通过连接认证获取MongoDB连接
            //            MongoClient mongoClient = new MongoClient(addrs,credentials);
            ///////end

            Class clazz = data.get(0).getClass();
            String collName = clazz.getSimpleName().toLowerCase();
            MongoCollection<Document> collection = mongoDatabase.getCollection(collName);
            List<Document> documents = new ArrayList<>();
            //将data变成Document
            for (T t : data) {
                Document doc = new Document(objectToMap(t));
                documents.add(doc);
            }
            collection.insertMany(documents);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从Mongo复原
     * @param clazz
     * @return
     */
    public static List readFromMongo(Class clazz, String dbName) {
        List list = new ArrayList<>();
        try {
            MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
            MongoDatabase mongoDatabase = mongoClient.getDatabase(dbName);
            String collName = clazz.getSimpleName().toLowerCase();
            MongoCollection<Document> collection = mongoDatabase.getCollection(collName);
            FindIterable<Document> findIterable = collection.find();
            MongoCursor<Document> iterator = findIterable.iterator();
            Field[] fields = clazz.getFields();
            while (iterator.hasNext()) {
                Document doc = iterator.next();
                Object obj = clazz.newInstance();
                for (Field field : fields) {
                    field.set(obj, doc.get(field.getName()));
                }
                list.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 写入Excel
     * @param data
     * @param <T>
     */
    public static <T> void writeToExcel(List<T> data, String filePath) {
        if (data == null) {
            return;
        }
        if (data.isEmpty()) {
            return;
        }
        // 新建xls文件
        HSSFWorkbook wb=new HSSFWorkbook();
        // 新建sheet
        HSSFSheet sheet=wb.createSheet("第一个sheet");
        // 插入标题行
        HSSFRow row=sheet.createRow(0);
        Class clazz = data.get(0).getClass();
        Field[] fields = clazz.getFields();
        int i = 0;
        for (Field field : fields) {
            row.createCell(i).setCellValue(field.getName());
            i++;
        }
        // 写入文件
        try {
            // 插入数据
            i = 1;
            for (T t : data) {
                HSSFRow mydata=sheet.createRow(i);
                for (int j = 0; j < fields.length; j++) {
                    mydata.createCell(j).setCellValue(fields[j].get(t).toString());
                }
                i++;
            }
            FileOutputStream out = new FileOutputStream(filePath);
            wb.write(out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
     }

    /**
     * 从Excel读并还原对象
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> readFromExcel(Class clazz, String filePath) {
        List<T> list = new ArrayList<>();
        try {
            InputStream in = new FileInputStream(filePath);
            POIFSFileSystem fs = new POIFSFileSystem(in);
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            //获取excel表的第一个sheet
            HSSFSheet sheet = wb.getSheetAt(0);
            if (sheet == null) {
                return list;
            }
            String[] rowNames = new String[sheet.getLastRowNum() + 1];
            // 获取栏目名
            HSSFRow rowTitle = sheet.getRow(0);
            for (int cellNum = 0; cellNum < rowTitle.getLastCellNum(); cellNum++) {
                HSSFCell cell = rowTitle.getCell(cellNum);
                logger.info("列名: " + cell.getStringCellValue());
                rowNames[cellNum] = cell.getStringCellValue();
            }
            // 获取具体数据
            for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                HSSFRow row = sheet.getRow(rowNum);
                T t = (T)clazz.newInstance();
                for (int cellNum = 0; cellNum < row.getLastCellNum(); cellNum++) {
                    HSSFCell cell = row.getCell(cellNum);
                    logger.info(cell.getStringCellValue());
                    String fieldName = rowNames[cellNum];
                    Field field = clazz.getField(fieldName);
                    Object value = StringToNumUtil.string2Num(cell.getStringCellValue(), field.getType());
                    field.set(t, value);
                }
                list.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 将对象序列成文件
     * @param data
     * @param filePath
     */
    public static void writeToFile(List<? extends Serializable> data, String filePath) {
        if (data == null) {
            return;
        }
        if (data.isEmpty()) {
            return;
        }
        File file = new File(filePath);
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从文件中还原对象
     * @param filePath
     * @param <T>
     * @return
     */
    public static <T> List<T> readFromFile(String filePath) {
        List<T> list = new ArrayList<>();
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath));
            list = (List<T>)in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 将成员对象的public字段全部转为map
     * @param obj
     * @return
     */
    public static Map<String, Object> objectToMap(Object obj) throws IllegalAccessException{
        Map<String, Object> map = new HashMap<>();
        if (obj == null) {
            return map;
        }
        Class clazz = obj.getClass();
        Field[] fields = clazz.getFields();
        for (Field field : fields) {
            map.put(field.getName(), field.get(obj));
        }
        return map;
    }

    /**
     * 将map转为对象
     * @param map
     * @param clazz
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static Object mapToObj(Map<String, Object> map, Class clazz) throws IllegalAccessException, InstantiationException{
        Object obj = clazz.newInstance();
        Field[] fields = clazz.getFields();
        for (Field field : fields) {
            field.set(obj, map.get(field.getName()));
        }
        return obj;
    }

    public static String objectToJson(Object object) throws IllegalAccessException{
        Map<String, Object> map = objectToMap(object);
        JSONObject jsonObject = new JSONObject(map);
        return jsonObject.toJSONString();
    }
}
