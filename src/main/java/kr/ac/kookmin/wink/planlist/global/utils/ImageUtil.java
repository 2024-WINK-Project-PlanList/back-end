package kr.ac.kookmin.wink.planlist.global.utils;

import kr.ac.kookmin.wink.planlist.global.exception.CustomException;
import kr.ac.kookmin.wink.planlist.global.exception.GlobalErrorCode;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;

public class ImageUtil {
    public static MultipartFile base64ToMultipartFile(String name, String base64) {
//        Assert.notBlank(name);
//        Assert.notBlank(base64);
//        Assert.isTrue(base64.startsWith("data:image/"));
        int colon = base64.indexOf(":");
        int semicolon = base64.indexOf(";");
        System.out.println("base64: " + base64);
        System.out.println("colon: " + colon);
        System.out.println("semicolon: " + semicolon);
        String mimeType = base64.substring(colon + 1, semicolon);
        String base64WithoutHeader = base64.substring(semicolon + 8);

        byte[] bytes = Base64.getDecoder().decode(base64WithoutHeader);

        String extension = ".jpg";

        try {
            extension = MimeTypes.getDefaultMimeTypes().forName(mimeType).getExtension();
        } catch (MimeTypeException e) {
            throw new CustomException(GlobalErrorCode.FILE_UPLOAD_FAILED, e);
        }
        String filename = name + extension;
        return new MockMultipartFile(filename, filename, mimeType, bytes);
    }
}
