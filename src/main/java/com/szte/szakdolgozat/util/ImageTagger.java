package com.szte.szakdolgozat.util;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.tensorflow.SavedModelBundle;
import org.tensorflow.Session;
import org.tensorflow.Tensor;
import org.tensorflow.Tensors;

import javax.annotation.PreDestroy;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@AllArgsConstructor
@Service
public class ImageTagger {

    private List<String> tagIds = new ArrayList<>();

    private Map<String, String> tagIdNameMap = new HashMap<>();

    private Session session;

    private SavedModelBundle model;

    public ImageTagger() {
        try {
            initModel();
            initTagIds();
            initTagIdNameMap();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void initModel() {
        model = SavedModelBundle.load("C:\\Users\\Andras\\Desktop\\Egyetem\\Szakdolgozat\\BackEnd\\szakdolgozat\\src\\main\\resources\\tagger\\model", "serve");
        session = model.session();
    }

    @PreDestroy
    private void preDestroy() {
        this.model.close();
    }

    private void initTagIdNameMap() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Andras\\Desktop\\Egyetem\\Szakdolgozat\\BackEnd\\szakdolgozat\\src\\main\\resources\\tagger\\class-descriptions.csv"));
        String line = null;
        while ((line = br.readLine()) != null) {
            String str[] = line.split(",");
            tagIdNameMap.put(str[0], str[1]);
        }
    }

    private void initTagIds() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Andras\\Desktop\\Egyetem\\Szakdolgozat\\BackEnd\\szakdolgozat\\src\\main\\resources\\tagger\\classes-trainable.txt"));
        String line;
        while ((line = br.readLine()) != null) {
            tagIds.add(line);
        }
        br.close();
    }

    public List<String> generateTags(byte[] imageBytes) {
        Tensor<String> inputTensor2 = Tensors.create(new byte[][]{imageBytes});
        Tensor<?> result = session.runner()
                .feed("input_values:0", inputTensor2)
                .fetch("multi_predictions:0")
                .run().get(0);
        float[] m = new float[5000];
        float[] resultArray = result.copyTo(m);
        List<Float> resultList = new ArrayList<>();
        for (float r : resultArray) {
            resultList.add(r);
        }
        return resultList.stream().sorted(Comparator.reverseOrder()).limit(5).map(value -> tagIdNameMap.get(tagIds.get(resultList.indexOf(value)))).toList();
    }
}
