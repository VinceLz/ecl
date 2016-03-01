package com.xawl.netdisk.file.service;

import java.sql.SQLException;
import java.util.List;

import com.xawl.netdisk.file.dao.FileDao;
import com.xawl.netdisk.file.domain.File;

public class FileService {
		private FileDao fileDao=new FileDao();
		
		
		public List<File> findByCf(String cf)
		{
			try {
				return fileDao.findByCf(cf);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		
		public File findByFid(String fid)
		{
			try {
				return fileDao.findByFid(fid);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		
		
		public void upLoadFile(File file)
		{
			try {
				fileDao.upLoadFile(file);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		
		public void writecf(String fid,String cf)
		{
			try {
				fileDao.writecf(fid, cf);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		
		
		public Boolean deleteByFid(String fid)
		{
			try {
				return fileDao.deleteByFid(fid);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		
		public File findByHash(String hash)
		{
			try {
				return fileDao.findByHash(hash);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		
		public File findByPath(String path)
		{
			try {
				return fileDao.findByFpath(path);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		//修改downcount
		
		public void editcount(String count,String fid)
		{
			try {
				fileDao.editFile(count, fid);
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
}
