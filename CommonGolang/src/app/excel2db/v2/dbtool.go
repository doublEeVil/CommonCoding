package v2

import (
	"database/sql"
	"log"

	_ "github.com/go-sql-driver/mysql"
)

type DB_INFO struct {
	User     string
	Password string
	DB_URL   string
	DB_Name  string
	DB_Mark  string
}

// 存链接用户名账号
var MY_DBINFO_MAP map[string]DB_INFO = nil

// 存真实链接
var MY_DB_MAP map[string]*sql.DB = make(map[string]*sql.DB)

func initDBInfos() map[string]DB_INFO {
	db1 := DB_INFO{
		"zjs",
		"123456",
		"127.0.0.1:3306",
		"ddd2h5_dev",
		"本地开发服",
	}
	db2 := DB_INFO{
		"zjs",
		"123456",
		"127.0.0.1:3306",
		"ddd2h5_dev",
		"本地开发服",
	}
	retMap := make(map[string]DB_INFO)
	retMap["1"] = db1
	retMap["2"] = db2
	return retMap
}

func getDb(dbid string) *sql.DB {
	if MY_DBINFO_MAP == nil {
		MY_DBINFO_MAP = initDBInfos()
	}

	db, isExist := MY_DB_MAP[dbid]
	if !isExist || db == nil {
		info, isExist := MY_DBINFO_MAP[dbid]
		if !isExist {
			log.Println("地址不存在 dbid: ", dbid)
			return nil
		}
		db, err := sql.Open("mysql", info.User+":"+info.Password+"@tcp("+info.DB_URL+")/"+info.DB_Name+"?charset=utf8")
		if err != nil {
			log.Fatal("数据库链接出错： ", err)
		}
		MY_DB_MAP[dbid] = db
		log.Println("----新打开数据库链接-----")
	}
	return MY_DB_MAP[dbid]
}

func getAllTables(dbid string) map[string]int {
	tabMap := make(map[string]int)
	rows, err := getDb(dbid).Query("show tables")
	if err != nil {
		log.Println("得到全部表名报错：", err)
	}
	for rows.Next() {
		var tabName string
		rows.Scan(&tabName)
		tabMap[tabName] = 0
	}
	rows.Close()
	return tabMap
}

func getColumnName(tbName string, dbid string, colMap map[string]int) error {
	rows, err := getDb(dbid).Query("select * from " + tbName + " limit 1")
	if err != nil {
		return err
	}
	//返回所有列
	cols, _ := rows.Columns()

	for _, col := range cols {
		colMap[col] = 0
	}
	rows.Close()
	return nil
}

func clearTable(tbName string, dbid string) {
	_, err := getDb(dbid).Exec(" DELETE FROM " + tbName)
	if err != nil {
		log.Println("清空表报错： ", err)
	}
}

func insertToDb(sql string, dbid string) {
	_, err := getDb(dbid).Exec(sql)
	if err != nil {
		log.Println("插入数据报错：", err)
	}
}
