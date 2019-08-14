package com.biorecorder.multisignal.edflib.example;

import com.biorecorder.multisignal.edflib.EdfWriter;
import com.biorecorder.multisignal.recordformat.DataHeader;
import com.biorecorder.multisignal.recordformat.FormatVersion;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;

public class EdfWriterExample {
    static int numberOfChannels = 2;
    static int channel0Frequency = 500; // Hz
    static int channel1Frequency = 500; // Hz
    public static void main(String[] args) {
        DataHeader headerConfig  = new DataHeader(FormatVersion.BDF_24BIT, 2);
        headerConfig.setSampleFrequency(0, channel0Frequency);
        headerConfig.setLabel(0, "first channel");
        headerConfig.setPhysicalRange(0, -500, 500);
        headerConfig.setDigitalRange(0, -500, 500);
        headerConfig.setPhysicalDimension(0, "uV");

        headerConfig.setSampleFrequency(1, channel1Frequency);
        headerConfig.setLabel(1, "second channel");
        headerConfig.setPhysicalRange(1, -500, 500);
        headerConfig.setDigitalRange(1, -500, 500);
        headerConfig.setPhysicalDimension(0, "uV");
        File recordsDir = new File(System.getProperty("user.dir"), "records");
        File file = new File(recordsDir,"xxx.bdf");
        EdfWriter edfFileWriter = null;
        try {
            edfFileWriter = new EdfWriter(file, headerConfig);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int[] samplesFromChannel0 = new int[channel0Frequency];
        int[] samplesFromChannel1 = new int[channel1Frequency];
        Random rand = new Random();
        for(int i = 0; i < 100; i++) {
            // create random samples for channel 0
            for(int j = 0; j < samplesFromChannel0.length; j++) {
                samplesFromChannel0[j] = rand.nextInt(1000)-500;
            }

            // create random samples for channel 1
            for(int j = 0; j < samplesFromChannel1.length; j++) {
                samplesFromChannel1[j] = rand.nextInt(1000)-500;
            }

            // write samples from both channels to the edf file
            edfFileWriter.writeSamples(samplesFromChannel0);
            edfFileWriter.writeSamples(samplesFromChannel1);
        }
        edfFileWriter.close();
    }
}
