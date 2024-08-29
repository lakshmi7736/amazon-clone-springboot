package com.vonnueAmazonClone.amazonClone.Handle;

import java.io.IOException;

public class ImageProcessingException extends RuntimeException {
    public ImageProcessingException(String message) {
        super(message);
    }

    public ImageProcessingException(String message, Throwable cause) {
        super(message, cause);
    }

}