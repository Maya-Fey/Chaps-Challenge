package nz.ac.vuw.ecs.swen225.a3.persistence;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import nz.ac.vuw.ecs.swen225.a3.commons.Contracts;
import nz.ac.vuw.ecs.swen225.a3.plugin.ExternalCodeLoader;
import nz.ac.vuw.ecs.swen225.a3.plugin.Level;

/**
 * The interface between the game and the level loader
 * 
 * @author Claire
 */
public class LevelInterface {
	
	private static final LevelInterface instance = new LevelInterface();
	
	/**
	 * @return The canonical LevelInterface instance
	 */
	public static final LevelInterface getInstance()
	{
		return instance;
	}
	
	private List<Level> levels = new ArrayList<>();
	
	/** 
	 * For naming temp files
	 */
	private final Random rand = new Random();
	
	private final Predicate<String> levelPredicate = Pattern.compile("\\Alevel[0-9]+\\.zip\\Z").asPredicate();
	
	private LevelInterface()
	{
		File dir = new File("levels/");
		
		Contracts.arbitrary(dir.isDirectory(), "Levels is not a directory! Unable to load levels.");
		
		File[] files = dir.listFiles();
		Set<File> matches = new HashSet<File>();
		
		for(File file : files) 
			if(levelPredicate.test(file.getName()))
				matches.add(file);
		
		Contracts.arbitrary(matches.size() > 0, "No levels found");
		
		for(File file : matches)
		{			
			try(ZipFile zFile = new ZipFile(file.getAbsolutePath())) {
				JsonObject state = null;
				Set<ExternalCodeLoader> loaders = new HashSet<>();
				
				@SuppressWarnings("unchecked")
				Enumeration<ZipEntry> entries = (Enumeration<ZipEntry>) zFile.entries();
				
				while(entries.hasMoreElements()) {
					ZipEntry entry = entries.nextElement();
					InputStream is = zFile.getInputStream(entry);
					
					if(entry.getName().contains("state.json")) {
						
						JsonReader reader = Json.createReader(is);
						
						try {
							state = reader.readObject();
						} finally {
							reader.close();
						}
						
					} else if(entry.getName().contains("jar")) {
						
						File tmp = File.createTempFile("tmp" + rand.nextInt(10000000), ".jar");
						FileOutputStream stream = new FileOutputStream(tmp);
						
						byte[] buf = new byte[1024];
						int len = 0;
						while((len = is.read(buf, 0, 1024)) > 0)
							stream.write(buf, 0, len);
						
						stream.close();
						
						loaders.add(new ExternalCodeLoader(tmp));
						
					}
					
					is.close();
				}
				
				if(state != null) {
					//TODO: Do something with this
					int levelnum = Integer.parseInt(file.getName().substring(5, file.getName().length() - 4));
					levels.add(new Level(state, loaders));
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Returns the level at the specified index
	 * 
	 * @param num The level number requested
	 * @return The level
	 */
	public Level getLevel(int num)
	{
		return levels.get(num);
	}

}
