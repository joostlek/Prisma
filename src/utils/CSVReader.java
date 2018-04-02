package utils;

import model.person.Teacher;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class CSVReader {
    public List<String[]> read(String fileName) throws IOException {
        BufferedReader br;
        ArrayList<String[]> csvData = new ArrayList<>();
        String line;
        String cvsSplitBy = ",";

        br = new BufferedReader(new FileReader(fileName));
        while ((line = br.readLine()) != null) {
            csvData.add(line.split(cvsSplitBy));
        }
        return csvData;
    }
}
