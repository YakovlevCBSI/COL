package com.cbsi.fcat.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.microsoft.azure.storage.*;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;

public class BlobUtil {
	public static final String AccountInfo = "DefaultEndpointsProtocol=http;" +
										    "AccountName=fcat;" +
										    "AccountKey=QW61pH61vWqi/7YvwRzApVml/S9Ag+AwvfC+kzN8U5NQsAzH7RVWoUmtfoCU9kpvfr0WzuoYOylJhR12ReAVIQ==";

			
	public static final String ContainerName = "files";

	public static CloudBlobContainer container;
	
	public static void getContainer(){
		try{
			CloudStorageAccount storageAccount = CloudStorageAccount.parse(AccountInfo);
			CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
			
			container = blobClient.getContainerReference(ContainerName);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void uploadFile(String filePath){
		try {
			CloudBlockBlob blob = container.getBlockBlobReference(new File(filePath).getName());
			File source = new File(filePath);
		
			blob.upload(new FileInputStream(source), source.length());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
