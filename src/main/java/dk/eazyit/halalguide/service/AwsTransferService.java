package dk.eazyit.halalguide.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.transfer.TransferManager;
import dk.eazyit.halalguide.domain.Location;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Privat
 * Date: 05/02/16
 * Time: 14.20
 * To change this template use File | Settings | File Templates.
 */
@Service
public class AwsTransferService {

    @Autowired
    private AmazonS3 amazonS3;

    public void uploadPicture(Location location, MultipartFile file) throws IOException {

        String bucket = String.valueOf("halalguide/" + location.getId());

        String fileName = String.valueOf(location.getId() + "-" + DateTime.now().getMillis());

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        TransferManager transferManager = new TransferManager(this.amazonS3);

        transferManager.upload(bucket, fileName, file.getInputStream(), metadata);
    }
}
