package mr.rimrowad.repository;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Repository;

import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Repository
public class FileRepository {
	private static final String BUCKET = "bucketeer-62c6d841-5362-4b75-9197-fb74eb6c39f8";
	private AmazonS3 s3client;

	@PostConstruct
	public void onInit() {
		AWSCredentials credentials = new BasicAWSCredentials(
				  "AKIAJGXHAELQBHGYNUIQ", 
				  "TuXoMaaDKtNZjXYq4AI2Skk43i2ye5ZwxieQNP1/"
				);
		s3client = AmazonS3ClientBuilder
				  .standard()
				  .withCredentials(new AWSStaticCredentialsProvider(credentials))
				  .withRegion(Regions.EU_WEST_1)
				  .build();
	}
	public void putFile(String fileKey, byte[] file) {
		s3client.putObject(BUCKET, fileKey, new ByteArrayInputStream(file), null);
	}
	public byte[] getFile(String fileKey) {
		try {
			return IOUtils.toByteArray(s3client.getObject(BUCKET, fileKey).getObjectContent());
		} catch (SdkClientException | IOException e) {
			return null;
		}
	}
}
