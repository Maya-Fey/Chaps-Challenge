package nz.ac.vuw.ecs.swen225.a3.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonWriter;

import nz.ac.vuw.ecs.swen225.a3.commons.Contracts;

/**
 * An interface for saving and loading JSON files
 * 
 * @author Claire
 */
public class JsonFileInterface {
	
	/**
	 * Loads a JSON file and returns the root node.
	 * 
	 * @param file The JSON file being loaded. Must exist.
	 * @return The root node. Must not be null.
	 * 
	 * @throws IOException If there is an error reading the file.
	 */
	public static JsonObject loadFromFile(File file) throws IOException
	{
		Contracts.notNull(file, "File must not be null");
		Contracts.existsAndIsFile(file, "File must both exist and be a file.");
		
		JsonReader reader = Json.createReader(new FileInputStream(file));
		
		try {
			return reader.readObject();
		} finally {
			reader.close();
		}
	}
	
	/**
	 * Saves a JSON object to a file.
	 * 
	 * @param file The file that we're saving to
	 * @param obj The root node. Must not be null.
	 * 
	 * @throws IOException If there is an error writing the file.
	 */
	public static void saveToFile(JsonObject obj, File file) throws IOException
	{
		Contracts.notNull(file, "File must not be null");
		Contracts.notNull(obj, "Obj must not be null");
		Contracts.arbitrary(!file.isDirectory(), "File must not be a directory");
		
		file.createNewFile();
		
		JsonWriter writer = Json.createWriter(new FileOutputStream(file));
		
		try {
			writer.write(obj);
		} finally {
			writer.close();
		}
		
	}

}
