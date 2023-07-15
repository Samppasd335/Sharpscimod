package com.mycompany.datagrapher;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProcessData {

    public byte[] convertTextToPDF(String userInput) throws IOException {
        return drawToPdfFile(createDrawableMatrix(parseInput(userInput)));
    }

    private List<String> parseInput(String userInput) throws IOException {
        try (BufferedReader reader = new BufferedReader(new StringReader(userInput))) {
            List<String> list = reader.lines()
                    .flatMap(line -> Arrays.stream(line.split("n")))
                    .map(line -> line.replaceAll("[\"\\\\t]", "")
                            .replaceAll("\\s+", "")
                            .trim())
                    .collect(Collectors.toCollection(ArrayList::new));

            Collections.reverse(list);
            return list;
        }
    }

    public byte[] drawToPdfFile(List<List<Shape>> matrix) throws IOException {
        DataVisualizer visualizer = new DataVisualizer();

        for (List<Shape> row : matrix) {
            visualizer.addDrawable(row);
        }

        return visualizer.generatePdfAsByteArray();
    }

    private List<List<Shape>> createDrawableMatrix(List<String> data) throws IOException {
        int rows = data.size();
        List<List<Shape>> matrix = new ArrayList<>();

        for (int i = 0; i < rows; i++) {
            String line = data.get(i);
            int cols = line.length();
            List<Shape> row = new ArrayList<>();

            for (int j = 0; j < cols; j++) {
                char symbol = line.charAt(j);
                Shape shape = createShape(symbol, j, i);
                row.add(shape);
            }
            matrix.add(row);
        }
        return matrix;
    }
    private Shape createShape(char symbol, float x, float y) throws IOException {
        return switch (symbol) {
            case '1' -> new Circle(x, y, true, false);
            case '0' -> new Circle(x, y, false, true);
            case 'x' -> new Line(x, y);
            default -> new Circle(x, y, true, true);
        };
    }
}