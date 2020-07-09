
public class Command
{
	String string, strTemp;
	int line;
	
	public Command(String string , int line)
	{
		this.string = string;
		this.line = line;
	}
	
	public String getString()
	{
		return this.string;
	}
	
	public String getType()
	{
		if(string.startsWith("@"))
			return "A_COMMAND";
		else if (string.startsWith("("))
			return "L_COMMAND";
		else
			return "C_COMMAND";
	}
	
	public String getComp()
	{
		String comp = null;
		if(string.contains("="))
			strTemp = string.substring(string.indexOf("=") + 1);
		else
			strTemp = string.substring(0, string.indexOf(";"));
		
		switch(strTemp)
		{
			case "0":
				comp = "0101010";
				break;
			case "1":
				comp = "0111111";
				break;
			case "-1":
				comp = "0111010";
				break;
			case "D":
				comp = "0001100";
				break;
			case "A":
				comp = "0110000";
				break;
			case "!D":
				comp = "0001101";
				break;
			case "!A":
				comp = "0110001";
				break;
			case "-D":
				comp = "0001111";
				break;
			case "-A":
				comp = "0110011";
				break;
			case "D+1":
				comp = "0011111";
				break;
			case "A+1":
				comp = "0110111";
				break;
			case "D-1":
				comp = "0001110";
				break;
			case "A-1":
				comp = "0110010";
				break;
			case "D+A":
				comp = "0000010";
				break;
			case "D-A":
				comp = "0010011";
				break;
			case "A-D":
				comp = "0000111";
				break;
			case "D&A":
				comp = "0000000";
				break;
			case "D|A":
				comp = "0010101";
				break;
			case "M":
				comp = "1110000";
				break;
			case "!M":
				comp = "1110001";
				break;
			case "-M":
				comp = "1110011";
				break;
			case "M+1":
				comp = "1110111";
				break;
			case "M-1":
				comp = "1110010";
				break;
			case "D+M":
				comp = "1000010";
				break;
			case "D-M":
				comp = "1010011";
				break;
			case "M-D":
				comp = "1000111";
				break;
			case "D&M":
				comp = "1000000";
				break;
			case "D|M":
				comp = "1010101";
				break;
		}
		
		return comp;
	}
	
	public String getDest()
	{
		String dest = null;
		if(string.contains("="))
			strTemp = string.substring(0, string.indexOf("="));
		else
			strTemp = "j_instr";
		
		switch(strTemp)
		{
			case "j_instr":
				dest = "000";
				break;
			case "M":
				dest = "001";
				break;
			case "D":
				dest = "010";
				break;
			case "MD":
				dest = "011";
				break;
			case "A":
				dest = "100";
				break;
			case "AM":
				dest = "101";
				break;
			case "AD":
				dest = "110";
				break;
			case "AMD":
				dest = "111";
				break;
		}
		
		return dest;
	}
	
	public String getJump()
	{
		String jump = null;
		if(string.contains(";"))
			strTemp = string.substring(string.indexOf(";") + 1);
		else
			strTemp = "c_instr";
		//don't forget to switch first and last case back, you are testing why
		//jump bits always print as 111
		switch(strTemp)
		{
			case "c_instr":
				jump = "000";
				break;
			case "JGT":
				jump = "001";
				break;
			case "JEQ":
				jump = "010";
				break;
			case "JGE":
				jump = "011";
				break;
			case "JLT":
				jump = "100";
				break;
			case "JNE":
				jump = "101";
				break;
			case "JLE":
				jump = "110";
				break;
			case "JMP":
				jump = "111";
				break;
		}
		
		return jump;
	}
	
	public String getValue()
	{
		int value = 0;
		String strValue = null;
		boolean var = false;
		
		try
		{
			value = Integer.parseInt(this.string.substring(1));
		}
		catch(NumberFormatException e)
		{
			strValue = this.string.substring(1 , this.string.length());
			var = true;
		}
		
		if(!var)
			strValue = Integer.toBinaryString(value);
		
		while(strValue.length() < 16 && !var)
		{
			strValue = "0".concat(strValue);
		}
		
		return strValue;
	}
	
	public int getLine()
	{
		if(getType().equals("L_COMMAND"))
			return this.line + 1;
		else
			return this.line;
	}
}
