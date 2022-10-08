package uet.oop.bomberman.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Level {
    private File file;
    private Scanner reader;

    // Level properties
    private int levelNumber;
    private int rowCount;
    private int columnCount;

    public Map map;
    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Scanner getReader() {
        return reader;
    }

    public void setReader(Scanner reader) {
        this.reader = reader;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
    }

    public static Level level1 = new Level("res/levels/Level1.txt");
//    public static Level level1 = new Level("Level1.txt");
//    public static Level level1 = new Level("Level1.txt");
//    public static Level level1 = new Level("Level1.txt");
//    public static Level level1 = new Level("Level1.txt");
//    public static Level level1 = new Level("Level1.txt");
//    public static Level level1 = new Level("Level1.txt");
//    public static Level level1 = new Level("Level1.txt");
//    public static Level level1 = new Level("Level1.txt");

    Level(String path) {
        try {
            setFile(new File(path));
            setReader(new Scanner(getFile()));
            int levelNumber = this.reader.nextInt();
            setLevelNumber(levelNumber);
            int row = this.reader.nextInt();
            setRowCount(row);
            int column = this.reader.nextInt();
            setColumnCount(column);
            System.out.printf("Level %1$d: %2$d row, %3$d column%n", levelNumber, row, column);
            this.map = new Map();
            readMap(this.reader, this.rowCount, this.columnCount);
        }
        catch (FileNotFoundException e) {
            System.out.println(path + " not found!");
            e.printStackTrace();
        }
    }

    public void readMap(Scanner reader,int rowCount, int colCount) {
        reader.nextLine();
        for (int i = 0; i < rowCount; i++) {
            char[] rowRaw = reader.nextLine().toCharArray();
            Row row = new Row();
            for (int j = 0; j < colCount; j++) {
                row.row.add(rowRaw[j]);
            }
            map.map.add(row);
        }
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
    }
}

