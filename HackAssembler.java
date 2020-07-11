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
		String arrCurrent = read.readLine(), strValue;
		Command comTemp;
		int line = 0, mem = 16;
		Hashtable<String, Integer> hash = new Hashtable<String, Integer>();
		hash.put("SP", 0);
		hash.put("LCL", 1);
		hash.put("ARG", 2);
		hash.put("THIS", 3);
		hash.put("THAT", 4);
		hash.put("R0", 0);
		hash.put("R1", 1);
		hash.put("R2", 2);
		hash.put("R3", 3);
		hash.put("R4", 4);
		hash.put("R5", 5);
		hash.put("R6", 6);
		hash.put("R7", 7);
		hash.put("R8", 8);
		hash.put("R9", 9);
		hash.put("R10", 10);
		hash.put("R11", 11);
		hash.put("R12", 12);
		hash.put("R13", 13);
		hash.put("R14", 14);
		hash.put("R15", 15);
		hash.put("SCREEN", 16384);
		hash.put("KBD", 24576);
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
					comTemp = new Command(arrCurrent , line);
					if(!comTemp.getType().equals("L_COMMAND"))
					{
						comlist.add(comTemp);
						line++;
					}
					//adding try catch here to make parser push symbols to hash table
					if(comTemp.getType().equals("L_COMMAND"))
					{
						hash.put(comTemp.getValue().substring(0, comTemp.getValue().length() - 1) , line);
					}
				}
			}
			
			arrCurrent = read.readLine();
		}
		comlist.trimToSize();
		for(Command c : comlist)
			System.out.println(c.getString() + " : " + c.getLine());
		//iterate through arraylist, if gettype returns "A_COMMAND", check 
		//if address contains a letter. if so, add address to hashtable.
		Iterator<Command> iterator = comlist.iterator();
		while(iterator.hasNext())
		{
			comTemp = iterator.next();
			if(comTemp.getType().equals("A_COMMAND"))
			{
				try
				{
					Integer.parseInt(comTemp.getValue());
				}
				catch(NumberFormatException e)
				{
					strValue = comTemp.getValue();
					if(!hash.containsKey(strValue))
					{
						hash.put(strValue, mem);
						comTemp.setValue(Integer.toString(hash.get(strValue)));
						mem++;
					}
					else
						comTemp.setValue(Integer.toString(hash.get(strValue)));
				}
			}
		}
		System.out.println("post A");
		System.out.println(hash.toString());
		Command[] comarray = new Command[comlist.size()];
		int arrCount = 0;
		//now that you only have the command lines, an array the size of the
		//trimmed arraylist can be created and loaded
		for(Command a : comlist)
		{
			if(a.getType().equals("A_COMMAND") && hash.containsKey(a.getValue()))
				a.setValue(Integer.toString(hash.get(a.getValue())));
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
		int binCount = 0;
		String binTemp;
		for(Command c : comlist)
		{
			if(c.getType().equals("A_COMMAND"))
			{
				binTemp = Integer.toBinaryString(Integer.parseInt(c.getValue()));
				System.out.println(binTemp);
				while(binTemp.length() < 16)
				{
					binTemp = "0" + binTemp;
					// this is what i was using originally
					// but it seems like it shouldn't work even though
					//I know it does
					//binTemp = "0".concat(binTemp);
				}
				binCom[binCount] = binTemp;
				binCount++;
			}
			else if(c.getType().equals("C_COMMAND"))
			{
				binTemp = "111" + c.getComp() + c.getDest() + c.getJump();
				binCom[binCount] = binTemp;
				binCount++;
			}
		}
		
		return binCom;
	}
}
