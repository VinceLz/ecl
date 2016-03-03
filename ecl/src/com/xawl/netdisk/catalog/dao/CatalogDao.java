package com.xawl.netdisk.catalog.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;

import com.xawl.netdisk.catalog.domain.Catalog;
import com.xawl.netdisk.file.dao.FileDao;
import com.xawl.netdisk.file.domain.File;

public class CatalogDao {
	private QueryRunner qr = new TxQueryRunner();
	private FileDao fileDao = new FileDao();

	// 给我一个cid，就可以查找下面一级的目录和文件
	public Catalog findByCidToCatalog(String cid) throws SQLException {
		String sql = "select * from catalog where cId=?";
		Map<String, Object> beanMap = qr.query(sql, new MapHandler(), cid);
		Catalog catalog = CommonUtils.toBean(beanMap, Catalog.class);
		// 还有pid cF 这个属性就行映射
		if (beanMap.get("pId") != null) {
			// 这不是最顶层目录 需要封装他的父级目录 只有pid
			Catalog c = new Catalog();
			c.setcId((String) beanMap.get("pId"));
			catalog.setParent(c);
		}
		sql = "select * from catalog where pId=?";
		List<Map<String, Object>> beanMapList = qr.query(sql,
				new MapListHandler(), catalog.getcId());

		List<Catalog> cataList = this.listToBean(beanMapList);

		catalog.setChildren(cataList);
		// 开始封装catalog 的子文件

		String cf = (String) beanMap.get("cF");
		List<File> myfile = fileDao.findByCf(cf);
		catalog.setMyFile(myfile);
		return catalog;
	}

	// 给我一个list map 集合 我帮你转换成 list bean map-bean
	public List<Catalog> listToBean(List<Map<String, Object>> map) {
		List<Catalog> toList = new ArrayList<Catalog>();
		for (Map<String, Object> mymap : map) {
			Catalog c = CommonUtils.toBean(mymap, Catalog.class);
			if (mymap.get("pId") != null) {
				Catalog c1 = new Catalog();
				c1.setcId((String) mymap.get("pId"));
				c.setParent(c1);
			}
			toList.add(c);
		}
		return toList;
	}

	// 通过cid 你bean 一个对象

	public Catalog findByCid(String cid) throws SQLException {
		String sql = "select * from catalog where cId=?";
		return qr.query(sql, new BeanHandler<Catalog>(Catalog.class), cid);
	}

	public void createCatalog(Catalog c) throws SQLException {
		String sql = "insert into catalog (cId,pId,cName,cDate,isShare) values(?,?,?,?,?)";
		Object[] para = { c.getcId(), c.getParent().getcId(), c.getcName(),
				c.getcDate(), c.getIsShare() };
		qr.update(sql, para);
		return;
	}

	// create root
	public void createRootCatalog(Catalog c) throws SQLException {
		String sql = "insert into catalog (cId,cName,cDate,cF) values(?,?,?,?)";
		Object[] para = { c.getcId(), c.getcName(), c.getcDate(), c.getCf() };
		qr.update(sql, para);
		return;
	}

	// 通过cid 找到cf值
	public String cidTocf(String cid) throws SQLException {
		String sql = "select cf from catalog where cId=?";
		return (String) qr.query(sql, new ScalarHandler(), cid);
	}

	// 写入cf值

	public void intoCf(String cid, String cf) throws SQLException {
		String sql = "update catalog set cF=? where cId=?";
		qr.update(sql, cf, cid);
		return;
	}

	/*
	 * 给cid 删除他后面所有的文件和文件夹 需要遍历数 递归
	 */
	public void deleteByCatalog(String cid, ServletContext context)
			throws SQLException {

		Catalog cata = this.findByCidToCatalog(cid);

		// 先删除该目录下的文件
		if (cata.getMyFile() != null) {

			System.out.println("开始删除纯文件");
			fileDao.removeAll(cata.getMyFile(), context);
			// 还要删除本地文件
		}

		String sql = "select cF from catalog where cId=?";
		String cf = (String) qr.query(sql, new ScalarHandler(), cata.getcId());
		sql = "delete from catalog_file where cf=?";
		qr.update(sql, cf);

		sql = "delete from catalog where cId=?";
		qr.update(sql, cata.getcId());
		List<Catalog> chileList = cata.getChildren();

		for (Catalog c : chileList) {
			deleteByCatalog(c.getcId(), context);
		}

		return;
	}

	// 通过cid 查找他的上下级
	public Catalog findAllCatalog(String cid) throws SQLException {
		String sql = "select * from catalog where cId=?";
		// 打包为Map集合，封装子类
		Map<String, Object> mapbean = qr.query(sql, new MapHandler(), cid);
		Catalog cata = CommonUtils.toBean(mapbean, Catalog.class);
		if (mapbean.get("pId") != null) {
			// 说明他有上级
			Catalog parent = findByCid((String) mapbean.get("pId"));
			cata.setParent(parent);
			// 查看是否有直接子类 有就封装
			
		} 
		List<Catalog> child=chilList(cata.getcId());
		cata.setChildren(child);
		return cata;
	}

	// 根据用户名删除所有文件夹 文件信息
	public void deleteAll(String root) throws SQLException {
		List<Catalog> child=chilList(root);
		//删除该目录的file文件
		deleteFromCatalog(root);
		//开始遍历递归
		for(Catalog c:child){
			deleteAll(c.getcId());
		}
		deleteFromCatalog1(root);
	}

	// 通过pid查询目录类
	public List<Catalog> chilList(String pid) throws SQLException {
		String sql = "select * from catalog where pId=?";
		List<Map<String, Object>> map = qr
				.query(sql, new MapListHandler(), pid);
		List<Catalog> Catalog1 = new ArrayList<Catalog>();
		Catalog c1 = null;
		for (Map<String, Object> m : map) {
			if (m.get("pId") != null) {
				c1 = CommonUtils.toBean(m, Catalog.class);
				Catalog c = this.findByCid((String) m.get("pId"));
				c1.setParent(c);
			}
			Catalog1.add(c1);
		}
		return Catalog1;
	}
	//删除一个目录下的file
	public void deleteFromCatalog(String cid) throws SQLException{
		String sql="select cF from catalog where cId=?";
		String cf = (String) qr.query(sql, new ScalarHandler(), cid);
		//开始删除file文件
		sql="select * from catalog_file where cf=?";
		List<Map<String,Object>> map=qr.query(sql, new MapListHandler(),cf);
		for(Map<String,Object> m:map){
			String fid=(String) m.get("fid");
			deleteFromFile(fid);
		}
		deleteFromCatalog_file(cf);
	}
	
	
	public void deleteFromFile(String fid) throws SQLException {
			String sql="delete from file where fId=?";
			qr.update(sql,fid);
	}
	public void deleteFromCatalog_file(String cf) throws SQLException {
		String sql="delete from catalog_file where cf=?";
		qr.update(sql,cf);
}

	//通过cid删除数据库的catalog目录
	public void deleteFromCatalog1(String cid) throws SQLException{
		String sql="delete from catalog where cId=?";
		qr.update(sql,cid);
	}
	
	@Test
	public void Text() throws SQLException{
//		Catalog c=findAllCatalog("5QR6");
//		for(Catalog x:c.getChildren()){
//			System.out.println(x);
//		}
		deleteAll("vVEQ");
	}
	
	
	
	
}
