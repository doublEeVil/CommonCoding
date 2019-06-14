package com._22evil.excel2db;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class App {

    public static void main(String ... args) throws Exception {
        long t1 = System.currentTimeMillis();

        String configPath = "C:\\Users\\Administrator\\Desktop\\excel2db\\config.json";
        String excelPath = "C:\\Users\\Administrator\\Desktop\\excel2db\\excel";

        File config = new File(configPath);
        String text = "";
        InputStream in = new FileInputStream(config);
        byte[] data = new byte[1024];
        int len = in.read(data);
        String configText = new String(data, 0, len);
        JSONObject json = JSONObject.parseObject(configText);

        JSONArray array = json.getJSONArray("db");
        Object[] arrs = array.toArray();
        List<Connection> connList = new ArrayList<>();

        Class.forName("com.mysql.jdbc.Driver");

        // 1. 先获取全部sql链接
        for (Object obj :arrs) {
            JSONObject objs = (JSONObject)obj;
            if (objs.getBoolean("write")) {
                String host = objs.getString("url");
                String db = objs.getString("db");
                String user = objs.getString("user");
                String pwd = objs.getString("pwd");
                Connection conn = DriverManager.getConnection("jdbc:mysql://" + host + "/" + db, user, pwd);
                connList.add(conn);
            }
        }

        // 2. 查询文件，得到表名
        File root = new File(excelPath);
        File[] files = root.listFiles();

        // k: tabname v: 具体数据
        Map<String, List<TbEntity>> excelEntityMap = new HashMap<>();
        for (File file : files) {
            if (file.isDirectory()) continue;
            System.out.println("---正在读取文件：" + file.getName());
            if (file.getName().endsWith(".xls")) {
                actionXLS(file, excelEntityMap);
            } else if (file.getName().endsWith(".xlsx")) {
                actionXLSX(file, excelEntityMap);
            }
        }

        for (Connection conn : connList) {
            conn.setAutoCommit(false);
            PreparedStatement pstmt;
            Set<String> tabNames = excelEntityMap.keySet();
            for (String tbName : tabNames) {
                System.out.println("----正在写入数据：" + tbName);
                pstmt = conn.prepareStatement("DESC " + tbName);
                ResultSet rs = null;
                try {
                    rs = pstmt.executeQuery();
                } catch (Exception e) {
                    continue; //说明服务器不存在这个表，直接跳过即可
                }
                List<String> columns = new ArrayList<>();
                while (rs.next()) {
                    columns.add(rs.getString(1));
                }
                // 清空表数据
                pstmt = conn.prepareStatement("truncate " + tbName);
                pstmt.execute();

                StringBuilder sb = new StringBuilder();
                sb.append("INSERT INTO ").append(tbName);
                sb.append("(");
                for (int i = 0; i < columns.size(); i++) {
                    sb.append("`").append(columns.get(i)).append("`");
                    if (i != columns.size() - 1) {
                        sb.append(",");
                    }
                }
                sb.append(") VALUES (");
                for (int i = 0; i < columns.size(); i++) {
                    sb.append("?");
                    if (i != columns.size() - 1) {
                        sb.append(",");
                    }
                }
                sb.append(")");
                pstmt = conn.prepareStatement(sb.toString());
                List<TbEntity> list = excelEntityMap.get(tbName);

                int count = 0;
                for (TbEntity entity : list) {
                    for (int i = 0; i < columns.size(); i++) {
                        pstmt.setObject(i + 1, entity.get(columns.get(i)));
                    }
                    pstmt.addBatch();
                    if (count++ > 50) {
                        pstmt.executeBatch();
                        conn.commit();
                        count = 0;
                    }
                }
                pstmt.executeBatch();
                conn.commit();
            }
            conn.close();
        }
        System.out.println("====总耗时：" + (System.currentTimeMillis() - t1) + " ms");
    }

    private static void actionXLS(File file, Map<String, List<TbEntity>> excelEntityMap) throws Exception{
        ZipSecureFile.setMinInflateRatio(0);
        Workbook wb = WorkbookFactory.create(file);
        int sheetCnt = wb.getNumberOfSheets();
        for (int i = 0; i < sheetCnt; i++) {
            Sheet sheet = wb.getSheetAt(i);
            String tbName = sheet.getSheetName();
            if (!tbName.startsWith("tab_")) {
                continue;
            }
            List<TbEntity> list = new ArrayList<>();
            List<String> names = new ArrayList<>();
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue;
                } else if (row.getRowNum() == 1) {
                    int start = row.getFirstCellNum();
                    int end = row.getLastCellNum();
                    Cell cell;
                    for (; start < end; start++) {
                        cell = row.getCell(start);
                        if (cell == null) {
                            break;//此时已经到头了
                        }
                        names.add(cell.getStringCellValue());
                    }
                } else {
                    int start = row.getFirstCellNum();
                    int end = row.getLastCellNum();
                    Cell cell;
                    TbEntity entity = new TbEntity();
                    int index = 0;
                    boolean add = true;
                    if (start == end || start != 0) {
                        add = false;
                    }
                    for (; start < end && add; start++) {
                        if (tbName.equals("tab_activities_template") && start == 5) {
                            System.out.println();
                        }
                        cell = row.getCell(start);
                        CellType type = cell.getCellType();
                        if (start == 0 && type  != CellType.NUMERIC) {
                            add = false;
                            break; //说明都是空白内容，仅保存格式样式而已
                        }
                        Object val = null;
                        if (type == CellType.BLANK) {
                            val = "";
                        } else if (type == CellType.STRING) {
                            val = cell.getStringCellValue();
                        } else if (type == CellType.NUMERIC) {
                            // 这个是日期
                            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                val = new java.sql.Timestamp(cell.getDateCellValue().getTime());
                            } else {
                                val = (int) cell.getNumericCellValue();
                            }
                        } else if (type == CellType.BOOLEAN) {
                            val = cell.getBooleanCellValue();
                        } else if (type == CellType.FORMULA) {
                            if (cell.getCachedFormulaResultType() == CellType.NUMERIC) {
                                val = (int) cell.getNumericCellValue();
                            } else if (cell.getCachedFormulaResultType() == CellType.STRING) {
                                val = cell.getStringCellValue();
                            }
                        } else {
                            // System.out.println("未知的类型：" + cell);
                        }
                        entity.set(names.get(index), val);
                        index++;
                        if (index >= names.size()) {
                            break;  // 没必要读取空白字段
                        }
                    }
                    Integer dd = 150119;
                    if (tbName.equals("tab_item") && entity.get("id") == dd) {
                        System.out.println(entity);
                    }
                    if (add) list.add(entity);
                }
            }
            excelEntityMap.put(tbName, list);
        }
        wb.close();
    }

    private static void actionXLSX(File file, Map<String, List<TbEntity>> excelEntityMap) throws Exception{
        actionXLS(file, excelEntityMap);
    }

    static class TbEntity {
        private Map<String, Object> valueMap = new HashMap<>();

        public void set(String key, Object val) {
            valueMap.put(key, val);
        }

        private Object get(String key) {
            return valueMap.get(key);
        }

        public String toString() {
            return valueMap.toString();
        }
    }
}
