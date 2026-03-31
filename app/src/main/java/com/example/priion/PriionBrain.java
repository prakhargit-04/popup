package com.example.priion;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.util.Log;

import org.json.JSONObject;
import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PriionBrain {
    private static final String TAG = "PRIION_LOGS";

    // The Deep Learning Engine
    private Interpreter tflite;
    // The Dictionary
    private Map<String, Integer> vocab = new HashMap<>();
    // The same 20-word limit we set in Python
    private static final int MAX_LENGTH = 20;

    public PriionBrain(Context context) {
        try {
            // 1. Load the AI Brain from the assets folder
            tflite = new Interpreter(loadModelFile(context, "priion_model.tflite"));

            // 2. Load the Dictionary so the AI knows what words mean
            loadVocab(context);

            Log.d(TAG, "🧠 LEVEL 5 NEURAL NETWORK ONLINE!");
        } catch (Exception e) {
            Log.e(TAG, "Failed to load Deep Learning model", e);
        }
    }

    // --- HELPER TO READ THE .TFLITE FILE ---
    private MappedByteBuffer loadModelFile(Context context, String modelPath) throws Exception {
        AssetFileDescriptor fileDescriptor = context.getAssets().openFd(modelPath);
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

    // --- HELPER TO READ THE VOCAB.JSON FILE ---
    private void loadVocab(Context context) throws Exception {
        InputStream is = context.getAssets().open("vocab.json");
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();

        String jsonStr = new String(buffer, StandardCharsets.UTF_8);
        JSONObject jsonObject = new JSONObject(jsonStr);

        Iterator<String> keys = jsonObject.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            vocab.put(key, jsonObject.getInt(key));
        }
    }

    // --- THE MAGIC PREDICTION METHOD ---
    public String classify(String text) {
        if (tflite == null) return "low"; // Failsafe if the AI didn't load

        Log.d(TAG, "🤖 NEURAL NET THINKING START -----------------");
        Log.d(TAG, "Message received: '" + text + "'");

        // 1. Clean the text (remove punctuation and make lowercase)
        text = text.toLowerCase().replaceAll("[^a-z0-9\\s]", "");
        String[] words = text.split("\\s+");

        // 2. Convert words into the numbers the Neural Net understands
        float[][] input = new float[1][MAX_LENGTH];
        for (int i = 0; i < words.length && i < MAX_LENGTH; i++) {
            // If the word isn't in the dictionary, assign it '1' (Unknown Token)
            input[0][i] = vocab.getOrDefault(words[i], 1);
        }

        Log.d(TAG, "Converted to Array: " + Arrays.toString(input[0]));

        // 3. Ask the Neural Network for the answer!
        float[][] output = new float[1][4];
        tflite.run(input, output);

        // 4. The output is 4 percentages (Critical, Important, Low, Spam)
        float[] probabilities = output[0];
        Log.d(TAG, String.format("Scores -> Critical: %.2f%% | Important: %.2f%% | Low: %.2f%% | Spam: %.2f%%",
                probabilities[0]*100, probabilities[1]*100, probabilities[2]*100, probabilities[3]*100));

        // 5. Find the winner
        int maxIndex = 0;
        for (int i = 1; i < probabilities.length; i++) {
            if (probabilities[i] > probabilities[maxIndex]) {
                maxIndex = i;
            }
        }

        // Must match the exact label_mapping from your Python script!
        String[] labels = {"critical", "important", "low", "spam"};
        String bestCategory = labels[maxIndex];

        Log.d(TAG, "🏆 AI CHOSE: " + bestCategory.toUpperCase());
        Log.d(TAG, "🤖 NEURAL NET THINKING END -------------------");

        return bestCategory;
    }
}