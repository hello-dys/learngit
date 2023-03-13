package P1;

import java.io.*;
import java.util.Scanner;

public class MagicSquare {
    public static boolean generateMagicSquare(int n) {
        if (n % 2 == 0 || n <= 0){
            System.out.println("输入的n不合法");
            return false;
        }
        isLegalMagicSquare("src\\P1\\txt\\6.txt");
        int magic[][] = new int[n][n];
        int row = 0, col = n / 2, i, j, square = n * n;
        for (i = 1; i <= square; i++) {
            magic[row][col] = i;
            if (i % n == 0)
                row++;
            else {
                if (row == 0)
                    row = n - 1;
                else
                    row--;
                if (col == (n - 1))
                    col = 0;
                else
                    col++;
            }
        }
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++)
                System.out.print(magic[i][j] + "\t");
            System.out.println();
        }
        File file=new File("src\\P1\\txt\\6.txt");
        FileWriter f=null;
        BufferedWriter f1=null;
        try {
            f=new FileWriter("src\\P1\\txt\\6.txt");
            f1=new BufferedWriter(f);
            for (i = 0; i < n; i++) {
                for (j = 0; j < n; j++)
                    f1.write(String.valueOf(magic[i][j])+'\t');
                f1.newLine();
            }
        } catch (Exception e) {
        }finally {
            try {
                f1.close();
                f.close();//关闭文件
            } catch (Exception e2) {
            }
        }
        return true;
    }
    public static boolean isLegalMagicSquare(String fileName){
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        int[][] number = new int[1000][1000];
        int x = 0;
        //读取文件
        try {
            String str = "";
            fis = new FileInputStream(fileName);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            int j=0;
            while ((str = br.readLine()) != null) {
                String[] s = str.split("\t");
                for (int i = 0; i<s.length;i++){
                    if (s[i].matches("[0-9]*") == false){
                        System.out.println("矩阵中的某些数字并非正整数");
                        return false;
                    }
                }
                x = s.length;
                for (int i = 0; i<s.length;i++){
                    number[j][i]  = Integer.valueOf(s[i]);
                }
                j++;
            }
//            for (int m=0; m<x;m++){
//                for (int n=0;n<x;n++){
//                    System.out.print(number[m][n]);
//                    System.out.print(" ");
//                }
//                System.out.println("");
//            }
        } catch (FileNotFoundException e) {
            System.out.println("找不到指定文件");
        } catch (IOException e) {
            System.out.println("读取文件失败");
        } finally {
            try {
                br.close();
                isr.close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        int sum = 0, i = 0, j = 0, n = x, temp = 0, f = 0, t = 0;
        //求第一行元素和
        for(i = 0; i < n; i++){
            sum += number[0][i];
        }
        // 判断每列元素和是否和第一行元素和相等
        for(i=1; i<n; i++) {
            temp = 0;
            for(j=0; j<n; j++) {
                temp += number[i][j];
            }
            if(temp != sum) {
                f = 1;
                break;
            }
        }
        // 判断对角线元素和是否和第一行元素和相等
        if(f == 0)
        {
            for(i=0; i<n; i++)
            {
                temp = 0;
                for(j=0; j<n; j++)
                {
                    temp += number[j][i];
                }
                if(temp != sum)
                {
                    f = 1;
                    break;
                }
            }
        }
        //判断反对角线元素和是否和第一行元素和相等
        temp = 0;
        if(f == 0)
        {
            for(i=0; i<n; i++)
            {
                temp += number[i][i];
                t += number[i][n-i-1];
            }
            if(temp != sum || t != sum) f = 1;
        }
        if (f == 0) {
            System.out.println("true");
            return true;
        }
        else{
            System.out.println("false");
            return false;
        }
    }
    public static void main(String[] args) {
        System.out.println("请输入n：");
        Scanner scan = new Scanner(System.in);
        int i = 0;
        if (scan.hasNextInt()) {
            i = scan.nextInt();
            generateMagicSquare(i);

        } else {
            System.out.println("输入的不是整数！");
        }
        isLegalMagicSquare("src\\P1\\txt\\1.txt");
        isLegalMagicSquare("src\\P1\\txt\\2.txt");
        isLegalMagicSquare("src\\P1\\txt\\3.txt");
        isLegalMagicSquare("src\\P1\\txt\\4.txt");
        isLegalMagicSquare("src\\P1\\txt\\5.txt");
    }
}