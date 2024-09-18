package kr.ac.kookmin.wink.planlist.global.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import kr.ac.kookmin.wink.planlist.global.exception.CustomException;
import kr.ac.kookmin.wink.planlist.global.exception.GlobalErrorCode;
import kr.ac.kookmin.wink.planlist.global.utils.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class S3Service {
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadImageFile(MultipartFile imageFile, String newFileName) {
        String originalName = imageFile.getOriginalFilename();
        String ext = originalName.substring(originalName.lastIndexOf("."));
        String changedName = newFileName + ext;

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("image/" + ext);
        metadata.setContentLength(imageFile.getSize());

        try {
            amazonS3.putObject(
                    new PutObjectRequest(bucket, changedName, imageFile.getInputStream(), metadata)
            );
        } catch (IOException e) {
            throw new CustomException(GlobalErrorCode.FILE_UPLOAD_FAILED, e);
        }

        return amazonS3.getUrl(bucket, changedName).toString();
    }

    public String uploadBase64Image(String imageBase64, String directory, String newFileName) {
        return uploadImageFile(ImageUtil.base64ToMultipartFile(newFileName, imageBase64), directory + newFileName);
    }
}
