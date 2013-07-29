package viper.ui.user;

import static com.googlecode.javacv.cpp.opencv_highgui.*;
import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;
import static com.googlecode.javacv.cpp.opencv_contrib.*;
import java.io.File;
import java.io.FilenameFilter;

import viper.ui.main.StoredPreferences;

public class OpenCVFaceRecognizer implements StoredPreferences {
	private String dir;
	private String image;
	
	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public OpenCVFaceRecognizer() {
		
	}
	
	public OpenCVFaceRecognizer(String dir, String image) {
		this.dir = dir;
		this.image = image;
	}
    public boolean isMatch() {
        IplImage testImage = cvLoadImage(image);

        File root = new File(dir);

        FilenameFilter pngFilter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".jpg");
            }
        };

        File[] imageFiles = root.listFiles(pngFilter);
        System.out.println("imageFiles.length: " + imageFiles.length);
        MatVector images = new MatVector(imageFiles.length);

        int[] labels = new int[imageFiles.length];
        String[] filename = new String[imageFiles.length];
        
        int counter = 0;
        int label;

        IplImage img;
        IplImage grayImg;

        for (File image : imageFiles) {
            img = cvLoadImage(image.getAbsolutePath());

            grayImg = IplImage.create(img.width(), img.height(), IPL_DEPTH_8U, 1);

            cvCvtColor(img, grayImg, CV_BGR2GRAY);

            images.put(counter, grayImg);

            labels[counter] = counter;
            String s = image.getName();
            s = s.substring(s.lastIndexOf("/") + 1, s.lastIndexOf("."));
            filename[counter] = s;
            counter++;
        }

        IplImage greyTestImage = IplImage.create(testImage.width(), testImage.height(), IPL_DEPTH_8U, 1);

        FaceRecognizer faceRecognizer = createFisherFaceRecognizer();
        // FaceRecognizer faceRecognizer = createEigenFaceRecognizer();
        // FaceRecognizer faceRecognizer = createLBPHFaceRecognizer()

        faceRecognizer.train(images, labels);

        cvCvtColor(testImage, greyTestImage, CV_BGR2GRAY);

        int predictedLabel = faceRecognizer.predict(greyTestImage);

        System.out.println("Predicted label: " + filename[predictedLabel]);
        
        if (filename[predictedLabel].equals(PREF.get(USERNAME, null))) {
        	return true;
        }
        else {
        	return false;
        }
        	
    }
}
