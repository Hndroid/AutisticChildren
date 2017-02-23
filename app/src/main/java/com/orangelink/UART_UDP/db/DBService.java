package com.orangelink.UART_UDP.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DBService {

	private SQLiteDatabase db;
	private DBManager dbManager;

	public DBService(Context context) {
		dbManager = new DBManager(context);
	}

//	public List<AreaBean> getParentAreaList() {
//		db = dbManager.openDatabase();
//		String sql = "select * from area where pcode = 0 and code != -1 and code != 9000000000";
//		Cursor cursor = db.rawQuery(sql, new String[] {});
//		List<AreaBean> list = new ArrayList<AreaBean>();
//		while (cursor.moveToNext()) {
//			AreaBean c = new AreaBean();
//			c.code = cursor.getString(cursor.getColumnIndex("CODE"));
//			c.pcode = cursor.getString(cursor.getColumnIndex("PCODE"));
//			c.hieraechy = cursor.getString(cursor.getColumnIndex("HIERAECHY"));
//			c.name = cursor.getString(cursor.getColumnIndex("NAME"));
//			c.level1name = cursor.getString(cursor.getColumnIndex("LEVEL1NAME"));
//			c.level2name = cursor.getString(cursor.getColumnIndex("LEVEL2NAME"));
//			c.level3name = cursor.getString(cursor.getColumnIndex("LEVEL3NAME"));
//			c.level4name = cursor.getString(cursor.getColumnIndex("LEVEL4NAME"));
//			c.level5name = cursor.getString(cursor.getColumnIndex("LEVEL5NAME"));
//			c.seftid = cursor.getString(cursor.getColumnIndex("SEFTID"));
//			c.fullname = cursor.getString(cursor.getColumnIndex("FULLNAME"));
//			c.phoneticizeab = cursor.getString(cursor.getColumnIndex("PHONETICIZEAB"));
//			c.phoneticize = cursor.getString(cursor.getColumnIndex("PHONETICIZE"));
//			list.add(c);
//		}
//		if (cursor != null) {
//			cursor.close();
//		}
//		if (null != db) {
//			db.close();
//		}
//		return list;
//	}
//
//	public AreaBean getAreaByCode(String code) {
//		db = dbManager.openDatabase();
//		String sql = "select * from area where code = ?";
//		Cursor cursor = db.rawQuery(sql, new String[] { code });
//		AreaBean c = new AreaBean();
//		while (cursor.moveToNext()) {
//			c.code = cursor.getString(cursor.getColumnIndex("CODE"));
//			c.pcode = cursor.getString(cursor.getColumnIndex("PCODE"));
//			c.hieraechy = cursor.getString(cursor.getColumnIndex("HIERAECHY"));
//			c.name = cursor.getString(cursor.getColumnIndex("NAME"));
//			c.level1name = cursor.getString(cursor.getColumnIndex("LEVEL1NAME"));
//			c.level2name = cursor.getString(cursor.getColumnIndex("LEVEL2NAME"));
//			c.level3name = cursor.getString(cursor.getColumnIndex("LEVEL3NAME"));
//			c.level4name = cursor.getString(cursor.getColumnIndex("LEVEL4NAME"));
//			c.level5name = cursor.getString(cursor.getColumnIndex("LEVEL5NAME"));
//			c.seftid = cursor.getString(cursor.getColumnIndex("SEFTID"));
//			c.fullname = cursor.getString(cursor.getColumnIndex("FULLNAME"));
//			c.phoneticizeab = cursor.getString(cursor.getColumnIndex("PHONETICIZEAB"));
//			c.phoneticize = cursor.getString(cursor.getColumnIndex("PHONETICIZE"));
//			break;
//		}
//		if (cursor != null) {
//			cursor.close();
//		}
//		if (null != db) {
//			db.close();
//		}
//		return c;
//	}
//
//	public String getAreaCode(String name) {
//		db = dbManager.openDatabase();
//		String sql = "select * from area where name = ?";
//		Cursor cursor = db.rawQuery(sql, new String[] { name });
//		AreaBean c = new AreaBean();
//		while (cursor.moveToNext()) {
//			c.code = cursor.getString(cursor.getColumnIndex("CODE"));
//			c.pcode = cursor.getString(cursor.getColumnIndex("PCODE"));
//			c.hieraechy = cursor.getString(cursor.getColumnIndex("HIERAECHY"));
//			c.name = cursor.getString(cursor.getColumnIndex("NAME"));
//			c.level1name = cursor.getString(cursor.getColumnIndex("LEVEL1NAME"));
//			c.level2name = cursor.getString(cursor.getColumnIndex("LEVEL2NAME"));
//			c.level3name = cursor.getString(cursor.getColumnIndex("LEVEL3NAME"));
//			c.level4name = cursor.getString(cursor.getColumnIndex("LEVEL4NAME"));
//			c.level5name = cursor.getString(cursor.getColumnIndex("LEVEL5NAME"));
//			c.seftid = cursor.getString(cursor.getColumnIndex("SEFTID"));
//			c.fullname = cursor.getString(cursor.getColumnIndex("FULLNAME"));
//			c.phoneticizeab = cursor.getString(cursor.getColumnIndex("PHONETICIZEAB"));
//			c.phoneticize = cursor.getString(cursor.getColumnIndex("PHONETICIZE"));
//			break;
//		}
//		if (cursor != null) {
//			cursor.close();
//		}
//		if (null != db) {
//			db.close();
//		}
//		return c.code;
//	}
//
//	public String getAreaName(String code) {
//		db = dbManager.openDatabase();
//		String sql = "select * from area where code = ?";
//		Cursor cursor = db.rawQuery(sql, new String[] { code });
//		AreaBean c = new AreaBean();
//		while (cursor.moveToNext()) {
//			c.code = cursor.getString(cursor.getColumnIndex("CODE"));
//			c.pcode = cursor.getString(cursor.getColumnIndex("PCODE"));
//			c.hieraechy = cursor.getString(cursor.getColumnIndex("HIERAECHY"));
//			c.name = cursor.getString(cursor.getColumnIndex("NAME"));
//			c.level1name = cursor.getString(cursor.getColumnIndex("LEVEL1NAME"));
//			c.level2name = cursor.getString(cursor.getColumnIndex("LEVEL2NAME"));
//			c.level3name = cursor.getString(cursor.getColumnIndex("LEVEL3NAME"));
//			c.level4name = cursor.getString(cursor.getColumnIndex("LEVEL4NAME"));
//			c.level5name = cursor.getString(cursor.getColumnIndex("LEVEL5NAME"));
//			c.seftid = cursor.getString(cursor.getColumnIndex("SEFTID"));
//			c.fullname = cursor.getString(cursor.getColumnIndex("FULLNAME"));
//			c.phoneticizeab = cursor.getString(cursor.getColumnIndex("PHONETICIZEAB"));
//			c.phoneticize = cursor.getString(cursor.getColumnIndex("PHONETICIZE"));
//			break;
//		}
//		if (cursor != null) {
//			cursor.close();
//		}
//		if (null != db) {
//			db.close();
//		}
//		return c.name;
//	}
//
//	public List<AreaBean> getHotAreaList(String[] hotareaNames) {
//		if (hotareaNames == null || hotareaNames.length == 0) {
//			return null;
//		}
//		db = dbManager.openDatabase();
//		String sql = "select * from area where name = ?";
//		for (int i = 0; i < hotareaNames.length - 1; i++) {
//			sql += " or name = ?";
//		}
//		Cursor cursor = db.rawQuery(sql, hotareaNames);
//		List<AreaBean> list = new ArrayList<AreaBean>();
//		while (cursor.moveToNext()) {
//			AreaBean c = new AreaBean();
//			c.code = cursor.getString(cursor.getColumnIndex("CODE"));
//			c.pcode = cursor.getString(cursor.getColumnIndex("PCODE"));
//			c.hieraechy = cursor.getString(cursor.getColumnIndex("HIERAECHY"));
//			c.name = cursor.getString(cursor.getColumnIndex("NAME"));
//			c.level1name = cursor.getString(cursor.getColumnIndex("LEVEL1NAME"));
//			c.level2name = cursor.getString(cursor.getColumnIndex("LEVEL2NAME"));
//			c.level3name = cursor.getString(cursor.getColumnIndex("LEVEL3NAME"));
//			c.level4name = cursor.getString(cursor.getColumnIndex("LEVEL4NAME"));
//			c.level5name = cursor.getString(cursor.getColumnIndex("LEVEL5NAME"));
//			c.seftid = cursor.getString(cursor.getColumnIndex("SEFTID"));
//			c.fullname = cursor.getString(cursor.getColumnIndex("FULLNAME"));
//			c.phoneticizeab = cursor.getString(cursor.getColumnIndex("PHONETICIZEAB"));
//			c.phoneticize = cursor.getString(cursor.getColumnIndex("PHONETICIZE"));
//			list.add(c);
//		}
//		if (cursor != null) {
//			cursor.close();
//		}
//		if (null != db) {
//			db.close();
//		}
//		return list;
//	}
//
//	public List<AreaBean> getChildAreaList(String pcode) {
//		db = dbManager.openDatabase();
//		String sql = "select * from area where pcode = ?";
//		Cursor cursor = db.rawQuery(sql, new String[] { pcode });
//		List<AreaBean> list = new ArrayList<AreaBean>();
//		while (cursor.moveToNext()) {
//			AreaBean c = new AreaBean();
//			c.code = cursor.getString(cursor.getColumnIndex("CODE"));
//			c.pcode = cursor.getString(cursor.getColumnIndex("PCODE"));
//			c.hieraechy = cursor.getString(cursor.getColumnIndex("HIERAECHY"));
//			c.name = cursor.getString(cursor.getColumnIndex("NAME"));
//			c.level1name = cursor.getString(cursor.getColumnIndex("LEVEL1NAME"));
//			c.level2name = cursor.getString(cursor.getColumnIndex("LEVEL2NAME"));
//			c.level3name = cursor.getString(cursor.getColumnIndex("LEVEL3NAME"));
//			c.level4name = cursor.getString(cursor.getColumnIndex("LEVEL4NAME"));
//			c.level5name = cursor.getString(cursor.getColumnIndex("LEVEL5NAME"));
//			c.seftid = cursor.getString(cursor.getColumnIndex("SEFTID"));
//			c.fullname = cursor.getString(cursor.getColumnIndex("FULLNAME"));
//			c.phoneticizeab = cursor.getString(cursor.getColumnIndex("PHONETICIZEAB"));
//			c.phoneticize = cursor.getString(cursor.getColumnIndex("PHONETICIZE"));
//			list.add(c);
//		}
//		if (cursor != null) {
//			cursor.close();
//		}
//		if (null != db) {
//			db.close();
//		}
//		return list;
//	}

}
