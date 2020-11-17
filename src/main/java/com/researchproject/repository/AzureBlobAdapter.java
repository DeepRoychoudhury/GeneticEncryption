package com.researchproject.repository;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import com.azure.storage.blob.BlobClientBuilder;
import com.azure.storage.blob.models.BlobProperties;

@Service
public class AzureBlobAdapter {

    @Autowired
    BlobClientBuilder client;
    
    //Saving file to Azure blob storage
    public boolean upload(String file, String blobName) {
        if(file != null && file.length() > 0) {
			InputStream dataStream = new ByteArrayInputStream(file.getBytes(StandardCharsets.UTF_8));
			client.blobName(blobName).buildClient().upload(dataStream, file.length());
			return true;
        }
        return false;
    }

    public String getFile(String name) {
        try {
            File temp = new File(name);
            BlobProperties properties = client.blobName(name).buildClient().downloadToFile(temp.getPath());
            String s = Files.readAllLines(Paths.get(temp.getPath())).toString().replace("[", "").replace("]", "");
			/*
			 * byte[] content = Files.readAllBytes(Paths.get(temp.getPath()));
			 * temp.delete();
			 */
            return s;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteFile(String name) {
        try {
            client.blobName(name).buildClient().delete();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
}
