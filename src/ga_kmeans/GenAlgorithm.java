package ga_kmeans;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;


public class GenAlgorithm extends ApplicationFrame {

    static List<Kromosom> dataSource = new ArrayList<>();
    static List<Kromosom> dataTemp = new ArrayList<>();
    static XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
    double totalFitness;
    double[] nilaiFitness = new double[4];
    double[][] populasiArray = new double[][]{
    {random(120), random(160),
        random(120), random(160),
        random(120), random(160),
        random(120), random(160)},
    {random(120), random(160),
        random(120), random(160),
        random(120), random(160),
        random(120), random(160)},
    {random(120), random(160),
        random(120), random(160),
        random(120), random(160),
        random(120), random(160)},
    {random(120), random(160),
        random(120), random(160),
        random(120), random(160),
        random(120), random(160)}};
    double[][] populasiBaru = new double[4][8];
    double[][] populasiGabungan = new double[8][8];
    double[] solusi = new double[8];
    int[] index = new int[4];
    int generasi = 100;

    public void bacaFile() {
        BufferedReader br;
        String line;

        try {
            br = new BufferedReader(new FileReader("src\\resources\\DatasetRuspini.csv"));
            while ((line = br.readLine()) != null) {

                String[] kromosomData = line.split(",");

                Kromosom kromosom = new Kromosom();
                kromosom.setX(Integer.parseInt(kromosomData[1]));
                kromosom.setY(Integer.parseInt(kromosomData[2]));
                kromosom.setCluster(Integer.parseInt(kromosomData[3]));

                dataSource.add(kromosom);
            }
            
        dataTemp.addAll(dataSource);
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        }
        
        XYSeries class1 = new XYSeries("Class 1");
        XYSeries class2 = new XYSeries("Class 2");
        XYSeries class3 = new XYSeries("Class 3");
        XYSeries class4 = new XYSeries("Class 4");
        

        for(Kromosom kromosom : dataSource){
            switch(kromosom.getCluster()){
                 case 1:
                    class1.add(kromosom.getX(), kromosom.getY());
                    break;
                case 2:
                    class2.add(kromosom.getX(), kromosom.getY());
                    break;
                case 3:
                    class3.add(kromosom.getX(), kromosom.getY());
                    break;
                case 4:
                    class4.add(kromosom.getX(), kromosom.getY());
                    break;
            }
        }
        xySeriesCollection.addSeries(class1);
        xySeriesCollection.addSeries(class2);
        xySeriesCollection.addSeries(class3);
        xySeriesCollection.addSeries(class4);
        
        for (int h = 0; h < generasi; h++) {
            totalFitness = 0;
            for (int i = 0; i < populasiArray.length; i++) {
                System.out.println("Individu " + (i + 1));
                for (int j = 0; j < 4; j++) {
                    System.out.println("X: " + populasiArray[i][j * 2] + ", Y: " + populasiArray[i][j * 2 + 1]);
                }
            }
            for (int i = 0; i < populasiArray.length; i++) {
                totalFitness += nilaiFitness[i] = fitness(populasiArray[i]);
            }
            System.out.println("Nilai Fitness Individu 1 : " + nilaiFitness[0]);
            System.out.println("Nilai Fitness Individu 2 : " + nilaiFitness[1]);
            System.out.println("Nilai Fitness Individu 3 : " + nilaiFitness[2]);
            System.out.println("Nilai Fitness Individu 4 : " + nilaiFitness[3]);

            totalFitness = nilaiFitness[0] + nilaiFitness[1] + nilaiFitness[2] + nilaiFitness[3];
            System.out.println("Total Fitness : " + totalFitness);
            index = roulette(totalFitness, nilaiFitness);
            populasiArray = selection(index, populasiArray);
            for (int i = 0; i < populasiArray.length; i++) {
                System.out.println("Individu " + (i + 1));
                for (int j = 0; j < 4; j++) {
                    System.out.println("X: " + populasiArray[i][j * 2] + ", Y: " + populasiArray[i][j * 2 + 1]);
                }
            }
            populasiBaru = crossOver(populasiArray);
            System.out.println("====================================CROSSOVER=========================");
            for (int i = 0; i < populasiBaru.length; i++) {
                System.out.println("Individu " + (i + 1));
                for (int j = 0; j < 4; j++) {
                    System.out.println("X: " + populasiBaru[i][j * 2] + ", Y: " + populasiBaru[i][j * 2 + 1]);
                }
            }
            populasiBaru = mutasi(populasiBaru);
            System.out.println("====================================Mutasi=========================");
            for (int i = 0; i < populasiBaru.length; i++) {
                System.out.println("Individu " + (i + 1));
                for (int j = 0; j < 4; j++) {
                    System.out.println("X: " + populasiBaru[i][j * 2] + ", Y: " + populasiBaru[i][j * 2 + 1]);
                }
            }
            if (h < generasi - 1) {
                populasiArray = elitisme(populasiArray, populasiBaru);
            } else {
                solusi = terbaik(populasiArray, populasiBaru);
            }

        }
        System.out.println("====================================Solusi=========================");
        XYSeries centroid = new XYSeries("Centroid");
        for (int j = 0; j < 4; j++) {
            System.out.println("X: " + Math.abs(solusi[j*2]) + ", Y: " + Math.abs(solusi[j * 2 + 1]));
            centroid.add(Math.abs(solusi[j*2]), Math.abs(solusi[j*2+1]));
        }
        xySeriesCollection.addSeries(centroid);

    }

    public double random(double x) {
        Random r = new Random();
        double hasil_random = r.nextInt((int) x);

        return hasil_random;
    }

