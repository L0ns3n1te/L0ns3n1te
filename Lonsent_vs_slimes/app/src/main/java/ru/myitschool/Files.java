package ru.myitschool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Files {
    public static void output (String fileName, long x)
    {
        try
        {
            PrintWriter printWriter = new PrintWriter ("data" + "/" + File.separator+"data" + "/" + File.separator+"ru.myitschool" + "/" + File.separator+"files" + "/" + File.separator +fileName);
            printWriter.print (x);
            printWriter.close ();
        } catch (FileNotFoundException e) {}
    }
    public static int input (String fileName) throws FileNotFoundException
    {
        String root = "data" + "/" + File.separator+"data" + "/" + File.separator+"ru.myitschool" + "/" + File.separator+"files" + "/" + File.separator +fileName;
        File file = new File(root);
        try
        {file.createNewFile();}
        catch (IOException e){}
        try
        {
            if(file.length() == 0) {
                PrintWriter printWriter = new PrintWriter (root);
                printWriter.print (0);
                printWriter.close ();
            }
        } catch (IOException e) {}
        Scanner scanner = new Scanner (file);
        int a = 0;
        try
        {
            a = scanner.nextInt();
        }
        catch (NoSuchElementException e) {
        }
        return a;
    }
}
