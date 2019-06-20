package catan.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.stream.Stream;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Font;


public class ResourceLoader {

	public static final String RESOURCE_BASE = "/assets";
	public static final boolean PRINT_PROGRESS = false;

	private HashMap<String, Image> img;
	private HashMap<String, Font> fonts;
	private HashMap<String, AudioClip> audio;


	public ResourceLoader() {
		this.init();
	}

	public void init() {
		img = new HashMap<>();
		fonts = new HashMap<>();
		audio = new HashMap<>();
	}

	public void load() {
		if (PRINT_PROGRESS) System.out.println("Loading assets...");


		try {
			URI uri = this.getClass().getResource(RESOURCE_BASE).toURI();
			Path myPath;
			FileSystem fileSystem = null;
			if (uri.getScheme().equals("jar")) {
				try {
					fileSystem = FileSystems.getFileSystem(uri);
				} catch (FileSystemNotFoundException e) {
					fileSystem = FileSystems.newFileSystem(uri, Collections.<String, String>emptyMap());
				}

				myPath = fileSystem.getPath(RESOURCE_BASE);
			} else {
				myPath = Paths.get(uri);
			}

			Stream<Path> walk = Files.walk(myPath);
			for (Iterator<Path> it = walk.iterator(); it.hasNext();) {
				final Path next = it.next();
				final String nextAsset = next.toString().substring(next.toString().indexOf(RESOURCE_BASE));

				if (nextAsset.equals(RESOURCE_BASE)) continue;
				if (Files.isDirectory(next)) continue;

				if (PRINT_PROGRESS) System.out.println("\t" + nextAsset);
				InputStream stream = this.getClass().getResourceAsStream(nextAsset);
				URL url = this.getClass().getResource(nextAsset);
				final String key = nextAsset.substring(RESOURCE_BASE.length() + 1);

				switch (nextAsset.split("/")[2]) {
					case "img":
						img.put(key, this.loadImageFromStream(stream));
						break;
					case "fonts":
						fonts.put(key, this.loadFontFromStream(stream));
						break;
					case "sfx":
					case "music":
						audio.put(key, this.loadAudioClipFromURL(url));
						break;

					default:
						break;
				}

				stream.close();
			}

			walk.close();
			if (fileSystem != null && fileSystem.isOpen()) fileSystem.close();
		} catch (URISyntaxException | IOException e) {
			e.printStackTrace();
		}


		if (PRINT_PROGRESS) System.out.println("\nDone loading assets.");
	}


	private Image loadImageFromStream(InputStream stream) {
		return new Image(stream);
	}

	private Font loadFontFromStream(InputStream stream) {
		return Font.loadFont(stream, 12);
	}

	private AudioClip loadAudioClipFromURL(URL url) {
		return new AudioClip(url.toExternalForm());
	}


	public Image getImage(String name) {
		final Image source = img.get(name);
		WritableImage target = new WritableImage((int) source.getWidth(), (int) source.getHeight());
		target.getPixelWriter().setPixels(0, 0, (int) source.getWidth(), (int) source.getHeight(), source.getPixelReader(), 0, 0);

		return target;
	}

	public Font getFont(String name) {
		final Font source = fonts.get(name);
		Font target = new Font(source.getFamily(), source.getSize());

		return target;
	}

	public AudioClip getAudioClip(String name) {
		final AudioClip source = audio.get(name);
		AudioClip target = new AudioClip(source.getSource());

		return target;
	}

}
