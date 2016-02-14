package dk.eazyit.halalguide.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.transfer.TransferManager;
import dk.eazyit.halalguide.domain.Location;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Privat
 * Date: 05/02/16
 * Time: 14.20
 * To change this template use File | Settings | File Templates.
 */
@Service
public class AWSFileService {

    private static final Logger logger = LoggerFactory.getLogger(AWSFileService.class);

    @Value("${aws.s3.endpoint}")
    private String endpoint;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Autowired
    private AmazonS3 amazonS3;

    public String uploadPicture(final Location entity, MultipartFile file) throws IOException {

        String bucket = String.valueOf(bucketName + "/" + entity.getId());

        String fileName = String.valueOf(UUID.randomUUID() + "-" + DateTime.now().getMillis() + ".png");

        final String ressourceUrl = "https://" + endpoint + "/" + bucket + "/" + fileName;

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        TransferManager transferManager = new TransferManager(this.amazonS3);

        logger.debug("Starting upload of picture " + ressourceUrl);

        transferManager.upload(bucket, fileName, file.getInputStream(), metadata);

        return ressourceUrl;
    }

}
