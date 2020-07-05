package sorting;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

class WriteFile {
    public static void write(ArrayList<String> arr, String filepath) {
        try {
            FileWriter writer = new FileWriter(filepath);
            for (String s : arr) {
                writer.write(s);
            }
            writer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public static void writel(ArrayList<Long> arrl, String filepath) {
        try {
            FileWriter writer = new FileWriter(filepath);
            for (Long l : arrl) {
                writer.write(String.valueOf(l));
            }
            writer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

class ReadFile {
    public static ArrayList<String> read(String filepath) {
        ArrayList<String> arr = new ArrayList<String>();
        try {
            File myFile = new File(filepath);
            FileReader fileReader = new FileReader(myFile);
            BufferedReader reader = new BufferedReader(fileReader);
            String line = null;
            while ((line = reader.readLine()) != null) {
                arr.add(line);
            }
            reader.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return arr;
    }
}



class Entry implements Comparable<Entry>{
    long number;
    long freq;

    Entry(long n, long f) {
        this.number = n;
        this.freq = f;
    }

    public int compareTo (Entry e) {
        long n = number;
        long f = freq;
        long tempN = e.getNumber();
        long tempF = e.getFreq();
        if (n > tempN && tempF == f) {return 1;}
        if (n < tempN && tempF == f) {return -1;}
        if (f < tempF) {return -1;}
        if (f > tempF) {return 1;}

        return 0;
    }
    public long getNumber() {return this.number;}
    public long getFreq() {return this.freq;}
}

class EntryW implements Comparable<EntryW>{
    String word;
    long freq;

    EntryW(String w, long f) {
        this.word = w;
        this.freq = f;
    }

    public int compareTo (EntryW e) {
        String w = word;
        long f = freq;
        String tempS = e.getWord();
        long tempF = e.getFreq();
        if (w.compareTo(tempS) > 0 && f == tempF) {return 1;}
        if (w.compareTo(tempS) < 0 && f == tempF) {return -1;}
        if (f < tempF) {return -1;}
        if (f > tempF) {return 1;}

        return 0;
    }
    public String getWord() {return this.word;}
    public long getFreq() {return this.freq;}
}



public class Main {
    public static String typeSort = "natural";
    public static String typeData = "";
    static long total = 0L;
    static String filePath = "";
    static String filePathOut = "";
    static ArrayList<Long> nums = new ArrayList<>();
    static ArrayList<String> words = new ArrayList<>();
    static ArrayList<String> lines = new ArrayList<>();
    static boolean isWork = true;

    public static void setArr() {

        if (filePath.equals("")) {
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                String s = scanner.nextLine();
                lines.add(s);
            }
        } else {
            lines = ReadFile.read(filePath);
        }

        if (typeData.equals("long")) {
            for (String s : lines) {
                String[] sarr = s.split("\\s+");
                    for (String sa : sarr) {
                        long number = Long.parseLong(sa);
                        nums.add(number);
                        total++;
                    }
            }
        } else if (typeData.equals("word")) {
            for (String s : lines) {
                String[] input = s.split("\\s+");
                total += input.length;
                for (String sw : input) {
                    words.add(sw);
                }
            }

        } else {
            for (String s : lines) {
                total++;
            }

        }
    }

    public static void setInput(String[] args){

            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("-sortingType")) {
                    if (i < args.length-1) {
                        typeSort = args[i + 1];
                    }
                }
                if (args[i].equals("-dataType")) {
                    if (i < args.length-1) {
                        typeData = args[i + 1];
                    } else {
                        System.out.println("No data type defined!");
                        isWork = false;
                    }
                }
                if (args[i].equals("-inputFile")) {
                    if (i < args.length-1) {
                        filePath = ".\\" + args[i + 1];
                    }
                }
                if (args[i].equals("-outputFile")) {
                    if (i < args.length-1) {
                        filePathOut = args[i + 1];
                        WriteFile.write(lines, filePathOut);
                    }
                }
            }
            if (typeData.equals("")) {
                isWork = false;
            }

    }

    public static long getPerc(long maxcount, long count) {
        return maxcount * 100 / count;
    }

    public static void sortNum() {
        Collections.sort(nums);
        System.out.println("Total numbers: " + total +".");
        System.out.print("Sorted data: ");

        for (Long n : nums) {
            System.out.print(n + " ");
        }

        if (!filePathOut.equals("")) {
            WriteFile.writel(nums, filePathOut);

        }


    }

    public static void sortNumC() {
        ArrayList<Long> temp = new ArrayList<>();
        ArrayList<Long[]> stats = new ArrayList<>();
        ArrayList<Entry> st = new ArrayList<>();

        Collections.sort(nums);
        for (Long l : nums) {
            if(!temp.contains(l)) {
                long occurrences = Collections.frequency(nums, l);
                Long[] entry = new Long[2];
                entry[0] = l;
                entry[1] = occurrences;
                stats.add(entry);
                st.add(new Entry(l, occurrences));
            }
            temp.add(l);
        }
        System.out.println("Total numbers: " + total +".");

        Collections.sort(st);

        for (Entry n : st) {
            System.out.println(n.getNumber() + ": " + n.getFreq() + " time(s), " + getPerc(n.getFreq(), total) + "%");

        }

    }

    public static void sortWord() {
        Collections.sort(words);
        System.out.println("Total words: " + total +".");
        System.out.print("Sorted data: ");
        for (String w : words) {
            System.out.print(w + " ");
        }
        if (!filePathOut.equals("")) {
            WriteFile.write(words, filePathOut);

        }
    }

    public static void sortWordC() {
        ArrayList<String> temp = new ArrayList<>();
        ArrayList<String> outF = new ArrayList<>();
        ArrayList<EntryW> st = new ArrayList<>();
        Collections.sort(words);
        System.out.println("Total words: " + total +".");
        Collections.sort(words);
        for (String w : words) {
            if(!temp.contains(w)) {
                long occurrences = Collections.frequency(words, w);
                st.add(new EntryW(w, occurrences));
            }
            temp.add(w);
        }

        Collections.sort(st);

        for (EntryW n : st) {
            String out = n.getWord() + ": " + n.getFreq() + " time(s), " + getPerc(n.getFreq(), total) + "%";
            System.out.println(out);
            outF.add(out);
        }
        if (!filePathOut.equals("")) {
            WriteFile.write(outF, filePathOut);

        }
    }

    public static void sortLine() {
        Collections.sort(lines);
        System.out.println("Total lines: " + total +".");
        System.out.println("Sorted data: ");
        for (String l :lines) {
            System.out.println(l + " ");
        }
        if (!filePathOut.equals("")) {
            WriteFile.write(lines, filePathOut);

        }

    }

    public static void sortLineC() {
        ArrayList<String> temp = new ArrayList<>();
        ArrayList<EntryW> st = new ArrayList<>();
        Collections.sort(lines);
        System.out.println("Total lines: " + total + ".");
        Collections.sort(lines);
        for (String w : lines) {
            if (!temp.contains(w)) {
                long occurrences = Collections.frequency(lines, w);
                st.add(new EntryW(w, occurrences));
            }
            temp.add(w);
        }
        Collections.sort(st);

        for (EntryW n : st) {
            System.out.println(n.getWord() + ": " + n.getFreq() + " time(s), " + getPerc(n.getFreq(), total) + "%");

        }
    }


    public static void main(final String[] args) {

     if (args.length > 1) {
         setInput(args);
         if (isWork) {
             setArr();
             switch (typeSort) {
                 case "natural":
                     switch (typeData) {
                         case "long":
                             sortNum();
                             break;
                         case "line":
                             sortLine();
                             break;
                         case "word":
                             sortWord();
                             break;
                         default:
                             break;
                     }
                     break;
                 case "byCount":
                     switch (typeData) {
                         case "long":
                             sortNumC();
                             break;
                         case "line":
                             sortLineC();
                             break;
                         case "word":
                             sortWordC();
                             break;
                         default:
                             break;
                     }
                     break;
                 default:
                     break;
             }
         }
     }

    }
}