    private double fitness(double[] individu) {
        double nilaiFitness = 0;
        for (int i = 0; i < 4; i++) {
            double mindis = 999;
            for (Kromosom data : dataSource) {
                double dist = (double) Math.sqrt(Math.pow(data.getX() - individu[i * 2], 2)
                        + Math.pow(data.getY() - individu[i * 2 + 1], 2));
                if (mindis > dist) {
                    mindis = dist;
                }
            }
            nilaiFitness += mindis;
        }
        return 1 / nilaiFitness * 1000;
    }

    private int[] roulette(double totalFitness, double[] nilaiFitness) {
        totalFitness = totalFitness * 10000;
        int[] selected = new int[4];
        for (int i = 0; i < populasiArray.length; i++) {
            double tmp = random(totalFitness);
            boolean cek = false;
            int count = 0, t = 0;
            while (!cek) {
                t += nilaiFitness[count] * 10000;
                if (tmp < t) {
                    cek = true;
                } else {
                    count++;
                }
            }
            selected[i] = count;
        }
        return selected;
    }

    private double[][] selection(int[] index, double[][] populasiArray) {
        double[][] newPopulasi = new double[4][4];
        for (int i = 0; i < populasiArray.length; i++) {
            newPopulasi[i] = populasiArray[index[i]];
        }
        return newPopulasi;
    }

    private double[][] crossOver(double[][] populasiArray) {
        for (int i = 0; i < 2; i++) {
            if (random(10) < 10) {
                int btsKiri = (int) random(6);
                int btsKanan = (int) random(7);
                if (btsKanan < btsKiri) {
                    btsKanan = (int) random(7);
                } else {
                    for (int j = btsKiri; j <= btsKanan; j++) {
                        double temp = populasiArray[i * 2][j];
                        populasiArray[i * 2][j] = populasiArray[i * 2 + 1][j];
                        populasiArray[i * 2 + 1][j] = temp;
                    }
                }
            }
        }
        return populasiArray;
    }

    private double[][] mutasi(double[][] populasiBaru) {
        for (double[] pop : populasiBaru) {
            if (random(10) < 10) {
                int x = (int) random(7);
                if (random(10) < 6) {
                    if (pop[x] == 0) {
                        pop[x] += 2;
                    } else {
                        pop[x] -= 2;
                    }
                } else {
                    if (pop[x] == 160) {
                        pop[x] -= 2;
                    } else {
                        pop[x] += 2;
                    }
                }
            }
        }
        return populasiBaru;
    }

    private double[][] elitisme(double[][] populasiArray, double[][] populasiBaru) {
        int count = 0;
        for (int i = 0; i < populasiArray.length; i++) {
            populasiGabungan[i] = populasiArray[i];
            count++;
        }
        for (int i = 0; i < populasiBaru.length; i++) {
            populasiGabungan[count++] = populasiBaru[i];
        }
        double[] nilaiFitness = new double[8];
        double[][] selected = new double[4][4];
        for (int i = 0; i < populasiGabungan.length; i++) {
            nilaiFitness[i] = fitness(populasiGabungan[i]);
        }
        for (int i = 0; i < populasiGabungan.length; i++) {
            for (int j = 0; j < populasiGabungan.length - 1; j++) {
                if (nilaiFitness[j] < nilaiFitness[j + 1]) {
                    double temp = nilaiFitness[j];
                    nilaiFitness[j] = nilaiFitness[j + 1];
                    nilaiFitness[j + 1] = temp;

                    double[] tempGab = populasiGabungan[j];
                    populasiGabungan[j] = populasiGabungan[j + 1];
                    populasiGabungan[j + 1] = tempGab;
                }
            }
        }
        for (int i = 0; i < (populasiGabungan.length / 2); i++) {
            selected[i] = populasiGabungan[i];
        }
        return selected;
    }

    private double[] terbaik(double[][] populasiArray, double[][] populasiBaru) {
        int count = 0, jwb = 0;
        double temp = 0;
        for (int i = 0; i < populasiArray.length; i++) {
            populasiGabungan[i] = populasiArray[i];
            count++;
        }
        for (int i = 0; i < populasiBaru.length; i++) {
            populasiGabungan[count++] = populasiBaru[i];
        }
        double[] nilaiFitness = new double[8];
        for (int i = 0; i < populasiGabungan.length; i++) {
            nilaiFitness[i] = fitness(populasiGabungan[i]);
        }
        for (int i = 0; i < nilaiFitness.length; i++) {
            if (nilaiFitness[i] > temp) {
                temp = nilaiFitness[i];
                jwb = i;
            }
        }
        return populasiGabungan[jwb];
    }
    
    public GenAlgorithm(String s) {
        super(s);
        JPanel jpanel = createDemoPanel();
        jpanel.setPreferredSize(new Dimension(800, 600));
        add(jpanel);
    }
    
    public static JPanel createDemoPanel() {
        JFreeChart jfreechart = ChartFactory.createScatterPlot(
                "Genetic Algorthm on KMeans", "X", "Y", dataset(),
                PlotOrientation.VERTICAL, true, true, false);

        return new ChartPanel(jfreechart);
    }
    
    private static XYDataset dataset() {
        return xySeriesCollection;
    }
    public static void main(String[] args){
        GenAlgorithm genAlgorithm = new GenAlgorithm("Genetic Algorthm on KMeans");
        genAlgorithm.bacaFile();
        genAlgorithm.pack();
        RefineryUtilities.centerFrameOnScreen(genAlgorithm);
        genAlgorithm.setVisible(true);
    }
    
}
