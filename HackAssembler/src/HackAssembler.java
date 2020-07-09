import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class HackAssembler
{

	public static void main(String[] args) throws IOException
	{
		File file = new File(args[0]);
		String strfile = file.toString().replace(".asm", "1.hack");
		File fileOut = new File(strfile);
		//check if file already exists
		if(fileOut.exists())
		{
			fileOut.delete();
			fileOut.createNewFile();
		}
		else
			fileOut.createNewFile();
		BufferedReader in = new BufferedReader(new FileReader(file));
		BufferedWriter out = new BufferedWriter(new FileWriter(fileOut));
		Command[] comarrprop;
		String[] bincomprop;
		
		comarrprop = parser(in);
		bincomprop = coder(comarrprop);
		for(String s : bincomprop)
		{
			System.out.println(s);
			out.write(s, 0, s.length());
			out.newLine();
		}
		out.close();
	}
	
	//this method removes all comments and white spaces, and outputs 
	//an array of "Command" objects, which is essentially just a one line string
	//of a single command, with some additional capability to look at 
	//individual aspects of each command, i.e. comp, dest, etc.
	public static Command[] parser(BufferedReader read) throws IOException
	{
		
		ArrayList<Command> comlist = new ArrayList<Command>();
		String arrCurrent = read.readLine();
		Command comTemp;
		boolean truth;
		int line = 0;
		//commands are first loaded into an arraylist so that they can be recorded
		//without knowing the amount of actual command lines
		while(arrCurrent != null)
		{
			//check for blank line
			if(!arrCurrent.isBlank())
			{
				//check for comment line
				if(!arrCurrent.substring(0,2).equals("//"))
				{
					//check if there is a comment on the same line as a 
					//command
					if(arrCurrent.contains("//"))
						arrCurrent = arrCurrent.substring(0 , arrCurrent.indexOf("/"));
					arrCurrent = arrCurrent.trim();
					comTemp = new Command(arrCurrent);
					comlist.add(comTemp);
				}
			}
			
			arrCurrent = read.readLine();
		}
		
		//iterate through arraylist, if gettype returns "A_COMMAND", check 
		//if address contains a letter. if so, add address to hashtable.
		//if gettype returns "L_COMMAND", get index of next line.
		Hashtable hash = new Hashtable();
		Iterator<Command> iterator = comlist.iterator();
		while(iterator.hasNext())
		{
			comTemp = iterator.next();
			if(comTemp.getType().equals("A_COMMAND"))
			{
				if
			}
		}
		comlist.trimToSize();
		Command[] comarray = new Command[comlist.size()];
		int arrCount = 0;
		//now that you only have the command lines, an array the size of the
		//trimmed arraylist can be created and loaded
		for(Command a : comlist)
		{
			comarray[arrCount] = a;
			arrCount++;
		}
		return comarray;
	}
	
	//this method translates each line of actual code to a binary string
	//and outputs the array of all binary strings
	public static String[] coder(Command[] comlist)
	{
		String[] binCom = new String[Array.getLength(comlist)];
		int tempAddr, binCount = 0;
		String binTemp;
		for(Command c : comlist)
		{
			if(c.getType().equals("A_COMMAND"))
			{
				binTemp = c.getValue();
				binCom[binCount] = binTemp;
				binCount++;
			}
			else
			{
				binTemp = "111" + c.getComp() + c.getDest() + c.getJump();
				binCom[binCount] = binTemp;
				binCount++;
			}
		}
		
		return binCom;
	}
	
	public static 
}
