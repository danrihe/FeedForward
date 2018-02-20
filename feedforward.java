//CPS109 Assignment #2 - feedforward
//Danri He - Student #500765982
//December 5th 2016

import java.util.*;
import java.lang.*;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class feedforward
{

public static void main (String[] args) throws IOException
{
    Scanner in = new Scanner(System.in);
    double[][] hiddenWeights = new double[300][785];
    double[][] outputWeights = new double[10][301];
    

    hiddenWeights = hiddenWeightsReader();
    outputWeights = outputWeightsReader();

    //double[] imageArray = null;
    BufferedImage img = ImageIO.read(new File(args[0]));
     // get pixel data
     double[] dummy = null;
     double[] imageArray = img.getData().getPixels(0, 0, img.getWidth(),
        img.getHeight(), dummy);

for (int i = 0; i < imageArray.length - 1; i++)
{
    imageArray[i] = (imageArray[i]/255.0);
}
double[] sum = new double[300];
for (int x = 0; x < 300; x++)
{
    for (int i = 0; i < 784; i++)
    {
        if (imageArray[i]!= 0.0)
        {
            sum[x] += (imageArray[i]*hiddenWeights[x][i]);
        }  
    }
    sum[x] += hiddenWeights[x][784];
    sum[x] = 1/(1+Math.pow(Math.E, -sum[x]));
}

double[] output = new double[10];
for (int x = 0; x < 10; x++)
{
    for (int i = 0; i < 300; i++)
    {
        output[x] += (sum[i]*outputWeights[x][i]);
    }
    output[x] += outputWeights[x][300];
    output[x] = 1/(1+Math.pow(Math.E, -output[x]));
}

System.out.println("The network prediction is " + indexCalculator(output) + ".");
}

public static int indexCalculator(double[] comparison)
{
    //double difference = 1;
    int index = 0;
    //int count = 0;
    double largestValue = 0;
    for (int i = 0; i < comparison.length; i++)
    {
        if (largestValue < comparison[i] || i == 0){
            largestValue = comparison[i];
            index = i;
        }
    }
    return index;
}

public static double[][] hiddenWeightsReader() throws IOException
{
    Scanner readHiddenWeights = new Scanner(new File("hidden-weights.txt"));
    double[][] hiddenWeights = new double[300][785];
    for (int i = 0; i < hiddenWeights.length; i++)
            {
                for (int x = 0; x < hiddenWeights[0].length; x++)
                {
                    hiddenWeights[i][x] = readHiddenWeights.nextDouble();
                }
    }
    return hiddenWeights;
}

public static double[][] outputWeightsReader() throws IOException
{
    Scanner readOutputWeights = new Scanner(new File("output-weights.txt"));
    double[][] outputWeights = new double[10][301];
    for (int i = 0; i < outputWeights.length; i++)
        {
            for (int x = 0; x < outputWeights[0].length; x++)
            {
                outputWeights[i][x] += readOutputWeights.nextDouble();
            }
        }
        return outputWeights;
}
}
