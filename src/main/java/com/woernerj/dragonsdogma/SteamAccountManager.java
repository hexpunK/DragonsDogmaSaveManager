package com.woernerj.dragonsdogma;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.swing.JFileChooser;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.NameFileFilter;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.woernerj.dragonsdogma.bo.types.xml.steamid.SteamIdConversionResponse;
import com.woernerj.dragonsdogma.bo.types.xml.steamid.SteamIdLookupResponse;
import com.woernerj.dragonsdogma.gui.DragonsDogmaSaveManager;

/**
 * Provides a method to find Steam accounts that have save data stored 
 * on this machine, and maps their user ID to the account nickname.
 * 
 * @author Jordan Woerner
 * @version 1.0
 * @since 2016-08-29
 */
public class SteamAccountManager {

	private static final Logger LOG = LogManager.getLogger(SteamAccountManager.class);
	private static final FileFilter FILTER = new NameFileFilter("userdata");
	
	private String steamPath;
	
	/**
	 * Finds the Steam accounts on this machine and maps the user ID (steam3) 
	 * to their nickname for easier identification.
	 * 
	 * @return A {@link Map} of the steam3 ID (as an {@link Integer}) to a 
	 * nickname as a {@link String}.
	 * @since 1.0
	 */
	public Map<String, Integer> getSteamAccounts() {
		Optional<File> steamDir = findSteamFolder();
		if (steamDir.isPresent()) {
			return getSteamUsers(steamDir.get().listFiles(FILTER)[0]);
		}
		steamDir = requestSteamFolder();
		if (steamDir.isPresent()) {
			return getSteamUsers(steamDir.get().listFiles(FILTER)[0]);
		}
		return Collections.emptyMap();
	}
	
	/**
	 * Set the file path (absolute) that contains the Steam user accounts 
	 * and save data discovered.
	 * 
	 * @param steamPath The new Steam file path as a {@link String}.
	 * @since 1.0
	 */
	protected void setSteamPath(String steamPath) {
		this.steamPath = steamPath;
	}
	/**
	 * Gets the path used by this instance of {@link SteamAccountManager} that 
	 * was used to get the Steam accounts on this machine and any save data 
	 * folders.
	 * 
	 * @return The absolute file path of the Steam installation as a {@link 
	 * String}.
	 * @since 1.0 
	 */
	public String getSteamPath() {
		return this.steamPath;
	}
	
