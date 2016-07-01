package com.woernerj.dragonsdogma.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.apache.commons.lang3.ArrayUtils;

import com.woernerj.dragonsdogma.bo.CompressionProgressCallback;

public class CompressionUtils {

	public static byte[] compress(final byte[] raw) {
		return compress(raw, new CompressionProgressCallback() { });
	}
	public static byte[] compress(final byte[] raw, final CompressionProgressCallback callback) {
		if (ArrayUtils.isEmpty(raw)) return new byte[0];
		
		Deflater deflater = new Deflater();
		deflater.setInput(raw);
		deflater.finish();
		
		try (ByteArrayOutputStream out = new ByteArrayOutputStream(raw.length)){
			byte[] buffer = new byte[1024];
			while (!deflater.finished()) {
				double perc = (deflater.getBytesRead())/(double)raw.length;
				out.write(buffer, 0, deflater.deflate(buffer));
				callback.update(perc);
			}
			deflater.end();
			return out.toByteArray();
		} catch (IOException e) {
			return null;
		}
	}
	
	public static byte[] decompress(final byte[] compressed) {
		return decompress(compressed, new CompressionProgressCallback() { });
	}
	public static byte[] decompress(final byte[] compressed, final CompressionProgressCallback callback) {
		if (ArrayUtils.isEmpty(compressed)) return new byte[0];
		
		Inflater inflater = new Inflater();
		inflater.setInput(compressed);
		
		try (ByteArrayOutputStream out = new ByteArrayOutputStream(compressed.length)){
			byte[] buffer = new byte[1024];
			while (inflater.getRemaining() > 0) {
				float perc = inflater.getBytesRead()/(float)compressed.length;
				out.write(buffer, 0, inflater.inflate(buffer));
				callback.update(perc);
			}
			inflater.end();
			return out.toByteArray();
		} catch (DataFormatException | IOException e) {
			return null;
		}
	}
}
