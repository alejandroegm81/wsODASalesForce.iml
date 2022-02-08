package db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;








public class CopyFile
{
  public void copyfile(String srFile, String dtFile) {
    try {
      File e = new File(srFile);
      File f2 = new File(dtFile);
      FileInputStream in = new FileInputStream(e);
      FileOutputStream out = new FileOutputStream(f2);
      byte[] buf = new byte[1024];
      
      int len;
      while ((len = in.read(buf)) > 0) {
        out.write(buf, 0, len);
      }
      
      in.close();
      out.close();
      System.out.println("File copied.");
    } catch (FileNotFoundException var9) {
      System.out.println(var9.getMessage() + " in the specified directory.");
      System.exit(0);
    } catch (IOException var10) {
      System.out.println(var10.getMessage());
    } 
  }
}