	private Optional<File> findSteamFolder() {
		Preferences prefs = Preferences.userNodeForPackage(DragonsDogmaSaveManager.class);
		this.steamPath = prefs.get("steamlocation", null);
		 
		if (StringUtils.isBlank(steamPath)) {
			ProcessBuilder builder = new ProcessBuilder("reg", "query", "HKEY_CURRENT_USER\\SOFTWARE\\Valve\\Steam");
			Process proc = null;
			Optional<String> str = Optional.empty();
			try {
				proc = builder.start();
				try (BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()))) {
					 str = reader.lines()
							 .filter(l -> l.contains("SteamPath"))
							 .findFirst();
				}
			} catch (IOException e) {
				LOG.error("Error reading output of REG", e);
				return Optional.empty();
			} finally {
				if (proc != null) {
					try {
						proc.waitFor();
					} catch (InterruptedException e) {
						LOG.error("REG process was interrupted", e);
						proc = null;
					}
				}
			}
			if (str.isPresent()) {
				String regStr = "REG_SZ";
				int regPos = str.get().indexOf(regStr)+regStr.length();
				this.steamPath = str.get().substring(regPos).trim();
			}
		}

		try {
			prefs.put("steamlocation", this.steamPath);
			prefs.flush();
		} catch (BackingStoreException e) {
			LOG.warn("Failed to save perferences", e);
		}
		return Optional.ofNullable(new File(this.steamPath));
	}
	
	private Optional<File> requestSteamFolder() {
		JFileChooser filePicker = new JFileChooser();
		filePicker.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		filePicker.setDialogTitle("Select Steam Install Location");
		filePicker.setMultiSelectionEnabled(false);
		filePicker.showOpenDialog(null);

		return Optional.ofNullable(filePicker.getSelectedFile());
	}
	
	private Map<String, Integer> getSteamUsers(final File steamDir) {
		if (!steamDir.exists()) {
			// Break out early if we don't have the right directory.
			return Collections.emptyMap();
		}
		
		final String[] userFolders = steamDir.list(DirectoryFileFilter.DIRECTORY);
		final Preferences prefs = Preferences.userNodeForPackage(DragonsDogmaSaveManager.class);
		final Preferences accountNodeprefs = prefs.node("accounts");
		
		Map<String, Integer> userAccounts = new HashMap<>();
		for (String userId : userFolders) {
			try {
				final Integer userIdInt = Integer.valueOf(userId);
				String nickName = accountNodeprefs.get(userId, null);
				if (nickName == null) {
					// No cache for this user, find it and cache it.
					performNicknameLookup(userId).ifPresent(name -> {
						userAccounts.put(name, userIdInt);
						accountNodeprefs.put(userId, name);
					});
				} else {
					// Cached in Preferences. Noice.
					userAccounts.put(nickName, userIdInt);
				}
			} catch (NumberFormatException ex) {
				continue; // Not even a user ID.
			}
		}
		
		try {
			prefs.flush();
		} catch (BackingStoreException e) {
			LOG.error("Failed to store preferences", e);
		}
		return userAccounts;
	}
	
	private Optional<String> performNicknameLookup(final String userId) {
		URIBuilder builder = new URIBuilder();
		
		try (CloseableHttpClient client = HttpClients.createDefault()) {
			HttpGet getClient = new HttpGet("https://gist.githubusercontent.com/hexpunK/b135ca9d0a919117e8e46d7a0f68e7cf/raw/10517f1bce551849064670b7f8b0da4635936f1b/DragonsDogmaSaveManagerAPI");
			
			String apiKey = null;
			try (CloseableHttpResponse resp = client.execute(getClient)) {
				if (resp.getStatusLine().getStatusCode() != HttpStatus.SC_OK) return Optional.empty();
				try (BufferedReader rd = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()))) {
					apiKey = rd.readLine();
				}
			}
			if (apiKey == null) {
				LOG.error("Could not retrieve API key");
				return Optional.empty();
			}
			
			builder.setScheme("https")
				.setHost("steamid.eu")
				.setPath("/api/convert.php")
				.setParameter("input", String.format("[U:1:%s]", userId));
			Optional<SteamIdConversionResponse> conversion = performApiRequest(SteamIdConversionResponse.class, builder.build());
			
			if (conversion.isPresent()) {
				if (conversion.get().getError() == null) {
					builder.setScheme("https")
						.setHost("steamid.eu")
						.setPath("/api/request.php")
						.setParameter("player", conversion.get().getConversions().get(0).getSteamId64())
						.setParameter("api", apiKey);
					Optional<SteamIdLookupResponse> lookup = performApiRequest(SteamIdLookupResponse.class, builder.build());
					if (lookup.isPresent()) {
						if (lookup.get().getError() == null) {
							return Optional.of(lookup.get().getProfile().getName());
						}
						LOG.warn("API error", lookup.get().getError().getErrorMessage());
					}
				}
				LOG.warn("API error", conversion.get().getError().getErrorMessage());
			}
		} catch (IOException e) {
			LOG.error("", e);
		} catch (URISyntaxException e) {
			LOG.error("Could not construct API URI", e);
		}
		return Optional.empty();
	}
	
	@SuppressWarnings("unchecked")
	private <T> Optional<T> performApiRequest(Class<T> outputClass, URI uri) {
		try (CloseableHttpClient client = HttpClients.createDefault()) {
			HttpGet getClient = new HttpGet(uri);
			try (CloseableHttpResponse resp = client.execute(getClient)) {
				if (resp.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
					LOG.warn("HTTP error", resp.getStatusLine().getReasonPhrase());
					return Optional.empty();
				}
				try {
					JAXBContext context = JAXBContext.newInstance(outputClass);
					Unmarshaller marshaller = context.createUnmarshaller();
					return Optional.ofNullable((T)marshaller.unmarshal(resp.getEntity().getContent()));
				} catch (JAXBException e) {
					LOG.error("Failed to parse SteamID API response", e);
				}
			}
		} catch (IOException e) {
			LOG.error("Could not communicate with Steam ID API", e);
		}
		return Optional.empty();
	}
}
