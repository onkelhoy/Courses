package model;

import javax.xml.parsers.*;
import org.xml.sax.SAXException;
import java.io.*;
import java.nio.file.*;
import java.nio.charset.Charset;

public class MemberDB 
{
	private File xmlFile;
	private MemberList members;
	
	public MemberDB(MemberList members)
	{
		xmlFile = new File("members.xml");
		this.members = members;
	}
	
	/* Called when program starts to read in members saved in file */
	public void loadDB()
	{
		if (xmlFile.exists())
		{
			process(xmlFile);
		}
	}
	
	/* Called right before program exits to save members in a file.
	 * This method creates an xml-file with information on each member
	 * and boat. */
	public void saveDB()
	{
		FileSystem fileSystem = FileSystems.getDefault();
		Path file = fileSystem.getPath("members.xml");
		try (BufferedWriter fileOut = Files.newBufferedWriter(file,
			Charset.forName("UTF-16")))
		{
			String xmlDoc = "<?xml version=\"1.0\"?>";
			fileOut.write(xmlDoc, 0, xmlDoc.length());
			fileOut.newLine();
			xmlDoc = "<memberdb membercount=\"" + Member.getNumberOfMembers() + "\">";
			fileOut.write(xmlDoc, 0, xmlDoc.length());
			fileOut.newLine();
			
			for (Member m : members)
			{
				xmlDoc = "\t<member name=\"" + m.getName() + "\" persnum=\"" + m.getPersonalNumber() + "\" id=\"" + m.getID() + "\">";
				fileOut.write(xmlDoc, 0, xmlDoc.length());
				fileOut.newLine();
			
				int boats = m.getNumberOfBoats();
				for (int i = 0; i < boats; i++)
				{
					Boat b = m.getBoat(i);
					xmlDoc = "\t\t<boat type=\"" + b.getType() + "\" length=\"" + b.getLength() + "\"/>";
					fileOut.write(xmlDoc, 0, xmlDoc.length());
					fileOut.newLine();
				}
				xmlDoc = "\t</member>";
				fileOut.write(xmlDoc, 0, xmlDoc.length());
				fileOut.newLine();
			}
			xmlDoc = "</memberdb>";
			fileOut.write(xmlDoc, 0, xmlDoc.length());
			fileOut.newLine();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/* Process the saved file. See xmlHandlers class for further details. */
	private void process(File file)
	{
		SAXParserFactory parserFactory = SAXParserFactory.newInstance();
		SAXParser parser = null;
		parserFactory.setNamespaceAware(true);
		parserFactory.setValidating(true);
		
		try
		{
			parser = parserFactory.newSAXParser();
		}
		catch (SAXException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		catch (ParserConfigurationException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		
		xmlHandlers handler = new xmlHandlers(members);
		try
		{
			parser.parse(file, handler);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (SAXException e)
		{
			e.printStackTrace();
		}
	}
}

