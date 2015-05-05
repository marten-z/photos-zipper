package net.martenhz.photoszipper;

import java.io.File;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.io.FilenameUtils;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifDirectoryBase;
import com.drew.metadata.exif.ExifIFD0Directory;

public class Utils {

	public static void renameFile(final File file, final StringBuilder newFileName, int iteration) {
		System.out.println("New file name: " + newFileName.toString());
		final boolean isRenamed = file.renameTo(new File(newFileName.toString()));
		
		// File possibly already exists (for example when using burst mode on a camera, it can shoot multiple pictures per second)
	    if(!isRenamed) {
	    	iteration++;
	    	
	    	final String extension = FilenameUtils.getExtension(file.getAbsolutePath());
	    	
	    	if(iteration == 1) {
	    		newFileName.delete(newFileName.length() - (extension.length() + 1), newFileName.length());
	    		newFileName.append("_01");
	    		newFileName.append(".");
	    		newFileName.append(extension);
	    	} else {
	         	if(iteration < 10) {
	         		newFileName.delete(newFileName.length() - 1 - (extension.length() + 1), newFileName.length());
	         	} else {
	         		newFileName.delete(newFileName.length() - String.valueOf(iteration).length() - (extension.length() + 1), newFileName.length());
	         	}
	         	newFileName.append(iteration);
	         	newFileName.append(".");
	         	newFileName.append(extension);
	    	}
	    }
	}

	public static void renameFile(final File file, final StringBuilder newFileName) {
		renameFile(file, newFileName, 0);
	}

	public static void renameFileByDate(final File file) throws Exception {
			if (file.exists()) {
				try {
					System.out.println( "Trying to rename file: " + file.getAbsolutePath());
					
					final Metadata meta = ImageMetadataReader.readMetadata(file);
		        
			        // Read Exif Data
		            final Directory directory = meta.getFirstDirectoryOfType(ExifIFD0Directory.class);
		            
		            if(directory != null) {
		                // Read the date
		            	final Date date = directory.getDate(ExifDirectoryBase.TAG_DATETIME);
		            	final DateFormat df = DateFormat.getDateInstance();
		                df.format(date);
		                
		                final Calendar calendar = df.getCalendar();	                	                
		                calendar.setTimeInMillis(calendar.getTimeInMillis() - (calendar.get(Calendar.ZONE_OFFSET) + calendar.get(Calendar.DST_OFFSET)));
		                
		                // Do the actual renaming
		                final StringBuilder newFileName = new StringBuilder(FilenameUtils.getFullPath(file.getAbsolutePath()));
		                newFileName.append(calendar.get(Calendar.YEAR));
		                newFileName.append("-");
		                
		                final int month = (calendar.get(Calendar.MONTH) + 1);
		                final int day = calendar.get(Calendar.DAY_OF_MONTH);
		                final int hour = calendar.get(Calendar.HOUR_OF_DAY);
		                final int minute = calendar.get(Calendar.MINUTE);
		                final int second = calendar.get(Calendar.SECOND);
		                
		                newFileName.append(month < 10 ? "0" + month : month);
		                newFileName.append("-");
		                newFileName.append(day < 10 ? "0" + day : day);
		                newFileName.append("_");
		                newFileName.append(hour < 10 ? "0" + hour : hour);
		                newFileName.append(":");
		                newFileName.append(minute < 10 ? "0" + minute : minute);
		                newFileName.append(":");
		                newFileName.append(second < 10 ? "0" + second : second);
		                newFileName.append(".");
		                newFileName.append(FilenameUtils.getExtension(file.getAbsolutePath()));
		                
		                renameFile(file, newFileName);	               
		            }
				} catch (ImageProcessingException e) {
					e.getMessage();
	//				throw e;
				}
		        
		    } else {
		        throw new Exception("Unable to read file: " + file.getAbsolutePath());
		    }
		}

	public static void renamePictures(final String path) throws Exception {
		final File folder = new File(path);
		
		if(!folder.exists() || !folder.isDirectory() || !folder.canRead()) {
			throw new Exception("Folder not readable");
		}
		
		for(final File file : folder.listFiles()) {
			if(file.isDirectory()) {
				renamePictures(file.getAbsolutePath());
			} else if (file.isFile()) {
				renameFileByDate(file);
			}
		}
		
	}

}
