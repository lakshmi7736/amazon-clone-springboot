package com.vonnueAmazonClone.amazonClone.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

public interface imageProcessing {
    //    static final long MAX_IMAGE_SIZE = 1024 * 1024 * 1024; // 1 GB (adjust the size as needed)
    static byte[] serializeImageList(List<byte[]> imageDataList) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(imageDataList);
        }
        return byteArrayOutputStream.toByteArray();
    }


    // This method deserializes a single byte[] blob into a List<byte[]> assuming
// the blob contains serialized list of images
    static List<byte[]> deserializeImageBlob(byte[] imageBlob) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(imageBlob))) {
            return (List<byte[]>) ois.readObject();
        }
    }

//    static List<byte[]> deserializeImageBlob(byte[] imageBlob) throws IOException, ClassNotFoundException {
//        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(imageBlob))) {
//            return (List<byte[]>) ois.readObject();
//        }
//    }
}






