import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MergeSort {

    public static int[] createRandomArray(int arrayLength) {
        Random random = new Random();
        int[] array = new int[arrayLength];
        for (int i = 0; i < arrayLength; i++) {
            array[i] = random.nextInt(101);
        }
        return array;
    }

    public static void writeArrayToFile(int[] array, String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        for (int num : array) {
            writer.write(num + "\n");
        }
        writer.close();
    }

    public static int[] readFileToArray(String filename) throws IOException {
        List<Integer> numbers = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;

        while ((line = reader.readLine()) != null) {
            numbers.add(Integer.parseInt(line.trim()));
        }
        reader.close();
        return numbers.stream().mapToInt(i -> i).toArray();
    }

    void merge(int arr[], int first, int second, int three) {
        int a = second - first + 1;
        int b = three - second;

        int firstArr[] = new int[a];
        int secondArr[] = new int[b];

        for (int i = 0; i < a; i++) {
            firstArr[i] = arr[first + i];
        }
        for (int i = 0; i < b; i++) {
            secondArr[i] = arr[second + 1 + i];
        }

        int i = 0, j = 0;
        int index = first;

        while (i < a && j < b) {
            if (firstArr[i] <= secondArr[j]) {
                arr[index] = firstArr[i];
                i++;
            } else {
                arr[index] = secondArr[j];
                j++;
            }
            index++;
        }

        while (i < a) {
            arr[index] = firstArr[i];
            i++;
            index++;
        }

        while (j < b) {
            arr[index] = secondArr[j];
            j++;
            index++;
        }
    }

    void sort(int arr[], int first, int three) {
        if (first < three) {
            int second = (first + three) / 2;
            sort(arr, first, second);
            sort(arr, second + 1, three);
            merge(arr, first, second, three);
        }
    }

    static void printArray(int arr[]) {
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        MergeSort sorter = new MergeSort();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose an option:\n1. Generate a random array\n2. Enter a custom array");
        int choice = scanner.nextInt();
        int[] arr;

        if (choice == 1) {
            System.out.print("Enter the length of the random array: ");
            int arrayLength = scanner.nextInt();
            arr = createRandomArray(arrayLength);
            try {
                writeArrayToFile(arr, "input.txt");
            } catch (IOException e) {
                System.err.println("Error writing to file: " + e.getMessage());
            }
        } else {
            System.out.print(" Please enter the number of elements in the custom array: ");
            int size = scanner.nextInt();
            arr = new int[size];
            System.out.println("Enter the elements for the array:");
            for (int i = 0; i < size; i++) {
                arr[i] = scanner.nextInt();
            }
        }

        try {
            arr = readFileToArray("input.txt");
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
            return;
        }

        System.out.println("Original Array:");
        printArray(arr);
        sorter.sort(arr, 0, arr.length - 1);
        System.out.println("Sorted Array using Merge Sort:");
        printArray(arr);
    }
}

