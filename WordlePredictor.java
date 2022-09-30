
/**
 *Find the wordle word by eliminating words it cannot be
 *
 * @author conalhughes
 * @version 1.0
 */
import java.util.*;
import java.io.*;

public class WordlePredictor
{
    public static void main(String[]args)
    {
        Scanner sc = new Scanner(System.in);
        Random rd = new Random();
        Dictionary word = new Dictionary();
        // this will be the list of possible words. once a letter is confirmed or eliminated, the list will update
        LinkedList<String> list = new LinkedList<>(); 
        for(int p = 0; p < word.getSize(); p++)
        {
            list.add(word.getWord(p));
        }
        System.out.println("Welcome to the Wordle Predictor!\nOn the prompt, please enter the word you guessed.");
        System.out.println("On the next line enter in your score! 2 for a green block, 1 for yellow and 0 for grey.");
        System.out.println("All possible remaining words will be listed");

        boolean winner = false;
        
        for(int loop = 0; loop < 6; loop++)
        {
            String test = sc.nextLine();
            String score, input;
            do 
            {
                System.out.println("Now your score, no spaces please!");
                input = sc.nextLine();
            } while (input.length() != 5);

            score = input;
            
            if(score.equals("22222"))
            {
                System.out.println("Congratulations!");
                winner = true;
                break;
            }
            else
            {
                for(int p = 0; p < list.size(); p++)
                {
                    String s = "" + list.get(p);
                    if(s.equals(test))
                    {
                        list.remove(p);
                        break;
                    }
                }
            }
            char dup1 = '!';
            char dup2 = '!';
            for(int p = 0; p < test.length()-1; p++) // compares each letter to each other, looks for duplicates
            {
                for(int i = p+1; i < test.length(); i++)
                {
                    if(test.charAt(p) == test.charAt(i))
                    {
                        if(dup1 == '!')
                        {
                            dup1 = test.charAt(i);
                        }
                        else
                        {
                            dup2 = test.charAt(i);
                        }
                    }
                }
            }
            // now scoring is finished, update list
            int letterIndex = 0; //
            while(letterIndex < 5)
            {
                char c1 = score.charAt(letterIndex);
                char c2 = test.charAt(letterIndex);
                if(c1 == '2') // remove all words that dont have the correct letter in the correct place
                {
                    int p = 0;
                    while(p < list.size())
                    {
                        String s = "" + list.get(p);
                        if(s.charAt(letterIndex) != c2) // if word does not contain letter in right place
                        {
                            list.remove(p);
                        }
                        else
                        {
                            p++;
                        }
                    }
                }
                else if(c1 == '1') // if letter is right but in the wrong place
                {
                    int p = 0;
                    while(p < list.size())
                    {
                        String s = "" + list.get(p);
                        if(countChar(c2,s) == 0)// if word does not contain letter at all
                        {
                            list.remove(p);
                        }
                        else if(s.charAt(letterIndex) == c2) // if the letter is in the place its not suppoesd to be
                        {
                            list.remove(p);
                        }
                        else
                        {
                            p++;
                        }
                    }
                }
                else // the letter is wrong and needs to be removed
                {
                    int p = 0;
                    while(p < list.size())
                    {
                        String s = "" + list.get(p);
                        if(countChar(c2,s) > 0)// if word contains the letter at all, remove
                        {
                            list.remove(p);
                        }
                        else
                        {
                            p++;
                        }
                    }
                }
                letterIndex++;
            }
            System.out.println("possibilities " + list.size());


            if(list.size() == 0) // something went wrong
            {
                System.out.println("I need to work on this apparantly...");
                break;
            }


            for(int p = 0; p < list.size(); p++) // prints all remaining possibilities
            {
                System.out.println(list.get(p));
            }
            System.out.println("Pick a word and try again!");
        }

        // loop has been completed 6 times, game is over
        if(!winner)
            System.out.println("Unlucky, try again soon!");
    }
    public static int countChar(char c, String s)
    {
        int count = 0;
        for(int p = 0; p < s.length(); p++)
        {
            if(s.charAt(p) == c)
            {
                count++;
            }
        }
        return count;
    }
}
class Dictionary
{
     
    private String input[]; 

    public Dictionary()
    {
        input = load("/home/conalhughes/Documents/practice/java/WordlePredictor/wordleWords.txt");  
    }
    
    public int getSize(){
        return input.length;
    }
    
    public String getWord(int n){
        return input[n].trim();
    }
    
    private String[] load(String file) {
        File aFile = new File(file);     
        StringBuffer contents = new StringBuffer();
        BufferedReader input = null;
        try {
            input = new BufferedReader( new FileReader(aFile) );
            String line = null; 
            int i = 0;
            while (( line = input.readLine()) != null){
                contents.append(line);
                i++;
                contents.append(System.getProperty("line.separator"));
            }
        }catch (FileNotFoundException ex){
            System.out.println("Can't find the file - are you sure the file is in this location: "+file);
            ex.printStackTrace();
        }catch (IOException ex){
            System.out.println("Input output exception while processing file");
            ex.printStackTrace();
        }finally{
            try {
                if (input!= null) {
                    input.close();
                }
            }catch (IOException ex){
                System.out.println("Input output exception while processing file");
                ex.printStackTrace();
            }
        }
        String[] array = contents.toString().split("\n");
        for(String s: array){
            s.trim();
        }
        return array;
    }
}
