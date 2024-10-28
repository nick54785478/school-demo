package com.example.demo.infra.store;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;

/**
 * 透過 Mongo DB 的 GridFS 儲存文件
 */
@Service
public class GridFSService {

	@Autowired
	private GridFSBucket gridFSBucket;

	/**
	 * 上傳 byte 資料到 MongoDB 的 GridFS 中
	 *
	 * @param fileName    文件名稱
	 * @param fileContent byte 資料
	 * @return 上傳文件的 ObjectId
	 */
	public ObjectId uploadFile(String fileName, byte[] fileContent) {
		GridFSUploadOptions options = new GridFSUploadOptions().chunkSizeBytes(358400); // 設置每個塊的大小
		InputStream inputStream = new ByteArrayInputStream(fileContent);
		return gridFSBucket.uploadFromStream(fileName, inputStream, options);
	}

	/**
	 * 上傳 InputStream 文件到 MongoDB 的 GridFS 中
	 * 
	 * @param fileName    文件名稱
	 * @param inputStream 文件的 InputStream
	 * @return 上傳文件的 ObjectId
	 */
	public ObjectId uploadFile(String fileName, InputStream inputStream) {
		GridFSUploadOptions options = new GridFSUploadOptions().chunkSizeBytes(358400); // 設置每個塊的大小
		return gridFSBucket.uploadFromStream(fileName, inputStream, options);
	}
	
	/**
     * 透過文件 ID 取出文件，並以 byte array 返回
     *
     * @param fileId 文件的 ObjectId
     * @return 文件的 byte[] 資料
     */
    public byte[] downloadFile(ObjectId fileId) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        gridFSBucket.downloadToStream(fileId, outputStream);
        return outputStream.toByteArray();
    }
    
    /**
     * 透過文件 ID 取出文件，並以 InputStream 返回
     *
     * @param fileId 文件的 ObjectId
     * @return 文件的 InputStream 資料
     */
    public InputStream downloadFileAsInputStream(ObjectId fileId) {
        byte[] downloadFile = this.downloadFile(fileId);
        return new ByteArrayInputStream(downloadFile);
    }

    /**
     * 透過文件 ID 列表取出文件，並以 InputStream 返回
     *
     * @param fileIds 文件的 ObjectId
     * @return 文件的 InputStream 資料列表
     */
    public List<InputStream> downloadFilesAsInputStream(List<ObjectId> fileIds) {
    	 List<InputStream> inputStreamList = new ArrayList<>();
         for (ObjectId objectId : fileIds) {
             InputStream inputStream = gridFSBucket.openDownloadStream(objectId);
             inputStreamList.add(inputStream);  // 加入 InputStream 列表
         }
         return inputStreamList;
    } 
    


}
