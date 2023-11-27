package file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class DataProc {
  File file = null;

  public DataProc(String filename) {
    file = new File("D:/KDT_SSG/tmp(실습용)/" + filename + ".txt");
  }

  // 파일저장, 불러오기
  public void createFile() {
    try {
      if (file.createNewFile()) {
        System.out.println("파일 생성 성공");
      } else {
        System.out.println("파일 생성 실패");
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void dataSave(String arr[]) {
    try {
      PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));

      for (String s : arr) {
        pw.println(s);
      }
      pw.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  public String[] dataLoad() {
    BufferedReader br;
    try {
      br = new BufferedReader(new FileReader(file));

      // 데이터의 개수
      int count = 0;
      String str = "";
      while ((str = br.readLine()) != null) {
        count++;
      }
      br.close();

      // 배열할당
      String datas[] = new String[count];

      br = new BufferedReader(new FileReader(file));

      // 데이터 저장
      int c = 0;
      while ((str = br.readLine()) != null) {
        datas[c] = str;
        c++;
      }
      br.close();

    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}
