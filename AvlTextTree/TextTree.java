import java.io.*;
import java.util.NoSuchElementException;
public class TextTree extends AvlTree{
 	/*TextTree Constructor
 	*Takes a text file read in by a BufferedReader, and constructs an Avl Tree.
 	*Words are sorted into the AvlTree Lexographically 
 	*Every word in the document has its own node, and each node has a list identifying which lines
 	*the words appear in the Tree
 	*/
	public TextTree(String textFile) throws IOException,FileNotFoundException {
		super();
		BufferedReader text = new BufferedReader(new FileReader(textFile));
		String line = text.readLine();
		Integer lineN = 1;
		while(line!= null){
			String[] splitLine = line.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
			for(int i = 0; i < splitLine.length; ++i){
				this.insert(splitLine[i], lineN);
			}
		++lineN;
		line = text.readLine();
		}
	}

	public static void main(String[] args){
		try{
		
		TextTree palindromeTree = new TextTree(args[0]);
		palindromeTree.printTree();
		}
		catch(FileNotFoundException e1){
			System.err.println("The file name given as an argument doesnt exist");
		}
		catch(IndexOutOfBoundsException e2){
			System.err.println("You forgot to give a file as an argument");
		}
		catch(IOException e){
			System.err.println("The file you gave as a command line argument has no text");
		}


	}
}